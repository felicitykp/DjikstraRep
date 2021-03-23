import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

public class TMap extends JPanel{

	//***VARIABLES***
	private final int IMG_WIDTH = 700, IMG_HEIGHT = 700;
	private final int BORDER = 40;
	private final int PANEL_WIDTH = 450;
	private String GRAY = "#939494", WHITE = "#fffcf7", BLACK = "#231f20", DGRAY = "#605f60", PURP = "#a790c4";
	private final int CIRCLEW = 10;
	
	
	ArrayList<String> inputs = new ArrayList<String>();
	boolean v1 = false;
	String v1info, v2info;
	ArrayList<String> reqStops = new ArrayList<String>();
	String mainText = "";
	
	
	LocationGraph<String> vtx = new LocationGraph<String>();
	Image map;
	
	
	//***CONSTRUCTOR***
	public TMap() throws IOException {
		
		//load the image
		map = ImageIO.read(new File("final.JPEG"));
		
		//build everything
		buildGraph();
		connectVert();
		
		//UI STUFF
		
			//main panel
			JPanel mainPanel = new JPanel();
			BoxLayout boxlayout = new BoxLayout(mainPanel, BoxLayout.X_AXIS);
			mainPanel.setLayout(boxlayout);
			mainPanel.setBackground(Color.decode(GRAY));
			mainPanel.setBorder(BorderFactory.createEmptyBorder(9, 9, 25, 30));
			
			//image panel
			this.setPreferredSize(new Dimension( IMG_WIDTH, IMG_HEIGHT));
			this.setBackground(Color.decode(GRAY));
			this.addMouseListener(new MouseListener() {

				//MOUSE LISTENER TO LOAD THE ORIGINAL VERTICES
				public void mousePressed(MouseEvent e) {
					
					if(v1 == false) {
						if(isOn(e.getX(), e.getY()) != null) {
							LocationGraph<String>.Vertex v = isOn(e.getX(), e.getY());
							v1 = true;
							v1info = v.info;
							
						}
					} else {
						if(isOn(e.getX(), e.getY()) != null) {
							LocationGraph<String>.Vertex v = isOn(e.getX(), e.getY());
							v2info = v.info;
							
						}
					}
					repaint();
					
					//####INITAL GRAPH BUILDER####
					
					/*
					int currX = e.getX();
					int currY = e.getY(); 
					String currName = (String)JOptionPane.showInputDialog(null, "What is the name of this Vertex", "Input",
							JOptionPane.PLAIN_MESSAGE, null, null, null);
					String curr = Integer.toString(currX) + "~" + Integer.toString(currY) + "~" + currName + "\n";					
					inputs.add(curr);
					*/
					
					//####INTIAL EDGE BUILDER####
					
					/*
					if(v1 == false) {
						if(isOn(e.getX(), e.getY()) != null) {
							LocationGraph<String>.Vertex v = isOn(e.getX(), e.getY());
							v1 = true;
							v1info = v.info;
							int[] arr = v.getLoc();
						}
					} else {
						if(isOn(e.getX(), e.getY()) != null) {
							LocationGraph<String>.Vertex v = isOn(e.getX(), e.getY());
							v2info = v.info;
							int[] arr = v.getLoc();
						}
					}
					repaint();
					*/
					
				}
				public void mouseClicked(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
			mainPanel.add(this);
			
			//SIDE PANEL
			JPanel sidePanel = new JPanel();
			sidePanel.setBackground(Color.decode(GRAY));
			sidePanel.setPreferredSize(new Dimension(400, IMG_HEIGHT-400));
			mainPanel.add(sidePanel);
			
				sidePanel.add(Box.createRigidArea(new Dimension(PANEL_WIDTH, 10))); //SPACING
			
				//title
				JLabel title = new JLabel("Boston T Helper", SwingConstants.CENTER);
				title.setPreferredSize(new Dimension(PANEL_WIDTH / 2, 50));
				title.setOpaque(true);
				title.setFont(new Font("ARIAL", Font.BOLD, 20));
				title.setForeground(Color.decode(WHITE));
				title.setBackground(Color.decode(BLACK));
				sidePanel.add(title);
				
				sidePanel.add(Box.createRigidArea(new Dimension(PANEL_WIDTH, 7))); //SPACING
				
				//output text
				JTextArea textArea = new JTextArea();
				textArea.setForeground(Color.decode(BLACK));
				textArea.setBackground(Color.decode(WHITE));
				textArea.setPreferredSize(new Dimension(PANEL_WIDTH, IMG_HEIGHT - 30));
				textArea.setEditable(false);
				textArea.setText(mainText);
				textArea.setFont(new Font("ARIAL", Font.PLAIN, 13));
				textArea.setBorder(BorderFactory.createEmptyBorder(20,20, 20, 20));
				//sidePanel.add(textArea);
				
				JScrollPane scrolly = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				scrolly.setPreferredSize(new Dimension(PANEL_WIDTH, IMG_HEIGHT - 170));
				sidePanel.add(scrolly);
				
				
				sidePanel.add(Box.createRigidArea(new Dimension(PANEL_WIDTH, 7))); //SPACING
				
				//run button
				JButton run = new JButton("Find the Path");
				run.setBackground(Color.decode(BLACK));
				run.setForeground(Color.decode(WHITE));
				run.setFont(new Font("ARIAL", Font.BOLD, 15));
				run.setPreferredSize(new Dimension(PANEL_WIDTH / 3, 40));
				run.setOpaque(true);
				run.setBorderPainted(false);
				run.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						mainText = "";
						runDijkstra();
						textArea.setText(mainText);
						repaint();
					}
					
				});
				sidePanel.add(run);
				
				sidePanel.add(Box.createRigidArea(new Dimension(10, 40))); //SPACING
				
				//reset button
				JButton reset = new JButton("Reset");
				reset.setBackground(Color.decode(BLACK));
				reset.setForeground(Color.decode(WHITE));
				reset.setFont(new Font("ARIAL", Font.BOLD, 15));
				reset.setPreferredSize(new Dimension(PANEL_WIDTH / 3, 40));
				reset.setOpaque(true);
				reset.setBorderPainted(false);
				reset.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						v1 = false;
						v1info = null;
						v2info = null;
						mainText = "";
						textArea.setText(mainText);
						repaint();
					}
				});
				sidePanel.add(reset);
			
			//BUILDER BUTTON - DISABLED
			/*
			JButton temp = new JButton("temp button");
			temp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					//####EDGES####
					System.out.println(v1info + "~" + v2info);
					int[]arr1 = vtx.vertices.get(v1info).getLoc();
					int[]arr2 = vtx.vertices.get(v2info).getLoc();
					v1 = false;
					v1info = null;
					v2info = null;
					repaint();
					
					
					//####VERTICES####
					System.out.println(inputs);
					try {
						writeTextFile("");
					} catch (IOException e1) {
						e1.printStackTrace();
						System.out.println("Could not build Text File");
					}
				}
			});
			mainPanel.add(temp);
			*/
			
			//main container
			JFrame frame = new JFrame();
			frame.setSize(IMG_WIDTH + BORDER + 500, IMG_HEIGHT + BORDER);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.add(mainPanel);
			frame.setLocationRelativeTo(null);
			frame.setResizable(false);
			frame.setVisible(true);
			frame.setFocusable(true);
			
			//repaint
			frame.repaint();

	}
	
	//***GRAPHICS METHODS***
	public void paint(Graphics g) {
		g.drawImage(map, 20, 20, IMG_WIDTH, IMG_HEIGHT, null);
		drawVertices(g);
		
		//highlight selected vertices
		if(v1info != null) {
			int[] arr = vtx.vertices.get(v1info).getLoc();
			drawCenteredCircle(g, arr[0], arr[1], CIRCLEW, Color.decode(BLACK));
		}
		if(v2info != null) {
			int[] arr = vtx.vertices.get(v2info).getLoc();
			drawCenteredCircle(g, arr[0], arr[1], CIRCLEW, Color.decode(BLACK));
		}
		if(reqStops.size() != 0) {
			for(int i = 0; i < reqStops.size(); i++) {
				int[] arr = vtx.vertices.get(reqStops.get(i)).getLoc();
				drawCenteredCircle(g, arr[0], arr[1], CIRCLEW, Color.decode(BLACK));
			}
		}
		
		
	}
	
	public void drawVertices(Graphics g) {
		for(LocationGraph<String>.Vertex v : vtx.vertices.values()) {	
			int[] arr = v.getLoc();
			drawCenteredCircle(g, arr[0], arr[1], CIRCLEW, Color.decode(DGRAY));
		}
	}
	
	public void drawCenteredCircle(Graphics g, int x, int y, int r, Color color) {
		x = x-(r/2);
		y = y-(r/2);
		g.setColor(color);
		g.fillOval(x,y,r,r);
	}
	
	public LocationGraph<String>.Vertex isOn(int xCurr, int yCurr) {
		for(LocationGraph<String>.Vertex v : vtx.vertices.values()) {
			int[] arr = v.getLoc();
			int dist = calcDist(arr[0], arr[1], xCurr, yCurr);
			if(dist < (CIRCLEW / 2)) {
				return v;
			}
		}
		return null;
	}
			
	
	//***MISC METHODS***
	public void writeTextFile(String name) throws IOException { 
		BufferedWriter bw = new BufferedWriter(new FileWriter(name));
		
		for(int i = 0; i < inputs.size(); i++) {
			bw.write(inputs.get(i));
		}
		
		bw.close();
	}
	
	public void buildGraph() throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader("vertex_textFile"));
		
		//run through each line
		for(String line = br.readLine(); line != null; line = br.readLine()) {
			
			//split the lines
			String[] arr = line.split("~");
			
			//add to graph
			vtx.addVertex(arr[2], Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
		}
		br.close();
	}
	
	public void connectVert() throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader("edge_textFile"));
		
		//run through each line
				for(String line = br.readLine(); line != null; line = br.readLine()) {
					
					//split the lines
					String[] arr = line.split("~");
					
					//add to graph
					vtx.connect(arr[0], arr[1]);
				}
		
		br.close();
		
	}
	
	public int calcDist(int vX1, int vY1, int vX2, int vY2) {
		return (int)(Math.sqrt( (vY2 - vY1) * (vY2 - vY1) + (vX2 - vX1) * (vX2 - vX1)));
	}
	
	public void runDijkstra() {
		
		//reset
		mainText = "";
		reqStops.clear();
		
		//check for valid inputs
		if(v1info == null || v2info == null) {
			mainText = "Error: Please select two locations";
		} 
		
		//run
		else {
			
			ArrayList<Object> path = vtx.dijkstra(v1info, v2info);
	//		System.out.println(path);
			mainText = "Here is the stops you want to take:\n\n";
			for(int i = 0; i < path.size(); i+=2) {
				mainText += path.get(i) +"\n";
				reqStops.add((String) path.get(i));
			}
		}
	}
	
	
	
	//***MAIN METHOD***
	public static void main(String args[]) throws IOException {
		new TMap();
	}
	
	
	
}
