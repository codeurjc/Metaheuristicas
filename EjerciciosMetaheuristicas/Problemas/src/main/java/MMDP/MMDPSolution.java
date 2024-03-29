package MMDP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MMDPSolution implements Comparable<MMDPSolution>{
	private MMDPInstance instance;
	private double totalWeight;
	private List<MMDPNode> nodes;
	
	public MMDPSolution(MMDPInstance instance, List<MMDPNode> nodes) {
		this.instance = instance;
		this.nodes = nodes;
		calculateWeight();
	}

	public void calculateWeight() {
		// Value per defect
		totalWeight = nodes.get(0).getDistanceToNode(nodes.get(1));
		
		for(int i=0; i < nodes.size(); i++) {
			for(int j=i + 1; j < nodes.size(); j++) {
				totalWeight = Math.min(totalWeight, nodes.get(i).getDistanceToNode(nodes.get(j)));
			}
		}
	}
	
	public double calculateWeightChangeNode(MMDPNode oldNode, MMDPNode newNode) {
		List<MMDPNode> newNodes = new ArrayList<>();
		
		nodes.forEach(n -> {
			if(n.equals(oldNode)) {
				newNodes.add(newNode);
			} else {
				newNodes.add(n);
			}
		});
		
		// Value per defect
		double newTotalWeight = newNodes.get(0).getDistanceToNode(newNodes.get(1));
		
		for(int i=0; i < newNodes.size(); i++) {
			for(int j=i + 1; j < newNodes.size(); j++) {
				newTotalWeight = Math.min(newTotalWeight, newNodes.get(i).getDistanceToNode(newNodes.get(j)));
			}
		}
				
		return newTotalWeight;
	}
	
	public void changeNode(MMDPNode oldNode, MMDPNode newNode) {
		for(int i=0; i < nodes.size(); i++) {
			if(nodes.get(i).equals(oldNode)) {
				nodes.set(i, newNode);
				break;
			}
		}
		
		calculateWeight();
	}

	public double getTotalWeight() {
		return totalWeight;
	}

	public List<MMDPNode> getNodes() {
		return nodes;
	}

	public MMDPInstance getInstance() {
		return instance;
	}
	
	@Override
	public String toString() {
		return Arrays.toString(this.nodes.toArray());
	}

	@Override
	public int compareTo(MMDPSolution sol) {
		return (int)(sol.getTotalWeight() - this.getTotalWeight()); // Maximize 
	}
}
