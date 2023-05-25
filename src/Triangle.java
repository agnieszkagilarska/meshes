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

//    public boolean isPointInside(Point point) {
//        double denominator = ((p2.getY() - p3.getY()) * (p1.getX() - p3.getX()) +
//                (p3.getX() - p2.getX()) * (p1.getY() - p3.getY()));
//
//        double a = ((p2.getY() - p3.getY()) * (point.getX() - p3.getX()) +
//                (p3.getX() - p2.getX()) * (point.getY() - p3.getY())) / denominator;
//
//        double b = ((p3.getY() - p1.getY()) * (point.getX() - p3.getX()) +
//                (p1.getX() - p3.getX()) * (point.getY() - p3.getY())) / denominator;
//
//        double c = 1.0 - a - b;
//
//        return (a >= 0 && b >= 0 && c >= 0);
//    }
public boolean isPointInside(Point point) {
    double d1 = sign(point, p1, p2);
    double d2 = sign(point, p2, p3);
    double d3 = sign(point, p3, p1);

    boolean hasNegative = (d1 < 0) || (d2 < 0) || (d3 < 0);
    boolean hasPositive = (d1 > 0) || (d2 > 0) || (d3 > 0);

    return !(hasNegative && hasPositive);
}

    private double sign(Point p1, Point p2, Point p3) {
        return (p1.getX() - p3.getX()) * (p2.getY() - p3.getY()) - (p2.getX() - p3.getX()) * (p1.getY() - p3.getY());
    }

    public boolean isInsideCircumcircle(Point point) {
        double ax = p1.getX() - point.getX();
        double ay = p1.getY() - point.getY();
        double bx = p2.getX() - point.getX();
        double by = p2.getY() - point.getY();
        double cx = p3.getX() - point.getX();
        double cy = p3.getY() - point.getY();

        double d = (ax * (by - cy)) - (bx * (ay - cy)) + (cx * (ay - by));
        if (d > 0) {
            return true;
        }
        return false;
    }
    public boolean hasVertex(Point point) {
        return p1.equals(point) || p2.equals(point) || p3.equals(point);
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