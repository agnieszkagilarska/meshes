import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImagePointGenerator {
    private static final int BLACK_COLOR = Color.BLACK.getRGB();
    private static final int WHITE_COLOR = Color.WHITE.getRGB();

    public static List<Point> generatePointsFromImage(String imagePath, int step) {
        List<Point> points = new ArrayList<>();

        try {
            // Wczytanie obrazu z pliku PNG
            BufferedImage image = ImageIO.read(new File(imagePath));

            int width = image.getWidth();
            int height = image.getHeight();

            // Przetwarzanie pikseli obrazu
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Sprawdzenie czy piksel jest czarny
                    if (image.getRGB(x, y) == BLACK_COLOR) {
                        // Sprawdzenie czy piksel jest na granicy kształtu
                        if (isBoundaryPixel(image, x, y)) {
                            // Dodanie punktu do listy
                            points.add(new Point(x, y));
                        } else {
                            // Dodanie punktów wewnątrz czarnego obszaru z większym odstępem
                            if (x % step == 0 && y % step == 0) {
                                points.add(new Point(x, y));
                            }
                        }
                    } else if (image.getRGB(x, y) == WHITE_COLOR) {
                        // Dodanie punktów na białym tle z mniejszym odstępem
                        if (x % (step / 2) == 0 && y % (step / 2) == 0) {
                            points.add(new Point(x, y));
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return points;
    }

    private static boolean isBoundaryPixel(BufferedImage image, int x, int y) {
        int width = image.getWidth();
        int height = image.getHeight();

        // Sprawdzenie czy przynajmniej jeden z sąsiadujących pikseli nie jest czarny
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