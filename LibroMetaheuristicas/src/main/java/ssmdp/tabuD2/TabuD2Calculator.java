package ssmdp.tabuD2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import ssmdp.MDPSolution;
import ssmdp.graph.MDPGraph;
import ssmdp.graph.Node;
import ssmdp.util.Weighted;

public class TabuD2Calculator {

    private static final float BETA = 0.1f;
    private static final float DELTA = 0.0001f;

    private MDPGraph graph;

    private int[] freq;
    private float[] quality;

    private int maxFreq;
    private float maxQuality;

    private int numTotalNodes;

    protected float[] ponderation;

    public TabuD2Calculator(MDPGraph graph) {
        this.graph = graph;
        initFreqQuality();
    }

    private void initFreqQuality() {
        numTotalNodes = graph.getNodes().size();
        freq = new int[numTotalNodes];
        quality = new float[numTotalNodes];
        maxFreq = 0;
        maxQuality = 0;
        ponderation = new float[numTotalNodes];
    }

    public void refreshMemory(MDPSolution solution) {
        for (Node node : solution) {
            int index = node.getIndex();
            quality[index] = ((quality[index] * freq[index] + solution
                    .getWeight()))
                    / (freq[index] + 1);
            freq[index]++;
            maxFreq = Math.max(maxFreq, freq[index]);
            maxQuality = Math.max(maxQuality, quality[index]);
        }
    }

    protected void calculatePonderation(Collection<Node> nodes) {
        if (maxFreq > 0) {
            for (Node node : nodes) {
                int index = node.getIndex();
                ponderation[index] = (DELTA * quality[index] / maxQuality)
                        - (BETA * freq[index] / maxFreq);
            }
        }
    }

    public MDPSolution createSolutionRefreshMemory() {
        MDPSolution solution = createSolutionNoRefreshMemory();
        refreshMemory(solution);
        return solution;
    }

    public MDPSolution createSolutionRefreshMemory(Collection<Node> nodes) {
        MDPSolution solution = createSolutionNoRefreshMemory(nodes);
        refreshMemory(solution);
        return solution;
    }

    public MDPSolution createSolutionNoRefreshMemory(Collection<Node> nodes) {

        calculatePonderation(nodes);

        List<Weighted<Node>> nodesContribution = new LinkedList<Weighted<Node>>();
        float minContribution = Float.MAX_VALUE;
        float maxContribution = 0;

        for (Node n : nodes) {
            float weight = 0;
            for (Node on : nodes) {
                if (n != on) {
                    weight += n.getDistanceTo(on);
                }
            }
            nodesContribution.add(new Weighted<Node>(n, weight));

            minContribution = Math.min(minContribution, weight);
            maxContribution = Math.max(maxContribution, weight);
        }

        tabuD2NodesContribution(nodesContribution, minContribution, maxContribution);

        return new MDPSolution(graph, nodesContribution);
    }

    public MDPSolution createSolutionNoRefreshMemory() {

        calculatePonderation(graph.getNodes());

        List<Weighted<Node>> nodesContribution = new LinkedList<Weighted<Node>>();
        float minContribution = Float.MAX_VALUE;
        float maxContribution = 0;

        for(Weighted<Node> wNode: graph.getNodesDistance()){
            nodesContribution.add(new Weighted<Node>(wNode));
            minContribution = Math.min(minContribution, wNode.getWeight());
            maxContribution = Math.max(maxContribution, wNode.getWeight());
        }

        tabuD2NodesContribution(nodesContribution, minContribution, maxContribution);

        return new MDPSolution(graph, nodesContribution);
    }

    private void tabuD2NodesContribution(List<Weighted<Node>> nodesContribution, float minContribution, float maxContribution) {

        while (nodesContribution.size() > graph.getNumSolutionNodes()) {

            Node worstNode = searchWorstNode(nodesContribution,
                    minContribution, maxContribution);

            minContribution = Float.MAX_VALUE;
            maxContribution = 0;

            for (Iterator<Weighted<Node>> it = nodesContribution.iterator(); it.hasNext();) {

                Weighted<Node> wn = it.next();

                if (wn.getElement() == worstNode) {
                    it.remove();
                } else {

                    float weight = wn.getWeight()
                            - wn.getElement().getDistanceTo(worstNode);
                    wn.setWeight(weight);

                    minContribution = Math.min(minContribution, weight);
                    maxContribution = Math.max(maxContribution, weight);

                }
            }
        }
    }

    private Node searchWorstNode(List<Weighted<Node>> nodesContribution,
                                 float minContribution, float maxContribution) {
        float range = maxContribution - minContribution;

        Node worstPonderatedNode = null;
        float worstPonderatedWeight = Float.MAX_VALUE;

        for (Weighted<Node> wn : nodesContribution) {
            float originalDistance = wn.getWeight();
            float ponderatedWeight = originalDistance + range
                    * ponderation[wn.getElement().getIndex()];

            if (ponderatedWeight < worstPonderatedWeight) {
                worstPonderatedWeight = ponderatedWeight;
                worstPonderatedNode = wn.getElement();
            }
        }

        return worstPonderatedNode;
    }

    public List<MDPSolution> createSolutionsNoRefreshMemory(int numSolutions) {
        List<MDPSolution> solutions = new ArrayList<MDPSolution>();
        for (int i = 0; i < numSolutions; i++) {
            MDPSolution solution = createSolutionNoRefreshMemory();
            solutions.add(solution);
        }
        return solutions;
    }

    public List<MDPSolution> createSolutionsRefreshMemory(int numSolutions) {
        List<MDPSolution> solutions = new ArrayList<MDPSolution>();
        for (int i = 0; i < numSolutions; i++) {
            MDPSolution solution = createSolutionRefreshMemory();
            solutions.add(solution);
        }
        return solutions;
    }

    public MDPGraph getGraph() {
        return graph;
    }
}