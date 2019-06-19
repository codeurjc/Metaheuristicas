package MMDP;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Common.ElementPrint;

public class MMDP {
	private static String mmdpFolder = "src/main/resources/MMDP";
	private static String GKD_Ia = mmdpFolder + "/GKD-Ia"; // Small instance
	private static String GKD_IC_1_10 = mmdpFolder + "/GKD-Ic_1_10"; // Normal instance
	private static String GKD_IC_11_20 = mmdpFolder + "/GKD-Ic_11_20"; // Big instance
	
	public static void main(String[] args) {
		File dirInstances = new File(GKD_IC_1_10);
		File listFiles[] = dirInstances.listFiles();
		Arrays.sort(listFiles); // Order show files

		List<ElementPrint> elementsPrint = new ArrayList<>();
		
		for (File fileIntance : listFiles) {
			MMDPInstance instance = new MMDPInstance(fileIntance);
			instance.loadInstance();
			
			MMDPSolution solution = calculateBestImprovementSolution(instance, 1);
			elementsPrint.add(new ElementPrint(fileIntance.getName(), solution.getTotalWeight(), solution.toString()));
		}
		
		writeResults(elementsPrint, "MMDP Random ", "mmdp.txt");
	}
	
	public static void writeResults(List<ElementPrint> fileSolutions, String title, String fileName) {
		String fileOutput = mmdpFolder + "/output/" + fileName;
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileOutput))){
			bw.write(title + "\n");
			
			fileSolutions.forEach(r -> {
				try {
					DecimalFormat df = new DecimalFormat("#.####");
					
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

	public static MMDPSolution calculateRandomSolution(MMDPInstance instance, int solutions) {
		MMDPRandomConstructive constructive = new MMDPRandomConstructive(instance);
		List<MMDPSolution> randomSolutions = constructive.solutions(solutions);
		
		System.out.print("Random solution - " + instance.getFile().getName() + ":\t");
		
		MMDPSolutionsList bestSolutions = new MMDPSolutionsList(solutions);
		bestSolutions.addAllSolutions(randomSolutions.iterator());
		
		System.out.print(bestSolutions.getBestSolution().getTotalWeight() + "\t" + bestSolutions.getBestSolution().toString() + "\n");
		
		return bestSolutions.getBestSolution();
	}
	
	public static MMDPSolution calculateFirstImprovementRandomSolution(MMDPInstance instance, int solutions) {
		MMDPRandomConstructive constructive = new MMDPRandomConstructive(instance);
		List<MMDPSolution> randomSolutions = constructive.solutions(solutions);
		MMDPFirstImprovement fi = new MMDPFirstImprovement();
		MMDPSolutionsList bestSolutions = new MMDPSolutionsList(solutions);
		
		System.out.print("First Improvement Random - " + instance.getFile().getName() + ":\t");
		
		for(int i=0; i < randomSolutions.size(); i++) {
			MMDPSolution imSolution =  fi.improveSolutionRandom(randomSolutions.get(i));
			bestSolutions.addSolution(imSolution);
		}
		
		System.out.print(bestSolutions.getBestSolution().getTotalWeight() + "\t" + bestSolutions.getBestSolution().toString() + "\n");
				
		return bestSolutions.getBestSolution();
	}
	
	public static MMDPSolution calculateFirstImprovementLexicographicalSolution(MMDPInstance instance, int solution) {
		MMDPRandomConstructive constructive = new MMDPRandomConstructive(instance);
		List<MMDPSolution> randomSolutions = constructive.solutions(solution);
		MMDPFirstImprovement fi = new MMDPFirstImprovement();
		MMDPSolutionsList bestSolutions = new MMDPSolutionsList(solution);
		
		System.out.print("First Improvement Lexicographical - " + instance.getFile().getName() + ":\t");
		
		for(int i=0; i < randomSolutions.size(); i++) {
			MMDPSolution imSolution =  fi.improveSolutionLexicographical(randomSolutions.get(i));
			bestSolutions.addSolution(imSolution);
		}
			
		System.out.print(bestSolutions.getBestSolution().getTotalWeight() + "\t" + bestSolutions.getBestSolution().toString() + "\n");
		
		return bestSolutions.getBestSolution();
	}
	
	public static MMDPSolution calculateBestImprovementSolution(MMDPInstance instance, int solutions) {
		MMDPRandomConstructive constructive = new MMDPRandomConstructive(instance);
		List<MMDPSolution> randomSolutions = constructive.solutions(solutions);
		MMDPBestImprovement fi = new MMDPBestImprovement();
		MMDPSolutionsList bestSolutions = new MMDPSolutionsList(solutions);
		
		System.out.print("Best Improvement - " + instance.getFile().getName() + ":\t");
		
		for(int i=0; i < randomSolutions.size(); i++) {
			MMDPSolution imSolution =  fi.improveSolution(randomSolutions.get(i));
			bestSolutions.addSolution(imSolution);
		}
				 
		System.out.print(bestSolutions.getBestSolution().getTotalWeight() + "\t" + bestSolutions.getBestSolution().toString() + "\n");
				
		return bestSolutions.getBestSolution();
	}
	
	public static MMDPSolution calculateTabuSearchSolution(MMDPInstance instance) {
		MMDPRandomConstructive constructive = new MMDPRandomConstructive(instance);
		List<MMDPSolution> randomSolutions = constructive.solutions(1);
		MMDPTabuSearch tabuSearch = new MMDPTabuSearch();
		MMDPSolutionsList bestSolutions = new MMDPSolutionsList(1);
		
		System.out.print("TabuSearch - " + instance.getFile().getName() + ":\t");
		
		for(int i=0; i < randomSolutions.size(); i++) {
			MMDPSolution imSolution =  tabuSearch.improveSolution(randomSolutions.get(i));
			bestSolutions.addSolution(imSolution);
		}
						 
		System.out.print(bestSolutions.getBestSolution().getTotalWeight() + "\t" + bestSolutions.getBestSolution().toString() + "\n");
		
		return bestSolutions.getBestSolution();
	}
}