/*
 * comp6442 Assignment 1 - Personal Organiser
 * Author: Guyue HU, u5608260
 * ANU
 * 
 */

import   java.awt.*; 

import   javax.swing.Icon; 
import   javax.swing.ImageIcon; 
import   javax.swing.JFrame; 
import   javax.swing.JLabel;
import   javax.swing.JButton; 

import java.awt.geom.Ellipse2D;
import   java.awt.image.*;

public class ScaleIcon implements Icon {
	/*
	 * This class make the Icon into proper size
	 * reference: http://bbs.csdn.net/topics/370105795
	 */
	private BufferedImage i = null;
	private Icon icon = null;
	
	public ScaleIcon(Icon icon) {
		this.icon = icon;
	}
	
	public int getIconHeight() {
		return icon.getIconHeight();
	}
	
	public int getIconWidth() {
		return icon.getIconWidth();
	}
	
	public void paintIcon(Component c, Graphics g, int x, int y) {
		//float width = c.getWidth();
		//float height = c.getHeight();
		float width = 100;
		float height = 100;
		float iconWidth = icon.getIconWidth();
		float iconHeight = icon.getIconHeight();
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,   
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		//System.out.println("Width:" + iconWidth + "    Height:" + iconHeight);
		
		// rescale the image
		g2d.scale(width/iconWidth, height/iconHeight);
		// make the photo circle
		Ellipse2D.Double circle = new Ellipse2D.Double(5, 0, icon.getIconWidth() * 0.95, icon.getIconHeight() * 0.95);
		g2d.setClip(circle);
		icon.paintIcon(c, g2d, 0, 0);
		g2d.setClip(null);  
		
	}


}