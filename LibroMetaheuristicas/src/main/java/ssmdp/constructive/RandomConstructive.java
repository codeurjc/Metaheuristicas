package ssmdp.constructive;

import java.util.ArrayList;
import java.util.List;

import ssmdp.MDPSolution;
import ssmdp.graph.MDPGraph;
import ssmdp.graph.Node;
import ssmdp.util.RandomList;

public class RandomConstructive extends Constructive {

    public RandomConstructive(MDPGraph totalGraph) {
        super(totalGraph);
    }

    @Override
    public MDPSolution createSolution() {

        // Se crea la lista que albergará los nodos seleccionados
        // de forma aleatoria
        List<Node> nodes = new ArrayList<Node>();

        // Se usa la clse RandomList para recorrer de forma aleatoria
        // los elementos de una lista. En este caso es la lista de
        // nodos del grafo
        for (Node n : RandomList.create(graph.getNodes())) {

            // Se añade el nodo seleccionado aleatoriamente
            nodes.add(n);

            // Si el número de nodos seleccionados corresponde con el
            // número de nodos de una solución, se sale del bucle
            if (nodes.size() == graph.getNumSolutionNodes()) {
                break;
            }
        }

        // Se construye una solución con los nodos seleccionados y se
        // devuelve
        return new MDPSolution(nodes, graph);
    }

}
