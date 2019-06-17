package CPH;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Common.ElementPrint;

public class CPH {
	private static String cphFolder = "src/main/resources/CHP";
	private static String phub_50_5 = cphFolder + "/phub_50_5"; // Normal instance
	private static String phub_100_10 = cphFolder + "/phub_100_10"; //Big instance
	
	public static void main(String[] args) {

		File dirInstances = new File(phub_50_5);
		File listFiles[] = dirInstances.listFiles();
		Arrays.sort(listFiles); // Order show files
		
		List<ElementPrint> elementsPrint = new ArrayList<>();

		for (File fileIntance : listFiles) {
			CPHInstance instance = new CPHInstance(fileIntance);
			instance.loadInstance();
			
			CPHSolution solution = calculateSolutionRandom(instance, 5000);
			elementsPrint.add(new ElementPrint(fileIntance.getName(), solution.getTotalWeight()));
		}
		
		writeResults(elementsPrint, "CPH Random ", "cph.txt");
		
	}
	
	public static void writeResults(List<ElementPrint> fileSolutions, String title, String fileName) {
		String fileOutput = cphFolder + "/output/" + fileName;
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileOutput))){
			bw.write(title + "\n");
			
			fileSolutions.forEach(r -> {
				try {
					DecimalFormat df = new DecimalFormat("#.####");
					
					bw.write(r.getNameFile() + "\t" + df.format(r.getResult()) + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			
			System.out.println("File " + fileName + " saved correctly...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static CPHSolution calculateSolutionRandom(CPHInstance instance, int solutions) {
		CPHRandomConstructive constructive = new CPHRandomConstructive(instance);
		List<CPHSolution> randomSolutions = constructive.solutions(solutions);
		
		System.out.print("Random solution - " + instance.getFile().getName() + ":\t");

		CPHSolutionsList bestSolutions = new CPHSolutionsList(solutions);
		bestSolutions.addAllSolutions(randomSolutions.iterator());
		
		System.out.print(bestSolutions.getBestSolution().getTotalWeight() + "\n");
		
		return bestSolutions.getBestSolution();
	}
	
	public static CPHSolution calculateSolutionFirstImprovementRandom(CPHInstance instance, int solutions) {
		CPHRandomConstructive constructive = new CPHRandomConstructive(instance);
		List<CPHSolution> randomSolutions = constructive.solutions(solutions);
		CPHFirstImprovement fi = new CPHFirstImprovement();
		CPHSolutionsList bestSolutions = new CPHSolutionsList(solutions);
		
		System.out.print("First Improvement Random - " + instance.getFile().getName() + ":\t");

		for(int i=0; i < randomSolutions.size(); i++) {
			CPHSolution imSolution =  fi.improveSolutionRandom(randomSolutions.get(i));
			bestSolutions.addSolution(imSolution);
		}
		
		System.out.print(bestSolutions.getBestSolution().getTotalWeight() + "\n");
						
		return bestSolutions.getBestSolution();
	}
	
	public static CPHSolution calculateSolutionFirstImprovementLexicographical(CPHInstance instance, int solutions) {
		CPHRandomConstructive constructive = new CPHRandomConstructive(instance);
		List<CPHSolution> randomSolutions = constructive.solutions(solutions);
		CPHFirstImprovement fi = new CPHFirstImprovement();
		CPHSolutionsList bestSolutions = new CPHSolutionsList(solutions);

		System.out.print("First Improvement Lexicographical - " + instance.getFile().getName() + ":\t");

		for(int i=0; i < randomSolutions.size(); i++) {
			CPHSolution imSolution =  fi.improveSolutionLexicographical(randomSolutions.get(i));
			bestSolutions.addSolution(imSolution);
		}
		
		System.out.print(bestSolutions.getBestSolution().getTotalWeight() + "\n");
						
		return bestSolutions.getBestSolution();
	}
	
	public static CPHSolution calculateSolutionBestImprovement(CPHInstance instance, int solutions) {
		CPHRandomConstructive constructive = new CPHRandomConstructive(instance);
		List<CPHSolution> randomSolutions = constructive.solutions(solutions);
		CPHBestImprovement fi = new CPHBestImprovement();
		CPHSolutionsList bestSolutions = new CPHSolutionsList(solutions);
		
		System.out.print("Best Improvement - " + instance.getFile().getName() + ":\t");

		for(int i=0; i < randomSolutions.size(); i++) {
			CPHSolution imSolution =  fi.improveSolution(randomSolutions.get(i));
			bestSolutions.addSolution(imSolution);
		}
		
		System.out.print(bestSolutions.getBestSolution().getTotalWeight() + "\n");
						
		return bestSolutions.getBestSolution();
	}
}
