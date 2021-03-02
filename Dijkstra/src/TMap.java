import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TMap {

	//***VARIABLES***
	private final int IMG_WIDTH = 700, IMG_HEIGHT = 700;
	private final int BORDERX = 40, BORDERY = 70;
	private String GRAY = "#9a9b9b";
	
	ArrayList<String> inputs = new ArrayList<String>();
	
	//***CONSTRUCTOR***
	public TMap() throws IOException {
		
		
		//UI STUFF
		
			//main panel
			JPanel panel = new JPanel();
			BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
			panel.setLayout(boxlayout);
			panel.setBorder(BorderFactory.createEmptyBorder(20,20, 20, 20));
			panel.setBackground(Color.decode(GRAY));
			
			//image label
			BufferedImage mapPic = ImageIO.read(new File("map.JPEG"));
			JLabel picLabel = new JLabel(new ImageIcon(mapPic));
			picLabel.addMouseListener(new MouseListener() {


				public void mouseClicked(MouseEvent e) {
					
					//get the inputs
					int currX = e.getX();
					int currY = e.getY(); 
					String currName = (String)JOptionPane.showInputDialog(panel, "What is the name of this Vertex", "Input",
							JOptionPane.PLAIN_MESSAGE, null, null, null);
					
					//add to one long string in correct format
					String curr = Integer.toString(currX) + "~" + Integer.toString(currY) + "~" + currName;					
					
					//add to array list
					inputs.add(curr);
			
				}

				
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
				
			});
			
			panel.add(picLabel);
			
			//main container
			JFrame frame = new JFrame();
			frame.setSize(IMG_WIDTH + BORDERX + 500, IMG_HEIGHT + BORDERY);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.add(panel);
			frame.setLocationRelativeTo(null);
			frame.setResizable(false);
			frame.setVisible(true);
			panel.setFocusable(true);
			
	}
	
	//***MAIN METHOD***
	public static void main(String args[]) throws IOException {
		new TMap();
	}
	
	
	
}
