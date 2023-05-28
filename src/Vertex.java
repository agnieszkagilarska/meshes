public class Vertex {

    public double id;
    public double x;
    public double y;
    public boolean isBlack;

    public Vertex(double id, double x, double y) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public Vertex(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vertex(double id, double x, double y, boolean isBlack) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.isBlack = isBlack;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getId() {
        return id;
    }

    public boolean isBlack() {
        return isBlack;
    }

    public void setBlack(boolean black) {
        isBlack = black;
    }

}
