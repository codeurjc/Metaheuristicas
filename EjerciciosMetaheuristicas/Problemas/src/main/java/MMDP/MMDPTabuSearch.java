package MMDP;

public class MMDPTabuSearch {
	
	private MMDPSolution solution;
	private MMDPSolution bestSolution;
	private double bestWeight;
	private int tabuList[];
	private int tabuTurns = 3;
	
	public MMDPSolution improveSolution(MMDPSolution solution) {
		tabuList = new int[solution.getInstance().getN()];
		int maxIterations = 100;
		int iteration = 0;
		
		MMDPInstance instance = solution.getInstance();
		this.solution = solution;
		bestSolution = new MMDPSolution(solution);
		bestWeight = bestSolution.getTotalWeight();

		do {
			iteration++;
			
			for (int i = 0; i < solution.getNodes().size(); i++) {
				boolean nodeChanged = false;
				double bestChangeWeight = 0;
				MMDPNode oldNode = solution.getNodes().get(i);
				MMDPNode bestChangeNode = null;
				
				for (int j = 0; j < instance.getNodes().size(); j++) {
					MMDPNode newNode = instance.getNodes().get(j);

					if (tabuList[newNode.getIndex()] == 0) {
						
						if (!solution.getNodes().contains(newNode)) {
							double newSolution = solution.calculateWeightChangeNode(oldNode, newNode);
							
							if (newSolution > solution.getTotalWeight()) {
								changeNode(oldNode, newNode);
								nodeChanged = true;
								break;
							} else {
								if(bestChangeWeight < newSolution) {
									bestChangeWeight = newSolution;
									bestChangeNode = newNode;
								}
							}
						}
					}
				}
				
				if(!nodeChanged && bestChangeNode != null) {
					changeNode(oldNode, bestChangeNode);
				}
			}
			
			for(int i=0; i < tabuList.length; i++) {
				if(tabuList[i] > 0) {
					tabuList[i]--;
				}
			}
						
		} while (iteration < maxIterations);

		return new MMDPSolution(bestSolution);
	}
	
	private void changeNode(MMDPNode oldNode, MMDPNode newNode) {
		tabuList[oldNode.getIndex()] = tabuTurns;
		
		solution.changeNode(oldNode, newNode);
		if(solution.getTotalWeight() > bestWeight) {
			bestWeight = solution.getTotalWeight(); 
			bestSolution = new MMDPSolution(solution);
		}		
	}
}
