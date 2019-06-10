package ssmdp;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import ssmdp.graph.MDPGraph;
import ssmdp.graph.Node;
import ssmdp.util.Weighted;
import ssmdp.util.WeightedIterator;

import static ssmdp.util.Weighted.max;
import static ssmdp.util.Weighted.min;

public class MDPSolution implements Comparable<MDPSolution>,
        Iterable<Node> {

    private MDPGraph graph;

    private Weighted<Node> worstWNode = null;
    private Weighted<Node> bestWNode = null;

    private float totalWeight = 0;

    private List<Weighted<Node>> nodesDistance;
    private List<Node> nodes;
    private boolean[] nodesPresence;

    private int refSetPosition = -1;

    public MDPSolution(List<Node> nodes, MDPGraph totalGraph) {
        this.nodes = nodes;
        this.graph = totalGraph;
        this.nodesPresence = new boolean[totalGraph.getNumNodes()];
        calculateWeightAndPresenceNodes();
    }

    public MDPSolution(MDPSolution solution) {
        if (solution.nodesDistance == null) {
            nodes = new LinkedList<Node>(solution.nodes);
        } else {
            nodesDistance = new LinkedList<Weighted<Node>>();
            for (Weighted<Node> wn : solution.nodesDistance) {
                nodesDistance.add(new Weighted<Node>(wn));
            }
            bestWNode = solution.bestWNode;
            worstWNode = solution.worstWNode;
        }
        nodesPresence = new boolean[solution.getGraph()
                .getNumNodes()];
        System.arraycopy(solution.nodesPresence, 0, nodesPresence,
                0, nodesPresence.length);
        totalWeight = solution.totalWeight;
        graph = solution.graph;
    }

    public MDPSolution(MDPGraph graph) {
        nodesPresence = new boolean[graph.getNumNodes()];
        if (graph.isNodesDistanceCalculated()) {
            nodesDistance = new LinkedList<Weighted<Node>>();
            for (Weighted<Node> wn : graph.getNodesDistance()) {
                nodesDistance.add(new Weighted<Node>(wn));
            }
            calculateWeightAndPresenceNodesCont();
        } else {
            nodes = new LinkedList<Node>(graph.getNodes());
            calculateWeightAndPresenceNodes();
        }
    }

    public MDPSolution(MDPGraph graph,
                       List<Weighted<Node>> nodesContribution) {
        this.graph = graph;
        this.nodesDistance = new LinkedList<Weighted<Node>>();
        for (Weighted<Node> wn : nodesContribution) {
            this.nodesDistance.add(new Weighted<Node>(wn));
        }
        nodesPresence = new boolean[graph.getNumNodes()];
        calculateWeightAndPresenceNodesCont();
    }

    private void calculateWeightAndPresenceNodes() {
        totalWeight = 0;
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            nodesPresence[node.getIndex()] = true;
            for (int j = i + 1; j < nodes.size(); j++) {
                Node otherNode = nodes.get(j);
                totalWeight += node.getDistanceTo(otherNode);
            }
        }
    }

    private void calculateWeightAndPresenceNodesCont() {
        totalWeight = 0;
        for (Weighted<Node> wn : nodesDistance) {
            totalWeight += wn.getWeight();
            nodesPresence[wn.getElement().getIndex()] = true;
            refreshWorstAndBest(wn);
        }
        totalWeight = totalWeight / 2;
    }

    public MDPGraph getGraph() {
        return graph;
    }

    public Weighted<Node> getWorstNode() {
        if (worstWNode == null) {
            calculateWorstBestWOC();
        }
        return worstWNode;
    }

    public Weighted<Node> getBestNode() {
        if (bestWNode == null) {
            calculateWorstBestWOC();
        }
        return bestWNode;
    }

    private void calculateWorstBestWOC() {
        for (Node n : nodes) {
            float weight = 0;
            for (Node on : nodes) {
                weight += n.getDistanceTo(on);
            }
            Weighted<Node> wn = new Weighted<Node>(n, weight);
            refreshWorstAndBest(wn);
        }
    }

    private void refreshWorstAndBest(Weighted<Node> wn) {
        worstWNode = min(wn, worstWNode);
        bestWNode = max(wn, bestWNode);
    }

    public float getWeight() {
        return totalWeight;
    }

    public int compareTo(MDPSolution solution) {
        if (getWeight() > solution.getWeight()) {
            return 1;
        } else if (getWeight() == solution.getWeight()) {
            return 0;
        } else {
            return -1;
        }
    }

    public boolean contains(Node node) {
        return nodesPresence[node.getIndex()];
    }

    public List<Weighted<Node>> getNodesDistance() {
        if (nodesDistance == null) {
            calculateNodesDistance();
        }
        return nodesDistance;
    }

    public void calculateNodesDistance() {

        if (nodesDistance == null) {
            nodesDistance = new ArrayList<Weighted<Node>>();

            worstWNode = null;
            bestWNode = null;

            double totalContribution = 0;

            for (Node n : this) {
                double weight = 0;
                for (Node on : this) {
                    weight += n.getDistanceTo(on);
                }

                totalContribution += weight;

                Weighted<Node> wn = new Weighted<Node>(n, (float) weight);
                nodesDistance.add(wn);

                refreshWorstAndBest(wn);
            }

            totalWeight = (float) (totalContribution / 2d);
            nodes = null;
        }
    }

    public void changeNode(Node olderNode, Node newNode) {

        if(nodesPresence[olderNode.getIndex()] == false){
            throw new InvalidParameterException("El nodo "+olderNode.getIndex()
                    +" no está en la solución");
        }

        worstWNode = null;
        bestWNode = null;

        if (nodesDistance != null) {

            float newNodeDistance = 0;
            Weighted<Node> newWN = new Weighted<Node>(newNode, 0);

            for (int i = 0, n = nodesDistance.size(); i < n; i++) {

                Weighted<Node> wn = nodesDistance.get(i);

                if (wn.getElement() == olderNode) {
                    totalWeight -= wn.getWeight();
                    nodesDistance.set(i, newWN);
                } else {
                    wn.setWeight(wn.getWeight()
                            - wn.getElement().getDistanceTo(olderNode)
                            + wn.getElement().getDistanceTo(newNode));

                    newNodeDistance += wn.getElement().getDistanceTo(
                            newNode);

                    refreshWorstAndBest(wn);
                }
            }

            newWN.setWeight(newNodeDistance);
            totalWeight += newNodeDistance;

            refreshWorstAndBest(newWN);

        } else {

            for (int i = 0, n = nodes.size(); i < n; i++) {
                Node node = nodes.get(i);
                totalWeight -= node.getDistanceTo(olderNode);
                totalWeight += node.getDistanceTo(newNode);
                if (node == olderNode) {
                    nodes.set(i, newNode);
                }
            }

        }

        nodesPresence[olderNode.getIndex()] = false;
        nodesPresence[newNode.getIndex()] = true;
    }

    public void removeNode(Node node) {

        worstWNode = null;
        bestWNode = null;

        if (nodesDistance != null) {

            for (Iterator<Weighted<Node>> it = nodesDistance
                    .iterator(); it.hasNext();) {

                Weighted<Node> wn = it.next();

                if (wn.getElement() == node) {
                    totalWeight -= wn.getWeight();
                    it.remove();
                } else {
                    wn.setWeight(wn.getWeight()
                            - wn.getElement().getDistanceTo(node));
                    refreshWorstAndBest(wn);
                }
            }

        } else {

            for (Iterator<Node> it = nodes.iterator(); it.hasNext();) {
                Node n = it.next();
                totalWeight -= n.getDistanceTo(node);
                if (n == node) {
                    it.remove();
                }
            }
        }

        nodesPresence[node.getIndex()] = false;
    }

    public void addNode(Node node) {

        worstWNode = null;
        bestWNode = null;

        if (nodesDistance != null) {

            float newNodeContribution = 0;

            for (Weighted<Node> wn : nodesDistance) {
                wn.setWeight(wn.getWeight()
                        + wn.getElement().getDistanceTo(node));
                newNodeContribution += wn.getElement().getDistanceTo(
                        node);
                refreshWorstAndBest(wn);
            }

            Weighted<Node> wn = new Weighted<Node>(node,
                    newNodeContribution);
            nodesDistance.add(wn);

            refreshWorstAndBest(wn);

            totalWeight += newNodeContribution;

        } else {

            for (Node n : nodes) {
                totalWeight += n.getDistanceTo(node);
            }

            nodes.add(node);
        }

        nodesPresence[node.getIndex()] = true;
    }

    public void removeNodesContribution() {
        this.nodes = new ArrayList<Node>();
        for (Weighted<Node> wNode : nodesDistance) {
            nodes.add(wNode.getElement());
        }
        nodesDistance = null;
    }

    public void asMDPSolution(MDPSolution solution) {
        totalWeight = solution.totalWeight;
        nodesDistance = solution.nodesDistance;
        nodesPresence = solution.nodesPresence;
        nodes = solution.nodes;
        bestWNode = solution.bestWNode;
        worstWNode = solution.worstWNode;
    }

    public float calculateDistanceWithoutNode(Node ignoredNode,
                                              Node newNode) {
        float contribution = 0;
        for (Node n : this) {
            if (n != ignoredNode) {
                contribution += newNode.getDistanceTo(n);
            }
        }
        return contribution;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Weight: " + totalWeight);
        sb.append("[");
        for (int i = 0; i < nodesPresence.length; i++) {
            if (nodesPresence[i]) {
                sb.append(i);
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public int getNumNodes() {
        if (nodes != null) {
            return nodes.size();
        } else {
            return nodesDistance.size();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof MDPSolution) {
            MDPSolution solution = (MDPSolution) o;
            return Arrays
                    .equals(nodesPresence, solution.nodesPresence);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(nodesPresence);
    }

    public List<Node> createNodeList() {
        List<Node> nodes = new ArrayList<Node>();
        for (Node node : this) {
            nodes.add(node);
        }
        return nodes;
    }

    public int[] createNodeIndexes() {
        int[] nodeIndexes = new int[getNumNodes()];
        int i = 0;
        for (Node n : this) {
            nodeIndexes[i] = n.getIndex();
            i++;
        }
        return nodeIndexes;
    }

    public void calculatePreciseWeight() {

        Node[] orderedNodes = new Node[getNumNodes()];
        int counter = 0;
        for (int i = 0; i < nodesPresence.length; i++) {
            if (nodesPresence[i]) {
                orderedNodes[counter] = graph.getNode(i);
                counter++;
            }
        }

        double weight = 0;
        for (int i = 0; i < orderedNodes.length; i++) {
            for (int j = i + 1; j < orderedNodes.length; j++) {
                weight += orderedNodes[i].getDistanceTo(orderedNodes[j]);
            }
        }

        totalWeight = (float) weight;
    }

    public int getRefSetPosition() {
        return refSetPosition;
    }

    public void setRefSetPosition(int refSetPosition) {
        this.refSetPosition = refSetPosition;
    }

    public Iterator<Node> iterator() {
        if (nodes != null) {
            return nodes.iterator();
        } else {
            return new WeightedIterator<Node>(nodesDistance.iterator());
        }
    }

}
