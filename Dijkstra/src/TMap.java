import java.awt.Color;
import java.awt.Dimension;
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

public class TMap extends JPanel{

	//***VARIABLES***
	private final int IMG_WIDTH = 700, IMG_HEIGHT = 700;
	private final int BORDER = 40;
	private String GRAY = "#9a9b9b";
	
	ArrayList<String> inputs = new ArrayList<String>();
	LocationGraph<String> vtx = new LocationGraph<String>();
	Image map;
	
	
	//***CONSTRUCTOR***
	public TMap() throws IOException {
		
		//load the image
		map = ImageIO.read(new File("final.JPEG"));
		
		//build everything
		buildGraph();
		System.out.println(vtx.vertices);
		
		
		
		//UI STUFF
		
			//main panel
			JPanel mainPanel = new JPanel();
			BoxLayout boxlayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
			mainPanel.setLayout(boxlayout);
			mainPanel.setBackground(Color.decode(GRAY));
			mainPanel.setBorder(BorderFactory.createEmptyBorder(20,20, 20, 20));
		
			//image panel (which is now class)
			
			/*
			this.addMouseListener(new MouseListener() {

				public void mousePressed(MouseEvent e) {
					
					//get the inputs
					int currX = e.getX();
					int currY = e.getY(); 
					String currName = (String)JOptionPane.showInputDialog(null, "What is the name of this Vertex", "Input",
							JOptionPane.PLAIN_MESSAGE, null, null, null);
					
					//add to one long string in correct format
					String curr = Integer.toString(currX) + "~" + Integer.toString(currY) + "~" + currName + "\n";					
					
					//add to array list
					inputs.add(curr);
					
					
				}
				public void mouseClicked(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
			*/
			mainPanel.add(this);
			
			
			JButton temp = new JButton("class info");
			temp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					System.out.println(inputs);
					
					/*
					try {
						writeTextFile();
					} catch (IOException e1) {
						e1.printStackTrace();
						System.out.println("Could not build Text File");
					}
					*/
					
				}
				
			});
			mainPanel.add(temp);
			
			
			
			//main container
			JFrame frame = new JFrame();
			frame.setSize(IMG_WIDTH + BORDER + 500, IMG_HEIGHT + BORDER);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.add(mainPanel);
			frame.setLocationRelativeTo(null);
			frame.setResizable(false);
			frame.setVisible(true);
			this.setFocusable(true);
			
			//repaint
			frame.repaint();

	}
	
	//***GRAPHICS METHODS***
		public void paint(Graphics g) {
			g.drawImage(map, 20, 20, IMG_WIDTH, IMG_HEIGHT, null);
			drawVertices(g);
		}
		
		public void drawVertices(Graphics g) {
			
			for(LocationGraph<String>.Vertex v : vtx.vertices.values()) {	
				int[] arr = v.getLoc();
				drawCenteredCircle(g, arr[0], arr[1], 10);
			}
		}
		
		
		public void drawCenteredCircle(Graphics g, int x, int y, int r) {
			x = x-(r/2);
			y = y-(r/2);
			g.setColor(Color.BLACK);
			g.fillOval(x,y,r,r);
		}
	
	//***MISC METHODS***
	public void writeTextFile() throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter("vertex_textFile"));
		
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
	
	
	
	//***MAIN METHOD***
	public static void main(String args[]) throws IOException {
		new TMap();
	}
	
	
	
}
