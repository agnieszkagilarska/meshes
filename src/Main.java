import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static JFrame frame;
    private static BufferedImage originalImage;

    public static void main(String[] args) throws IOException {
        originalImage = ImageIO.read(new File("image.png"));

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

        JButton exportButton = new JButton("Wyeksportuj");
        exportButton.addActionListener(e -> exportImage());

        JButton addButton = new JButton("Dodaj");
        addButton.addActionListener(e -> addImage());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(exportButton);
        buttonPanel.add(addButton);

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
        image = ImageIO.read(new File("afterQuadtree.png"));
        return image;
    }

    private static BufferedImage generateImage2() throws IOException {
        int step = 7;
        List<Vertex> points = ImagePointGenerator.generatePointsFromImage("afterTriangulation.png", step);
        List<Triangle> triangles = DelaunayTriangulation.triangulate(points);
        BufferedImage afterTriangulation = ImageIO.read(new File("afterTriangulation.png"));
        TriangleDrawer tD = new TriangleDrawer();
        tD.drawTriangles(afterTriangulation, triangles, Color.red);
        return afterTriangulation;
    }

    private static void exportImage() {
        // Logika eksportu obrazu
    }

    private static void addImage() {
        // Logika dodawania obrazu
    }
}
