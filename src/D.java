import java.util.ArrayList;
import java.util.List;

public class D {

    public static void main(String[] args) {
        // Tworzenie zbioru punktów
        List<D.Vertex> points = new ArrayList<>();
        points.add(new Vertex(0, 0));
        points.add(new Vertex(1.5, 0));
        //points.add(new Vertex()(0.5, 0.5));
        points.add(new Vertex(0, 1));
        points.add(new Vertex(1, 1));

        // Wywołanie triangulacji Delaunaya
        List<Triangle> triangles = D.triangulate(points);

        // Wyświetlanie trójkątów
        for (int i = 0; i < triangles.size(); i++) {
            System.out.println("Trójkąt " + (i + 1) + ": " + triangles.get(i));
        }
    }





    private static final double EPSILON = 1.0e-6;

    //------------------------------------------------------------
    // Vertex class
    //------------------------------------------------------------

    public static class Vertex {
        public double x;
        public double y;

        public Vertex(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }

    //------------------------------------------------------------
    // Triangle class
    //------------------------------------------------------------

    public static class Triangle {
        public Vertex v0;
        public Vertex v1;
        public Vertex v2;
        public Vertex center;
        public double radius_squared;
        public double radius;

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

    //------------------------------------------------------------
    // Edge class
    //------------------------------------------------------------

    public static class Edge {
        public Vertex v0;
        public Vertex v1;

        public Edge(Vertex v0, Vertex v1) {
            this.v0 = v0;
            this.v1 = v1;
        }

        public boolean equals(Edge other) {
            return (this.v0 == other.v0 && this.v1 == other.v1);
        }

        public Edge inverse() {
            return new Edge(this.v1, this.v0);
        }
    }

    //------------------------------------------------------------
    // triangulate
    //
    // Perform the Delaunay Triangulation of a set of vertices.
    //
    // vertices: List of Vertex objects
    //
    // returns: List of Triangles
    //------------------------------------------------------------
    public static List<Triangle> triangulate(List<Vertex> vertices) {
        List<Triangle> triangles = new ArrayList<>();

        //
        // First, create a "supertriangle" that bounds all vertices
        //
        Triangle st = createBoundingTriangle(vertices);

        triangles.add(st);

        //
        // Next, begin the triangulation one vertex at a time
        //
        for (Vertex vertex : vertices) {
            // NOTE: This is O(n^2) - can be optimized by sorting vertices
            // along the x-axis and only considering triangles that have
            // potentially overlapping circumcircles
            triangles = addVertex(vertex, triangles);
        }

        //
        // Remove triangles that shared edges with "supertriangle"
        //
        triangles.removeIf(triangle ->
                triangle.v0 == st.v0 || triangle.v0 == st.v1 || triangle.v0 == st.v2 ||
                        triangle.v1 == st.v0 || triangle.v1 == st.v1 || triangle.v1 == st.v2 ||
                        triangle.v2 == st.v0 || triangle.v2 == st.v1 || triangle.v2 == st.v2);

        return triangles;
    }

    // Internal: create a triangle that bounds the given vertices, with room to spare
    private static Triangle createBoundingTriangle(List<Vertex> vertices) {
        // NOTE: There's a bit of a heuristic here. If the bounding triangle
        // is too large and you see overflow/underflow errors. If it is too small
        // you end up with a non-convex hull.

        double minx, miny, maxx, maxy;
        minx = miny = Double.POSITIVE_INFINITY;
        maxx = maxy = Double.NEGATIVE_INFINITY;

        for (Vertex vertex : vertices) {
            minx = Math.min(minx, vertex.x);
            miny = Math.min(miny, vertex.y);
            maxx = Math.max(maxx, vertex.x);
            maxy = Math.max(maxy, vertex.y);
        }

        double dx = (maxx - minx) * 10;
        double dy = (maxy - miny) * 10;

        Vertex stv0 = new Vertex(minx - dx, miny - dy * 3);
        Vertex stv1 = new Vertex(minx - dx, maxy + dy);
        Vertex stv2 = new Vertex(maxx + dx * 3, maxy + dy);

        return new Triangle(stv0, stv1, stv2);
    }

    // Internal: update triangulation with a vertex
    private static List<Triangle> addVertex(Vertex vertex, List<Triangle> triangles) {
        List<Edge> edges = new ArrayList<>();

        // Remove triangles with circumcircles containing the vertex
        List<Edge> finalEdges = edges;
        triangles.removeIf(triangle -> {
            if (triangle.inCircumcircle(vertex)) {
                finalEdges.add(new Edge(triangle.v0, triangle.v1));
                finalEdges.add(new Edge(triangle.v1, triangle.v2));
                finalEdges.add(new Edge(triangle.v2, triangle.v0));
                return true;
            }
            return false;
        });

        edges = uniqueEdges(edges);

        // Create new triangles from the unique edges and new vertex
        for (Edge edge : edges) {
            triangles.add(new Triangle(edge.v0, edge.v1, vertex));
        }

        return triangles;
    }

    // Internal: remove duplicate edges from a list
    private static List<Edge> uniqueEdges(List<Edge> edges) {
        // TODO: This is O(n^2), make it O(n) with a hash or some such
        List<Edge> uniqueEdges = new ArrayList<>();
        for (int i = 0; i < edges.size(); ++i) {
            Edge edge1 = edges.get(i);
            boolean unique = true;

            for (int j = 0; j < edges.size(); ++j) {
                if (i == j)
                    continue;
                Edge edge2 = edges.get(j);
                if (edge1.equals(edge2) || edge1.inverse().equals(edge2)) {
                    unique = false;
                    break;
                }
            }

            if (unique)
                uniqueEdges.add(edge1);
        }

        return uniqueEdges;
    }
}