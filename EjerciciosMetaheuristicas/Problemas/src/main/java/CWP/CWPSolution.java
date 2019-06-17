package CWP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CWPSolution implements Comparable<CWPSolution>{
	private CWPInstance instance;
	private int totalWeight;
	private List<CWPNode> nodes;
	
	public CWPSolution(CWPInstance instance, List<CWPNode> nodes) {
		this.instance = instance;
		this.nodes = nodes;
		calculateWeight();
	}

	public void calculateWeight() {		
		totalWeight = 0;
		
		for(int i=0; i < nodes.size(); i++) {
			int weight = 0;
			for(int j=0; j <= i; j++) {
				for(int k=i+1; k < nodes.size(); k++) {
					weight += nodes.get(j).isVertex(nodes.get(k));
				}
			}
			
			totalWeight = Math.max(totalWeight, weight);
		}
	}
	
	public int calculateWeightChangeNode(CWPNode oldNode, CWPNode newNode) {
		List<CWPNode> newNodes = new ArrayList<>();
		
		nodes.forEach(n -> {
			if(n.equals(oldNode)) {
				newNodes.add(newNode);
			} else if(n.equals(newNode)) {
				newNodes.add(oldNode);
			}else {
				newNodes.add(n);
			}
		});
				
		// Value per defect
		int newTotalWeight = 0;
		
		for(int i=0; i < newNodes.size(); i++) {
			int weight = 0;
			for(int j=0; j <= i; j++) {
				for(int k=i+1; k < newNodes.size(); k++) {
					weight += newNodes.get(j).isVertex(newNodes.get(k));
				}
			}
			
			newTotalWeight = Math.max(newTotalWeight, weight);
		}
				
		return newTotalWeight;
	}
	
	public void changeNode(CWPNode oldNode, CWPNode newNode) {
		int changes = 0;
		
		for(int i=0; i < nodes.size(); i++) {
			if(nodes.get(i).equals(oldNode)) {
				nodes.set(i, newNode);
				changes++;
			} else if(nodes.get(i).equals(newNode)) {
				nodes.set(i, oldNode);
				changes++;
			}
			
			if(changes == 2) {
				break;
			}
		}
		
		calculateWeight();
	}

	public int getTotalWeight() {
		return totalWeight;
	}

	public List<CWPNode> getNodes() {
		return nodes;
	}

	public CWPInstance getInstance() {
		return instance;
	}
	
	@Override
	public String toString() {
		return Arrays.toString(this.nodes.toArray());
	}

	@Override
	public int compareTo(CWPSolution sol) {
		return (int)(this.getTotalWeight() - sol.getTotalWeight()); // Minimize 
	}
}
