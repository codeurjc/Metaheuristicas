package CWP;

import java.io.File;
import java.util.List;

public class CWP {
	private static String cwpFolder = "src/main/resources/CWP";
	private static String instancesFolder = cwpFolder + "/CW_hb";

	public static void main(String[] args) {

		File dirInstances = new File(instancesFolder);

		CWPInstance instance = new CWPInstance(new File(instancesFolder + "/lns__131.mtx.rnd"));
		instance.loadInstance();
		
		System.out.println("Random best solution: " + calculateSolutionRandom(instance));	
		System.out.println(calculateSolutionBestImprovement(instance, 5000));		
	}
	
	public static int calculateSolutionRandom(CWPInstance instance) {
		CWPRandomConstructive constructive = new CWPRandomConstructive(instance);
		List<CWPSolution> randomSolutions = constructive.solutions(5000);
		
		CWPSolutionsList bestSolutions = new CWPSolutionsList(5000);
		bestSolutions.addAllSolutions(randomSolutions.iterator());
					
		return bestSolutions.getBestSolution().getTotalWeight();
	}
	
	public static double calculateSolutionFirstImprovementRandom(CWPInstance instance) {
		CWPRandomConstructive constructive = new CWPRandomConstructive(instance);
		List<CWPSolution> randomSolutions = constructive.solutions(5000);
		CWPFirstImprovement fi = new CWPFirstImprovement();
		
		for(int i=0; i < randomSolutions.size(); i++) {
			CWPSolution imSolution =  fi.improveSolutionRandom(randomSolutions.get(i));
			randomSolutions.set(i, imSolution);
		}
		
		CWPSolutionsList bestSolutions = new CWPSolutionsList(5000);
		bestSolutions.addAllSolutions(randomSolutions.iterator());
				
		return bestSolutions.getBestSolution().getTotalWeight();
	}
	
	public static double calculateSolutionFirstImprovementLexicographical(CWPInstance instance) {
		CWPRandomConstructive constructive = new CWPRandomConstructive(instance);
		List<CWPSolution> randomSolutions = constructive.solutions(5000);
		CWPFirstImprovement fi = new CWPFirstImprovement();
		
		for(int i=0; i < randomSolutions.size(); i++) {
			CWPSolution imSolution =  fi.improveSolutionLexicographical(randomSolutions.get(i));
			randomSolutions.set(i, imSolution);
		}
		
		CWPSolutionsList bestSolutions = new CWPSolutionsList(5000);
		bestSolutions.addAllSolutions(randomSolutions.iterator());
				
		return bestSolutions.getBestSolution().getTotalWeight();
	}
	
	public static double calculateSolutionBestImprovement(CWPInstance instance, int numRandomSolutions) {
		CWPRandomConstructive constructive = new CWPRandomConstructive(instance);
		List<CWPSolution> randomSolutions = constructive.solutions(numRandomSolutions);
		CWPSolutionsList bestSolutions = new CWPSolutionsList(numRandomSolutions);
		CWPBestImprovement fi = new CWPBestImprovement();
		
		System.out.println("Improve the random solutions with the method 'Best Improvement'");
		System.out.println("Calculate solution from the file: " + instance.getFile().getName());
		System.out.println("Generate " + "'" + numRandomSolutions + "'" + " random solutions");
		
		long startTime = System.currentTimeMillis();
		System.out.print("Best solutions found: [\t");
		int weightBestSolution = -1;
		for(int i=0; i < randomSolutions.size(); i++) {
			CWPSolution imSolution =  fi.improveSolution(randomSolutions.get(i));
			bestSolutions.addSolution(imSolution);
			int weightSolution = bestSolutions.getBestSolution().getTotalWeight();
			System.out.println("Vuelta: " + i);
			if(weightSolution < weightBestSolution || weightBestSolution == -1) {
				weightBestSolution = weightSolution;
				
				System.out.print(weightBestSolution + "\t");
			}
		}
		long endTime = System.currentTimeMillis();
		
		System.out.print("]\n");
		System.out.println("Total time: " + ((endTime - startTime)/60000) + " minutes");
		
						
		return bestSolutions.getBestSolution().getTotalWeight();
	}
}
