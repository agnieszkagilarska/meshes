public class Triangle {
    private Point p1;
    private Point p2;
    private Point p3;

    public Triangle(Point p1, Point p2, Point p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public Triangle(Point startPoint) {
    }

    public boolean isInsideCircumcircle(Point point) {
        double ax = p1.getX() - point.getX();
        double ay = p1.getY() - point.getY();
        double bx = p2.getX() - point.getX();
        double by = p2.getY() - point.getY();
        double cx = p3.getX() - point.getX();
        double cy = p3.getY() - point.getY();

        double d = (ax * (by * cx - bx * cy)) - (ay * (bx * cx - by));
        if (d > 0) {
            return true;
        }
        return false;
    }

    public Point getp1() {
        return p1;
    }

    public Point getp2() {
        return p2;
    }

    public Point getp3() {
        return p3;
    }
}