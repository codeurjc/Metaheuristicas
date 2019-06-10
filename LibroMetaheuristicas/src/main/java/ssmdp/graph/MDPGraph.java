package ssmdp.graph;

import java.util.ArrayList;
import java.util.List;
import ssmdp.util.Weighted;

public class MDPGraph {

    private float weights[][];
    private List<Node> nodes;
    private List<Weighted<Node>> nodesDistance;
    private int numSolutionNodes = 0;
    private String name;

    public MDPGraph(int numSolutionNodes, float[][] weights) {
        this("Unknow",numSolutionNodes,weights);
    }

    public MDPGraph(String name, int numSolutionNodes, float[][] weights) {
        this.name = name;
        this.numSolutionNodes = numSolutionNodes;
        this.weights = weights;
        nodes = new ArrayList<Node>();
        for (int i = 0; i < weights.length + 1; i++) {
            nodes.add(new Node(i, this));
        }
    }

    public Node getNode(int index) {
        return nodes.get(index);
    }

    public float getWeight(int i, int j) {
        if(i == j){
            return 0;
        } else {
            return weights[Math.max(i, j) - 1][Math.min(i, j)];
        }
    }

    public int getNumNodes() {
        return nodes.size();
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public boolean isNodesDistanceCalculated() {
        return nodesDistance != null;
    }

    public List<Weighted<Node>> getNodesDistance() {
        if (nodesDistance == null) {
            nodesDistance = new ArrayList<Weighted<Node>>();

            for (Node n : getNodes()) {
                float weight = 0;
                for (Node on : getNodes()) {
                    weight += n.getDistanceTo(on);
                }
                nodesDistance.add(new Weighted<Node>(n, weight));
            }
        }

        return nodesDistance;
    }

    public String getName() {
        return name;
    }

    public int getNumSolutionNodes() {
        return numSolutionNodes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Number of Nodes: "
                + getNumNodes() + "\n");
        int numFila = 1;
        for (float[] fila : weights) {
            sb.append(numFila).append(": ");
            int col = 0;
            for (float valor : fila) {
                sb.append(valor).append(" ").append("[" + col + "]");
                col++;
            }
            sb.append("\n");
            numFila++;
        }
        return sb.toString();
    }

}
