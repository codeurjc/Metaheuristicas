package CWP;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CWPRandomConstructive {
	private CWPInstance instance;

	public CWPRandomConstructive(CWPInstance instance) {
		this.instance = instance;
	}
	
	public List<CWPSolution> solutions(int numSolutions){
		List<CWPSolution> solutions = new ArrayList<>();
		
		for(int x=0; x < numSolutions; x++) {
			solutions.add(createSolution());
		}
		
		return solutions;
	}
	
	private CWPSolution createSolution() {
		List<CWPNode> nodes = new ArrayList<>();
		Random rnd = new Random();
		
		do {
			int randomIndex = rnd.nextInt(instance.getVertex()) + 1;
			CWPNode node = new CWPNode(randomIndex, instance);
			
			if(!nodes.contains(node)) {
				nodes.add(node);
			}
		} while(nodes.size() != this.instance.getVertex());
		
		return new CWPSolution(instance, nodes);
	}
	
}
