// FlexLayout

import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.*;


/**
 *	Application which takes images and lays them out on a flexagon.<br>
 *	Parameters:
 *	<dl compact>
 *	<dt>
 *		FACES
 *	<dd>
 *		indicates the number of faces.
 *		It must be 6 or 12.
 *	<dt>
 *		IMAGEn
 *	<dd>
 *		i.e. IMAGE0, IMAGE1, etc.
 *		These are filenames or URLs specifying the images
 *		for each face of the flexagon.
 *	</dl>
 *
 * @author		Kathryn Huxtable <khuxtable@ukans.edu>
 * @version		1.0 30 December 1997
 */

public class FlexLayout extends Frame {
    private void getImage(int f)
    {
		int base = backFlag ? numFaces/2 : 0;

    	try {
			System.out.println("Getting pixels for " + imageNames[f + base]);
			faces[f] = new FlexFace(imageNames[f + base], width, height);
		}
		catch (NullPointerException e) {
			e.printStackTrace();
			System.exit(1);
		}
		catch (OutOfMemoryError e) {
			e.printStackTrace();
			System.exit(1);
		}
    }

	// Input variables

//	private int		height   = 222;		// height of images
//	private int		width    = 256;		// width of images

	private int		height   = 1300;	// height of images
	private int		width    = 1500;	// width of images

	private String	outputName =		// output file name
								"fleximage_";
	private String	outputType =		// output file type
								".raw";

		// The number of faces for the flexagon must be 6 or 12.
		// I don't check this yet.

//	private int		numFaces = 6;		// number of faces in flexagon
//
//	private String[] imageNames = {
//		"images/Come From Krete.png",
//		"images/Soap Bubbles.png",
//		"images/Pwyll & Rhiannon.png",
//		"images/Harvest Moon.png",
//		"images/Gaia.png",
//		"images/Drawing Down.png",
//	};

	private int		numFaces = 12;		// number of faces in flexagon

	private String[] imageNames = {
		"dodeca/websym.png",
		"dodeca/soap bubbles.png",
		"dodeca/peaceful elements.png",
		"dodeca/harvest moon.png",
		"dodeca/drawing down.png",
		"dodeca/gaia.png",
		"dodeca/beltane97.png",
		"dodeca/Pwyll & Rhiannon.png",
		"dodeca/greeting the sun.png",
		"dodeca/day henge.png",
		"dodeca/Pele2.png",
		"dodeca/yemaya.png",
	};


	// Working variables

	final String FileMenuExit = "Exit";

	// Constants for representing the facets.

	private static final int a = 0;
	private static final int b = 1;
	private static final int c = 2;
	private static final int d = 3;
	private static final int e = 4;
	private static final int f = 5;

	private FlexFace[]			faces;		// array of faces
	private boolean				backFlag;	// front or back of image?
	private FileOutputStream	out;		// stream for resultant images


    public static void main(String args[]) {
		FlexLayout layout = new FlexLayout();

		layout.pack();
		layout.show();

		layout.generate();
    }


	public FlexLayout() {
        // System.out.println("Total Memory = " + java.lang.Runtime.getRuntime().totalMemory());
        // System.out.println("Free Memory  = " + java.lang.Runtime.getRuntime().freeMemory());

    		//{{INIT_CONTROLS
		setLayout(null);
		setVisible(false);
		setSize(insets().left + insets().right + 430,insets().top + insets().bottom + 270);
		setTitle("Untitled");
		//}}
		//{{INIT_MENUS
		menuBar1 = new java.awt.MenuBar();
		menu1 = new java.awt.Menu("&File");
		menuItem1 = new java.awt.MenuItem("MenuItem");
		menu1.add(menuItem1);
		menuItem2 = new java.awt.MenuItem("Exit");
		menu1.add(menuItem2);
		menuBar1.add(menu1);
		setMenuBar(menuBar1);
		//$$ menuBar1.move(0,0);
		//}}
}
	/**
	 *	Generate the layout
	 */

	public void generate() {

		// Get the image pixels for each face

		faces = new FlexFace[numFaces/2];


		backFlag = false;

		getImages();

		if (numFaces == 6)
			drawFrontHexa();
		else {
			drawFrontLeftDodeca();
			drawFrontRightDodeca();
		}


		backFlag = true;

		getImages();

		if (numFaces == 6)
			drawBackHexa();
		else {
			drawBackLeftDodeca();
			drawBackRightDodeca();
		}

	}


	private void getImages() {

		for (int f = 0; f < numFaces/2; f++)
		    getImage(f);

	}


