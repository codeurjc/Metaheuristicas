package MDP;

import java.util.ArrayList;
import java.util.List;

public class Graph {
	private double weights[][];
	private List<Node> nodes;
	private List<NodeUsed> nodesUsed;
	private List<Diversity> diversity;
	private int numSolutionNodes;
	
	public Graph(double[][] weights, int numSolutionNodes) {
		this.numSolutionNodes = numSolutionNodes;
		this.weights = weights;
		
		nodes = new ArrayList<>();
		for(int i=0; i < weights.length; i++) {
			nodes.add(new Node(i, this));
		}
		
		nodesUsed = new ArrayList<>();
		for(Node n: nodes) {
			nodesUsed.add(new NodeUsed(n));
		}
		
		diversity = new ArrayList<>();
		for(int i=nodes.size() - 1; i >= 0; i--) {
			diversity.add(new Diversity(nodes.get(i)));
		}
	}

	public double[][] getWeights() {
		return weights;
	}

	public void setWeights(double[][] weights) {
		this.weights = weights;
	}
	
	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public List<NodeUsed> getNodesUsed() {
		return nodesUsed;
	}

	public void setNodesUsed(List<NodeUsed> nodesUsed) {
		this.nodesUsed = nodesUsed;
	}

	public List<Diversity> getDiversity() {
		return diversity;
	}

	public void setDiversity(List<Diversity> diversity) {
		this.diversity = diversity;
	}

	public int getNumSolutionNodes() {
		return numSolutionNodes;
	}

	public void setNumSolutionNodes(int numSolutionNodes) {
		this.numSolutionNodes = numSolutionNodes;
	}

	@Override
	public String toString() {
		String output = "Weights: \n";
		
		for(int i=0; i < weights.length; i++) {
			for(int j=0; j < weights[i].length; j++) {
				output += "[" + i + "," + j + "] = " + weights[i][j] + "\n";
			}
		}
		
		return output;
	}
	
}
