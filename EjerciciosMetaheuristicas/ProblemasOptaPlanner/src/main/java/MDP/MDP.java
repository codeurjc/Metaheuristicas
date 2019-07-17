package MDP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

public class MDP {
	private static String mdpFolder = "src/main/resources/MDP";
	private static String instances = mdpFolder + "/instances"; // Instances
	private static String prueba = mdpFolder + "/test.txt";
	
	public static void main(String[] args) {
		File dirInstances = new File(instances);
		File listFiles[] = dirInstances.listFiles();
		Arrays.sort(listFiles); // Order show files

		//List<ElementPrint> elementsPrint = new ArrayList<>();
		
		/*for (File fileIntance : listFiles) {
			Graph graph = loadGraph(fileIntance);
			
			calculateSolution(graph, fileIntance.getName());
			break;
		}*/
		
		File testFile = new File(prueba);
		Graph graph = loadGraph(testFile);
		
		calculateSolution(graph, testFile.getName());
	}
	
	private static Graph loadGraph(File file) {
		int n;
		int m;
		double weights[][];

		try (BufferedReader bf = new BufferedReader(new FileReader(file))) {

			String line = bf.readLine();
			StringTokenizer st = new StringTokenizer(line);
			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());

			weights = new double[n][n];

			while ((line = bf.readLine()) != null) {
				st = new StringTokenizer(line);

				int i = Integer.parseInt(st.nextToken());
				int j = Integer.parseInt(st.nextToken());
				double weight = Double.parseDouble(st.nextToken());

				weights[i][j] = weight;
				weights[j][i] = weight;
			}

			return new Graph(weights, m);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static void calculateSolution(Graph graph, String fileName) {
        // Build the Solver
        SolverFactory<Solution> solverFactory = SolverFactory.createFromXmlResource(
        		"MDP/solver/MMDPSolverConfiguration.xml");
        Solver<Solution> solver = solverFactory.buildSolver();
        
        // Load a nodes
        Solution unsolvedSolution = new Solution();
        System.out.println(graph.getNodes().size());
        unsolvedSolution.setDiversity(graph.getDiversity());
        unsolvedSolution.setNodesUsed(graph.getNodesUsed());
        unsolvedSolution.setNodes(graph.getNodes());
        
        // Solve the problem
        Solution solved = solver.solve(unsolvedSolution);
        
        for(Diversity d: solved.getDiversity()) {
        	System.out.println("Nodo: " + d.getNode().getIndex() + " con " + d.getPreviousStandstill().getNode().getIndex());
        }
        
        System.out.print(fileName + "\t" + solved.getScore() + "\t" + Arrays.toString(solved.getDiversity().toArray()) + "\n");
	}
}
