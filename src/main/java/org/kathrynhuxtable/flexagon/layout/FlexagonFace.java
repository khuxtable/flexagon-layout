package org.kathrynhuxtable.flexagon.layout;

import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Store pixels for each face of the flexagon. Supply rows of pixels for rotated
 * facets.
 *
 * @author Kathryn Huxtable
 */

public class FlexagonFace {

    private static final double[] sin = {
        Math.sin(0 * Math.PI / 3.0), // 0 degrees ccw
        Math.sin(1 * Math.PI / 3.0), // 60 degrees ccw
        Math.sin(2 * Math.PI / 3.0), // 120 degrees ccw
        Math.sin(3 * Math.PI / 3.0), // 180 degrees ccw
        Math.sin(4 * Math.PI / 3.0), // 240 degrees ccw
        Math.sin(5 * Math.PI / 3.0), // 300 degrees ccw
    };

    private static final double[] cos = {
        Math.cos(0 * Math.PI / 3.0), // 0 degrees ccw
        Math.cos(1 * Math.PI / 3.0), // 60 degrees ccw
        Math.cos(2 * Math.PI / 3.0), // 120 degrees ccw
        Math.cos(3 * Math.PI / 3.0), // 180 degrees ccw
        Math.cos(4 * Math.PI / 3.0), // 240 degrees ccw
        Math.cos(5 * Math.PI / 3.0), // 300 degrees ccw
    };

    /**
     * Define the facets of the flexagon face.
     */
    public enum Facet {
        A(0), B(1), C(2), D(3), E(4), F(5);

        private int    index;
        private double beginFactor;
        private double endFactor;

        /**
         * Creates A new Facet object.
         *
         * @param index DOCUMENT ME!
         */
        Facet(int index) {
            this.index = index;

            switch (index) {

            case 0:
                beginFactor = 0.5;
                endFactor   = 0.5;
                break;

            case 1:
                beginFactor = 0.5;
                endFactor   = 1.5;
                break;

            case 2:
                beginFactor = 1.5;
                endFactor   = 1.5;
                break;

            case 3:
                beginFactor = 0.0;
                endFactor   = 1.0;
                break;

            case 4:
                beginFactor = 1.0;
                endFactor   = 1.0;
                break;

            case 5:
            default:
                beginFactor = 1.0;
                endFactor   = 2.0;
                break;
            }
        }

        /**
         * DOCUMENT ME!
         *
         * @return DOCUMENT ME!
         */
        public int getIndex() {
            return index;
        }

        /**
         * DOCUMENT ME!
         *
         * @param  y      DOCUMENT ME!
         * @param  width  DOCUMENT ME!
         * @param  height DOCUMENT ME!
         *
         * @return DOCUMENT ME!
         */
        public int begin(int y, int width, int height) {
            double beg_factor = beginFactor * height;

            if (this == Facet.A || this == Facet.C || this == Facet.E) {
                beg_factor -= y;
            } else {
                beg_factor += y;
            }

            return (int) Math.floor(beg_factor * width / 2.0 / height);
        }

        /**
         * DOCUMENT ME!
         *
         * @param  y      DOCUMENT ME!
         * @param  width  DOCUMENT ME!
         * @param  height DOCUMENT ME!
         *
         * @return DOCUMENT ME!
         */
        public int end(int y, int width, int height) {
            double end_factor = endFactor * height;

            if (this == Facet.A || this == Facet.C || this == Facet.E) {
                end_factor += y;
            } else {
                end_factor -= y;
            }

            return (int) Math.floor(end_factor * width / 2.0 / height);
        }
    }

    private double[] ctrX = new double[6];
    private double[] ctrY = new double[6];

    private double[] coord = new double[2];

    private int[] raster;
    private int   width;
    private int   height;

    /**
     * Creates A new FlexagonFace object.
     *
     * @param  imageName DOCUMENT ME!
     * @param  width     DOCUMENT ME!
     * @param  height    DOCUMENT ME!
     *
     * @throws NullPointerException DOCUMENT ME!
     */
    public FlexagonFace(String imageName, int width, int height) throws NullPointerException {
        this.width  = width;
        this.height = height;

        for (int src = 0; src < 6; src++) {
            ctrX[src] = Math.round(width * ((src % 3) + 1) / 4.0);
            ctrY[src] = Math.round(height * ((src < 3 ? 0 : 0.5) + ((src & 1) == 0 ? 2 : 1) / 6.0));
        }

        getImagePixels(imageName);
    }

