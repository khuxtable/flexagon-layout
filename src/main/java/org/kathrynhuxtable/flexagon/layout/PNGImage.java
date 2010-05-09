package org.kathrynhuxtable.flexagon.layout;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Write a PNG file with a flexagon image.
 *
 * @author Kathryn Huxtable
 */
public class PNGImage {

    private int width;
    private int height;

    private BufferedImage image;
    private Graphics2D    g;
    private int           row;
    private int           col;

    /**
     * Creates a new PNGImage object.
     *
     * @param width  DOCUMENT ME!
     * @param height DOCUMENT ME!
     */
    public PNGImage(int width, int height) {
        this.width  = width;
        this.height = height;

        image = new BufferedImage((int) (2.75f * width), height, BufferedImage.TYPE_INT_RGB);
        g     = image.createGraphics();
        row   = 0;
    }

    /**
     * Draw a row of a facet
     *
     * @param  y     DOCUMENT ME!
     * @param  face  DOCUMENT ME!
     * @param  facet DOCUMENT ME!
     * @param  rot   DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int drawRow(int y, FlexagonFace face, int facet, int rot) {
        if (y % 10 == 0) {
            System.err.print("On row " + y + "       \r");
        }

        rot /= 60;

        boolean upsideDown = (rot & 1) == 1;

        if (upsideDown) {
            rot = (rot + 3) % 6; // rotate 180 degrees
            y   = height / 2 - y; // and flip
        }

        int[] pixels = new int[width];

        int w = face.getRow(y, facet, rot, pixels);

        if (upsideDown) {
            for (int i = w - 1; i >= 0; i--) {
                drawPixel(pixels[i]);
            }
        } else {
            for (int i = 0; i < w; i++) {
                drawPixel(pixels[i]);
            }
        }

        return w;
    }

    /**
     * Draw blank pixels for left border.
     *
     * @param  y         DOCUMENT ME!
     * @param  pointDown DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int rowStart(int y, boolean pointDown) {
        col = 0;

        if (pointDown) {
            y = height / 2 - y;
        }

        int end = (int) Math.floor(y * width / 2.0 / height);

        for (int dx = 0; dx < end; dx++) {
            drawPixel(0);
        }

        return end;
    }

    /**
     * Draw blank pixels for right border. This just fills out the row.
     *
     * @param  x DOCUMENT ME!
     * @param  y DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int rowFinish(int x, int y) {
        int end = (int) (2.75 * width);

        for (int dx = x; dx < end; dx++) {
            drawPixel(0);
        }

        row++;

        return end - x;
    }

    /**
     * DOCUMENT ME!
     *
     * @param pixel DOCUMENT ME!
     */
    private void drawPixel(int pixel) {
        g.setColor(new Color(pixel));
        g.fillRect(col, row, 2, 2);
        col++;
    }

    /**
     * DOCUMENT ME!
     *
     * @param filename the file name.
     */
    public void close(String filename) {
        g.dispose();

        try {
            ImageIO.write(image, "png", new File(filename + ".png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
