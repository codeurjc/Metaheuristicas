package ssmdp.improving;

import java.util.List;

import ssmdp.MDPSolution;
import ssmdp.graph.MDPGraph;
import ssmdp.graph.Node;
import ssmdp.util.RotateListView;
import ssmdp.util.Weighted;

public class LocalSearchTabuSearch extends ImprovingMethod {

    private static final float MAX_ITER_PERCENT = 0.1f;
    private static final float TABU_TENURE_SOL_PERCENT = 0.28f;
    private static final float TABU_TENURE_SET_PERCENT = 0.028f;

    private int maxIter;
    private int tabuTenureSolution;
    private int tabuTenureSet;

    private int iterationCounter = 0;
    private int[] tabuEnd;

    private MDPSolution solution;
    private int notImproveIterationCounter;

    private MDPSolution bestSolution;
    private float bestSolutionWeight;

    @Override
    public void improveSolution(MDPSolution solution) {

        int solutionNodes = solution.getNumNodes();
        int setNodes = solution.getGraph().getNumNodes() - solutionNodes;

        maxIter = (int) (setNodes * MAX_ITER_PERCENT);
        tabuTenureSet = (int) (setNodes * TABU_TENURE_SET_PERCENT);
        tabuTenureSolution = (int) (solutionNodes * TABU_TENURE_SOL_PERCENT);

        this.solution = solution;
        solution.calculateNodesDistance();

        MDPGraph graph = solution.getGraph();

        bestSolution = new MDPSolution(solution);
        bestSolutionWeight = bestSolution.getWeight();

        iterationCounter = 0;

        List<Node> nodes = graph.getNodes();
        tabuEnd = new int[nodes.size()];

        Iterable<? extends Node> iterableForNodes = new RotateListView<Node>(nodes);

        do {

            iterationCounter++;

            Weighted<Node> oldWNode = getWorstNode(solution);

            Node oldNode = oldWNode.getElement();
            float weightWithoutOld = solution.getWeight()
                    - oldWNode.getWeight();

            float bestChangeWeight = 0;
            Node bestChangeNode = null;
            boolean nodeChanged = false;

            for (Node newNode : iterableForNodes) {

                if (!solution.contains(newNode)) {

                    if (tabuEnd[newNode.getIndex()] < iterationCounter) {

                        float newWeight = weightWithoutOld
                                + solution.calculateDistanceWithoutNode(
                                oldNode, newNode);

                        if (newWeight > solution.getWeight()) {

                            changeNode(oldNode, newNode);
                            nodeChanged = true;
                            break;

                        } else {

                            if (bestChangeWeight < newWeight) {
                                bestChangeWeight = newWeight;
                                bestChangeNode = newNode;
                            }
                        }
                    }
                }
            }

            if(!nodeChanged){
                changeNode(oldNode, bestChangeNode);
            }

        } while(notImproveIterationCounter < maxIter);

        solution.asMDPSolution(bestSolution);
        solution.calculatePreciseWeight();
    }

    private Weighted<Node> getWorstNode(MDPSolution solution) {

        Weighted<Node> oldWNode = null;

        for (Weighted<Node> wn : solution.getNodesDistance()) {
            if (tabuEnd[wn.getElement().getIndex()] < iterationCounter) {
                oldWNode = Weighted.min(oldWNode,wn);
            }
        }

        return oldWNode;
    }

    private void changeNode(Node oldNode, Node newNode) {

        tabuEnd[oldNode.getIndex()] = iterationCounter + tabuTenureSet;
        tabuEnd[newNode.getIndex()] = iterationCounter + tabuTenureSolution;

        solution.changeNode(oldNode, newNode);

        if (solution.getWeight() > bestSolutionWeight) {
            bestSolutionWeight = solution.getWeight();
            bestSolution = new MDPSolution(solution);
            notImproveIterationCounter = 0;
        } else {
            notImproveIterationCounter++;
        }
    }

}