	private void drawFrontHexa() {
		int x;

		open("front");


		// Draw the left end

		for (int y = 0; y < height/2; y++) {
			x = drawStart(y, false);

			x += drawRow(y, 0, b, 120);
			x += drawRow(y, 1, c, 120);
			x += drawRow(y, 2, f, 240);
			x += drawRow(y, 0, c, 120);
			x += drawRow(y, 1, f, 240);
			x += drawRow(y, 2, e, 240);
			x += drawRow(y, 0, f, 240);
			x += drawRow(y, 1, e, 240);
			x += drawRow(y, 2, d,   0);
			x += drawRow(y, 0, e, 240);

			x += drawFinish(x, y);
		}


		// Draw the right end

		for (int y = 0; y < height/2; y++) {
			x = drawStart(y, true);

			x += drawRow(y, 0, e, 240);
			x += drawRow(y, 1, d,   0);
			x += drawRow(y, 2, a,   0);
			x += drawRow(y, 0, d,   0);
			x += drawRow(y, 1, a,   0);
			x += drawRow(y, 2, b, 120);
			x += drawRow(y, 0, a,   0);
			x += drawRow(y, 1, b, 120);
			x += drawRow(y, 2, c, 120);
			x += drawRow(y, 0, b, 120);

			x += drawFinish(x, y);
		}

		close();
	}


	private void drawBackHexa() {
		int x;

		open("back");


		// Draw the right end

		for (int y = 0; y < height/2; y++) {
			x = drawStart(y, false);

			x += drawRow(y, 3, a, 300);
			x += drawRow(y, 5, b,  60);
			x += drawRow(y, 5, c,  60);
			x += drawRow(y, 4, b,  60);
			x += drawRow(y, 4, c,  60);
			x += drawRow(y, 3, b,  60);
			x += drawRow(y, 3, c,  60);
			x += drawRow(y, 5, f, 180);
			x += drawRow(y, 5, e, 180);
			x += drawRow(y, 4, f, 180);

			x += drawFinish(x, y);
		}


		// Draw the left end

		for (int y = 0; y < height/2; y++) {
			x = drawStart(y, true);

			x += drawRow(y, 4, f, 180);
			x += drawRow(y, 4, e, 180);
			x += drawRow(y, 3, f, 180);
			x += drawRow(y, 3, e, 180);
			x += drawRow(y, 5, d, 300);
			x += drawRow(y, 5, a, 300);
			x += drawRow(y, 4, d, 300);
			x += drawRow(y, 4, a, 300);
			x += drawRow(y, 3, d, 300);
			x += drawRow(y, 3, a, 300);

			x += drawFinish(x, y);
		}

		close();
	}


	private void drawFrontLeftDodeca() {
		int x;

		open("front_left");


		// Draw the left end

		for (int y = 0; y < height/2; y++) {
			x = drawStart(y, false);

			x += drawRow(y, 4, f, 240);
			x += drawRow(y, 0, b, 180);
			x += drawRow(y, 1, c, 180);
			x += drawRow(y, 4, e, 240);
			x += drawRow(y, 3, f, 240);
			x += drawRow(y, 2, f, 300);
			x += drawRow(y, 0, c, 180);
			x += drawRow(y, 3, e, 240);
			x += drawRow(y, 5, d,   0);
			x += drawRow(y, 1, f, 300);

			x += drawFinish(x, y);
		}


		// Draw the right end

		for (int y = 0; y < height/2; y++) {
			x = drawStart(y, true);

			x += drawRow(y, 1, f, 300);
			x += drawRow(y, 2, e, 300);
			x += drawRow(y, 5, a,   0);
			x += drawRow(y, 4, d,   0);
			x += drawRow(y, 0, f, 300);
			x += drawRow(y, 1, e, 300);
			x += drawRow(y, 4, a,   0);
			x += drawRow(y, 3, d,   0);
			x += drawRow(y, 2, d,  60);
			x += drawRow(y, 0, e, 300);

			x += drawFinish(x, y);
		}

		close();
	}


	private void drawFrontRightDodeca() {
		int x;

		open("front_right");


		// Draw the left end

		for (int y = 0; y < height/2; y++) {
			x = drawStart(y, false);

			x += drawRow(y, 0, e, 300);
			x += drawRow(y, 3, a,   0);
			x += drawRow(y, 5, b, 120);
			x += drawRow(y, 1, d,  60);
			x += drawRow(y, 2, a,  60);
			x += drawRow(y, 5, c, 120);
			x += drawRow(y, 4, b, 120);
			x += drawRow(y, 0, d,  60);
			x += drawRow(y, 1, a,  60);
			x += drawRow(y, 4, c, 120);

			x += drawFinish(x, y);
		}


		// Draw the right end

		for (int y = 0; y < height/2; y++) {
			x = drawStart(y, true);

			x += drawRow(y, 4, c, 120);
			x += drawRow(y, 3, b, 120);
			x += drawRow(y, 2, b, 180);
			x += drawRow(y, 0, a,  60);
			x += drawRow(y, 3, c, 120);
			x += drawRow(y, 5, f, 240);
			x += drawRow(y, 1, b, 180);
			x += drawRow(y, 2, c, 180);
			x += drawRow(y, 5, e, 240);
			x += drawRow(y, 4, f, 240);

			x += drawFinish(x, y);
		}

		close();
	}


