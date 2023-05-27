import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Fill {
    public void writeMatrixToFile(String imagePath) throws IOException {
        BufferedImage image = ImageIO.read(new File(imagePath));
        int height = image.getHeight();
        int width = image.getWidth();
        int[][] matrix = new int[height][width];

        String fileName = "matrixFile.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Iterate over each pixel in the image
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    Color pixelColor = new Color(image.getRGB(j, i));
                    if (pixelColor.getRed() == 0 && pixelColor.getBlue() == 0 && pixelColor.getGreen() == 0) {
                        matrix[i][j] = 1;
                    } else {
                        matrix[i][j] = 0;
                    }

                    writer.write(Integer.toString(matrix[i][j]));
                    writer.write(" ");
                    j += 12;
                }
                writer.newLine();
                i += 12;
            }
        }
    }
    public void writeVerticesToFile(List<Vertex> vertices) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("verticesFile.txt"))) {
            for (Vertex vertex : vertices) {
                String line = vertex.getId() + ", " + vertex.getX() + ", " + vertex.getY();
                writer.write(line);
                writer.newLine();
            }
        }
    }
    public void writeElementsToFile(List<Triangle> elements) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("elemnts.txt"))) {
            for (Triangle element : elements) {
                String line = element.getId() + ", "
                        + element.getV0().getId() + ", "
                        + element.getV1().getId() + ", "
                        + element.getV2().getId() + ", "
                        + (element.isInsideBlack() ? "1" : "0");
                writer.write(line);
                writer.newLine();
            }
        }
    }
}