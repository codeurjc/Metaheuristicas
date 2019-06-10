package MMDP;

public class BestImprovement {
	
	public MMDPSolution improveSolution(MMDPSolution solution) {
		boolean nodeChange;
		MMDPInstance instance = solution.getInstance();
		double bestWeight;
		Node bestOldNode;
		Node bestNewNode;
		
		do {
			nodeChange = false;
			bestWeight = solution.getTotalWeight();
			bestOldNode = null;
			bestNewNode = null;
			
			for(int i=0; i < solution.getNodes().size(); i++) {
				Node oldNode = solution.getNodes().get(i);
				
				for(int j=0; j < instance.getNodes().size(); j++) {
					Node newNode = instance.getNodes().get(j);
					
					if(!solution.getNodes().contains(newNode)) {
						
						double newSolution = solution.calculateWeightChangeNode(oldNode, newNode);

						if(newSolution > bestWeight) {
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
