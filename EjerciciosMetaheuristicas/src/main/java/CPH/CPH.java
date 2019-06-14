package CPH;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class CPH {
	private static String cphFolder = "src/main/resources/CHP";
	private static String instancesFolder = cphFolder + "/phub_50_5";
	
	public static void main(String[] args) {

		File dirInstances = new File(instancesFolder);

		
		CPHInstance instance = new CPHInstance(new File(instancesFolder + "/phub_50_5_1.txt"));
		instance.loadInstance();
		
		System.out.println(calculateSolutionBestImprovement(instance) + "\n");
		
	}
	
	public static int calculateSolutionRandom(CPHInstance instance) {
		CPHRandomConstructive constructive = new CPHRandomConstructive(instance);
		List<CPHSolution> randomSolutions = constructive.solutions(5000);
		
		CPHSolutionsList bestSolutions = new CPHSolutionsList(5000);
		bestSolutions.addAllSolutions(randomSolutions.iterator());
		
		System.out.println("Hubs: " + Arrays.toString(bestSolutions.getBestSolution().getHubs().toArray()));
		System.out.println("Relations: " + Arrays.toString(bestSolutions.getBestSolution().getSpokes()));
		
		
		return bestSolutions.getBestSolution().getTotalWeight();
	}
	
	public static double calculateSolutionFirstImprovementRandom(CPHInstance instance) {
		CPHRandomConstructive constructive = new CPHRandomConstructive(instance);
		List<CPHSolution> randomSolutions = constructive.solutions(5000);
		CPHFirstImprovement fi = new CPHFirstImprovement();
		
		for(int i=0; i < randomSolutions.size(); i++) {
			CPHSolution imSolution =  fi.improveSolutionRandom(randomSolutions.get(i));
			randomSolutions.set(i, imSolution);
		}
		
		CPHSolutionsList bestSolutions = new CPHSolutionsList(5000);
		bestSolutions.addAllSolutions(randomSolutions.iterator());
				
		return bestSolutions.getBestSolution().getTotalWeight();
	}
	
	public static double calculateSolutionFirstImprovementLexicographical(CPHInstance instance) {
		CPHRandomConstructive constructive = new CPHRandomConstructive(instance);
		List<CPHSolution> randomSolutions = constructive.solutions(5000);
		CPHFirstImprovement fi = new CPHFirstImprovement();
		
		for(int i=0; i < randomSolutions.size(); i++) {
			CPHSolution imSolution =  fi.improveSolutionLexicographical(randomSolutions.get(i));
			randomSolutions.set(i, imSolution);
		}
		
		CPHSolutionsList bestSolutions = new CPHSolutionsList(5000);
		bestSolutions.addAllSolutions(randomSolutions.iterator());
				
		return bestSolutions.getBestSolution().getTotalWeight();
	}
	
	public static double calculateSolutionBestImprovement(CPHInstance instance) {
		CPHRandomConstructive constructive = new CPHRandomConstructive(instance);
		List<CPHSolution> randomSolutions = constructive.solutions(5000);
		CPHBestImprovement fi = new CPHBestImprovement();
		
		for(int i=0; i < randomSolutions.size(); i++) {
			CPHSolution imSolution =  fi.improveSolution(randomSolutions.get(i));
			randomSolutions.set(i, imSolution);
		}
		
		CPHSolutionsList bestSolutions = new CPHSolutionsList(5000);
		bestSolutions.addAllSolutions(randomSolutions.iterator());
				
		return bestSolutions.getBestSolution().getTotalWeight();
	}
}
