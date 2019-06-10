package MMDP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MMDPInstance {

	private File file;
	private int n;
	private int m;
	private List<Node> nodes;
	private double weights[][];

	public MMDPInstance(File file) {
		this.file = file;
		this.nodes = new ArrayList<>();
	}

	public void loadInstance() {
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
			
			for(int i=0; i < n; i++) {
				nodes.add(new Node(i, this));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void printInstance() {
		System.out.println("File instance: " + this.file.getName());
		System.out.format("n=%d, m=%d \n", n, m);
		
		for(int i=0; i < weights.length; i++) {
			for(int j=0; j < weights[i].length; j++) {
				System.out.format("[%d, %d] = %.5f \n", i, j, weights[i][j]);
			}
		}
	}

	public File getFile() {
		return file;
	}

	public int getN() {
		return n;
	}

	public int getM() {
		return m;
	}
	
	public double getWeight(int i, int j) {
		return weights[i][j];
	}

	public double[][] getWeights() {
		return weights;
	}
	
	public List<Node> getNodes(){
		return nodes;
	}

}
