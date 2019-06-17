package CWP;

public class CWPBestImprovement {
	
	public CWPSolution improveSolution(CWPSolution solution) {
		boolean nodeChange;
		CWPInstance instance = solution.getInstance();
		double bestWeight;
		CWPNode bestOldNode;
		CWPNode bestNewNode;
		
		do {
			nodeChange = false;
			bestWeight = solution.getTotalWeight();
			bestOldNode = null;
			bestNewNode = null;
			
			for(int i=0; i < solution.getNodes().size(); i++) {
				CWPNode oldNode = solution.getNodes().get(i);
				
				for(int j=0; j < instance.getNodes().size(); j++) {
					CWPNode newNode = instance.getNodes().get(j);
					
					if(!oldNode.equals(newNode)) {
						double newSolution = solution.calculateWeightChangeNode(oldNode, newNode);						
						
						if(newSolution < bestWeight) {
							bestWeight = newSolution;
							bestOldNode = oldNode;
							bestNewNode = newNode;
						}
					}
				}
			}
			
			if(bestNewNode != null) {
				solution.changeNode(bestOldNode, bestNewNode);
				nodeChange = true;
			}
						
		} while (nodeChange);

		return solution;
	}
	
}
