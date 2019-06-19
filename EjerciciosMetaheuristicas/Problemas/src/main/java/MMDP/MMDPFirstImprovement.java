package MMDP;

import java.util.Collections;
import java.util.List;

public class MMDPFirstImprovement {
	private MMDPSolution improveSolution(MMDPSolution solution, Character order) {
		boolean nodeChange;
		MMDPInstance instance = solution.getInstance();
		List<MMDPNode> nodes = instance.getNodes();
		
		switch (order) {
		case 'l':
			Collections.sort(nodes);
			break;
		case 'r':
			Collections.shuffle(nodes);
			break;
		}
		
		do {
			nodeChange = false;

			for (int i = 0; i < solution.getNodes().size(); i++) {
				MMDPNode oldNode = solution.getNodes().get(i);
								
				for (int j = 0; j < instance.getNodes().size(); j++) {
					MMDPNode newNode = instance.getNodes().get(j);
										
					if (!solution.getNodes().contains(newNode)) {

						double newSolution = solution.calculateWeightChangeNode(oldNode, newNode);
						double oldSolution = solution.getTotalWeight();

						if (newSolution > oldSolution) {
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

	public MMDPSolution improveSolutionRandom(MMDPSolution solution) {
		return improveSolution(solution, 'r');
	}

	public MMDPSolution improveSolutionLexicographical(MMDPSolution solution) {
		return improveSolution(solution, 'l');
	}
}
