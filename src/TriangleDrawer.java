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
            Point p1 = triangle.getp1();
            Point p2 = triangle.getp2();
            Point p3 = triangle.getp3();

            int[] xPoints = { (int) Math.round(p1.getX()), (int) Math.round(p2.getX()), (int) Math.round(p3.getX()) };
            int[] yPoints = { (int) Math.round(p1.getY()), (int) Math.round(p2.getY()), (int) Math.round(p3.getY()) };

            g2d.drawPolygon(xPoints, yPoints, 3);
            g2d.fillPolygon(xPoints, yPoints, 3);
        }

        g2d.dispose();
        ImageIO.write(outputImage, "png", new File("afterTriangulation.png"));
        return outputImage;
    }
}