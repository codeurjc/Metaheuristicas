package MDP;

public class Node {
	private int index;
	private Graph graph;

	public Node(int index, Graph graph) {
		this.index = index;
		this.graph = graph;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	public double getDistanceToNode(Node n) {
		return graph.getWeights()[index][n.getIndex()];
	}
	
	@Override
	public String toString() {
		return Integer.toString(index); 
	}
}
