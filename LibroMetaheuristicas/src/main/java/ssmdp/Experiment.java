package ssmdp;

import java.io.File;
import java.io.IOException;
import java.util.List;

import ssmdp.ScatterSearchMDP.InformationMode;
import ssmdp.graph.MDPGraph;
import ssmdp.instance.FormatException;
import ssmdp.instance.MDPInstance;
import ssmdp.instance.MDPInstancesManager;

public class Experiment {

    private static final String INSTANCES_DIRECTORY = "src/main/resources/instances";
    private static final long TIMEOUT_MILLIS = 30 * 1000;

    public static void main(String[] args) throws IOException, FormatException {
        new Experiment();
    }

    public Experiment() throws IOException, FormatException {

        MDPInstancesManager instancesManager = new MDPInstancesManager();

        List<MDPInstance> instances = instancesManager.getAllInstances();

        ExperimentStatistics expStats = new ExperimentStatistics(instances.size(),3);

        System.out.println("Instancia\tSin Información\tCon Información\tCon Memoria");

        for(int i=0; i<instances.size(); i++){

            MDPInstance instance = instances.get(i);
            MDPGraph graph = instance.loadGraph(new File(INSTANCES_DIRECTORY));

            System.out.print("Instance "+instance.getName()+" (n="+graph.getNumNodes()+", m="+graph.getNumSolutionNodes()+")\t");

            expStats.setExperimentSol(i, 0, calculateSolution(graph, InformationMode.WITHOUT_INF));
            expStats.setExperimentSol(i, 1, calculateSolution(graph, InformationMode.WITH_INF));
            expStats.setExperimentSol(i, 2, calculateSolution(graph, InformationMode.WITH_MEMORY));

            System.out.println();
        }

        float[] meanDeviation = expStats.getMeanDeviation();
        int[] numBest = expStats.getNumBest();

        System.out.println("----------------------------------------------------");
        System.out.println("Desviación media\t"+meanDeviation[0]+"\t"+meanDeviation[1]+"\t"+meanDeviation[2]);
        System.out.println("Número Mejores\t"+numBest[0]+"\t"+numBest[1]+"\t"+numBest[2]);
    }

    private float calculateSolution(MDPGraph graph, InformationMode informationMode) {
        ScatterSearchMDP ss = new ScatterSearchMDP(graph, informationMode);
        MDPSolution solution = ss.calculateSolution(TIMEOUT_MILLIS);

        System.out.print(solution.getWeight()+"\t");

        return solution.getWeight();
    }

}
