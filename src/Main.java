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
        int step = 300;
        List<D.Vertex> points = ImagePointGenerator.generatePointsFromImage(imagePath, step);
//
        List<D.Triangle> triangles = D.triangulate(points);
        BufferedImage afterTriangulation = ImageIO.read(new File("image3.png"));
        TriangleDrawer tD = new TriangleDrawer();
        tD.drawTriangles(afterTriangulation, triangles, Color.red);
        //ImageIO.write(afterTriangulation, "png", new File("afterTriangulation.png"));
    }
}