    /**
     * DOCUMENT ME!
     *
     * @param imageName DOCUMENT ME!
     */
    private void getImagePixels(String imageName) {
        Image image = null;

        try {
            image = ImageIO.read(new FileInputStream(imageName));
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        raster = new int[width * height];

        PixelGrabber pg = new PixelGrabber(image, 0, 0, width, height, raster,
                                           0, width);

        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
            raster = null;
            throw new NullPointerException("interrupted waiting for pixels from " + imageName);
        }

        if ((pg.status() & ImageObserver.ABORT) != 0) {
            raster = null;
            throw new NullPointerException("image fetch aborted or returned error for " + imageName);
        }
    }

    /**
     * Return the height of the image.
     *
     * @return DOCUMENT ME!
     */

    public int getHeight() {
        return height;
    }

    /**
     * Return the width of the image.
     *
     * @return DOCUMENT ME!
     */

    public int getWidth() {
        return width;
    }

    /**
     * DOCUMENT ME!
     *
     * @param facet DOCUMENT ME!
     * @param rot   DOCUMENT ME!
     * @param x     DOCUMENT ME!
     * @param y     DOCUMENT ME!
     * @param ret   DOCUMENT ME!
     */
    private void rotate(Facet facet, int rot, double x, double y, double[] ret) {
        double cX = ctrX[facet.getIndex()];
        double cY = ctrY[facet.getIndex()];

        // right-handed coordinate system.
        x -= cX;
        y -= cY;

        ret[0] = cos[rot] * x - sin[rot] * y + cX;
        ret[1] = cos[rot] * y + sin[rot] * x + cY;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  x DOCUMENT ME!
     * @param  y DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private int getPixel(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height)
            return 0;

        return raster[y * width + x];
    }

    /**
     * DOCUMENT ME!
     *
     * @param  weight DOCUMENT ME!
     * @param  p1     DOCUMENT ME!
     * @param  p2     DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private int averagePixel(double weight, int p1, int p2) {
        int r1 = (p1 >> 16) & 0xFF;
        int g1 = (p1 >> 8) & 0xFF;
        int b1 = (p1) & 0xFF;
        int r2 = (p2 >> 16) & 0xFF;
        int g2 = (p2 >> 8) & 0xFF;
        int b2 = (p2) & 0xFF;

        int r = (int) (weight * r1 + (1.0 - weight) * r2);
        int g = (int) (weight * g1 + (1.0 - weight) * g2);
        int b = (int) (weight * b1 + (1.0 - weight) * b2);

        return (r << 16) | (g << 8) | b;
    }

    /**
     * Get A row of A facet.
     *
     * @param  y_orig y DOCUMENT ME!
     * @param  facet  DOCUMENT ME!
     * @param  rot    DOCUMENT ME!
     * @param  pixels DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */

    public int getRow(int y_orig, Facet facet, int rot, int[] pixels) {
        int begin = facet.begin(y_orig, width, height);
        int end   = facet.end(y_orig, width, height);
        int y     = (facet == Facet.A || facet == Facet.B || facet == Facet.C) ? y_orig : y_orig + height / 2;

        if (begin != end) {
            int x = begin;

            rotate(facet, rot, x, y, coord);

            double x1 = coord[0];
            double y1 = coord[1];

            // Calculate ending point for row
            rotate(facet, rot, end, y, coord);

            double x2   = coord[0];
            double y21  = coord[1];

            // Calculate increments for pixels in row
            double xinc = (x2 - x1) / (end - x);
            double yinc = (y21 - y1) / (end - x);

            // Calculate increments for perpendicular
            rotate(facet, rot, x, y + 1, coord);

            for (int i = 0; x < end; x++) {
                int sx = (int) Math.round(x1);
                int sy = (int) Math.round(y1);

                int    p1 = getPixel(sx, sy);
                int    p2 = p1;
                double w;

                if (Math.abs(x1 - sx) > Math.abs(y1 - sy)) {
                    int sx2 = sx + (x1 - sx >= 0 ? 1 : -1);

                    p2 = getPixel(sx2, sy);
                    w  = 1.0 - (double) Math.abs(x1 - sx);
                } else {
                    int sy2 = sy + (y1 - sy >= 0 ? 1 : -1);

                    p2 = getPixel(sx, sy2);
                    w  = 1.0 - (double) Math.abs(y1 - sy);
                }

                pixels[i++] = averagePixel(w, p1, p2);

                x1 += xinc;
                y1 += yinc;
            }
        }

        return end - begin;
    }
}
