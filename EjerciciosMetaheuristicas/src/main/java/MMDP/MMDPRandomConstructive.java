package MMDP;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MMDPRandomConstructive {
	private MMDPInstance instance;

	public MMDPRandomConstructive(MMDPInstance instance) {
		this.instance = instance;
	}
	
	public List<MMDPSolution> solutions(int numSolutions){
		List<MMDPSolution> solutions = new ArrayList<>();
		
		for(int x=0; x < numSolutions; x++) {
			solutions.add(createSolution());
		}
		
		return solutions;
	}
	
	private MMDPSolution createSolution() {
		List<MMDPNode> nodes = new ArrayList<>();
		Random rnd = new Random();
		
		do {
			int randomIndex = rnd.nextInt(instance.getN());
			MMDPNode node = new MMDPNode(randomIndex, instance);
			
			if(!nodes.contains(node)) {
				nodes.add(node);
			}
		} while(nodes.size() != this.instance.getM());
		
		return new MMDPSolution(instance, nodes);
	}
	
}
