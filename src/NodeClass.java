import java.util.ArrayList;
import java.util.List;

public class NodeClass extends Point {
    public double g;
    public double h;
    public double f;
    public NodeClass parent;
    public List<NodeClass> neighbors;

    public NodeClass(int x, int y, double g, double h, double f) {
        super(x, y);
        this.g = g;
        this.h = h;
        this.f = f;
        this.parent = null;
        this.neighbors = new ArrayList<>();
    }

     public void addNeighbor(NodeClass neighbor) {
        neighbors.add(neighbor);
     }
}
