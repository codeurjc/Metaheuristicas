package MMDP;

import java.util.Iterator;
import java.util.TreeSet;

public class SolutionsList {

	private TreeSet<MMDPSolution> solutions;
	
	public SolutionsList(int elements) {
		solutions = new TreeSet<>();
	}
	
	public void addAllSolutions(Iterator<MMDPSolution> it) {
		while(it.hasNext()) {
			this.solutions.add(it.next());
		}
	}
	
	public void addSolution(MMDPSolution solution) {
		this.solutions.add(solution);
	}
	
	public MMDPSolution getBestSolution() {	
		return this.solutions.first();
	}
	
}
