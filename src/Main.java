import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("image2.png"));
            Quadtree quadtree = new Quadtree(image);
            BufferedImage segmented = quadtree.segment();
            ImageIO.write(segmented, "png", new File("segmented.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String imagePath = "image3.png";
        int step = 50;
        List<Point> points = ImagePointGenerator.generatePointsFromImage(imagePath, step);
//        Point p1 = new Point(0, 25);
//        Point p2 = new Point(50, 0);
//        Point p3 = new Point(0, 50);
//        Point p4 = new Point(6, 4);
//        Point p5 = new Point(8, 6);
//
//        List<Point> points = new ArrayList<>();
//        points.add(p1);
//        points.add(p2);
//        points.add(p3);
//        points.add(p4);
//        points.add(p5);

        // Tworzenie instancji triangulacji Delaunay
        DelaunayTriangulation delaunay = new DelaunayTriangulation(points);

        // Triangulacja
        delaunay.triangulate();
        List<Triangle> triangles = delaunay.getTriangles();
        BufferedImage afterTriangulation = ImageIO.read(new File("image3.png"));
        TriangleDrawer tD = new TriangleDrawer();
        tD.drawTriangles(afterTriangulation, triangles, Color.red);
        //ImageIO.write(afterTriangulation, "png", new File("afterTriangulation.png"));
    }
}