package MDP;

import java.util.List;

import org.optaplanner.core.api.score.buildin.simple.SimpleScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

public class MDPEasyCalculator implements EasyScoreCalculator<Solution> {

	@Override
	public SimpleScore calculateScore(Solution solution) {
		List<Diversity> diversity = solution.getDiversity();
		double score = 0;

		for (int i = 0; i < diversity.size(); i++) {
			for (int j = i + 1; j < diversity.size(); j++) {
				if (diversity.get(i).getNode() != null && diversity.get(j).getNode() != null) {
					score += diversity.get(i).getNode().getDistanceToNode(diversity.get(j).getNode());
				}
			}
		}

		return SimpleScore.of((int) score);
	}

	public int[] getOccurrences(List<Diversity> diversity, int nNodes) {
		int[] ocurrences = new int[nNodes];

		for (Diversity d : diversity) {
			if (d.getNode() != null) {
				ocurrences[d.getNode().getIndex()] += 1;
			}
		}

		return ocurrences;
	}

}
