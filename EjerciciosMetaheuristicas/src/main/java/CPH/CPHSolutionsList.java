package CPH;

import java.util.Iterator;
import java.util.TreeSet;

public class CPHSolutionsList {

	private TreeSet<CPHSolution> solutions;
	
	public CPHSolutionsList(int elements) {
		solutions = new TreeSet<>();
	}
	
	public void addAllSolutions(Iterator<CPHSolution> it) {
		while(it.hasNext()) {
			this.solutions.add(it.next());
		}
	}
	
	public void addSolution(CPHSolution solution) {
		this.solutions.add(solution);
	}
	
	public CPHSolution getBestSolution() {	
		return this.solutions.first();
	}

	public TreeSet<CPHSolution> getSolutions() {
		return solutions;
	}
	
}
