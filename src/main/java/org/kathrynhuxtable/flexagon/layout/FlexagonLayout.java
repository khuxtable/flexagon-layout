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

    private String file_prefix = "fleximage";
    private int    height      = 222; // height of images
    private int    width       = 256; // width of images

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

    private int    numFaces    = 12; // number of faces in flexagon

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
    private boolean        backFlag; // front or back of image?

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
        PNGImage out = new PNGImage(width, height);

        // Draw the left end

        for (int y = 0; y < height / 2; y++) {
            out.rowStart(y, false);

            out.drawRow(y, faces[0], b, 120);
            out.drawRow(y, faces[1], c, 120);
            out.drawRow(y, faces[2], f, 240);
            out.drawRow(y, faces[0], c, 120);
            out.drawRow(y, faces[1], f, 240);
            out.drawRow(y, faces[2], e, 240);
            out.drawRow(y, faces[0], f, 240);
            out.drawRow(y, faces[1], e, 240);
            out.drawRow(y, faces[2], d, 0);
            out.drawRow(y, faces[0], e, 240);

            out.rowFinish();
        }

        // Draw the right end

        for (int y = 0; y < height / 2; y++) {
            out.rowStart(y, true);

            out.drawRow(y, faces[0], e, 240);
            out.drawRow(y, faces[1], d, 0);
            out.drawRow(y, faces[2], a, 0);
            out.drawRow(y, faces[0], d, 0);
            out.drawRow(y, faces[1], a, 0);
            out.drawRow(y, faces[2], b, 120);
            out.drawRow(y, faces[0], a, 0);
            out.drawRow(y, faces[1], b, 120);
            out.drawRow(y, faces[2], c, 120);
            out.drawRow(y, faces[0], b, 120);

            out.rowFinish();
        }

        out.close(file_prefix + "_front");
    }

    /**
     * DOCUMENT ME!
     */
    private void drawBackHexa() {
        PNGImage out = new PNGImage(width, height);

        int delta = numFaces / 2;

        // Draw the right end

        for (int y = 0; y < height / 2; y++) {
            out.rowStart(y, false);

            out.drawRow(y, faces[3 - delta], a, 300);
            out.drawRow(y, faces[5 - delta], b, 60);
            out.drawRow(y, faces[5 - delta], c, 60);
            out.drawRow(y, faces[4 - delta], b, 60);
            out.drawRow(y, faces[4 - delta], c, 60);
            out.drawRow(y, faces[3 - delta], b, 60);
            out.drawRow(y, faces[3 - delta], c, 60);
            out.drawRow(y, faces[5 - delta], f, 180);
            out.drawRow(y, faces[5 - delta], e, 180);
            out.drawRow(y, faces[4 - delta], f, 180);

            out.rowFinish();
        }

        // Draw the left end

        for (int y = 0; y < height / 2; y++) {
            out.rowStart(y, true);

            out.drawRow(y, faces[4 - delta], f, 180);
            out.drawRow(y, faces[4 - delta], e, 180);
            out.drawRow(y, faces[3 - delta], f, 180);
            out.drawRow(y, faces[3 - delta], e, 180);
            out.drawRow(y, faces[5 - delta], d, 300);
            out.drawRow(y, faces[5 - delta], a, 300);
            out.drawRow(y, faces[4 - delta], d, 300);
            out.drawRow(y, faces[4 - delta], a, 300);
            out.drawRow(y, faces[3 - delta], d, 300);
            out.drawRow(y, faces[3 - delta], a, 300);

            out.rowFinish();
        }

        out.close(file_prefix + "_back");
    }

    /**
     * DOCUMENT ME!
     */
    private void drawFrontLeftDodeca() {
        PNGImage out = new PNGImage(width, height);

        // Draw the left end

        for (int y = 0; y < height / 2; y++) {
            out.rowStart(y, false);

            out.drawRow(y, faces[4], f, 240);
            out.drawRow(y, faces[0], b, 180);
            out.drawRow(y, faces[1], c, 180);
            out.drawRow(y, faces[4], e, 240);
            out.drawRow(y, faces[3], f, 240);
            out.drawRow(y, faces[2], f, 300);
            out.drawRow(y, faces[0], c, 180);
            out.drawRow(y, faces[3], e, 240);
            out.drawRow(y, faces[5], d, 0);
            out.drawRow(y, faces[1], f, 300);

            out.rowFinish();
        }

        // Draw the right end

        for (int y = 0; y < height / 2; y++) {
            out.rowStart(y, true);

            out.drawRow(y, faces[1], f, 300);
            out.drawRow(y, faces[2], e, 300);
            out.drawRow(y, faces[5], a, 0);
            out.drawRow(y, faces[4], d, 0);
            out.drawRow(y, faces[0], f, 300);
            out.drawRow(y, faces[1], e, 300);
            out.drawRow(y, faces[4], a, 0);
            out.drawRow(y, faces[3], d, 0);
            out.drawRow(y, faces[2], d, 60);
            out.drawRow(y, faces[0], e, 300);

            out.rowFinish();
        }

        out.close(file_prefix + "_front_left");
    }

    /**
     * DOCUMENT ME!
     */
    private void drawFrontRightDodeca() {
        PNGImage out = new PNGImage(width, height);

        // Draw the left end

        for (int y = 0; y < height / 2; y++) {
            out.rowStart(y, false);

            out.drawRow(y, faces[0], e, 300);
            out.drawRow(y, faces[3], a, 0);
            out.drawRow(y, faces[5], b, 120);
            out.drawRow(y, faces[1], d, 60);
            out.drawRow(y, faces[2], a, 60);
            out.drawRow(y, faces[5], c, 120);
            out.drawRow(y, faces[4], b, 120);
            out.drawRow(y, faces[0], d, 60);
            out.drawRow(y, faces[1], a, 60);
            out.drawRow(y, faces[4], c, 120);

            out.rowFinish();
        }

        // Draw the right end

        for (int y = 0; y < height / 2; y++) {
            out.rowStart(y, true);

            out.drawRow(y, faces[4], c, 120);
            out.drawRow(y, faces[3], b, 120);
            out.drawRow(y, faces[2], b, 180);
            out.drawRow(y, faces[0], a, 60);
            out.drawRow(y, faces[3], c, 120);
            out.drawRow(y, faces[5], f, 240);
            out.drawRow(y, faces[1], b, 180);
            out.drawRow(y, faces[2], c, 180);
            out.drawRow(y, faces[5], e, 240);
            out.drawRow(y, faces[4], f, 240);

            out.rowFinish();
        }

        out.close(file_prefix + "_front_right");
    }

    /**
     * DOCUMENT ME!
     */
    private void drawBackRightDodeca() {
        int delta = numFaces / 2;

        PNGImage out = new PNGImage(width, height);

        // Draw the right end

        for (int y = 0; y < height / 2; y++) {
            out.rowStart(y, false);

            out.drawRow(y, faces[8 - delta], e, 180);
            out.drawRow(y, faces[7 - delta], f, 180);
            out.drawRow(y, faces[7 - delta], e, 180);
            out.drawRow(y, faces[6 - delta], f, 180);
            out.drawRow(y, faces[6 - delta], e, 180);
            out.drawRow(y, faces[11 - delta], d, 300);
            out.drawRow(y, faces[11 - delta], a, 300);
            out.drawRow(y, faces[10 - delta], d, 300);
            out.drawRow(y, faces[10 - delta], a, 300);
            out.drawRow(y, faces[9 - delta], d, 300);

            out.rowFinish();
        }

        // Draw the left end

        for (int y = 0; y < height / 2; y++) {
            out.rowStart(y, true);

            out.drawRow(y, faces[6 - delta], b, 60);
            out.drawRow(y, faces[6 - delta], c, 60);
            out.drawRow(y, faces[11 - delta], f, 180);
            out.drawRow(y, faces[11 - delta], e, 180);
            out.drawRow(y, faces[10 - delta], f, 180);
            out.drawRow(y, faces[10 - delta], e, 180);
            out.drawRow(y, faces[9 - delta], f, 180);
            out.drawRow(y, faces[9 - delta], e, 180);
            out.drawRow(y, faces[8 - delta], f, 180);
            out.drawRow(y, faces[8 - delta], e, 180);

            out.rowFinish();
        }

        out.close(file_prefix + "_back_right");
    }

    /**
     * DOCUMENT ME!
     */
    private void drawBackLeftDodeca() {
        int delta = numFaces / 2;

        PNGImage out = new PNGImage(width, height);

        // Draw the right end

        for (int y = 0; y < height / 2; y++) {
            out.rowStart(y, false);

            out.drawRow(y, faces[11 - delta], c, 60);
            out.drawRow(y, faces[10 - delta], b, 60);
            out.drawRow(y, faces[10 - delta], c, 60);
            out.drawRow(y, faces[9 - delta], b, 60);
            out.drawRow(y, faces[9 - delta], c, 60);
            out.drawRow(y, faces[8 - delta], b, 60);
            out.drawRow(y, faces[8 - delta], c, 60);
            out.drawRow(y, faces[7 - delta], b, 60);
            out.drawRow(y, faces[7 - delta], c, 60);
            out.drawRow(y, faces[6 - delta], b, 60);

            out.rowFinish();
        }

        // Draw the left end

        for (int y = 0; y < height / 2; y++) {
            out.rowStart(y, true);

            out.drawRow(y, faces[9 - delta], d, 300);
            out.drawRow(y, faces[9 - delta], a, 300);
            out.drawRow(y, faces[8 - delta], d, 300);
            out.drawRow(y, faces[8 - delta], a, 300);
            out.drawRow(y, faces[7 - delta], d, 300);
            out.drawRow(y, faces[7 - delta], a, 300);
            out.drawRow(y, faces[6 - delta], d, 300);
            out.drawRow(y, faces[6 - delta], a, 300);
            out.drawRow(y, faces[11 - delta], b, 60);
            out.drawRow(y, faces[11 - delta], c, 60);

            out.rowFinish();
        }

        out.close(file_prefix + "_back_left");
    }
}
