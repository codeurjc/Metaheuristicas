package CWP;

import java.util.Collections;
import java.util.List;

public class CWPFirstImprovement {
	private CWPSolution improveSolution(CWPSolution solution, Character order) {
		boolean nodeChange;
		CWPInstance instance = solution.getInstance();

		do {
			nodeChange = false;

			for (int i = 0; i < solution.getNodes().size(); i++) {
				CWPNode oldNode = solution.getNodes().get(i);

				List<CWPNode> nodes = instance.getNodes();
				switch (order) {
				case 'l':
					Collections.sort(nodes);
					break;
				case 'r':
					Collections.shuffle(nodes);
					break;
				}

				for (int j = 0; j < instance.getNodes().size(); j++) {
					CWPNode newNode = instance.getNodes().get(j);

					if (!oldNode.equals(newNode)) {

						double newSolution = solution.calculateWeightChangeNode(oldNode, newNode);
						double oldSolution = solution.getTotalWeight();

						if (newSolution < oldSolution) {
							solution.changeNode(oldNode, newNode);
							nodeChange = true;
							break;
						}
					}
				}
			}

		} while (nodeChange);

		return solution;
	}

	public CWPSolution improveSolutionRandom(CWPSolution solution) {
		return improveSolution(solution, 'r');
	}

	public CWPSolution improveSolutionLexicographical(CWPSolution solution) {
		return improveSolution(solution, 'l');
	}
}
