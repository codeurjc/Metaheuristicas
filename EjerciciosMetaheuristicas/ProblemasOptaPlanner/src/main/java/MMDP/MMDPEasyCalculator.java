package MMDP;

import java.util.List;

import org.optaplanner.core.api.score.buildin.hardsoftdouble.HardSoftDoubleScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

public class MMDPEasyCalculator implements EasyScoreCalculator<Solution> {

	@Override
	public HardSoftDoubleScore calculateScore(Solution solution) {
		List<Diversity> diversity = solution.getDiversity();
		
		int[] occurrences = null;
		
		if(diversity.size() > 0) {
			occurrences = getOccurrences(diversity, solution.getNodes().size());
		}
		
		double score = 0;
		double constraint = 0;
		boolean repeatNode = false;
		boolean unUsedNode = false;
		
		if (diversity.get(0).getNode() != null && diversity.get(1).getNode() != null) {
			score = diversity.get(0).getNode().getDistanceToNode(diversity.get(1).getNode());

			for (int i = 0; i < diversity.size(); i++) {
				
				if(diversity.get(i).getNode() == null) {
					unUsedNode = true;
				} else {
					if(occurrences[diversity.get(i).getNode().getIndex()] >= 2) {
						repeatNode = true;
					}
				}
				
				for (int j = i + 1; j < diversity.size(); j++) {
					if (diversity.get(i).getNode() != null && diversity.get(j).getNode() != null) {
						score = Math.min(score,
								diversity.get(i).getNode().getDistanceToNode(diversity.get(j).getNode()));
					}
				}
			}
		}
		
		if (repeatNode) {
			constraint -= 1; // No repeat nodes
		}

		if (unUsedNode) {
			constraint -= 2; // No unused nodes
		}

		return HardSoftDoubleScore.of(constraint, score);
	}
	
	public int[] getOccurrences(List<Diversity> diversity, int nNodes) {
		int[] ocurrences = new int[nNodes];

		for (Diversity d: diversity) {
			if (d.getNode() != null) {
				ocurrences[d.getNode().getIndex()] += 1;
			}
		}

		return ocurrences;
	}

}
