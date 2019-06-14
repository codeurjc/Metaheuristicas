package CPH;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class CPH {
	private static String cphFolder = "src/main/resources/CHP";
	private static String instancesFolder = cphFolder + "/phub_50_5";
	
	public static void main(String[] args) {

		File dirInstances = new File(instancesFolder);

		for (File fileIntance : dirInstances.listFiles()) {
			CPHInstance instance = new CPHInstance(new File(instancesFolder + "/phub_50_5_1.txt"));
			instance.loadInstance();
			
			System.out.println(fileIntance.getName() + "\t" + calculateSolutionRandom(instance) + "\n");
		}
		
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
}
