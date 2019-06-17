package CPH;

import java.util.ArrayList;
import java.util.Arrays;
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
		totalWeight = 0;
		
		for(int i=0; i < hubs.size(); i++) {
			for(int j=0; j < spokes.length; j++) {
				if(spokes[j] == hubs.get(i).getIndex()) {		
					totalWeight += hubs.get(i).getDistanceToNode(nodes.get(j));
				}
			}
		}
	}
	
	public int calculateWeightChangeHub(CPHNode oldHub, CPHNode newHub) {
		List<CPHNode> newHubs = new ArrayList<>();
		hubs.forEach(h -> {
			if(h.equals(oldHub)) {
				newHubs.add(newHub);
			} else {
				newHubs.add(h);
			}
		});
		
		int newSpokes[] = new int[spokes.length];
		for (int i=0; i < spokes.length; i++) {
			if(spokes[i] == oldHub.getIndex()) {
				newSpokes[i] = newHub.getIndex();
			} else {
				newSpokes[i] = spokes[i];
			}
		}
		
		int newTotalWeight = 0;
		for(int i=0; i < newHubs.size(); i++) {
			for(int j=0; j < newSpokes.length; j++) {
				if(newSpokes[j] == newHubs.get(i).getIndex()) {		
					newTotalWeight += newHubs.get(i).getDistanceToNode(nodes.get(j));
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
		
		for (int i=0; i < spokes.length; i++) {
			if(spokes[i] == oldHub.getIndex()) {
				spokes[i] = newHub.getIndex();
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
	public String toString() {
		List<List<Integer>> printSolution = new ArrayList<>();
		
		hubs.forEach(hub -> {
			List<Integer> elements = new ArrayList<>();
			elements.add(hub.getIndex());
			
			for(int i=0; i < spokes.length; i++) {
				if(spokes[i] == hub.getIndex()) {
					elements.add(i);
				}
			}
			
			printSolution.add(elements);
		});
		
		return Arrays.toString(printSolution.toArray());
	}

	@Override
	public int compareTo(CPHSolution sol) {
		return (int)(this.getTotalWeight() - sol.getTotalWeight()); // Minimize 
	}
}
