import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("image.png"));
            Quadtree quadtree = new Quadtree(image);
            BufferedImage segmented = quadtree.segment();
            ImageIO.write(segmented, "png", new File("afterQuadtree.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int step = 7;
        List<Vertex> points = ImagePointGenerator.generatePointsFromImage("image4.png", step);
        List<Triangle> triangles = DelaunayTriangulation.triangulate(points);
        BufferedImage afterTriangulation = ImageIO.read(new File("image4.png"));
        TriangleDrawer tD = new TriangleDrawer();
        tD.drawTriangles(afterTriangulation, triangles, Color.red);
        Fill fill = new Fill();
        fill.writeMatrixToFile("image4.png");
        fill.writeVerticesToFile(points);
        fill.writeElementsToFile(triangles);
    }
}