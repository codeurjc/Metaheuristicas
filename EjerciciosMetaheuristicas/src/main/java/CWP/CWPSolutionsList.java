package CWP;

import java.util.Iterator;
import java.util.TreeSet;

public class CWPSolutionsList {

	private TreeSet<CWPSolution> solutions;
	
	public CWPSolutionsList(int elements) {
		solutions = new TreeSet<>();
	}
	
	public void addAllSolutions(Iterator<CWPSolution> it) {
		while(it.hasNext()) {
			this.solutions.add(it.next());
		}
	}
	
	public void addSolution(CWPSolution solution) {
		this.solutions.add(solution);
	}
	
	public CWPSolution getBestSolution() {	
		return this.solutions.first();
	}
	
}
