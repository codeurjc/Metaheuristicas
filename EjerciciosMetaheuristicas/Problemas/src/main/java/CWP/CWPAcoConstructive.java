package CWP;

import java.util.ArrayList;
import java.util.List;

public class CWPAcoConstructive {
	private CWPInstance instance;
	private double alpha = 0.5;
	private double beta = 1;
	private double antFactor = 0.8;
	
	public CWPAcoConstructive(CWPInstance instance) {
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
		int nAnts = (int) (instance.getVertex() * antFactor);
		
		
		for (int i=0; i < nAnts; i++) {
			List<CWPNode> nodesSolution = new ArrayList<>();
			CWPSolution antSolution;
			
			do {
				
				
				
				
				
				
				
				
				
			} while(nodesSolution.size() != instance.getVertex());
			
			
			
			
		}
		
		
		return null;
	}
	
}
