package ssmdp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ssmdp.combinator.Combinator;
import ssmdp.combinator.D2Combinator;
import ssmdp.combinator.RandomCombinator;
import ssmdp.combinator.TabuD2Combinator;
import ssmdp.constructive.Constructive;
import ssmdp.constructive.GraspD2Constructive;
import ssmdp.constructive.RandomConstructive;
import ssmdp.constructive.TabuD2Constructive;
import ssmdp.diversificator.Diversificator;
import ssmdp.diversificator.NotUsedNodesDiv;
import ssmdp.graph.MDPGraph;
import ssmdp.improving.ImprovedLocalSearch;
import ssmdp.improving.ImprovingMethod;
import ssmdp.improving.LocalSearch;
import ssmdp.improving.LocalSearchTabuSearch;
import ssmdp.tabuD2.TabuD2Calculator;
import ssmdp.util.BoundedSortedList;
import ssmdp.util.Combinations;

public class ScatterSearchMDP {

    // Enumerado usado para seleccionar la configuración
    public enum InformationMode {
        WITHOUT_INF, WITH_INF, WITH_MEMORY
    }

    // Constantes del algoritmo
    private static final int NUM_INITIAL_SOLUTIONS = 100;
    private static final int NUM_BEST_SOLUTIONS = 5;
    private static final int NUM_DIVERSE_SOLUTIONS = 5;

    // Método de generación de soluciones diversas
    private Constructive constructive;

    // Método de mejora
    private ImprovingMethod improvingMethod;

    // Método de combinación
    private Combinator combinator;

    // Método de diversificación
    private Diversificator diversificator = new NotUsedNodesDiv();

    // Generador de combinaciones de elementos
    private Combinations combinations = new Combinations(2,
            getRefSetSize());

    // Conjunto de referencia (RefSet)
    private BoundedSortedList<MDPSolution> refSet;

    // Estructura de tipo tabla hash que guarda las soluciones
    // previamente mejoradas
    private Map<MDPSolution, MDPSolution> improvedSolutions;

    public ScatterSearchMDP(MDPGraph graph,
                            InformationMode informationMode) {

        switch (informationMode) {
            case WITHOUT_INF:
                constructive = new RandomConstructive(graph);
                combinator = new RandomCombinator();
                improvingMethod = new LocalSearch();
                break;
            case WITH_INF:
                constructive = new GraspD2Constructive(graph);
                combinator = new D2Combinator();
                improvingMethod = new ImprovedLocalSearch();
                break;
            case WITH_MEMORY:
                TabuD2Calculator tD2C = new TabuD2Calculator(graph);
                constructive = new TabuD2Constructive(tD2C);
                combinator = new TabuD2Combinator(tD2C);
                improvingMethod = new LocalSearchTabuSearch();
                break;
        }
    }

