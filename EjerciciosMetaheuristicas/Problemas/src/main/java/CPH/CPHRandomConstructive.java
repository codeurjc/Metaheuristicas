package CPH;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CPHRandomConstructive {
	private CPHInstance instance;

	public CPHRandomConstructive(CPHInstance instance) {
		this.instance = instance;
	}
	
	public List<CPHSolution> solutions(int numSolutions){
		List<CPHSolution> solutions = new ArrayList<>();
		
		for(int x=0; x < numSolutions; x++) {
			solutions.add(createSolution());
		}
		
		return solutions;
	}
	
	private CPHSolution createSolution() {
		List<Integer> asignedNodes = new ArrayList<>();
		List<CPHNode> hubs = new ArrayList<>();
		int[] sumHubs = new int[instance.getnNodes() + 1];
		int[] spokes = new int[instance.getnNodes() + 1];
		Random rnd = new Random();
		
		do {		
			if(hubs.size() != instance.getnHubs()) {
				int randomNode = rnd.nextInt(instance.getnNodes()) + 1;
				
				if(!asignedNodes.contains(randomNode)) {
					hubs.add(new CPHNode(randomNode, instance));
					spokes[randomNode] = -1;
					asignedNodes.add(randomNode);
				}
			} else {
				for(CPHNode h: hubs) {
					int randomNode = rnd.nextInt(instance.getnNodes()) + 1;
					
					if(!asignedNodes.contains(randomNode)) {
						int weight = instance.getmNodes()[randomNode][2];
												
						if((sumHubs[h.getIndex()] + weight) <= instance.getWeightHub()) {
							
							sumHubs[h.getIndex()] += weight;
							spokes[randomNode] = h.getIndex();
							asignedNodes.add(randomNode);
						}
					}
				}
				
			}
			
		} while(hubs.size() != instance.getnHubs() || asignedNodes.size() != instance.getnNodes());		
		
		return new CPHSolution(instance, hubs, spokes);
	}
	
}
