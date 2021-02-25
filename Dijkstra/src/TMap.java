import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TMap {

	//***VARIABLES***
	private final int IMG_WIDTH = 700, IMG_HEIGHT = 700;
	private String GRAY = "#9a9b9b";
	
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
			//picLabel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
			panel.add(picLabel);
			
			//main container
			JFrame frame = new JFrame();
			frame.setSize(IMG_WIDTH + 40, IMG_HEIGHT + 70);
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
