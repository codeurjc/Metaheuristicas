package MMDP;

import java.util.ArrayList;
import java.util.List;

public class Graph {
	private double weights[][];
	private List<Node> nodes;
	private List<Diversity> diversity;
	private int numSolutionNodes;
	
	public Graph(double[][] weights, int numSolutionNodes) {
		this.numSolutionNodes = numSolutionNodes;
		this.weights = weights;
		
		nodes = new ArrayList<>();
		for(int i=0; i < weights.length; i++) {
			nodes.add(new Node(i, this));
		}
		
		diversity = new ArrayList<>();
		for(int i=0; i < numSolutionNodes; i++) {
			diversity.add(new Diversity());
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
