package ssmdp.combinator;

import java.util.ArrayList;
import java.util.List;

import ssmdp.MDPSolution;

public abstract class Combinator {

    public List<MDPSolution> combineGroups(
            List<List<MDPSolution>> groups) {

        List<MDPSolution> combinedSolutions =
                new ArrayList<MDPSolution>();

        for (List<MDPSolution> group : groups) {
            MDPSolution solution = combineGroup(group);
            combinedSolutions.add(solution);
        }

        return combinedSolutions;
    }

    public abstract MDPSolution combineGroup(
            List<MDPSolution> group);

}