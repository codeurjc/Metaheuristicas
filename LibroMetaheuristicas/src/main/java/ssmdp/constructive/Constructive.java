package ssmdp.constructive;

import java.util.ArrayList;
import java.util.List;
import ssmdp.MDPSolution;
import ssmdp.graph.MDPGraph;

public abstract class Constructive {

    protected MDPGraph graph;

    public Constructive(MDPGraph graph) {
        this.graph = graph;
    }

    public MDPGraph getGraph() {
        return graph;
    }

    public List<MDPSolution> createSolutions(int numSolutions) {
        List<MDPSolution> solutions = new ArrayList<MDPSolution>();
        for(int i=0; i<numSolutions; i++){
            solutions.add(createSolution());
        }
        return solutions;
    }

    public abstract MDPSolution createSolution();

}
