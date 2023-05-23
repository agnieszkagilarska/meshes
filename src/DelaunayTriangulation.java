import java.util.ArrayList;
import java.util.List;

public class DelaunayTriangulation {
    private List<Point> points;
    private List<Triangle> triangles;

    public DelaunayTriangulation(List<Point> points) {
        this.points = points;
        this.triangles = new ArrayList<>();
    }

    public void triangulate() {
        // Dodaj wszystkie punkty do listy triangulacji jako wierzchołki początkowych trójkątów
        triangles.add(new Triangle(points.get(0), points.get(1), points.get(2)));

        // Iteruj po pozostałych punktach
        for (int i = 3; i < points.size(); i++) {
            Point currentPoint = points.get(i);

            // Znajdź wszystkie trójkąty, których punkt ten jest wewnątrz
            List<Triangle> insideTriangles = new ArrayList<>();
            for (Triangle triangle : triangles) {
                if (triangle.isInsideCircumcircle(currentPoint)) {
                    insideTriangles.add(triangle);
                }
            }

            // Stwórz nowe krawędzie dla trójkątów wewnątrznych i usuń je z listy
            List<Edge> newEdges = new ArrayList<>();
            for (Triangle triangle : insideTriangles) {
                newEdges.add(new Edge(triangle.getp1(), triangle.getp2()));
                newEdges.add(new Edge(triangle.getp2(), triangle.getp3()));
                newEdges.add(new Edge(triangle.getp3(), triangle.getp1()));
                triangles.remove(triangle);
            }

            // Usuń wspólne krawędzie
            //removeCommonEdges(newEdges);

            // Dodaj nowe trójkąty utworzone przez nowe krawędzie
            for (Edge edge : newEdges) {
                triangles.add(new Triangle(edge.getStart(), edge.getEnd(), currentPoint));
            }
        }
    }

    private void removeCommonEdges(List<Edge> newEdges) {
        for (int i = 0; i < newEdges.size() - 1; i++) {
            Edge edge1 = newEdges.get(i);

            for (int j = i + 1; j < newEdges.size(); j++) {
                Edge edge2 = newEdges.get(j);

                if (edge1.isCommon(edge2)) {
                    newEdges.remove(j);
                    newEdges.remove(i);
                    i--;
                    break;
                }
            }
        }
    }

    public List<Point> getPoints() {
        return points;
    }

    public List<Triangle> getTriangles() {
        return triangles;
    }
}