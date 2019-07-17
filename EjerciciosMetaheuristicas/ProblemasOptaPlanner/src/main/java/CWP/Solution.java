package CWP;

import java.util.List;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoftdouble.HardSoftDoubleScore;

@PlanningSolution
public class Solution {
	private List<Node> nodes;
	private List<Order> order;
	private HardSoftDoubleScore score;
	
	public Solution(List<Node> nodes, List<Order> order, HardSoftDoubleScore score) {
		this.nodes = nodes;
		this.order = order;
		this.score = score;
	}
	
	public Solution() {
	}

	@ValueRangeProvider(id = "nodeRange")
	@ProblemFactCollectionProperty
	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	
	@PlanningEntityCollectionProperty
	public List<Order> getOrder() {
		return order;
	}

	public void setOrder(List<Order> order) {
		this.order = order;
	}

	@PlanningScore
	public HardSoftDoubleScore getScore() {
		return score;
	}

	public void setScore(HardSoftDoubleScore score) {
		this.score = score;
	}
}
