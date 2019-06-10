package ssmdp.improving;

import java.util.Collections;
import java.util.List;

import ssmdp.MDPSolution;
import ssmdp.graph.MDPGraph;
import ssmdp.graph.Node;
import ssmdp.util.Weighted;

public class ImprovedLocalSearch extends ImprovingMethod {

    @Override
    public void improveSolution(MDPSolution solution) {

        MDPGraph graph = solution.getGraph();

        // Variable que controla la condición de parada. Indica
        // si se ha intercambiando algún nodo
        boolean nodeChanged;

        do {

            nodeChanged = false;

            //Obtenemos la lista de distancias de cada nodo
            List<Weighted<Node>> nodeDistances =
                    solution.getNodesDistance();

            //Ordenamos de menor a mayor la lista de distancias
            Collections.sort(nodeDistances);

            // Recorremos la lista ordenada de menor a mayor
            for (Weighted<Node> oldWNode: solution.getNodesDistance()){

                // Tomamos un nodo como candidato para ser eliminado
                Node oldNode = oldWNode.getElement();

                // Calculamos el peso que tendría la solución si
                // quitásemos ese nodo
                float weightWithoutOld = solution.getWeight()
                        - oldWNode.getWeight();

                // Recorremos la lista de nodos del grafo
                for (Node newNode : graph.getNodes()) {

                    // Si el nodo no se encuentra en la solución
                    // consideramos que es candidato para ser
                    // intercambiado por oldNode
                    if (!solution.contains(newNode)) {

                        // Calculamos la distancia del newNode al resto de
                        // los nodos considerando que oldNode no está en
                        // la solución
                        float contribution = solution
                                .calculateDistanceWithoutNode(oldNode, newNode);

                        // Calculamos el peso que tendría la
                        // solución en caso de intercambio
                        float newWeight = weightWithoutOld + contribution;

                        // Si el peso posible de la solución es mayor que el
                        // peso actual
                        if (newWeight > solution.getWeight()) {
                            // Cambiamos el nodo
                            solution.changeNode(oldNode, newNode);
                            nodeChanged = true;
                            break;
                        }
                    }
                }
            }
        } while (nodeChanged);

        solution.calculatePreciseWeight();
    }
}
