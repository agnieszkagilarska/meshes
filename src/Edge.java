public class Edge {
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
