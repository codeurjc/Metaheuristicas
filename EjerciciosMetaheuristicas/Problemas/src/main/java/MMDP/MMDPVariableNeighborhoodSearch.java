package MMDP;

public class MMDPVariableNeighborhoodSearch {
	
	public MMDPSolution improveSolution(MMDPSolution solution) {
		MMDPFirstImprovement fi = new MMDPFirstImprovement();
		
		int maxK = solution.getInstance().getM() / 2;
		int k = 1;
		
		do {			
			MMDPSolution auxSolution = new MMDPSolution(solution);
			auxSolution.randomChangeNodesVNS(k);									
			auxSolution = fi.improveSolutionRandom(auxSolution);
			
			if(auxSolution.getTotalWeight() > solution.getTotalWeight()) {
				solution = auxSolution;
				k = 1;
			} else {
				k++;
			}
						
		} while(k <= maxK);
		
		return solution;
	}
}
