package org.alex.games.bj;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.FilteredImageSource;
import java.io.IOException;
import java.io.InputStream;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;


public class FeltTop extends JPanel {

	private BufferedImage image = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = -8050655045065678999L;

	public FeltTop(){

		// the file is src/java/resources/deck-of-cards.jpg
		// Use a leading slash to indicate the path starts from the root of the classpath
		String resourcePath = "/deck-of-cards.jpg"; 
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
		//http://www.java2s.com/Code/Java/2D-Graphics-GUI/Imagecrop.htm
		//g.drawImage(image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer)
	}

	public static void main(String[] args) throws IOException {
		FeltTop f = new FeltTop();
		f.getExtents(f.image);
		// Detect edges
		BufferedImage edges = detectEdges(f.image);

		// Save the resulting edge image (replace "edges.png" with desired output file path)
		ImageIO.write(edges, "png", new File("edges.png"));
		System.out.println("Edge detection complete. Result saved to edges.png");

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
		System.out.println("Image deck-of-cards.jpg is wide=" + image.getWidth() + " height=" + image.getHeight() );

		int lastRow = 0;
		for (int y=0; y < 60; y++) { //image.getHeight(); y++) {
			int rowSum = 0;
			boolean rowIsGreen = true;
			for (int x=0; x < image.getWidth(); x++) {
				int pixel = image.getRGB(x, y);
				rowSum += pixel;
				// Use bitwise operations for performance (more efficient)
				// Use unsigned right shift
				//int alpha = (pixel >> 24) & 0xff;  //alpha here is always 255 because jpg doesn't have transparency
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = pixel & 0xff;
				if (red > 6 && blue > 7 && !(green >123 && green < 132))  {
					System.out.println(x + ","+ y + "=" + red + ", " + green + ", " + blue);
					rowIsGreen = false;
				}
			}
			if (rowSum != lastRow) {
				System.out.println("row " + y + " change detected " + rowSum  +" all green=" + rowIsGreen);
			}
			lastRow = rowSum;
		}
	}

}