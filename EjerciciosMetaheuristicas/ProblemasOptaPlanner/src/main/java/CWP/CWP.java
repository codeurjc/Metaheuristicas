package CWP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

public class CWP {
	private static String cwpFolder = "src/main/resources/CWP";
	private static String CW_hb = cwpFolder + "/CW_hb"; // Normal instance 

	public static void main(String[] args) {
		File dirInstances = new File(CW_hb);
		File listFiles[] = dirInstances.listFiles();
		Arrays.sort(listFiles); // Order show files

		//List<ElementPrint> elementsPrint = new ArrayList<>();
		
		for (File fileIntance : listFiles) {
			Graph graph = loadGraph(new File(CW_hb + "/gre__185.mtx.rnd"));
			//Graph graph = loadGraph(fileIntance);
			
			calculateSolution(graph, fileIntance.getName());
			break;
		}
	}
	
	private static Graph loadGraph(File file) {
		int vertex;
		int edge;
		int connections[][];
		
		try (BufferedReader bf = new BufferedReader(new FileReader(file))) {

			String line = bf.readLine();
			StringTokenizer st = new StringTokenizer(line);
			st.nextToken();
			vertex = Integer.parseInt(st.nextToken());
			edge = Integer.parseInt(st.nextToken());

			connections = new int[vertex+1][vertex+1];

			while ((line = bf.readLine()) != null) {
				st = new StringTokenizer(line);

				int i = Integer.parseInt(st.nextToken());
				int j = Integer.parseInt(st.nextToken());

				connections[i][j] = 1;
				connections[j][i] = 1;
			}
			
			return new Graph(connections, vertex, edge);
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
        		"CWP/solver/CWPSolverConfiguration.xml");
        Solver<Solution> solver = solverFactory.buildSolver();

        // Load a nodes
        Solution unsolvedSolution = new Solution();
        unsolvedSolution.setNodes(graph.getNodes());
        unsolvedSolution.setOrder(graph.getOrder());
        
        // Solve the problem
        Solution solved = solver.solve(unsolvedSolution);
        
        System.out.print(fileName + "\t" + solved.getScore() + "\t" + Arrays.toString(solved.getOrder().toArray()) + "\n");
	}
}
