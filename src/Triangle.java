public class Triangle {
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

        this.calcCircumcircle();
    }

    private void calcCircumcircle() {
        // From: http://www.exaflop.org/docs/cgafaq/cga1.html

        double A = this.v1.x - this.v0.x;
        double B = this.v1.y - this.v0.y;
        double C = this.v2.x - this.v0.x;
        double D = this.v2.y - this.v0.y;

        double E = A * (this.v0.x + this.v1.x) + B * (this.v0.y + this.v1.y);
        double F = C * (this.v0.x + this.v2.x) + D * (this.v0.y + this.v2.y);

        double G = 2.0 * (A * (this.v2.y - this.v1.y) - B * (this.v2.x - this.v1.x));

        double dx, dy;

        if (Math.abs(G) < EPSILON) {
            // Collinear - find extremes and use the midpoint

            double minx = Math.min(this.v0.x, Math.min(this.v1.x, this.v2.x));
            double miny = Math.min(this.v0.y, Math.min(this.v1.y, this.v2.y));
            double maxx = Math.max(this.v0.x, Math.max(this.v1.x, this.v2.x));
            double maxy = Math.max(this.v0.y, Math.max(this.v1.y, this.v2.y));

            this.center = new Vertex((minx + maxx) / 2, (miny + maxy) / 2);

            dx = this.center.x - minx;
            dy = this.center.y - miny;
        } else {
            double cx = (D * E - B * F) / G;
            double cy = (A * F - C * E) / G;

            this.center = new Vertex(cx, cy);

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


}
