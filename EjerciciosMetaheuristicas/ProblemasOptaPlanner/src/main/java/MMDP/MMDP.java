package MMDP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

public class MMDP {
	private static String mmdpFolder = "src/main/resources/MMDP";
	private static String solverConfigurationXML = mmdpFolder + "/solver/MMDPSolverConfiguration.xml";
	private static String GKD_Ia = mmdpFolder + "/GKD-Ia"; // Small instance
	private static String GKD_IC_1_10 = mmdpFolder + "/GKD-Ic_1_10"; // Normal instance
	private static String GKD_IC_11_20 = mmdpFolder + "/GKD-Ic_11_20"; // Big instance

	
	public static void main(String[] args) {
		File dirInstances = new File(GKD_Ia);
		File listFiles[] = dirInstances.listFiles();
		Arrays.sort(listFiles); // Order show files

		//List<ElementPrint> elementsPrint = new ArrayList<>();
		
		for (File fileIntance : listFiles) {
			Graph graph = loadGraph(fileIntance);
			
			calculateSolution(graph, fileIntance.getName());
			break;
		}
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
        		"MMDP/solver/MMDPSolverConfiguration.xml");
        Solver<Solution> solver = solverFactory.buildSolver();

        // Load a nodes
        Solution unsolvedSolution = new Solution();
        unsolvedSolution.setNodes(graph.getNodes());
        unsolvedSolution.setDiversity(graph.getDiversity());
        
        // Solve the problem
        Solution solved = solver.solve(unsolvedSolution);
        
        System.out.print(fileName + "\t" + solved.getScore() + "\t" + Arrays.toString(solved.getDiversity().toArray()) + "\n");
	}
}
