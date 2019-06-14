package CPH;

import java.util.Collections;
import java.util.List;

public class CWPFirstImprovement {
	private CPHSolution improveSolution(CPHSolution solution, Character order) {
		boolean nodeChange;
		CPHInstance instance = solution.getInstance();

		do {
			nodeChange = false;

			for (int i = 0; i < solution.getHubs().size(); i++) {
				CPHNode oldHub = solution.getHubs().get(i);

				List<CPHNode> nodes = instance.getNodes();
				switch (order) {
				case 'l':
					Collections.sort(nodes);
					break;
				case 'r':
					Collections.shuffle(nodes);
					break;
				}
								
				for (int j = 0; j < instance.getNodes().size(); j++) {
					CPHNode newHub = instance.getNodes().get(j);
										
					if (!solution.getHubs().contains(newHub)) {
/*
						double newSolution = solution.calculateWeightChangeNode(oldNode, newNode);
						double oldSolution = solution.getTotalWeight();

						if (newSolution < oldSolution) {
							solution.changeNode(oldNode, newNode);
							nodeChange = true;
							break;
						}*/
					}
				}
			}

		} while (nodeChange);

		return solution;
	}

	public CPHSolution improveSolutionRandom(CPHSolution solution) {
		return improveSolution(solution, 'r');
	}

	public CPHSolution improveSolutionLexicographical(CPHSolution solution) {
		return improveSolution(solution, 'l');
	}
}
