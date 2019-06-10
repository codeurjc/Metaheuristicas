package ssmdp.constructive;

import ssmdp.MDPSolution;
import ssmdp.graph.MDPGraph;
import ssmdp.tabuD2.TabuD2Calculator;

public class TabuD2Constructive extends Constructive {

    private TabuD2Calculator tabuD2Calculator;

    public TabuD2Constructive(MDPGraph graph) {
        this(new TabuD2Calculator(graph));
    }

    public TabuD2Constructive(TabuD2Calculator tabuD2Calculator) {
        super(tabuD2Calculator.getGraph());
        this.tabuD2Calculator = tabuD2Calculator;
    }

    @Override
    public MDPSolution createSolution() {
        return tabuD2Calculator.createSolutionRefreshMemory();
    }

}
