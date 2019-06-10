package ssmdp.constructive;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import ssmdp.MDPSolution;
import ssmdp.graph.MDPGraph;
import ssmdp.graph.Node;
import ssmdp.util.Weighted;

import static ssmdp.util.Weighted.max;
import static ssmdp.util.Weighted.min;

public class GraspD2Constructive extends Constructive {

    private static Random r = new Random();
    private float bestSolutionWeight;
    private boolean improvedInIteration;

    float alpha = 0.5f;

    public GraspD2Constructive(MDPGraph totalGraph) {
        super(totalGraph);
    }

    @Override
    public List<MDPSolution> createSolutions(int numSolutions) {

        alpha = 0.5f;
        List<MDPSolution> solutions = new ArrayList<MDPSolution>();
        int numSolutionsPerIteration = numSolutions / 5;

        bestSolutionWeight = 0;
        for (int i = 0; i < 5; i++) {

            improvedInIteration = false;

            for (int j = 0; j < numSolutionsPerIteration; j++) {
                createSolutionRefreshBest();
            }

            if (!improvedInIteration) {
                alpha -= 0.1;
            }
        }

        while (solutions.size() < numSolutions) {
            solutions.add(createSolutionAlpha());
        }

        return solutions;
    }

    private MDPSolution createSolutionRefreshBest() {
        MDPSolution solution = createSolutionAlpha();
        if (solution.getWeight() > bestSolutionWeight) {
            bestSolutionWeight = solution.getWeight();
            improvedInIteration = true;
        }
        return solution;
    }

    private MDPSolution createSolutionAlpha() {

        List<Weighted<Node>> nodesContribution = new ArrayList<Weighted<Node>>(
                graph.getNodesDistance().size());

        for (Weighted<Node> wNode : graph.getNodesDistance()) {
            nodesContribution.add(new Weighted<Node>(wNode));
        }

        Weighted<Node> bestWNode = null;
        Weighted<Node> worstWNode = null;

        for (Weighted<Node> wn : nodesContribution) {
            worstWNode = min(wn,worstWNode);
            bestWNode = max(wn,bestWNode);
        }

        while (nodesContribution.size() > graph.getNumSolutionNodes()) {

            float minContribution = worstWNode.getWeight();
            float distance = bestWNode.getWeight() - worstWNode.getWeight();

            List<Node> rcl = new ArrayList<Node>();
            for (Weighted<Node> wNode : nodesContribution) {
                if (wNode.getWeight() <= (minContribution + alpha
                        * distance)) {
                    rcl.add(wNode.getElement());
                }
            }

            Node oldNode = rcl.get(r.nextInt(rcl.size()));

            worstWNode = null;
            bestWNode = null;

            for (Iterator<Weighted<Node>> it = nodesContribution.iterator(); it
                    .hasNext();) {

                Weighted<Node> wn = it.next();

                if (wn.getElement() == oldNode) {
                    it.remove();
                } else {
                    wn.setWeight(wn.getWeight()
                            - wn.getElement().getDistanceTo(oldNode));

                    worstWNode = min(wn,worstWNode);
                    bestWNode = max(wn,bestWNode);
                }
            }
        }

        return new MDPSolution(graph, nodesContribution);
    }

    @Override
    public MDPSolution createSolution() {
        alpha = 0.5f;
        return createSolutionAlpha();
    }

}
