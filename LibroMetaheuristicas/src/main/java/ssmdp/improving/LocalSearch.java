package ssmdp.improving;

import ssmdp.MDPSolution;
import ssmdp.graph.MDPGraph;
import ssmdp.graph.Node;
import ssmdp.util.Weighted;

public class LocalSearch extends ImprovingMethod {

    @Override
    public void improveSolution(MDPSolution solution) {

        MDPGraph graph = solution.getGraph();

        Node bestOlderNode;
        Node bestNewNode;
        float bestWeight;

        boolean nodeChanged;

        do {

            nodeChanged = false;

            bestOlderNode = null;
            bestNewNode = null;
            bestWeight = solution.getWeight();

            for (Weighted<Node> wOldNode : solution.getNodesDistance()) {

                Node oldNode = wOldNode.getElement();

                for (Node newNode : graph.getNodes()) {

                    float withoutOlderNodeWeight = solution.getWeight() - wOldNode.getWeight();

                    if (!solution.contains(newNode)) {

                        for (Node otherSolNode : solution) {
                            if (otherSolNode != oldNode) {
                                withoutOlderNodeWeight +=
                                        newNode.getDistanceTo(otherSolNode);
                            }
                        }

                        if (withoutOlderNodeWeight > bestWeight) {
                            bestNewNode = newNode;
                            bestOlderNode = oldNode;
                            bestWeight = withoutOlderNodeWeight;
                        }
                    }
                }
            }

            if(bestNewNode != null){
                solution.changeNode(bestOlderNode,bestNewNode);
                nodeChanged = true;
            }

        } while(nodeChanged);

        solution.calculatePreciseWeight();
    }

}
