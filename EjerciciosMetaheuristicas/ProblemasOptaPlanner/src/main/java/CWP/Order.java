package CWP;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class Order {
	private Node node;
	
	public Order() {
	}
	
	public Order(Node node) {
		this.node = node;
	}

	@PlanningVariable(valueRangeProviderRefs= {"nodeRange"})
	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}
	
	@Override
	public boolean equals(Object obj) {
		Order o = (Order)obj;
		return (node.getIndex() == o.getNode().getIndex());
	}

	@Override
	public String toString() {
		if(node == null) {
			return "-1";
		}
		return Integer.toString(node.getIndex());
	}
}
