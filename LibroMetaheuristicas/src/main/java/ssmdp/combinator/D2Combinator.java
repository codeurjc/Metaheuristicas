package ssmdp.combinator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ssmdp.MDPSolution;
import ssmdp.graph.MDPGraph;
import ssmdp.graph.Node;
import ssmdp.util.Weighted;

public class D2Combinator extends Combinator {

    //Atributo que albergará el nodo con la distancia
    //mínima
    private Weighted<Node> worstWNode = null;

    @Override
    public MDPSolution combineGroup(List<MDPSolution> solutions) {

        // Se obtiene el grafo de las soluciones
        MDPGraph graph = solutions.get(0).getGraph();

        // Se insertan en un conjunto los nodos de las soluciones
        Set<Node> nodes = createUnion(solutions);

        //Se calculan las distancias entre dichos nodos
        List<Weighted<Node>> nodesDistance =
                calculateDist(nodes);

        // Mientras la lista de nodos tenga más nodos de los
        // necesarios en una solución
        while (nodesDistance.size() > graph.getNumSolutionNodes()) {

            Node oldNode = worstWNode.getElement();
            worstWNode = null;

            // Recorremos la lista de nodos con un iterador
            for (Iterator<Weighted<Node>> it =
                 nodesDistance.iterator(); it.hasNext();) {

                Weighted<Node> wn = it.next();

                // Si el nodo es el peor, le eliminamos
                if (wn.getElement() == oldNode) {
                    it.remove();
                } else {

                    // Actualizamos el peso de los nodos restando el
                    // valor de la distancia al nodo eliminado
                    wn.setWeight(wn.getWeight()
                            - wn.getElement().getDistanceTo(oldNode));

                    worstWNode = Weighted.min(worstWNode,wn);
                }
            }
        }

        return new MDPSolution(graph, nodesDistance);
    }

    private List<Weighted<Node>> calculateDist(Set<Node> nodes) {

        // Se crea una lista que albergará cada nodo asociado a la
        // suma de las distancias con el resto de los nodos
        List<Weighted<Node>> nodesDistance =
                new ArrayList<Weighted<Node>>(nodes.size());

        for (Node node : nodes) {

            // Se calcula la suma de las distancias de un nodo al resto
            // de nodos
            float distance = 0;
            for (Node otherNode : nodes) {
                distance += node.getDistanceTo(otherNode);
            }

            // Se crea un nodo asociado a su distancia
            Weighted<Node> wn = new Weighted<Node>(node, distance);

            // Se añade a la lista de distancias
            nodesDistance.add(wn);

            // Se actualiza el nodo con menos distancia
            worstWNode = Weighted.min(worstWNode,wn);
        }
        return nodesDistance;
    }

    private Set<Node> createUnion(List<MDPSolution> solutions) {
        Set<Node> nodes = new HashSet<Node>();
        for (MDPSolution solution : solutions) {
            for (Node node : solution) {
                nodes.add(node);
            }
        }
        return nodes;
    }

}
