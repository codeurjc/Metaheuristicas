package CPH;

public class CPHBestImprovement {
	
	public CPHSolution improveSolution(CPHSolution solution) {
		boolean nodeChange;
		CPHInstance instance = solution.getInstance();
		double bestWeight;
		CPHNode bestOldNode;
		CPHNode bestNewNode;
		
		do {
			nodeChange = false;
			bestWeight = solution.getTotalWeight();
			bestOldNode = null;
			bestNewNode = null;
			
			for(int i=0; i < solution.getHubs().size(); i++) {
				CPHNode oldHub = solution.getHubs().get(i);
				
				for(int j=0; j < instance.getNodes().size(); j++) {
					CPHNode newHub = instance.getNodes().get(j);
					
					if (!solution.getHubs().contains(newHub)) {		
						double newSolution = solution.calculateWeightChangeHub(oldHub, newHub);
	
						if(newSolution < bestWeight) {
							bestWeight = newSolution;
							bestOldNode = oldHub;
							bestNewNode = newHub;
						}
					}
				}
			}
			
			if(bestNewNode != null) {
				solution.changeHub(bestOldNode, bestNewNode);
				nodeChange = true;
			}
						
		} while (nodeChange);

		return solution;
	}
	
}
