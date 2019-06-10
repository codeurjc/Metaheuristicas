package ssmdp.graph;

public class Node {

    private int index;
    private MDPGraph graph;

    public Node(int index, MDPGraph graph) {
        this.index = index;
        this.graph = graph;
    }

    public float getDistanceTo(Node node) {
        return graph.getWeight(index, node.index);
    }

    public int getIndex() {
        return index;
    }

    public int compareTo(Node node) {
        return index - node.getIndex();
    }

    @Override
    public int hashCode() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Node) {
            Node mg = (Node) o;
            return index == mg.index;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return Integer.toString(index);
    }

}