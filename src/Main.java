import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            BufferedImage image = ImageIO.read(new File("image2.png"));
            Quadtree quadtree = new Quadtree(image);
            BufferedImage segmented = quadtree.segment();
            ImageIO.write(segmented, "png", new File("segmented.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}