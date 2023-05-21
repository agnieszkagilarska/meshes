import java.awt.*;
import java.awt.image.BufferedImage;

public class Quadtree {

    private static final int MIN_QUADRANT_SIZE = 15;

    private BufferedImage image;
    private Node root;

    private class Node {
        private int x;
        private int y;
        private int sizeX;
        private int sizeY;
        private boolean isLeaf;
        private Node[] children;

        public Node(int x, int y, int sizeX, int sizeY) {
            this.x = x;
            this.y = y;
            this.sizeX = sizeX;
            this.sizeY = sizeY;
            this.isLeaf = true;
            this.children = new Node[4];
        }

        private boolean isColorUniform() {
            int baseColor = image.getRGB(x, y);
            for (int i = x; i < x + sizeX; i++) {
                for (int j = y; j < y + sizeY; j++) {
                    int currentColor = image.getRGB(i, j);
                    if (currentColor != baseColor) {
                        this.isLeaf = false;
                        return false;
                    }
                }
            }
            return true;
        }

        public void split() {
            // calculate the size and position of each child node
            int childSizeX = sizeX / 2;
            int childSizeY = sizeY / 2;
            int x0 = x;
            int y0 = y;
            int x1 = x;
            int y1 = y + childSizeY;
            int x2 = x + childSizeX;
            int y2 = y;
            int x3 = x + childSizeX;
            int y3 = y + childSizeY;

            // create four new child nodes
            children[0] = new Node(x0, y0, childSizeX, childSizeY);
            children[1] = new Node(x1, y1, childSizeX, childSizeY);
            children[2] = new Node(x2, y2, childSizeX, childSizeY);
            children[3] = new Node(x3, y3, childSizeX, childSizeY);
        }

    }

    public Quadtree(BufferedImage image) {
        this.image = image;
        root = new Node(0, 0, image.getWidth(), image.getHeight());
    }

    public BufferedImage segment() {
        segmentRecursive(root);
        draw(root);
        return image;
    }


    private void segmentRecursive(Node node) {
        if (node.isColorUniform()) {
            return;
        }
        node.split();
        for (int i = 0; i < 4; i++) {
            if (node.children[i] != null && node.sizeX > MIN_QUADRANT_SIZE) {
                segmentRecursive(node.children[i]);
            }
        }
    }

    private void draw(Node node) {
        if (node.isLeaf) {
            return;
        }

        int x1 = node.x;
        int y1 = node.y;
        int x2 = node.x + node.sizeX - 1;
        int y2 = node.y + node.sizeY - 1;
        int MidX = node.x + node.sizeX / 2;
        int MidY = node.y + node.sizeY / 2;

        for (int j = 0; j < 4; j++) {
            if (!node.children[j].isLeaf) {
                for (int i = x1; i <= x2; i++) {
                    image.setRGB(i, MidY, Color.RED.getRGB());
                }
                for (int i = y1; i <= y2; i++) {
                    image.setRGB(MidX, i, Color.RED.getRGB());
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            if (node.children != null && node.children[i] != null) {
                draw(node.children[i]);
            }
        }

    }


}