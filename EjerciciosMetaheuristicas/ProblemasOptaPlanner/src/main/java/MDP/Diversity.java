package MDP;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.core.api.domain.variable.PlanningVariableGraphType;

@PlanningEntity
public class Diversity implements Standstill{
	private Node node;
	
	private Standstill previousStandstill;
	
	public Diversity() {
	}
	
	public Diversity(Node node) {
		this.node = node;
	}

	@Override
	//@PlanningVariable(valueRangeProviderRefs= {"nodeRange"})
	public Node getNode() {
		return node;
	}
	
	public void setNode(Node node) {
		this.node = node;
	}
	
	@PlanningVariable(valueRangeProviderRefs= {"nodeUsedRange", "solutionRange"}, 
			graphType = PlanningVariableGraphType.CHAINED)
	public Standstill getPreviousStandstill() {
		return previousStandstill;
	}
	
    public void setPreviousStandstill(Standstill previousStandstill) {
        this.previousStandstill = previousStandstill;
    }

	@Override
	public String toString() {
		return Integer.toString(node.getIndex());
	}

	@Override
	public double getDistanceTo(Standstill standstill) {
		return node.getDistanceToNode(standstill.getNode());
	}
}
