import java.util.ArrayList;
import java.util.List;

public class DelaunayTriangulation {
    public static List<Triangle> triangulate(List<Vertex> vertices) {
        List<Triangle> triangles = new ArrayList<>();
        List<Vertex> selectedVertices = new ArrayList<>();

        for (int i = 4; i < vertices.size(); i += 5) {
            selectedVertices.add(vertices.get(i));
        }
        Triangle st = createBoundingTriangle(selectedVertices);

        triangles.add(st);

        for (Vertex vertex : selectedVertices) {
            triangles = addVertex(vertex, triangles);
        }
        triangles.removeIf(triangle ->
                triangle.v0 == st.v0 || triangle.v0 == st.v1 || triangle.v0 == st.v2 ||
                        triangle.v1 == st.v0 || triangle.v1 == st.v1 || triangle.v1 == st.v2 ||
                        triangle.v2 == st.v0 || triangle.v2 == st.v1 || triangle.v2 == st.v2);

        return triangles;
    }

    private static Triangle createBoundingTriangle(List<Vertex> vertices) {
        int id = 0;
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

        Vertex stv0 = new Vertex(-1,minx - dx, miny - dy * 3);
        Vertex stv1 = new Vertex(-1, minx - dx, maxy + dy);
        Vertex stv2 = new Vertex(-1, maxx + dx * 3, maxy + dy);

        return new Triangle(id, stv0, stv1, stv2);
    }

    private static List<Triangle> addVertex(Vertex vertex, List<Triangle> triangles) {
        int id = 0;
        List<Edge> edges = new ArrayList<>();
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

        for (Edge edge : edges) {
            triangles.add(new Triangle(id, edge.v0, edge.v1, vertex));
            id++;
        }

        return triangles;
    }

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