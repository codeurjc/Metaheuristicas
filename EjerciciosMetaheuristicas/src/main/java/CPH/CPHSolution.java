package CPH;

import java.util.List;

public class CPHSolution implements Comparable<CPHSolution>{
	private CPHInstance instance;
	private int totalWeight;
	private List<CPHNode> hubs;
	private List<CPHNode> nodes;
	private int[] spokes;
	
	public CPHSolution(CPHInstance instance, List<CPHNode> hubs, int[] spokes) {
		this.instance = instance;
		this.hubs = hubs;
		this.nodes = instance.getNodes();
		this.spokes = spokes;
		
		calculateWeight();
	}

	public void calculateWeight() {		
		for(int i=0; i < hubs.size(); i++) {
			for(int j=0; j < spokes.length; j++) {
				if(spokes[j] == hubs.get(i).getIndex()) {		
					totalWeight += hubs.get(i).getDistanceToNode(nodes.get(j));
				}
			}
		}
	}
	
	public int calculateWeightChangeHub(CPHNode oldHub, CPHNode newHub) {
		for(int i=0; i < hubs.size(); i++) {
			if(hubs.get(i).equals(oldHub)) {
				hubs.set(i, newHub);
				break;
			}
		}
		
		int newTotalWeight = 0;
		for(int i=0; i < hubs.size(); i++) {
			for(int j=0; j < spokes.length; j++) {
				if(spokes[j] == hubs.get(i).getIndex()) {		
					newTotalWeight += hubs.get(i).getDistanceToNode(nodes.get(j));
				}
			}
		}
		
		return newTotalWeight;
	}
	
	public void changeHub(CPHNode oldHub, CPHNode newHub) {
		for(int i=0; i < hubs.size(); i++) {
			if(hubs.get(i).equals(oldHub)) {
				hubs.set(i, newHub);
				break;
			}
		}
		
		calculateWeight();
	}

	public CPHInstance getInstance() {
		return instance;
	}

	public int getTotalWeight() {
		return totalWeight;
	}

	public List<CPHNode> getHubs() {
		return hubs;
	}

	public List<CPHNode> getNodes() {
		return nodes;
	}

	public int[] getSpokes() {
		return spokes;
	}

	@Override
	public int compareTo(CPHSolution sol) {
		return (int)(this.getTotalWeight() - sol.getTotalWeight()); // Minimize 
	}
}
