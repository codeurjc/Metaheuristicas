package CWP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class CWPInstance {

	private File file;
	private int vertex;
	private int edge;
	private List<CWPNode> nodes;
	private int weights[][];

	public CWPInstance(File file) {
		this.file = file;
		this.nodes = new ArrayList<>();
	}

	public void loadInstance() {
		try (BufferedReader bf = new BufferedReader(new FileReader(file))) {

			String line = bf.readLine();
			StringTokenizer st = new StringTokenizer(line);
			st.nextToken();
			vertex = Integer.parseInt(st.nextToken());
			edge = Integer.parseInt(st.nextToken());

			weights = new int[vertex+1][vertex+1];

			while ((line = bf.readLine()) != null) {
				st = new StringTokenizer(line);

				int i = Integer.parseInt(st.nextToken());
				int j = Integer.parseInt(st.nextToken());

				weights[i][j] = 1;
				weights[j][i] = 1;
			}
			
			for(int i=1; i <= vertex; i++) {
				nodes.add(new CWPNode(i, this));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void printInstance() {
		System.out.println("File instance: " + this.file.getName());
		System.out.format("vertex=%d, edge=%d \n", vertex, edge);
		
		for(int i=0; i < weights.length; i++) {
			for(int j=0; j < weights[i].length; j++) {
				System.out.format("[%d, %d] = %d \n", i, j, weights[i][j]);
			}
		}
	}
	
	public List<CWPNode> getNodes() {
		return nodes;
	}

	public File getFile() {
		return file;
	}

	public int getVertex() {
		return vertex;
	}

	public int getEdge() {
		return edge;
	}

	public int getWeight(int i, int j) {
		return weights[i][j];
	}

	public int[][] getWeights() {
		return weights;
	}

}
