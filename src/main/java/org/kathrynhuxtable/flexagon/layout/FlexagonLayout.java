package org.kathrynhuxtable.flexagon.layout;

import javax.swing.JFrame;

/**
 * Application which takes images and lays them out on a flexagon.<br>
 * Parameters:
 *
 * <dl compact>
 *   <dt>FACES</dt>
 *   <dd>indicates the number of faces. It must be 6 or 12.</dd>
 *
 *   <dt>IMAGEn</dt>
 *   <dd>i.e. IMAGE0, IMAGE1, etc. These are filenames or URLs specifying the
 *     images for each face of the flexagon.</dd>
 * </dl>
 *
 * @author Kathryn Huxtable
 */

public class FlexagonLayout extends JFrame {
    private static final long serialVersionUID = -7424034403430831104L;

    // Constants for representing the facets.

    private static final int  a                = 0;
    private static final int  b                = 1;
    private static final int  c                = 2;
    private static final int  d                = 3;
    private static final int  e                = 4;
    private static final int  f                = 5;

    private int height   = 222; // height of images
    private int width    = 256; // width of images

// private int               height           = 1300; // height of images
// private int               width            = 1500; // width of images

    // The number of faces for the flexagon must be 6 or 12.
    // I don't check this yet.

// private int     numFaces = 6;       // number of faces in flexagon
//
// private String[] imageNames = {
// "images/Come From Krete.png",
// "images/Soap Bubbles.png",
// "images/Pwyll & Rhiannon.png",
// "images/Harvest Moon.png",
// "images/Gaia.png",
// "images/Drawing Down.png",
// };

    private int numFaces = 12; // number of faces in flexagon

    private String[] imageNames   = {
        "dodeca/websym.png",
        "dodeca/soap bubbles.png",
        "dodeca/peaceful elements.png",
        "dodeca/harvest moon.png",
        "dodeca/drawing down.png",
        "dodeca/Gaia.png",
        "dodeca/beltane97.png",
        "dodeca/Pwyll & Rhiannon.png",
        "dodeca/greeting the sun.png",
        "dodeca/day henge.png",
        "dodeca/Pele2.png",
        "dodeca/Yemaya.png",
    };

    // Working variables

    final String     FileMenuExit = "Exit";

    private FlexagonFace[] faces; // Array of flexagon faces.
    private boolean    backFlag; // front or back of image?

    java.awt.MenuBar  menuBar1;
    java.awt.Menu     menu1;
    java.awt.MenuItem menuItem1;
    java.awt.MenuItem menuItem2;

    /**
     * Creates a new FlexagonLayout object.
     */
    public FlexagonLayout() {
        // System.out.println("Total Memory = " + java.lang.Runtime.getRuntime().totalMemory());
        // System.out.println("Free Memory  = " + java.lang.Runtime.getRuntime().freeMemory());

        // {{INIT_CONTROLS
        setLayout(null);
        setVisible(false);
        setSize(getInsets().left + getInsets().right + 430, getInsets().top + getInsets().bottom + 270);
        setTitle("Untitled");
        // }}
        // {{INIT_MENUS
        menuBar1  = new java.awt.MenuBar();
        menu1     = new java.awt.Menu("&File");
        menuItem1 = new java.awt.MenuItem("MenuItem");
        menu1.add(menuItem1);
        menuItem2 = new java.awt.MenuItem("Exit");
        menu1.add(menuItem2);
        menuBar1.add(menu1);
        setMenuBar(menuBar1);
        // $$ menuBar1.move(0,0);
        // }}
    }

