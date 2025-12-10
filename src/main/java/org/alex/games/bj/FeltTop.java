package org.alex.games.bj;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.FilteredImageSource;
import java.awt.image.CropImageFilter;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.awt.Insets;


public class FeltTop extends JPanel {

	private ImageIcon deckOfCardsImage = null;
	private Image image = null;
	
	private Insets inserts;
	/**
	 * 
	 */
	private static final long serialVersionUID = -8050655045065678999L;

	public FeltTop(){
		
		deckOfCardsImage = new ImageIcon("resources/deck-of-cards.jpg");
		image = deckOfCardsImage.getImage();
		image = createImage(new FilteredImageSource(image.getSource(), new CropImageFilter(30, 30, 100, 124)));
		
	}
	
	public void paint(Graphics g){
		super.paint(g);
		//http://www.java2s.com/Code/Java/2D-Graphics-GUI/Imagecrop.htm
		//g.drawImage(image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer)
	}
	
}
