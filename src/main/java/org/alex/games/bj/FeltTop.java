package org.alex.games.bj;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.alex.games.bj.beans.CARDLETTER;
import org.alex.games.bj.beans.SUITS;


public class FeltTop extends JPanel {

	private BufferedImage image = null;

	// the file is src/java/resources/deck-of-cards.jpg
	// Use a leading slash to indicate the path starts from the root of the classpath
	public static final String DECK_OF_CARDS = "/English_pattern_playing_cards_deck.png"; 
	/**
	 * 
	 */
	private static final long serialVersionUID = -8050655045065678999L;

	public FeltTop(String resourcePath){
		try (InputStream is = FeltTop.class.getResourceAsStream(resourcePath)) {
			if (is == null) {
				throw new RuntimeException("Resource not found: " + resourcePath);
			}
			image = ImageIO.read(is);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Error reading image: input stream was null. Check if the file exists and is in the classpath.", e);
		}

	}

	public static BufferedImage cropImage(BufferedImage originalImage, int x, int y, int width, int height) {
		// Check if the requested region is within the original image bounds (omitted for brevity)
		// The getSubimage method handles boundary checks internally by throwing an exception

		return originalImage.getSubimage(x, y, width, height);
	}

	public void paint(Graphics g){
		super.paint(g);
		//g.drawImage(image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer)
	}

	public static void main(String[] args) throws IOException {
		FeltTop f = new FeltTop(DECK_OF_CARDS);
		//f.getExtents(f.image);
		// Detect edges
		//BufferedImage edgesImage = detectEdges(f.image);
		//f.getExtents(edgesImage);
		
		// Save the resulting edge image
		//ImageIO.write(edgesImage, "png", new File("edges.png"));
		//System.out.println("Edge detection complete. Result saved to edges.png");

		f.cutImageCards();
		
	}

	
	private void cutImageCards() throws IOException {
		int startX = 21;
		int startY = 21;
		int cardWidth = 273;
		int cardHeigth = 409;
		
		int[] columns = new int[] {
				21, //A
				315, //2
				608,
				902,
				1196,
				1489,
				1783,
				2077, //8
				2370,
				2664, //10
				2958,
				3251,
				3545 //K
		};
		int[] rows = new int[] {
				21,
				450,
				879,
				1309
		};
		int row = 0;
		for (SUITS s : SUITS.values()) {
			int column = 0;
			for (CARDLETTER c : CARDLETTER.values()) {
				BufferedImage cardImage = cropImage(this.image, columns[column], rows[row], cardWidth, cardHeigth);
				ImageIO.write(cardImage, "png", new File(s.name() + "_" + c.name() + ".png"));
				column ++;
			}
			row ++;
		}
		
		//draw some lines to help find the correct sizes
		 Graphics2D g2d = image.createGraphics();
		 g2d.setColor(Color.YELLOW);
		 for (int x = 0; x < columns.length; x++) {
			 g2d.drawLine(columns[x], startY, columns[x], startY + 50);
			 g2d.drawLine(columns[x] + cardWidth, startY, columns[x] + cardWidth, startY + 50);
		 }
		 
		 for (int y = 0; y < rows.length; y++ ) {
			 g2d.drawLine(startX , rows[y], startX + 50, rows[y]);
			 g2d.drawLine(startX, rows[y] + cardHeigth, startX + 50, rows[y] + cardHeigth);
		 }
		
		 ImageIO.write(image, "png", new File("aGuides.png"));
	}

	public static BufferedImage detectEdges(BufferedImage originalImage) {
		// Define a basic edge detection kernel (e.g., Laplacian-like)
		float[] edgeDetectionKernel = { 0.0f, -1.0f, 0.0f, -1.0f, 4.0f, -1.0f, 0.0f, -1.0f, 0.0f };

		// Create a Kernel object
		Kernel kernel = new Kernel(3, 3, edgeDetectionKernel);

		// Create a ConvolveOp filter
		// EDGE_NO_OP means pixels at the edge of the image are not processed
		ConvolveOp convolveOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

		// Create a destination image for the result
		BufferedImage edgesImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(),
				originalImage.getType());

		// Apply the filter
		convolveOp.filter(originalImage, edgesImage);

		return edgesImage;
	}

	private void getExtents(BufferedImage image) {
		System.out.println("Image is wide=" + image.getWidth() + " height=" + image.getHeight() );

		int lastRow = 0;
		for (int y=0; y < 23; y++) { //image.getHeight(); y++) {
			int rowSum = 0;
			boolean rowIsEmpty = true;
			for (int x=0; x < image.getWidth(); x++) {
				int pixel = image.getRGB(x, y);
				//rowSum += pixel;
				// Use bitwise operations for performance (more efficient)
				// Use unsigned right shift
				//int alpha = (pixel >> 24) & 0xff;  //alpha here is always 255 because jpg doesn't have transparency
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = pixel & 0xff;
				rowSum += red + blue + green;
				if (red + blue + green > 10) {
					//System.out.println(x + ","+ y + "=" + red + ", " + green + ", " + blue + " total=" + (red+green+blue) );
					rowIsEmpty = false;
				}
			}
			System.out.println("row " + y + " total " + rowSum  +" all green=" + rowIsEmpty);
			lastRow = rowSum;
		}
	}

}