	private void drawBackRightDodeca() {
		int x;

		open("back_right");


		// Draw the right end

		for (int y = 0; y < height/2; y++) {
			x = drawStart(y, false);

			x += drawRow(y,  8, e, 180);
			x += drawRow(y,  7, f, 180);
			x += drawRow(y,  7, e, 180);
			x += drawRow(y,  6, f, 180);
			x += drawRow(y,  6, e, 180);
			x += drawRow(y, 11, d, 300);
			x += drawRow(y, 11, a, 300);
			x += drawRow(y, 10, d, 300);
			x += drawRow(y, 10, a, 300);
			x += drawRow(y,  9, d, 300);

			x += drawFinish(x, y);
		}


		// Draw the left end

		for (int y = 0; y < height/2; y++) {
			x = drawStart(y, true);

			x += drawRow(y,  6, b,  60);
			x += drawRow(y,  6, c,  60);
			x += drawRow(y, 11, f, 180);
			x += drawRow(y, 11, e, 180);
			x += drawRow(y, 10, f, 180);
			x += drawRow(y, 10, e, 180);
			x += drawRow(y,  9, f, 180);
			x += drawRow(y,  9, e, 180);
			x += drawRow(y,  8, f, 180);
			x += drawRow(y,  8, e, 180);

			x += drawFinish(x, y);
		}

		close();
	}


	private void drawBackLeftDodeca() {
		int x;

		open("back_left");


		// Draw the right end

		for (int y = 0; y < height/2; y++) {
			x = drawStart(y, false);

			x += drawRow(y, 11, c,  60);
			x += drawRow(y, 10, b,  60);
			x += drawRow(y, 10, c,  60);
			x += drawRow(y,  9, b,  60);
			x += drawRow(y,  9, c,  60);
			x += drawRow(y,  8, b,  60);
			x += drawRow(y,  8, c,  60);
			x += drawRow(y,  7, b,  60);
			x += drawRow(y,  7, c,  60);
			x += drawRow(y,  6, b,  60);

			x += drawFinish(x, y);
		}


		// Draw the left end

		for (int y = 0; y < height/2; y++) {
			x = drawStart(y, true);

			x += drawRow(y,  9, d, 300);
			x += drawRow(y,  9, a, 300);
			x += drawRow(y,  8, d, 300);
			x += drawRow(y,  8, a, 300);
			x += drawRow(y,  7, d, 300);
			x += drawRow(y,  7, a, 300);
			x += drawRow(y,  6, d, 300);
			x += drawRow(y,  6, a, 300);
			x += drawRow(y, 11, b,  60);
			x += drawRow(y, 11, c,  60);

			x += drawFinish(x, y);
		}

		close();
	}


	//	Draw a row of a facet

	private int drawRow(int y, int face, int facet, int rot) {
		if (y % 10 == 0)
			System.err.print("On row " + y + "       \r");

		if (backFlag)
			face -= numFaces/2;

		rot /= 60;

		boolean upsideDown = (rot & 1) == 1;

		if (upsideDown) {
			rot = (rot + 3) % 6;		// rotate 180 degrees
			y = height/2 - y;			// and flip
		}

		int pixels[] = new int[width];

		int w = faces[face].getRow(y, facet, rot, pixels);

		if (upsideDown) {
			for (int i = w - 1; i >= 0; i--)
				drawPixel(pixels[i]);
		}
		else {
			for (int i = 0; i < w; i++)
				drawPixel(pixels[i]);
		}

		return w;
	}


	//	Draw blank pixels for left border.

	private int drawStart(int y, boolean pointDown) {
		if (pointDown)
			y = height/2 - y;

		int end = (int) Math.floor(y * width / 2.0 / height);

		for (int dx = 0; dx < end; dx++) {
			drawPixel(0);
		}

		return end;
	}


	//	Draw blank pixels for right border.
	//	This just fills out the row.

	private int drawFinish(int x, int y) {
		int end = (int) (2.75 * width);

		for (int dx = x; dx < end; dx++) {
			drawPixel(0);
		}

		return end - x;
	}


	private void drawPixel(int pixel) {
		int red   = (pixel >> 16) & 0xFF;
		int green = (pixel >>  8) & 0xFF;
		int blue  = (pixel >>  0) & 0xFF;

		try {
			out.write(red);
			out.write(green);
			out.write(blue);
		}
		catch (IOException e) {
			System.err.println("IO exception writing to image file");
			System.exit(0);
		}
	}


	private void open(String suffix) {

		String thisFile = outputName + suffix + outputType;

		System.err.println("Writing to file \"" + thisFile + "\"");

		// Open output file

		try {
			out = new FileOutputStream(thisFile);
		}
		catch (IOException e) {
			System.err.println("Can't open output file \"" + thisFile + "\"");
			System.exit(1);
		}
	}


	private void close() {

		// Close the output file

		try {
			out.close();
		}
		catch (IOException e) {
			System.err.println("can't close output file");
		}
	}
	//{{DECLARE_CONTROLS
	//}}
	//{{DECLARE_MENUS
	java.awt.MenuBar menuBar1;
	java.awt.Menu menu1;
	java.awt.MenuItem menuItem1;
	java.awt.MenuItem menuItem2;
	//}}
}
