package MDP;

public class NodeUsed implements Standstill{

	private Node node;
	
	public NodeUsed() {
	}
	
	public NodeUsed(Node node) {
		this.node = node;
	}
	
	@Override
	public Node getNode() {
		return node;
	}

	@Override
	public double getDistanceTo(Standstill standstill) {
		return node.getDistanceToNode(standstill.getNode());
	}

}
