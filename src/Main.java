import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {

    private static int step = 5 ;
    private static JFrame frame;
    private static BufferedImage originalImage;
    private static BufferedImage currentImage;
    private static String currentImagePath;
    public static void main(String[] args) throws IOException {
        originalImage = ImageIO.read(new File("image4.png"));
        currentImagePath= "image4.png";
        currentImage = originalImage;
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        frame = new JFrame("Image Processing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 800);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu imageMenu = new JMenu("Images");
        menuBar.add(imageMenu);



        JMenuItem menuItemOriginal = new JMenuItem("Original Image");
        menuItemOriginal.addActionListener(e -> {
            currentImage = originalImage;
            currentImagePath= "image4.png";
            displayImage(currentImage);
        });
        imageMenu.add(menuItemOriginal);

        JMenuItem menuItemImage1 = new JMenuItem("Image 1");
        menuItemImage1.addActionListener(e -> {
            try {
                BufferedImage image = ImageIO.read(new File("image1.png"));
                currentImage = image;
                currentImagePath= "image1.png";
                displayImage(currentImage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        imageMenu.add(menuItemImage1);

        JMenuItem menuItemImage2 = new JMenuItem("Image 2");
        menuItemImage2.addActionListener(e -> {
            try {
                BufferedImage image = ImageIO.read(new File("image2.png"));
                currentImage = image;
                currentImagePath= "image2.png";
                displayImage(currentImage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        imageMenu.add(menuItemImage2);

        JMenuItem menuItemImage3 = new JMenuItem("Image 3");
        menuItemImage3.addActionListener(e -> {
            try {
                BufferedImage image = ImageIO.read(new File("image3.png"));
                currentImage = image;
                currentImagePath= "image3.png";
                displayImage(currentImage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        imageMenu.add(menuItemImage3);


        JMenu meshMenu = new JMenu("Mesh");
        menuBar.add(meshMenu);

        JMenuItem menuItem1 = new JMenuItem("QuadTree");
        menuItem1.addActionListener(e -> {
            try {
                displayImage(generateImage1());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        meshMenu.add(menuItem1);

        JMenuItem menuItem2 = new JMenuItem("Triangulation");
       menuItem2.addActionListener(e -> {
            try {
                displayImage(generateImage2());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        meshMenu.add(menuItem2);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));


        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);

        displayImage(originalImage);

        frame.setVisible(true);


        JMenu optionsMenu = new JMenu("Export");
        menuBar.add(optionsMenu);

        JMenuItem optionsItem0 = new JMenuItem("Matrix 2D");
        optionsItem0.addActionListener(e -> {
            try {

               generateMatrix();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        optionsMenu.add(optionsItem0);


        JMenuItem optionsItem1 = new JMenuItem("Save Vertices");
        optionsItem1.addActionListener(e -> {
            try {

                generateVertex();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        optionsMenu.add(optionsItem1);

        JMenuItem optionsItem2 = new JMenuItem("Save connectivity matrix");
        optionsItem2.addActionListener(e -> {
            try {

                generateConnectivityMatrix();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        optionsMenu.add(optionsItem2);
    }

    private static void generateConnectivityMatrix() throws IOException {
        List<Vertex> points = ImagePointGenerator.generatePointsFromImage(currentImagePath, step);
        List<Triangle> triangles = DelaunayTriangulation.triangulate(points);
        Fill fill = new Fill();
        fill.writeElementsToFile(triangles);

        JOptionPane.showMessageDialog(null, "Dane zostały zapisane w postaci macierzy polaczeń.", "Zapisano dane dla "+ currentImagePath, JOptionPane.INFORMATION_MESSAGE);

    }

    private static void generateVertex() throws IOException {
        Fill fill = new Fill();
        List<Vertex> points = ImagePointGenerator.generatePointsFromImage(currentImagePath, step);
        fill.writeVerticesToFile(points);
        JOptionPane.showMessageDialog(null, "Dane zostały zapisane.", "Zapisano dane dla "+ currentImagePath, JOptionPane.INFORMATION_MESSAGE);

    }

    private static void generateMatrix() throws IOException {
        Fill fill = new Fill();
        fill.writeMatrixToFile(currentImagePath);
        JOptionPane.showMessageDialog(null, "Dane zostały zapisane w postaci macierzy 2D.", "Zapisano dane dla "+ currentImagePath, JOptionPane.INFORMATION_MESSAGE);

    }

    private static void displayImage(BufferedImage image) {
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        frame.getContentPane().removeAll();
        frame.getContentPane().add(imageLabel, BorderLayout.CENTER);
        frame.getContentPane().revalidate();
    }

    private static BufferedImage generateImage1() throws IOException {

        BufferedImage image;
        image = ImageIO.read(new File(currentImagePath));
        Quadtree quadtree = new Quadtree(image);
        BufferedImage segmented = quadtree.segment();
        ImageIO.write(segmented, "png", new File("afterQuadtree.png"));
        image = ImageIO.read(new File("afterQuadtree.png"));
        return image;
    }
    private static BufferedImage generateImage0() throws IOException {
        BufferedImage image;
        image = ImageIO.read(new File(String.valueOf(currentImage)));
        return image;
    }

    private static BufferedImage generateImage2() throws IOException {
        List<Vertex> points = ImagePointGenerator.generatePointsFromImage(currentImagePath, step);
        List<Triangle> triangles = DelaunayTriangulation.triangulate(points);
        BufferedImage afterTriangulation = ImageIO.read(new File(currentImagePath));
        TriangleDrawer tD = new TriangleDrawer();
        tD.drawTriangles(afterTriangulation, triangles, Color.red);
        BufferedImage triangulation = ImageIO.read(new File("afterTriangulation.png"));



        return triangulation;
    }

    private static void exportImage() {
        // Logika eksportu obrazu
    }

    private static void addImage() {
        // Logika dodawania obrazu
    }
}
