package CWP;

import java.util.List;

import org.optaplanner.core.api.score.buildin.hardsoftdouble.HardSoftDoubleScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

public class CWPEasyCalculator implements EasyScoreCalculator<Solution> {

	@Override
	public HardSoftDoubleScore calculateScore(Solution solution) {
		List<Order> orderNodes = solution.getOrder();
		int[] occurrences = null;

		if (orderNodes.size() > 0) {
			occurrences = getOccurrences(orderNodes);
		}

		double score = 0;
		double constraint = 0;
		boolean repeatNode = false;
		boolean unUsedNode = false;

		for (int i = 0; i < orderNodes.size(); i++) {
			if (orderNodes.get(i).getNode() == null) {
				unUsedNode = true;
			} else {
				int index = orderNodes.get(i).getNode().getIndex();
				if (occurrences[index] >= 2) {
					repeatNode = true;
				}
			}

			int weight = 0;
			for (int j = 0; j <= i; j++) {
				for (int k = i + 1; k < orderNodes.size(); k++) {
					if (orderNodes.get(j).getNode() != null && orderNodes.get(k).getNode() != null) {
						weight += orderNodes.get(j).getNode().isVertex(orderNodes.get(k).getNode());
					}
				}
			}

			score = Math.max(score, weight);
		}

		if (repeatNode) {
			constraint -= 1; // No repeat nodes
		}

		if (unUsedNode) {
			constraint -= 2; // No unused nodes
		}

		return HardSoftDoubleScore.of(constraint, -score);
	}

	public int[] getOccurrences(List<Order> orderNodes) {
		int[] ocurrences = new int[orderNodes.size() + 1];

		for (Order o : orderNodes) {
			if (o.getNode() != null) {
				ocurrences[o.getNode().getIndex()] += 1;
			}
		}

		return ocurrences;
	}

}
