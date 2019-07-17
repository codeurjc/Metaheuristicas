package MDP;

import java.util.List;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.simple.SimpleScore;

@PlanningSolution
public class Solution {
	private List<Node> nodes;
	private List<NodeUsed> nodesUsed;
	private List<Diversity> diversity;
	private SimpleScore score;
	
	public Solution(List<NodeUsed> nodes, List<Diversity> diversity, SimpleScore score) {
		this.nodesUsed = nodes;
		this.diversity = diversity;
		this.score = score;
	}
	
	public Solution() {
	}

	@ProblemFactCollectionProperty
	//@ValueRangeProvider(id = "nodeRange")
	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	@ValueRangeProvider(id = "nodeUsedRange")
	@ProblemFactCollectionProperty
	public List<NodeUsed> getNodesUsed() {
		return nodesUsed;
	}

	public void setNodesUsed(List<NodeUsed> nodes) {
		this.nodesUsed = nodes;
	}
	
	@PlanningEntityCollectionProperty
	@ValueRangeProvider(id = "solutionRange")
	public List<Diversity> getDiversity() {
		return diversity;
	}

	public void setDiversity(List<Diversity> diversity) {
		this.diversity = diversity;
	}

	@PlanningScore
	public SimpleScore getScore() {
		return score;
	}

	public void setScore(SimpleScore score) {
		this.score = score;
	}
}
