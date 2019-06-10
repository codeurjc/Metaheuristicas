package ssmdp.combinator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import ssmdp.MDPSolution;
import ssmdp.graph.MDPGraph;
import ssmdp.graph.Node;
import ssmdp.tabuD2.TabuD2Calculator;

public class TabuD2Combinator extends Combinator {

    private TabuD2Calculator tabuD2Calculator;

    public TabuD2Combinator(MDPGraph graph) {
        tabuD2Calculator = new TabuD2Calculator(graph);
    }

    public TabuD2Combinator(TabuD2Calculator tabuD2Calculator) {
        this.tabuD2Calculator = tabuD2Calculator;
    }

    public TabuD2Calculator getTabuD2Calculator(){
        return tabuD2Calculator;
    }

    @Override
    public MDPSolution combineGroup(List<MDPSolution> solutions) {

        Set<Node> nodes = new HashSet<Node>();
        for(MDPSolution solution: solutions){
            for(Node node: solution){
                nodes.add(node);
            }
        }

        return tabuD2Calculator.createSolutionRefreshMemory(nodes);
    }
}


