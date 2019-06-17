package CWP;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Common.ElementPrint;

public class CWP {
	private static String cwpFolder = "src/main/resources/CWP";
	private static String CW_hb = cwpFolder + "/CW_hb"; // Normal instance 

	public static void main(String[] args) {
		File dirInstances = new File(CW_hb);
		File listFiles[] = dirInstances.listFiles();
		Arrays.sort(listFiles); // Order show files
		
		List<ElementPrint> elementsPrint = new ArrayList<>();

		for (File fileIntance : listFiles) {
			CWPInstance instance = new CWPInstance(fileIntance);
			instance.loadInstance();
			
			CWPSolution solution = calculateSolutionRandom(instance, 5000);
			elementsPrint.add(new ElementPrint(fileIntance.getName(), solution.getTotalWeight(), solution.toString()));
		}
		
		writeResults(elementsPrint, "CWP Random ", "cwp.txt");
	}
	
	public static void writeResults(List<ElementPrint> fileSolutions, String title, String fileName) {
		String fileOutput = cwpFolder + "/output/" + fileName;
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileOutput))){
			bw.write(title + "\n");
			
			fileSolutions.forEach(r -> {
				try {
					DecimalFormat df = new DecimalFormat("#");
					
					bw.write(r.getNameFile() + "\t" + df.format(r.getResult()) + "\t" + r.getRepresentation() + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			
			System.out.println("File " + fileName + " saved correctly...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static CWPSolution calculateSolutionRandom(CWPInstance instance, int solutions) {
		CWPRandomConstructive constructive = new CWPRandomConstructive(instance);
		List<CWPSolution> randomSolutions = constructive.solutions(solutions);
		
		System.out.print("Random solution - " + instance.getFile().getName() + ":\t");

		CWPSolutionsList bestSolutions = new CWPSolutionsList(solutions);
		bestSolutions.addAllSolutions(randomSolutions.iterator());
		
		System.out.print(bestSolutions.getBestSolution().getTotalWeight() + "\t" + bestSolutions.getBestSolution().toString() + "\n");
					
		return bestSolutions.getBestSolution();
	}
	
	public static CWPSolution calculateSolutionFirstImprovementRandom(CWPInstance instance, int solutions) {
		CWPRandomConstructive constructive = new CWPRandomConstructive(instance);
		List<CWPSolution> randomSolutions = constructive.solutions(solutions);
		CWPFirstImprovement fi = new CWPFirstImprovement();
		CWPSolutionsList bestSolutions = new CWPSolutionsList(solutions);
		
		System.out.print("First Improvement Random - " + instance.getFile().getName() + ":\t");

		for(int i=0; i < randomSolutions.size(); i++) {
			CWPSolution imSolution =  fi.improveSolutionRandom(randomSolutions.get(i));
			bestSolutions.addSolution(imSolution);
		}
		
		System.out.print(bestSolutions.getBestSolution().getTotalWeight() + "\t" + bestSolutions.getBestSolution().toString() + "\n");
						
		return bestSolutions.getBestSolution();
	}
	
	public static CWPSolution calculateSolutionFirstImprovementLexicographical(CWPInstance instance, int solutions) {
		CWPRandomConstructive constructive = new CWPRandomConstructive(instance);
		List<CWPSolution> randomSolutions = constructive.solutions(solutions);
		CWPFirstImprovement fi = new CWPFirstImprovement();
		CWPSolutionsList bestSolutions = new CWPSolutionsList(solutions);

		System.out.print("First Improvement Lexicographical - " + instance.getFile().getName() + ":\t");

		for(int i=0; i < randomSolutions.size(); i++) {
			CWPSolution imSolution =  fi.improveSolutionLexicographical(randomSolutions.get(i));
			randomSolutions.set(i, imSolution);
			bestSolutions.addSolution(imSolution);
		}
		
		System.out.print(bestSolutions.getBestSolution().getTotalWeight() + "\t" + bestSolutions.getBestSolution().toString() + "\n");
						
		return bestSolutions.getBestSolution();
	}
	
	public static CWPSolution calculateSolutionBestImprovement(CWPInstance instance, int solutions) {
		CWPRandomConstructive constructive = new CWPRandomConstructive(instance);
		List<CWPSolution> randomSolutions = constructive.solutions(solutions);
		CWPSolutionsList bestSolutions = new CWPSolutionsList(solutions);
		CWPBestImprovement fi = new CWPBestImprovement();
		
		System.out.print("Best Improvement - " + instance.getFile().getName() + ":\t");

		for(int i=0; i < randomSolutions.size(); i++) {
			CWPSolution imSolution =  fi.improveSolution(randomSolutions.get(i));
			bestSolutions.addSolution(imSolution);
		}
		
		System.out.print(bestSolutions.getBestSolution().getTotalWeight() + "\t" + bestSolutions.getBestSolution().toString() + "\n");
						
		return bestSolutions.getBestSolution();
	}
}
