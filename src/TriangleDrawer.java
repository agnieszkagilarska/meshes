import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class TriangleDrawer {
    public static BufferedImage drawTriangles(BufferedImage inputImage, List<Triangle> triangles, Color color) throws IOException {
        BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, null);
        g2d.setColor(color);


        for (Triangle triangle : triangles) {
            Vertex p1 = triangle.v0;
            Vertex p2 = triangle.v1;
            Vertex p3 = triangle.v2;
            double distance1 = Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
            double distance2 = Math.sqrt(Math.pow(p3.x - p2.x, 2) + Math.pow(p3.y - p2.y, 2));
            double distance3 = Math.sqrt(Math.pow(p1.x - p3.x, 2) + Math.pow(p1.y - p3.y, 2));

            if (distance1 < 70 && distance2 < 70 && distance3 < 70) {
                int[] xPoints = {(int) Math.round(p1.getX()), (int) Math.round(p2.getX()), (int) Math.round(p3.getX())};
                int[] yPoints = {(int) Math.round(p1.getY()), (int) Math.round(p2.getY()), (int) Math.round(p3.getY())};

                g2d.drawPolygon(xPoints, yPoints, 3);
            }
        }

        g2d.dispose();
        ImageIO.write(outputImage, "png", new File("afterTriangulation.png"));
        return outputImage;
    }
}