package MMDP;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.TreeSet;

public class MMDP {
	private static String mmdpFolder = "src/main/resources/MMDP";
	private static String minInstancesFolder = mmdpFolder + "/GKD-Ic_1_10";

	public static void main(String[] args) {
		createFilesAllAlgorithms();
	}
	
	public static void runAlgorithm() {
		File dirInstances = new File(minInstancesFolder);

		for (File fileIntance : dirInstances.listFiles()) {
			MMDPInstance instance = new MMDPInstance(fileIntance);
			instance.loadInstance();
			
			System.out.println(fileIntance.getName() + "\t" + calculateSolutionBestImprovement(instance));
		}
	}
	
	public static void createFilesAllAlgorithms() {
		TreeSet<ElementPrint> solutionsRandom = new TreeSet<>();
		TreeSet<ElementPrint> solutionsFirstImprovementRandom = new TreeSet<>();
		TreeSet<ElementPrint> solutionsFirstImprovementLexicographical = new TreeSet<>();
		TreeSet<ElementPrint> solutionsBestImprovement = new TreeSet<>();
		File dirInstances = new File(minInstancesFolder);

		for (File fileIntance : dirInstances.listFiles()) {
			MMDPInstance instance = new MMDPInstance(fileIntance);
			instance.loadInstance();
			
			solutionsRandom.add(new ElementPrint(fileIntance.getName(), calculateSolutionRandom(instance)));
			solutionsFirstImprovementRandom.add(new ElementPrint(fileIntance.getName(), calculateSolutionFirstImprovementRandom(instance)));
			solutionsFirstImprovementLexicographical.add(new ElementPrint(fileIntance.getName(), calculateSolutionFirstImprovementLexicographical(instance)));
			solutionsBestImprovement.add(new ElementPrint(fileIntance.getName(), calculateSolutionBestImprovement(instance)));
		}
		
		writeResults(solutionsRandom, "Busqueda Aleatoria", "busqueda-aleatoria.txt");
		writeResults(solutionsFirstImprovementRandom, "Busqueda FirstImprovement Aleatoria", "busqueda-first-improvement-aleatoria.txt");
		writeResults(solutionsFirstImprovementLexicographical, "Busqueda FirstImprovement Lexicografica", "busqueda-first-improvement-lexicografica.txt");
		writeResults(solutionsBestImprovement, "Busqueda BestImprovement", "busqueda-best-improvement.txt");
	}
	
	public static void writeResults(TreeSet<ElementPrint> fileSolutions, String title, String fileName) {
		String fileOutput = mmdpFolder + "/output/" + fileName;
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

	public static double calculateSolutionRandom(MMDPInstance instance) {
		RandomConstructive constructive = new RandomConstructive(instance);
		List<MMDPSolution> randomSolutions = constructive.solutions(5000);
		
		SolutionsList bestSolutions = new SolutionsList(5000);
		bestSolutions.addAllSolutions(randomSolutions.iterator());
			
		return bestSolutions.getBestSolution().getTotalWeight();
	}
	
	public static double calculateSolutionFirstImprovementRandom(MMDPInstance instance) {
		RandomConstructive constructive = new RandomConstructive(instance);
		List<MMDPSolution> randomSolutions = constructive.solutions(5000);
		FirstImprovement fi = new FirstImprovement();
		
		for(int i=0; i < randomSolutions.size(); i++) {
			MMDPSolution imSolution =  fi.improveSolutionRandom(randomSolutions.get(i));
			randomSolutions.set(i, imSolution);
		}
		
		SolutionsList bestSolutions = new SolutionsList(5000);
		bestSolutions.addAllSolutions(randomSolutions.iterator());
				
		return bestSolutions.getBestSolution().getTotalWeight();
	}
	
	public static double calculateSolutionFirstImprovementLexicographical(MMDPInstance instance) {
		RandomConstructive constructive = new RandomConstructive(instance);
		List<MMDPSolution> randomSolutions = constructive.solutions(5000);
		FirstImprovement fi = new FirstImprovement();
		
		for(int i=0; i < randomSolutions.size(); i++) {
			MMDPSolution imSolution =  fi.improveSolutionLexicographical(randomSolutions.get(i));
			randomSolutions.set(i, imSolution);
		}
		
		SolutionsList bestSolutions = new SolutionsList(5000);
		bestSolutions.addAllSolutions(randomSolutions.iterator());
				
		return bestSolutions.getBestSolution().getTotalWeight();
	}
	
	public static double calculateSolutionBestImprovement(MMDPInstance instance) {
		RandomConstructive constructive = new RandomConstructive(instance);
		List<MMDPSolution> randomSolutions = constructive.solutions(5000);
		BestImprovement fi = new BestImprovement();
		
		for(int i=0; i < randomSolutions.size(); i++) {
			MMDPSolution imSolution =  fi.improveSolution(randomSolutions.get(i));
			randomSolutions.set(i, imSolution);
		}
		
		SolutionsList bestSolutions = new SolutionsList(5000);
		bestSolutions.addAllSolutions(randomSolutions.iterator());
				
		return bestSolutions.getBestSolution().getTotalWeight();
	}
}