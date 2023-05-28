public class Triangle {
    public int id =0;
    public Vertex v0;
    public Vertex v1;
    public Vertex v2;
    public Vertex center;
    public double radius_squared;
    public double radius;
    private static final double EPSILON = 1.0e-6;

    public Triangle(Vertex v0, Vertex v1, Vertex v2) {
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
    }

    public Triangle(int id, Vertex v0, Vertex v1, Vertex v2) {
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
        this.id = id;
        this.calcCircumcircle();
    }

    private void calcCircumcircle() {

        double A = this.v1.x - this.v0.x;
        double B = this.v1.y - this.v0.y;
        double C = this.v2.x - this.v0.x;
        double D = this.v2.y - this.v0.y;

        double E = A * (this.v0.x + this.v1.x) + B * (this.v0.y + this.v1.y);
        double F = C * (this.v0.x + this.v2.x) + D * (this.v0.y + this.v2.y);

        double G = 2.0 * (A * (this.v2.y - this.v1.y) - B * (this.v2.x - this.v1.x));

        double dx, dy;

        if (Math.abs(G) < EPSILON) {

            double minx = Math.min(this.v0.x, Math.min(this.v1.x, this.v2.x));
            double miny = Math.min(this.v0.y, Math.min(this.v1.y, this.v2.y));
            double maxx = Math.max(this.v0.x, Math.max(this.v1.x, this.v2.x));
            double maxy = Math.max(this.v0.y, Math.max(this.v1.y, this.v2.y));

            this.center = new Vertex(-2, (minx + maxx) / 2, (miny + maxy) / 2);

            dx = this.center.x - minx;
            dy = this.center.y - miny;
        } else {
            double cx = (D * E - B * F) / G;
            double cy = (A * F - C * E) / G;

            this.center = new Vertex(-2, cx, cy);

            dx = this.center.x - this.v0.x;
            dy = this.center.y - this.v0.y;
        }

        this.radius_squared = dx * dx + dy * dy;
        this.radius = Math.sqrt(this.radius_squared);
    }

    public boolean inCircumcircle(Vertex v) {
        double dx = this.center.x - v.x;
        double dy = this.center.y - v.y;
        double dist_squared = dx * dx + dy * dy;

        return (dist_squared <= this.radius_squared);
    }

    public int getId() {
        return id;
    }

    public Vertex getV0() {
        return v0;
    }

    public Vertex getV1() {
        return v1;
    }


    public Vertex getV2() {
        return v2;
    }
    public boolean isInsideBlack() {
        double edge1Length = calculateEdgeLength(v0, v1);
        double edge2Length = calculateEdgeLength(v1, v2);
        double edge3Length = calculateEdgeLength(v2, v0);

        return edge1Length < 50 && edge2Length < 50 && edge3Length < 50;
    }

    private double calculateEdgeLength(Vertex v1, Vertex v2) {
        double dx = v2.x - v1.x;
        double dy = v2.y - v1.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

}
