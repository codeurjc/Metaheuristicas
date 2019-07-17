package MMDP;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.core.api.domain.variable.PlanningVariableGraphType;

@PlanningEntity
public class Diversity {
	private Node node;
	
	public Diversity() {
	}
	
	public Diversity(Node node) {
		this.node = node;
	}

	@PlanningVariable(valueRangeProviderRefs= {"nodeRange"}, graphType = PlanningVariableGraphType.CHAINED)
	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	@Override
	public String toString() {
		return Integer.toString(node.getIndex());
	}
}
