import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class LocationGraph<E> {
	
	//***VARIABLES***
	HashMap<E, Vertex> vertices;
	
	//***CLASSES***
	public class Vertex {
		
		//variables
		int xLoc, yLoc; //THIS IS NOT AN ARRAY INSIDE THIS CLASS
		E info; 
		int dist;
		HashSet<Edge> edges; 
		
		//constructor
		public Vertex(E info, int xLoc, int yLoc) {
			this.info = info;
			this.xLoc = xLoc;
			this.yLoc = yLoc;
			this.dist = Integer.MAX_VALUE; //inital dist always infinity
			edges = new HashSet<Edge>();
		}
		
		//accessor methods
		public int[] getLoc() {
			int[] point = {xLoc, yLoc};
			return point;
		}
		
		public void setDist(int d) {
			dist = d;
		}
	
	}
	
	public class Edge {
		
		//variables
		int length; 
		Vertex v1, v2; 
		
		//constructor
		public Edge(int length, Vertex v1, Vertex v2) {
			this.length = length; 
			this.v1 = v1; 
			this.v2 = v2;
		}
		
		//methods
		public Vertex getNeighbor(Vertex v) {
			if (v.info.equals(v1.info)) {
				return v2;
			}
			return v1;
		}
	
	}
	
	public class PriorityQueue {
		
		//variables
		public ArrayList<Vertex> queue = new ArrayList<Vertex>();
		
		//methods
		public Vertex pop() {
			return queue.remove(0);
		}
		
		public void put(Vertex v) {
			
			//check if list is empty
			if(queue.size() == 0) {
				queue.add(v);
			}
			//check if node already exists
			else if(queue.contains(v)) {
				int index = queue.indexOf(v);
				
				//if new vertex has smaller dist
				if(queue.get(index).dist > v.dist) {
					queue.remove(queue.get(index));
					queue.add(v);
				}
			}
			
			//check if highest priority (smallest dist)
			else if(queue.get(0).dist > v.dist) {
				queue.add(0, v);
			}
			
			//check if lowest priority (biggest dist)
			else if(queue.get(queue.size()-1).dist < v.dist) {
				queue.add(v);
			}
			
			//binary search for remainder
			else {
				int start = 0;
				int end = queue.size()-1;
				
				while(start < end) {
					//get midpoint
					Vertex midpoint = queue.get((start+end)/2);
					
					//reset start and end based on midpoint priority
					if(midpoint.dist < v.dist) {
						start = (start+end)/2 + 1;
					} else {
						end = (start+end)/2;
					}
				}
				
				//add it to that point
				queue.add(start, v);
			}
		}
		
		public int size() {
			return queue.size();
		}
		
	}
	
	//***CONSTRUCTOR***
	public LocationGraph() {
		vertices = new HashMap<E, Vertex>();
	}
	
	//***SMALLER METHODS***
	public void addVertex(E info, int xLoc, int yLoc) {
		vertices.put(info, new Vertex(info, xLoc, yLoc));
	}
	
	public int getLength(Vertex v1, Vertex v2) {
		
		return (int)(Math.sqrt( (v2.yLoc - v1.yLoc) * (v2.yLoc - v1.yLoc) + (v2.xLoc - v1.xLoc) + (v2.xLoc - v1.xLoc)));

	}
	
	public void connect(E info1, E info2) {
		
		//initialize vertices
		Vertex v1 = vertices.get(info1);
		Vertex v2 = vertices.get(info2);
		
		//make the edge
		Edge e = new Edge(getLength(v1,v2), v1, v2);
		
		//add the edge to the vertices
		v1.edges.add(e);
		v2.edges.add(e);
		
	}
	
	//***ALGORITHM TINGS***
	public ArrayList<Object> dijkstra(Vertex start, Vertex target) {
		
		//variables
		PriorityQueue toVisit = new PriorityQueue();
		HashSet<Vertex> visited = new HashSet<Vertex>();
		HashMap<Vertex, Edge> leadsTo = new HashMap<Vertex,Edge>();
		
		//iterate through list and set all distances to infinity
		Iterator vIt = vertices.entrySet().iterator();
		while(vIt.hasNext()) {
			//access the actual vertex
			HashMap.Entry mapElement = (HashMap.Entry)vIt.next();
			Vertex v = (Vertex) mapElement.getValue();
			v.setDist(Integer.MAX_VALUE);
		}
		
		//set start dist to 0 and add to toVisit
		start.setDist(0);
		toVisit.put(start);
		
		//until itself target is the shortest 
		while(toVisit.size() != 0) {
			
			//get first thing
			Vertex curr = toVisit.pop();
			
			//check if its target
			if(curr == target) {
				// CALL BACKTRACE
				return backtrace(target, leadsTo);
			} else {
				
				//iterate through all unvisited neighbors
				for(Edge e : curr.edges) {
					
					Vertex neighbor = e.getNeighbor(curr);
					
					//check if visited
					if(visited.contains(neighbor.info)) { continue; }
					
					// calculate new total dist
					neighbor.setDist( curr.dist + e.length);
					
					//check if new dist < map dist
					if(neighbor.dist < vertices.get(neighbor.info).dist) {
						
						//update map dist
						vertices.get(neighbor.info).setDist(neighbor.dist);
						//add to queue
						toVisit.put(neighbor);
						//add to leadsTo
						leadsTo.put(neighbor, e);	
					}
				}
				
				//mark curr as visited
				visited.add(curr);
			}
			
		}
		
		return null;
		
	}
	
	public ArrayList<Object> backtrace(Vertex target, HashMap<Vertex, Edge> leadsTo) {
		
		Vertex curr = target;
		ArrayList<Object> path = new ArrayList<Object>();
	
		while (leadsTo.get(curr) != null) {
			path.add(0, curr.info);
			path.add(0, leadsTo.get(curr).length);
			curr = leadsTo.get(curr).getNeighbor(curr);
		}
		
		path.add(0, curr.info);
		return path;
	
	}
	
	
	
	

}
