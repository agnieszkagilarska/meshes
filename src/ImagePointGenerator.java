import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImagePointGenerator {
    private static final int BLACK_COLOR = Color.BLACK.getRGB();

    public static List<Vertex> generatePointsFromImage(String imagePath, int step) {
        List<Vertex> points = new ArrayList<>();
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));

            int width = image.getWidth();
            int height = image.getHeight();
            int id = 0;
            // Przetwarzanie pikseli obrazu
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Sprawdzenie czy piksel jest czarny
                    if (image.getRGB(x, y) == BLACK_COLOR) {
                        // Sprawdzenie czy piksel jest na granicy kształtu
                        if (isBoundaryPixel(image, x, y)) {
                            points.add(new Vertex(id, x, y, true));
                            id++;
                        }
                        if (x % step == 0 && y % step == 0) {
                            points.add(new Vertex(id, x, y, true));
                            id++;
                        }

                    }
                }

            }
            return points;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isBoundaryPixel(BufferedImage image, int x, int y) {
        int width = image.getWidth();
        int height = image.getHeight();

        if (x > 0 && image.getRGB(x - 1, y) != BLACK_COLOR) {
            return true;
        }
        if (x < width - 1 && image.getRGB(x + 1, y) != BLACK_COLOR) {
            return true;
        }
        if (y > 0 && image.getRGB(x, y - 1) != BLACK_COLOR) {
            return true;
        }
        if (y < height - 1 && image.getRGB(x, y + 1) != BLACK_COLOR) {
            return true;
        }

        return false;
    }
}