import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FilledMatrix {
    public void writeToFile(String imagePath) throws IOException {
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
    }}