    /**
     * DOCUMENT ME!
     *
     * @param f DOCUMENT ME!
     */
    private void getImage(int f) {
        int base = backFlag ? numFaces / 2 : 0;

        try {
            System.out.println("Getting pixels for " + imageNames[f + base]);
            faces[f] = new FlexagonFace(imageNames[f + base], width, height);
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    // Input variables

    /**
     * DOCUMENT ME!
     *
     * @param args DOCUMENT ME!
     */
    public static void main(String[] args) {
        FlexagonLayout layout = new FlexagonLayout();

        layout.pack();
        layout.setVisible(true);

        layout.generate();
        System.exit(0);
    }

    /**
     * Generate the layout
     */

    public void generate() {
        // Get the image pixels for each face

        faces = new FlexagonFace[numFaces / 2];

        backFlag = false;

        getImages();

        if (numFaces == 6) {
            drawFrontHexa();
        } else {
            drawFrontLeftDodeca();
            drawFrontRightDodeca();
        }

        backFlag = true;

        getImages();

        if (numFaces == 6) {
            drawBackHexa();
        } else {
            drawBackLeftDodeca();
            drawBackRightDodeca();
        }

    }

    /**
     * DOCUMENT ME!
     */
    private void getImages() {
        for (int f = 0; f < numFaces / 2; f++) {
            getImage(f);
        }

    }

    /**
     * DOCUMENT ME!
     */
    private void drawFrontHexa() {
        int x;

        PNGImage out = new PNGImage("front", width, height, numFaces, backFlag, faces);

        // Draw the left end

        for (int y = 0; y < height / 2; y++) {
            x = out.rowStart(y, false);

            x += out.drawRow(y, 0, b, 120);
            x += out.drawRow(y, 1, c, 120);
            x += out.drawRow(y, 2, f, 240);
            x += out.drawRow(y, 0, c, 120);
            x += out.drawRow(y, 1, f, 240);
            x += out.drawRow(y, 2, e, 240);
            x += out.drawRow(y, 0, f, 240);
            x += out.drawRow(y, 1, e, 240);
            x += out.drawRow(y, 2, d, 0);
            x += out.drawRow(y, 0, e, 240);

            x += out.rowFinish(x, y);
        }

        // Draw the right end

        for (int y = 0; y < height / 2; y++) {
            x = out.rowStart(y, true);

            x += out.drawRow(y, 0, e, 240);
            x += out.drawRow(y, 1, d, 0);
            x += out.drawRow(y, 2, a, 0);
            x += out.drawRow(y, 0, d, 0);
            x += out.drawRow(y, 1, a, 0);
            x += out.drawRow(y, 2, b, 120);
            x += out.drawRow(y, 0, a, 0);
            x += out.drawRow(y, 1, b, 120);
            x += out.drawRow(y, 2, c, 120);
            x += out.drawRow(y, 0, b, 120);

            x += out.rowFinish(x, y);
        }

        out.close();
    }

    /**
     * DOCUMENT ME!
     */
    private void drawBackHexa() {
        int x;

        PNGImage out = new PNGImage("back", width, height, numFaces, backFlag, faces);

        // Draw the right end

        for (int y = 0; y < height / 2; y++) {
            x = out.rowStart(y, false);

            x += out.drawRow(y, 3, a, 300);
            x += out.drawRow(y, 5, b, 60);
            x += out.drawRow(y, 5, c, 60);
            x += out.drawRow(y, 4, b, 60);
            x += out.drawRow(y, 4, c, 60);
            x += out.drawRow(y, 3, b, 60);
            x += out.drawRow(y, 3, c, 60);
            x += out.drawRow(y, 5, f, 180);
            x += out.drawRow(y, 5, e, 180);
            x += out.drawRow(y, 4, f, 180);

            x += out.rowFinish(x, y);
        }

        // Draw the left end

        for (int y = 0; y < height / 2; y++) {
            x = out.rowStart(y, true);

            x += out.drawRow(y, 4, f, 180);
            x += out.drawRow(y, 4, e, 180);
            x += out.drawRow(y, 3, f, 180);
            x += out.drawRow(y, 3, e, 180);
            x += out.drawRow(y, 5, d, 300);
            x += out.drawRow(y, 5, a, 300);
            x += out.drawRow(y, 4, d, 300);
            x += out.drawRow(y, 4, a, 300);
            x += out.drawRow(y, 3, d, 300);
            x += out.drawRow(y, 3, a, 300);

            x += out.rowFinish(x, y);
        }

        out.close();
    }

    /**
     * DOCUMENT ME!
     */
    private void drawFrontLeftDodeca() {
        int x;

        PNGImage out = new PNGImage("front_left", width, height, numFaces, backFlag, faces);

        // Draw the left end

        for (int y = 0; y < height / 2; y++) {
            x = out.rowStart(y, false);

            x += out.drawRow(y, 4, f, 240);
            x += out.drawRow(y, 0, b, 180);
            x += out.drawRow(y, 1, c, 180);
            x += out.drawRow(y, 4, e, 240);
            x += out.drawRow(y, 3, f, 240);
            x += out.drawRow(y, 2, f, 300);
            x += out.drawRow(y, 0, c, 180);
            x += out.drawRow(y, 3, e, 240);
            x += out.drawRow(y, 5, d, 0);
            x += out.drawRow(y, 1, f, 300);

            x += out.rowFinish(x, y);
        }

        // Draw the right end

        for (int y = 0; y < height / 2; y++) {
            x = out.rowStart(y, true);

            x += out.drawRow(y, 1, f, 300);
            x += out.drawRow(y, 2, e, 300);
            x += out.drawRow(y, 5, a, 0);
            x += out.drawRow(y, 4, d, 0);
            x += out.drawRow(y, 0, f, 300);
            x += out.drawRow(y, 1, e, 300);
            x += out.drawRow(y, 4, a, 0);
            x += out.drawRow(y, 3, d, 0);
            x += out.drawRow(y, 2, d, 60);
            x += out.drawRow(y, 0, e, 300);

            x += out.rowFinish(x, y);
        }

        out.close();
    }

    /**
     * DOCUMENT ME!
     */
    private void drawFrontRightDodeca() {
        int x;

        PNGImage out = new PNGImage("front_right", width, height, numFaces, backFlag, faces);

        // Draw the left end

        for (int y = 0; y < height / 2; y++) {
            x = out.rowStart(y, false);

            x += out.drawRow(y, 0, e, 300);
            x += out.drawRow(y, 3, a, 0);
            x += out.drawRow(y, 5, b, 120);
            x += out.drawRow(y, 1, d, 60);
            x += out.drawRow(y, 2, a, 60);
            x += out.drawRow(y, 5, c, 120);
            x += out.drawRow(y, 4, b, 120);
            x += out.drawRow(y, 0, d, 60);
            x += out.drawRow(y, 1, a, 60);
            x += out.drawRow(y, 4, c, 120);

            x += out.rowFinish(x, y);
        }

        // Draw the right end

        for (int y = 0; y < height / 2; y++) {
            x = out.rowStart(y, true);

            x += out.drawRow(y, 4, c, 120);
            x += out.drawRow(y, 3, b, 120);
            x += out.drawRow(y, 2, b, 180);
            x += out.drawRow(y, 0, a, 60);
            x += out.drawRow(y, 3, c, 120);
            x += out.drawRow(y, 5, f, 240);
            x += out.drawRow(y, 1, b, 180);
            x += out.drawRow(y, 2, c, 180);
            x += out.drawRow(y, 5, e, 240);
            x += out.drawRow(y, 4, f, 240);

            x += out.rowFinish(x, y);
        }

        out.close();
    }

    /**
     * DOCUMENT ME!
     */
    private void drawBackRightDodeca() {
        int x;

        PNGImage out = new PNGImage("back_right", width, height, numFaces, backFlag, faces);

        // Draw the right end

        for (int y = 0; y < height / 2; y++) {
            x = out.rowStart(y, false);

            x += out.drawRow(y, 8, e, 180);
            x += out.drawRow(y, 7, f, 180);
            x += out.drawRow(y, 7, e, 180);
            x += out.drawRow(y, 6, f, 180);
            x += out.drawRow(y, 6, e, 180);
            x += out.drawRow(y, 11, d, 300);
            x += out.drawRow(y, 11, a, 300);
            x += out.drawRow(y, 10, d, 300);
            x += out.drawRow(y, 10, a, 300);
            x += out.drawRow(y, 9, d, 300);

            x += out.rowFinish(x, y);
        }

        // Draw the left end

        for (int y = 0; y < height / 2; y++) {
            x = out.rowStart(y, true);

            x += out.drawRow(y, 6, b, 60);
            x += out.drawRow(y, 6, c, 60);
            x += out.drawRow(y, 11, f, 180);
            x += out.drawRow(y, 11, e, 180);
            x += out.drawRow(y, 10, f, 180);
            x += out.drawRow(y, 10, e, 180);
            x += out.drawRow(y, 9, f, 180);
            x += out.drawRow(y, 9, e, 180);
            x += out.drawRow(y, 8, f, 180);
            x += out.drawRow(y, 8, e, 180);

            x += out.rowFinish(x, y);
        }

        out.close();
    }

    /**
     * DOCUMENT ME!
     */
    private void drawBackLeftDodeca() {
        int x;

        PNGImage out = new PNGImage("back_left", width, height, numFaces, backFlag, faces);

        // Draw the right end

        for (int y = 0; y < height / 2; y++) {
            x = out.rowStart(y, false);

            x += out.drawRow(y, 11, c, 60);
            x += out.drawRow(y, 10, b, 60);
            x += out.drawRow(y, 10, c, 60);
            x += out.drawRow(y, 9, b, 60);
            x += out.drawRow(y, 9, c, 60);
            x += out.drawRow(y, 8, b, 60);
            x += out.drawRow(y, 8, c, 60);
            x += out.drawRow(y, 7, b, 60);
            x += out.drawRow(y, 7, c, 60);
            x += out.drawRow(y, 6, b, 60);

            x += out.rowFinish(x, y);
        }

        // Draw the left end

        for (int y = 0; y < height / 2; y++) {
            x = out.rowStart(y, true);

            x += out.drawRow(y, 9, d, 300);
            x += out.drawRow(y, 9, a, 300);
            x += out.drawRow(y, 8, d, 300);
            x += out.drawRow(y, 8, a, 300);
            x += out.drawRow(y, 7, d, 300);
            x += out.drawRow(y, 7, a, 300);
            x += out.drawRow(y, 6, d, 300);
            x += out.drawRow(y, 6, a, 300);
            x += out.drawRow(y, 11, b, 60);
            x += out.drawRow(y, 11, c, 60);

            x += out.rowFinish(x, y);
        }

        out.close();
    }
}
