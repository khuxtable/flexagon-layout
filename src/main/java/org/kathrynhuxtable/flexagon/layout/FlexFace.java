package org.kathrynhuxtable.flexagon.layout;

// FlexFace

import java.awt.*;
import java.awt.Color;
import java.awt.image.ColorModel;
import java.awt.image.ImageFilter;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.lang.*;


/**
 *	Store pixels for each face of the flexagon.
 *	Supply rows of pixels for rotated facets.
 *
 * @author		Kathryn Huxtable <khuxtable@ukans.edu>
 * @version		1.0 30 December 1997
 */

public class FlexFace {

	private static ColorModel defaultRGB = ColorModel.getRGBdefault();

	private final static double[]	sin  = {
		Math.sin(0 * Math.PI / 3.0),			//   0 degrees ccw
		Math.sin(1 * Math.PI / 3.0),			//  60 degrees ccw
		Math.sin(2 * Math.PI / 3.0),			// 120 degrees ccw
		Math.sin(3 * Math.PI / 3.0),			// 180 degrees ccw 
		Math.sin(4 * Math.PI / 3.0),			// 240 degrees ccw
		Math.sin(5 * Math.PI / 3.0)				// 300 degrees ccw
	};

	private final static double[]	cos  = {
		Math.cos(0 * Math.PI / 3.0),			//   0 degrees ccw
		Math.cos(1 * Math.PI / 3.0),			//  60 degrees ccw
		Math.cos(2 * Math.PI / 3.0),			// 120 degrees ccw
		Math.cos(3 * Math.PI / 3.0),			// 180 degrees ccw 
		Math.cos(4 * Math.PI / 3.0),			// 240 degrees ccw
		Math.cos(5 * Math.PI / 3.0)				// 300 degrees ccw
	};

	private double[]	ctrX = new double[6];
	private double[]	ctrY = new double[6];

	private double		coord[] = new double[2];

	private int			raster[];
	private int			width, height;

	private FileOutputStream	out;


	public FlexFace(String imageName, int width, int height)
						throws NullPointerException {
		this.out = out;
		this.width = width;
		this.height = height;

		for (int src = 0; src < 6; src++) {
			ctrX[src] = Math.round(width  * ((src % 3) + 1) / 4.0);

			ctrY[src] = Math.round(height * ((src < 3 ? 0 : 0.5) +
											((src & 1) == 0 ? 2 : 1) / 6.0));
		}

		getImagePixels(imageName);
	}


	private void getImagePixels(String imageName) {
		ImageProducer ip;

		try {
	        ip = new PNGImageProducer(new FileInputStream(imageName));
		}
		catch (FileNotFoundException e) {
			throw new NullPointerException("couldn't get image " + imageName);
		}

		if (ip == null) {
			throw new NullPointerException("couldn't get image " + imageName);
		}

		raster = new int[width * height];

		PixelGrabber pg = new PixelGrabber(ip, 0, 0, width, height, raster,
										   0, width);

		try {
			pg.grabPixels();
		}
		catch (InterruptedException e) {
			raster = null;
			throw new NullPointerException("interrupted waiting for pixels from " + imageName);
		}

		if ((pg.status() & ImageObserver.ABORT) != 0) {
			raster = null;
			throw new NullPointerException("image fetch aborted or returned error for " + imageName);
		}
	}


	/**
	 *	Return the height of the image.
	 */

	public int getHeight() {
		return height;
	}


	/**
	 *	Return the width of the image.
	 */

	public int getWidth() {
		return width;
	}


	private void rotate(int facet, int rot, double x, double y, double[] ret) {
		// right-handed coordinate system.

		x -= ctrX[facet];
		y -= ctrY[facet];

		ret[0] = cos[rot] * x - sin[rot] * y + ctrX[facet];
		ret[1] = cos[rot] * y + sin[rot] * x + ctrY[facet];
	}


	private int getPixel(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return 0;

		return raster[y * width + x];
	}


	private int averagePixel(double weight, int p1, int p2) {
		int r1 = (p1 >> 16) & 0xFF;
		int g1 = (p1 >>  8) & 0xFF;
		int b1 = (p1      ) & 0xFF;
		int r2 = (p2 >> 16) & 0xFF;
		int g2 = (p2 >>  8) & 0xFF;
		int b2 = (p2      ) & 0xFF;

		int r = (int) (weight * r1 + (1.0 - weight) * r2);
		int g = (int) (weight * g1 + (1.0 - weight) * g2);
		int b = (int) (weight * b1 + (1.0 - weight) * b2);

		return (r << 16) | (g << 8) | b;
	}


	private void setFacetRow(int facet, int rot, int x, int new_x, int y,
							 int pixels[]) {
		if (x == new_x)
			return;

		// Calculate starting point for row

		rotate(facet, rot, x, y, coord);

		double x1 = coord[0];
		double y1 = coord[1];

		// Calculate ending point for row

		rotate(facet, rot, new_x, y, coord);

		double x2 = coord[0];
		double y2 = coord[1];

		// Calculate increments for pixels in row

		double xinc = (x2 - x1) / (new_x - x);
		double yinc = (y2 - y1) / (new_x - x);

		// Calculate increments for perpendicular

		rotate(facet, rot, x, y + 1, coord);

		double x3 = coord[0];
		double y3 = coord[1];

		double xperp = x3 - x1;
		double yperp = y3 - y1;

		for (int i = 0; x < new_x; x++) {
			int sx = (int) Math.round(x1);
			int sy = (int) Math.round(y1);

			int p1 = getPixel(sx, sy);
			int p2 = p1;
			double w;

			if (Math.abs(x1 - sx) > Math.abs(y1 - sy)) {
				int sx2 = sx + (x1 - sx >= 0 ? 1 : -1);
				p2 = getPixel(sx2, sy);
				w = 1.0 - (double) Math.abs(x1 - sx);
			}
			else {
				int sy2 = sy + (y1 - sy >= 0 ? 1 : -1);
				p2 = getPixel(sx, sy2);
				w = 1.0 - (double) Math.abs(y1 - sy);
			}

			pixels[i++] = averagePixel(w, p1, p2);

			x1 += xinc;
			y1 += yinc;
		}
	}


	/**
	 *	Get a row of a facet.
	 */

	public int getRow(int y, int facet, int rot, int pixels[]) {

		double beg_factor;
		double end_factor;

		switch (facet) {
		case 0:
			beg_factor = 0.5;
			end_factor = 0.5;
			break;
		case 1:
			beg_factor = 0.5;
			end_factor = 1.5;
			break;
		case 2:
			beg_factor = 1.5;
			end_factor = 1.5;
			break;
		case 3:
			beg_factor = 0.0;
			end_factor = 1.0;
			break;
		case 4:
			beg_factor = 1.0;
			end_factor = 1.0;
			break;
		default: // case 5:
			beg_factor = 1.0;
			end_factor = 2.0;
			break;
		}

		beg_factor *= height;
		end_factor *= height;

		if ((facet & 1) == 0) {
			beg_factor -= y;
			end_factor += y;
		}
		else {
			beg_factor += y;
			end_factor -= y;
		}

		int beg = (int) Math.floor(beg_factor * width / 2.0 / height);
		int end = (int) Math.floor(end_factor * width / 2.0 / height);

		setFacetRow(facet,
					rot,
					beg,
					end,
					facet < 3 ? y : y + height/2,
					pixels);

		return end - beg;
	}
}

