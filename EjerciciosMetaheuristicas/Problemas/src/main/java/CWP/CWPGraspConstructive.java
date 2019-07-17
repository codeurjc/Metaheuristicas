package CWP;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static CWP.CWPWeight.max;
import static CWP.CWPWeight.min;

public class CWPGraspConstructive {
	private CWPInstance instance;

	float alpha = 0.5f;

	public CWPGraspConstructive(CWPInstance instance) {
		this.instance = instance;
	}

	public List<CWPSolution> solutions(int numSolutions) {
		List<CWPSolution> solutions = new ArrayList<>();

		for (int x = 0; x < numSolutions; x++) {
			solutions.add(createSolution());
		}

		return solutions;
	}

	private CWPSolution createSolution() {
		List<CWPNode> evaluateNodes = new ArrayList<>();
		Random r = new Random();
		
		do {
			List<CWPWeight> costsNodes = calculateCost(evaluateNodes);
			CWPWeight worstNode = costsNodes.get(0);
			CWPWeight bestNode = costsNodes.get(0);
			
			for (CWPWeight weightNode : costsNodes) {
				worstNode = max(worstNode, weightNode);
				bestNode = min(bestNode, weightNode);
			}
			
			List<CWPWeight> rcl = new ArrayList<>();
			for(CWPWeight weightNode: costsNodes) {
				if(weightNode.getValue() <= (worstNode.getValue() + alpha * (bestNode.getValue() - worstNode.getValue()))) {
					rcl.add(weightNode);
				}
			}
			
			CWPNode incorporateNode = rcl.get(r.nextInt(rcl.size())).getNode();
			evaluateNodes.add(incorporateNode);
		} while (evaluateNodes.size() != instance.getVertex());

		return new CWPSolution(instance, evaluateNodes);
	}

	private List<CWPWeight> calculateCost(List<CWPNode> evaluateNodes) {
		CWPSolution evaluateSolution;
		List<CWPWeight> nodesWeight = new ArrayList<>();

		for (CWPNode node : instance.getNodes()) {
			if (!evaluateNodes.contains(node)) {
				List<CWPNode> auxList = new ArrayList<>(evaluateNodes);
				auxList.add(node);

				evaluateSolution = new CWPSolution(instance, auxList);
				nodesWeight.add(new CWPWeight(node, evaluateSolution.getTotalWeight()));
			}
		}

		return nodesWeight;
	}

}
