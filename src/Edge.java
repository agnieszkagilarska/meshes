public class Edge {
        private Point start;
        private Point end;

        public Edge(Point start, Point end) {
            this.start = start;
            this.end = end;
        }

        public boolean isCommon(Edge other) {
            if (start.equals(other.start) || start.equals(other.end)
                    || end.equals(other.start) || end.equals(other.end)) {
                return true;
            }
            return false;
        }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }
}

