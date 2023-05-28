import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {

    private static JFrame frame;
    private static BufferedImage originalImage;

    public static void main(String[] args) throws IOException {
        originalImage = ImageIO.read(new File("image4.png"));

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


        JMenuItem menuItem0 = new JMenuItem("Empty image");
        menuItem0.addActionListener(e -> {
            try {
                displayImage(generateImage0());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        imageMenu.add(menuItem0);


        JMenuItem menuItem1 = new JMenuItem("QuadTree");
        menuItem1.addActionListener(e -> {
            try {
                displayImage(generateImage1());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        imageMenu.add(menuItem1);

        JMenuItem menuItem2 = new JMenuItem("Triangulation");
        menuItem2.addActionListener(e -> {
            try {
                displayImage(generateImage2());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        imageMenu.add(menuItem2);

        JMenu optionsMenu = new JMenu("Options");
        menuBar.add(imageMenu);

        JMenuItem optionsItem0 = new JMenuItem("Export");
        menuItem0.addActionListener(e -> {
            try {
                displayImage(generateImage0());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        imageMenu.add(menuItem0);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));


        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);

        displayImage(originalImage);

        frame.setVisible(true);
    }


    private static void displayImage(BufferedImage image) {
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        frame.getContentPane().removeAll();
        frame.getContentPane().add(imageLabel, BorderLayout.CENTER);
        frame.getContentPane().revalidate();
    }

    private static BufferedImage generateImage1() throws IOException {

        BufferedImage image;
        image = ImageIO.read(new File("image4.png"));
        Quadtree quadtree = new Quadtree(image);
        BufferedImage segmented = quadtree.segment();
        ImageIO.write(segmented, "png", new File("afterQuadtree.png"));
        image = ImageIO.read(new File("afterQuadtree.png"));
        return image;
    }
    private static BufferedImage generateImage0() throws IOException {
        BufferedImage image;
        image = ImageIO.read(new File("image4.png"));
        return image;
    }

    private static BufferedImage generateImage2() throws IOException {
        int step = 7;
        List<Vertex> points = ImagePointGenerator.generatePointsFromImage("image4.png", step);
        List<Triangle> triangles = DelaunayTriangulation.triangulate(points);
        BufferedImage afterTriangulation = ImageIO.read(new File("image4.png"));
        TriangleDrawer tD = new TriangleDrawer();
        tD.drawTriangles(afterTriangulation, triangles, Color.red);
        BufferedImage triangulation = ImageIO.read(new File("afterTriangulation.png"));


        Fill fill = new Fill();
        fill.writeMatrixToFile("image4.png");
        fill.writeVerticesToFile(points);
        fill.writeElementsToFile(triangles);

        JOptionPane.showMessageDialog(null, "Dane zostały zapisane w postaci macierzy polaczeń.", "Zapisano dane", JOptionPane.INFORMATION_MESSAGE);

        return triangulation;
    }

    private static void exportImage() {
        // Logika eksportu obrazu
    }

    private static void addImage() {
        // Logika dodawania obrazu
    }
}
