package ssmdp.diversificator;

import java.util.List;
import ssmdp.MDPSolution;

public abstract class Diversificator {

    public abstract List<MDPSolution> getDiverseSolutions(
            int numDiverseSolutions,
            List<MDPSolution> solutions,
            List<MDPSolution> selectedSolutions);

}
