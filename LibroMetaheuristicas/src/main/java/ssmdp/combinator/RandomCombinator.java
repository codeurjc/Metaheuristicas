package ssmdp.combinator;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import ssmdp.MDPSolution;
import ssmdp.graph.MDPGraph;
import ssmdp.graph.Node;

public class RandomCombinator extends Combinator {

    private static final Random r = new Random();

    @Override
    public MDPSolution combineGroup(List<MDPSolution> solutions) {

        int numNodesSolution = solutions.get(0).getNumNodes();
        MDPGraph totalGraph = solutions.get(0).getGraph();

        Set<Node> nodes = new HashSet<Node>();
        for(MDPSolution solution: solutions){
            for(Node node: solution){
                nodes.add(node);
            }
        }

        List<Node> sNodes = new LinkedList<Node>(nodes);
        while(sNodes.size() > numNodesSolution){
            int index = r.nextInt(sNodes.size());
            sNodes.remove(index);
        }

        return new MDPSolution(sNodes,totalGraph);
    }

}