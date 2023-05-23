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
        int step = 10;
        List<Point> points = ImagePointGenerator.generatePointsFromImage(imagePath, step);

        // Tworzenie instancji triangulacji Delaunay
        DelaunayTriangulation delaunay = new DelaunayTriangulation(points);

        // Triangulacja
        delaunay.triangulate();
        List<Triangle> triangles = delaunay.getTriangles();
        BufferedImage afterTriangulation = ImageIO.read(new File("image2.png"));
        TriangleDrawer tD = new TriangleDrawer();
        tD.drawTriangles(afterTriangulation, triangles, Color.red);
        //ImageIO.write(afterTriangulation, "png", new File("afterTriangulation.png"));
    }
}