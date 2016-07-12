/*
 * comp6442 Assignment 1 - Personal Organiser
 * Author: Guyue HU, u5608260
 * ANU
 * 
 */

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ImagePane extends JPanel { 
	
	/**
	 * This class creats a JPanel with background image
	 * reference: chensiyu04 
	 * www.blogjava.net/chensiyu04
	 */
	
	private static final long serialVersionUID = 1L;
    
	private Image image;
    
    public ImagePane(String filename) {
        try {
            image = ImageIO.read(new File(filename));
        } catch (IOException ex) {
            ex.printStackTrace(); 
        }
    }

    //Override the Jpanel to paint backgound image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
    
}