package CWP;

import java.util.ArrayList;
import java.util.List;

public class Graph {
	private int connections[][];
	private int vertex;
	private int edge;
	private List<Node> nodes;
	private List<Order> order;
	
	public Graph(int[][] connections, int vertex, int edge) {
		this.connections = connections;
		this.vertex = vertex;
		this.edge = edge;
		
		nodes = new ArrayList<>();
		order = new ArrayList<>();
		for(int i=1; i <= vertex; i++) {
			nodes.add(new Node(i, this));
			order.add(new Order());
		}
	}
	
	public int getConnection(int i, int j) {
		return connections[i][j];
	}

	public int[][] getConnections() {
		return connections;
	}

	public void setConnections(int[][] connections) {
		this.connections = connections;
	}

	public int getVertex() {
		return vertex;
	}

	public void setVertex(int vertex) {
		this.vertex = vertex;
	}

	public int getEdge() {
		return edge;
	}

	public void setEdge(int edge) {
		this.edge = edge;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public List<Order> getOrder() {
		return order;
	}

	public void setOrder(List<Order> order) {
		this.order = order;
	}

	@Override
	public String toString() {
		String output = "Weights: \n";
		
		for(int i=0; i < connections.length; i++) {
			for(int j=0; j < connections[i].length; j++) {
				output += "[" + i + "," + j + "] = " + connections[i][j] + "\n";
			}
		}
		
		return output;
	}
}
