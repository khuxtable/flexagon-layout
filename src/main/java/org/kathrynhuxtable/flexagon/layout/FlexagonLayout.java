package org.kathrynhuxtable.flexagon.layout;

import javax.swing.JFrame;

/**
 * Application which takes images and lays them out on A flexagon.<br>
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

    private String filePrefix       = "fleximage";
    private String hexaFront        = "_front";
    private String hexaBack         = "_back";
    private String dodecaFrontLeft  = "_front_left";
    private String dodecaFrontRight = "_front_right";
    private String dodecaBackLeft   = "_back_left";
    private String dodecaBackRight  = "_back_right";

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

    java.awt.MenuBar  menuBar1;
    java.awt.Menu     menu1;
    java.awt.MenuItem menuItem1;
    java.awt.MenuItem menuItem2;

    /**
     * Creates A new FlexagonLayout object.
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
     * @param f F DOCUMENT ME!
     */
    private void getImage(int f) {
        try {
            System.out.println("Getting pixels for " + imageNames[f]);
            faces[f] = new FlexagonFace(imageNames[f], width, height);
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
        // System.exit(0);
    }

    /**
     * Generate the layout
     */

    public void generate() {
        // Get the image pixels for each face
        getImages();

        if (numFaces == 6) {
            generateHexaFront();
            generateHexaBack();
        } else {
            generateDodecaFrontLeft();
            generateDodecaFrontRight();
            generateDodecaBackLeft();
            generateDodecaBackRight();
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void getImages() {
        faces = new FlexagonFace[numFaces];
        for (int f = 0; f < numFaces; f++) {
            getImage(f);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void generateHexaFront() {
        PNGImage out = new PNGImage(width, height);

        // Draw the left end
        for (int y = 0; y < height / 2; y++) {
            out.rowStart(y, false);
            out.drawRow(y, faces[0], FlexagonFace.Facet.B, 120);
            out.drawRow(y, faces[1], FlexagonFace.Facet.C, 120);
            out.drawRow(y, faces[2], FlexagonFace.Facet.F, 240);
            out.drawRow(y, faces[0], FlexagonFace.Facet.C, 120);
            out.drawRow(y, faces[1], FlexagonFace.Facet.F, 240);
            out.drawRow(y, faces[2], FlexagonFace.Facet.E, 240);
            out.drawRow(y, faces[0], FlexagonFace.Facet.F, 240);
            out.drawRow(y, faces[1], FlexagonFace.Facet.E, 240);
            out.drawRow(y, faces[2], FlexagonFace.Facet.D, 0);
            out.drawRow(y, faces[0], FlexagonFace.Facet.E, 240);
            out.rowFinish();
        }

        // Draw the right end
        for (int y = 0; y < height / 2; y++) {
            out.rowStart(y, true);
            out.drawRow(y, faces[0], FlexagonFace.Facet.E, 240);
            out.drawRow(y, faces[1], FlexagonFace.Facet.D, 0);
            out.drawRow(y, faces[2], FlexagonFace.Facet.A, 0);
            out.drawRow(y, faces[0], FlexagonFace.Facet.D, 0);
            out.drawRow(y, faces[1], FlexagonFace.Facet.A, 0);
            out.drawRow(y, faces[2], FlexagonFace.Facet.B, 120);
            out.drawRow(y, faces[0], FlexagonFace.Facet.A, 0);
            out.drawRow(y, faces[1], FlexagonFace.Facet.B, 120);
            out.drawRow(y, faces[2], FlexagonFace.Facet.C, 120);
            out.drawRow(y, faces[0], FlexagonFace.Facet.B, 120);
            out.rowFinish();
        }

        out.close(filePrefix + hexaFront);
    }

    /**
     * DOCUMENT ME!
     */
    private void generateHexaBack() {
        PNGImage out = new PNGImage(width, height);

        // Draw the right end
        for (int y = 0; y < height / 2; y++) {
            out.rowStart(y, false);
            out.drawRow(y, faces[3], FlexagonFace.Facet.A, 300);
            out.drawRow(y, faces[5], FlexagonFace.Facet.B, 60);
            out.drawRow(y, faces[5], FlexagonFace.Facet.C, 60);
            out.drawRow(y, faces[4], FlexagonFace.Facet.B, 60);
            out.drawRow(y, faces[4], FlexagonFace.Facet.C, 60);
            out.drawRow(y, faces[3], FlexagonFace.Facet.B, 60);
            out.drawRow(y, faces[3], FlexagonFace.Facet.C, 60);
            out.drawRow(y, faces[5], FlexagonFace.Facet.F, 180);
            out.drawRow(y, faces[5], FlexagonFace.Facet.E, 180);
            out.drawRow(y, faces[4], FlexagonFace.Facet.F, 180);
            out.rowFinish();
        }

        // Draw the left end
        for (int y = 0; y < height / 2; y++) {
            out.rowStart(y, true);
            out.drawRow(y, faces[4], FlexagonFace.Facet.F, 180);
            out.drawRow(y, faces[4], FlexagonFace.Facet.E, 180);
            out.drawRow(y, faces[3], FlexagonFace.Facet.F, 180);
            out.drawRow(y, faces[3], FlexagonFace.Facet.E, 180);
            out.drawRow(y, faces[5], FlexagonFace.Facet.D, 300);
            out.drawRow(y, faces[5], FlexagonFace.Facet.A, 300);
            out.drawRow(y, faces[4], FlexagonFace.Facet.D, 300);
            out.drawRow(y, faces[4], FlexagonFace.Facet.A, 300);
            out.drawRow(y, faces[3], FlexagonFace.Facet.D, 300);
            out.drawRow(y, faces[3], FlexagonFace.Facet.A, 300);
            out.rowFinish();
        }

        out.close(filePrefix + hexaBack);
    }

    /**
     * DOCUMENT ME!
     */
    private void generateDodecaFrontLeft() {
        PNGImage out = new PNGImage(width, height);

        // Draw the left end
        for (int y = 0; y < height / 2; y++) {
            out.rowStart(y, false);
            out.drawRow(y, faces[4], FlexagonFace.Facet.F, 240);
            out.drawRow(y, faces[0], FlexagonFace.Facet.B, 180);
            out.drawRow(y, faces[1], FlexagonFace.Facet.C, 180);
            out.drawRow(y, faces[4], FlexagonFace.Facet.E, 240);
            out.drawRow(y, faces[3], FlexagonFace.Facet.F, 240);
            out.drawRow(y, faces[2], FlexagonFace.Facet.F, 300);
            out.drawRow(y, faces[0], FlexagonFace.Facet.C, 180);
            out.drawRow(y, faces[3], FlexagonFace.Facet.E, 240);
            out.drawRow(y, faces[5], FlexagonFace.Facet.D, 0);
            out.drawRow(y, faces[1], FlexagonFace.Facet.F, 300);
            out.rowFinish();
        }

        // Draw the right end
        for (int y = 0; y < height / 2; y++) {
            out.rowStart(y, true);
            out.drawRow(y, faces[1], FlexagonFace.Facet.F, 300);
            out.drawRow(y, faces[2], FlexagonFace.Facet.E, 300);
            out.drawRow(y, faces[5], FlexagonFace.Facet.A, 0);
            out.drawRow(y, faces[4], FlexagonFace.Facet.D, 0);
            out.drawRow(y, faces[0], FlexagonFace.Facet.F, 300);
            out.drawRow(y, faces[1], FlexagonFace.Facet.E, 300);
            out.drawRow(y, faces[4], FlexagonFace.Facet.A, 0);
            out.drawRow(y, faces[3], FlexagonFace.Facet.D, 0);
            out.drawRow(y, faces[2], FlexagonFace.Facet.D, 60);
            out.drawRow(y, faces[0], FlexagonFace.Facet.E, 300);
            out.rowFinish();
        }

        out.close(filePrefix + dodecaFrontLeft);
    }

    /**
     * DOCUMENT ME!
     */
    private void generateDodecaFrontRight() {
        PNGImage out = new PNGImage(width, height);

        // Draw the left end
        for (int y = 0; y < height / 2; y++) {
            out.rowStart(y, false);
            out.drawRow(y, faces[0], FlexagonFace.Facet.E, 300);
            out.drawRow(y, faces[3], FlexagonFace.Facet.A, 0);
            out.drawRow(y, faces[5], FlexagonFace.Facet.B, 120);
            out.drawRow(y, faces[1], FlexagonFace.Facet.D, 60);
            out.drawRow(y, faces[2], FlexagonFace.Facet.A, 60);
            out.drawRow(y, faces[5], FlexagonFace.Facet.C, 120);
            out.drawRow(y, faces[4], FlexagonFace.Facet.B, 120);
            out.drawRow(y, faces[0], FlexagonFace.Facet.D, 60);
            out.drawRow(y, faces[1], FlexagonFace.Facet.A, 60);
            out.drawRow(y, faces[4], FlexagonFace.Facet.C, 120);
            out.rowFinish();
        }

        // Draw the right end
        for (int y = 0; y < height / 2; y++) {
            out.rowStart(y, true);
            out.drawRow(y, faces[4], FlexagonFace.Facet.C, 120);
            out.drawRow(y, faces[3], FlexagonFace.Facet.B, 120);
            out.drawRow(y, faces[2], FlexagonFace.Facet.B, 180);
            out.drawRow(y, faces[0], FlexagonFace.Facet.A, 60);
            out.drawRow(y, faces[3], FlexagonFace.Facet.C, 120);
            out.drawRow(y, faces[5], FlexagonFace.Facet.F, 240);
            out.drawRow(y, faces[1], FlexagonFace.Facet.B, 180);
            out.drawRow(y, faces[2], FlexagonFace.Facet.C, 180);
            out.drawRow(y, faces[5], FlexagonFace.Facet.E, 240);
            out.drawRow(y, faces[4], FlexagonFace.Facet.F, 240);
            out.rowFinish();
        }

        out.close(filePrefix + dodecaFrontRight);
    }

    /**
     * DOCUMENT ME!
     */
    private void generateDodecaBackRight() {
        PNGImage out = new PNGImage(width, height);

        // Draw the right end
        for (int y = 0; y < height / 2; y++) {
            out.rowStart(y, false);
            out.drawRow(y, faces[8], FlexagonFace.Facet.E, 180);
            out.drawRow(y, faces[7], FlexagonFace.Facet.F, 180);
            out.drawRow(y, faces[7], FlexagonFace.Facet.E, 180);
            out.drawRow(y, faces[6], FlexagonFace.Facet.F, 180);
            out.drawRow(y, faces[6], FlexagonFace.Facet.E, 180);
            out.drawRow(y, faces[11], FlexagonFace.Facet.D, 300);
            out.drawRow(y, faces[11], FlexagonFace.Facet.A, 300);
            out.drawRow(y, faces[10], FlexagonFace.Facet.D, 300);
            out.drawRow(y, faces[10], FlexagonFace.Facet.A, 300);
            out.drawRow(y, faces[9], FlexagonFace.Facet.D, 300);
            out.rowFinish();
        }

        // Draw the left end
        for (int y = 0; y < height / 2; y++) {
            out.rowStart(y, true);
            out.drawRow(y, faces[6], FlexagonFace.Facet.B, 60);
            out.drawRow(y, faces[6], FlexagonFace.Facet.C, 60);
            out.drawRow(y, faces[11], FlexagonFace.Facet.F, 180);
            out.drawRow(y, faces[11], FlexagonFace.Facet.E, 180);
            out.drawRow(y, faces[10], FlexagonFace.Facet.F, 180);
            out.drawRow(y, faces[10], FlexagonFace.Facet.E, 180);
            out.drawRow(y, faces[9], FlexagonFace.Facet.F, 180);
            out.drawRow(y, faces[9], FlexagonFace.Facet.E, 180);
            out.drawRow(y, faces[8], FlexagonFace.Facet.F, 180);
            out.drawRow(y, faces[8], FlexagonFace.Facet.E, 180);
            out.rowFinish();
        }

        out.close(filePrefix + dodecaBackRight);
    }

    /**
     * DOCUMENT ME!
     */
    private void generateDodecaBackLeft() {
        PNGImage out = new PNGImage(width, height);

        // Draw the right end
        for (int y = 0; y < height / 2; y++) {
            out.rowStart(y, false);
            out.drawRow(y, faces[11], FlexagonFace.Facet.C, 60);
            out.drawRow(y, faces[10], FlexagonFace.Facet.B, 60);
            out.drawRow(y, faces[10], FlexagonFace.Facet.C, 60);
            out.drawRow(y, faces[9], FlexagonFace.Facet.B, 60);
            out.drawRow(y, faces[9], FlexagonFace.Facet.C, 60);
            out.drawRow(y, faces[8], FlexagonFace.Facet.B, 60);
            out.drawRow(y, faces[8], FlexagonFace.Facet.C, 60);
            out.drawRow(y, faces[7], FlexagonFace.Facet.B, 60);
            out.drawRow(y, faces[7], FlexagonFace.Facet.C, 60);
            out.drawRow(y, faces[6], FlexagonFace.Facet.B, 60);
            out.rowFinish();
        }

        // Draw the left end
        for (int y = 0; y < height / 2; y++) {
            out.rowStart(y, true);
            out.drawRow(y, faces[9], FlexagonFace.Facet.D, 300);
            out.drawRow(y, faces[9], FlexagonFace.Facet.A, 300);
            out.drawRow(y, faces[8], FlexagonFace.Facet.D, 300);
            out.drawRow(y, faces[8], FlexagonFace.Facet.A, 300);
            out.drawRow(y, faces[7], FlexagonFace.Facet.D, 300);
            out.drawRow(y, faces[7], FlexagonFace.Facet.A, 300);
            out.drawRow(y, faces[6], FlexagonFace.Facet.D, 300);
            out.drawRow(y, faces[6], FlexagonFace.Facet.A, 300);
            out.drawRow(y, faces[11], FlexagonFace.Facet.B, 60);
            out.drawRow(y, faces[11], FlexagonFace.Facet.C, 60);
            out.rowFinish();
        }

        out.close(filePrefix + dodecaBackLeft);
    }
}
