package ssmdp.diversificator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ssmdp.MDPSolution;
import ssmdp.graph.Node;

public class NotUsedNodesDiv extends Diversificator {

    public List<MDPSolution> getDiverseSolutions(
            int numDiverseSolutions, List<MDPSolution> solutions,
            List<MDPSolution> selectedSolutions) {

        // Creamos la lista para albergar las soluciones diversas
        List<MDPSolution> diverseSolutions =
                new ArrayList<MDPSolution>();

        // Obtenemos el número de nodos del grafo
        int numNodes = solutions.get(0).getGraph().getNumNodes();

        // Creamos la estructura de datos que albergará el número de
        // apariciones del nodo i-ésimo en las soluciones
        // seleccionadas
        int[] selectedNodesCount = new int[numNodes];

        // Contamos los nodos ya seleccionados
        for (MDPSolution mdpGraph : selectedSolutions) {
            refreshNodeCount(selectedNodesCount, mdpGraph);
        }

        // Guardamos en otherSolutions aquellas soluciones que no
        // se encuentran en selectedSolutions
        Set<MDPSolution> otherSolutions =
                new HashSet<MDPSolution>(solutions);

        otherSolutions.removeAll(selectedSolutions);

        // Si el número de soluciones es menor o igual que el
        // número de soluciones diversas que hay que seleccionar,
        // devolvemos las que tenemos.
        if(otherSolutions.size() <= numDiverseSolutions){
            diverseSolutions.addAll(otherSolutions);
            return diverseSolutions;
        }

        // Mientras no hayamos seleccionado todas las soluciones
        // diversas
        while (diverseSolutions.size() < numDiverseSolutions) {

            int minSimilarity = Integer.MAX_VALUE;
            MDPSolution minSimilaritySol = null;

            if(otherSolutions.size() == 0){
                throw new Error();
            }

            for (MDPSolution solution : otherSolutions) {

                int similarity = calculateSimilarity(selectedNodesCount,
                        solution);

                if (similarity < minSimilarity) {
                    minSimilarity = similarity;
                    minSimilaritySol = solution;
                }
            }

            // Añadimos la solución más diferente en la la lista de
            // soluciones diversas
            diverseSolutions.add(minSimilaritySol);

            // Borramos esa solución de las candidatas para ser
            // seleccionadas
            otherSolutions.remove(minSimilaritySol);

            // Actualizamos la cuenta de nodos con la nueva solución
            refreshNodeCount(selectedNodesCount, minSimilaritySol);
        }
        return diverseSolutions;
    }

    private int calculateSimilarity(int[] selectedNodesCount,
                                    MDPSolution solution) {

        int similarity = 0;
        for (Node node : solution) {
            similarity += selectedNodesCount[node.getIndex()];
        }
        return similarity;
    }

    private void refreshNodeCount(int[] selectedNodesCount,
                                  MDPSolution solution) {

        for (Node node : solution) {
            selectedNodesCount[node.getIndex()]++;
        }
    }

}