    public MDPSolution calculateSolution(long millisTimeout) {

        //Se inicializa la tabla hash para guardar las soluciones
        //que se van mejorando, para no tener que hacerlo de nuevo
        improvedSolutions = new HashMap<MDPSolution, MDPSolution>();

        //Se inicializa el RefSet
        refSet =
                new BoundedSortedList<MDPSolution>(NUM_BEST_SOLUTIONS);

        //Cáculo del momento de finalización del algoritmo
        long finishTime = System.currentTimeMillis() + millisTimeout;

        //Creación de las soluciones iniciales
        List<MDPSolution> initialSolutions =
                constructive.createSolutions(NUM_INITIAL_SOLUTIONS);

        //Mejora de las soluciones e incorporación al RefSet
        improveAndRefreshRefSet(initialSolutions);

        //Se seleccionan las soluciones diversas
        List<MDPSolution> diverseSolutions =
                diversificator.getDiverseSolutions(NUM_DIVERSE_SOLUTIONS,
                        initialSolutions, refSet.getList());

        //Se prepara el RefSet para albergar las soluciones
        //diversas además de las soluciones por calidad
        refSet.setMaxSize(getRefSetSize());

        //Se añaden las soluciones diversas al refSet
        refSet.addAll(diverseSolutions);

        //Se crean los grupos de soluciones
        List<List<MDPSolution>> groups =
                combinations.getGroups(refSet.getList());

        //Se combinan los grupos de soluciones
        List<MDPSolution> refSetCombinations =
                combinator.combineGroups(groups);

        do {

            //Declaración de la lista que guardará las posiciones
            //de las nuevas soluciones del RefSet
            List<Integer> newSolPositions;

            do {

                // Mejora de las soluciones e incorporación al RefSet
                improveAndRefreshRefSet(refSetCombinations);

                // Se calculan las nuevas soluciones del refSet
                newSolPositions = getNewSolutionsPositions();

                // Si hay nuevas soluciones...
                if (newSolPositions.size() > 0) {

                    // Se obtienen los grupos que contienen a esas nuevas
                    // soluciones
                    groups = combinations.getGroupsContainingIndexes(
                            newSolPositions, refSet.getList());

                    // Y se combinan dichos grupos
                    refSetCombinations = combinator.combineGroups(groups);
                }

                // Si se ha sobrepasado el tiempo límite, se sale del
                // algoritmo
                if (System.currentTimeMillis() > finishTime) {
                    return refSet.getBiggest();
                }

            } while (newSolPositions.size() > 0);

            // Se queda el RefSet con las mejores soluciones
            refSet.retain(NUM_BEST_SOLUTIONS);

            // Se crean soluciones nuevas
            List<MDPSolution> newSolutions =
                    constructive.createSolutions(NUM_INITIAL_SOLUTIONS);

            // Se seleccionan las más diversas
            diverseSolutions = diversificator.getDiverseSolutions(
                    NUM_DIVERSE_SOLUTIONS, newSolutions, refSet.getList());

            // Y se añaden al RefSet
            refSet.addAll(diverseSolutions);

            // Se calculan las nuevas soluciones
            newSolPositions = getNewSolutionsPositions();

            // Se obtienen los grupos con esas nuevas soluciones
            groups = combinations.getGroupsContainingIndexes(
                    newSolPositions, refSet.getList());

            // Y se combinan esos grupos
            refSetCombinations = combinator.combineGroups(groups);

        } while (true);

    }

    private List<Integer> getNewSolutionsPositions() {

        //Obtiene las posiciones en el RefSet de las nuevas
        //soluciones. Para ello, cada solución alberga la posición
        //en el RefSet o -1 si es nueva.

        List<Integer> indexes = new ArrayList<Integer>();
        int index = 0;
        for (MDPSolution solution : refSet.getList()) {
            if (solution.getRefSetPosition() == -1) {
                indexes.add(index);
            }
            solution.setRefSetPosition(index);
            index++;
        }
        return indexes;
    }

    private void refreshRefSetIndexes() {

        //Actualiza las posiciones de las soluciones del RefSet

        int index = 0;
        for (MDPSolution sol : refSet.getList()) {
            sol.setRefSetPosition(index);
            index++;
        }
    }

    private void improveAndRefreshRefSet(
            List<MDPSolution> solutions) {

        refreshRefSetIndexes();

        for (MDPSolution solution : solutions) {

            MDPSolution improvedSolution;

            if (!improvedSolutions.containsKey(solution)) {
                MDPSolution original = new MDPSolution(solution);
                improvingMethod.improveSolution(solution);
                improvedSolutions.put(original, solution);
                improvedSolution = solution;

            } else {
                improvedSolution =
                        new MDPSolution(improvedSolutions.get(solution));
            }
            refSet.add(improvedSolution);
        }
    }

    public int getRefSetSize() {
        return NUM_BEST_SOLUTIONS + NUM_DIVERSE_SOLUTIONS;
    }

}
