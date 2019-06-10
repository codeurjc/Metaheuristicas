package MMDP;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomConstructive {
	private MMDPInstance instance;

	public RandomConstructive(MMDPInstance instance) {
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
		List<Node> nodes = new ArrayList<>();
		Random rnd = new Random();
		
		do {
			int randomIndex = rnd.nextInt(instance.getN());
			Node node = new Node(randomIndex, instance);
			
			if(!nodes.contains(node)) {
				nodes.add(node);
			}
		} while(nodes.size() != this.instance.getM());
		
		return new MMDPSolution(instance, nodes);
	}
	
}
