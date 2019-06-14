package CPH;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class CPHInstance {

	private File file;
	private int nNodes;
	private int nHubs;
	private int weightHub;
	private int mNodes[][];
	private List<CPHNode> nodes;

	public CPHInstance(File file) {
		this.file = file;
		this.nodes = new ArrayList<>();
	}

	public void loadInstance() {
		try (BufferedReader bf = new BufferedReader(new FileReader(file))) {

			String line = bf.readLine();
			StringTokenizer st = new StringTokenizer(line);
			nNodes = Integer.parseInt(st.nextToken());
			nHubs = Integer.parseInt(st.nextToken());
			weightHub = Integer.parseInt(st.nextToken());
						
			mNodes = new int[nNodes+1][3];

			while ((line = bf.readLine()) != null && !line.equals("")) {
				st = new StringTokenizer(line);

				int node = Integer.parseInt(st.nextToken());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				int value = Integer.parseInt(st.nextToken());
								
				mNodes[node][0] = x;
				mNodes[node][1] = y;
				mNodes[node][2] = value;
			}
			
			for(int i=0; i <= nNodes; i++) {
				nodes.add(new CPHNode(i, this));
			}
			
			// Order list
			Collections.sort(nodes);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void printInstance() {		
		System.out.println("File instance: " + this.file.getName());
		System.out.format("nodes=%d, hubs=%d, weight hub=%d \n", nNodes, nHubs, weightHub);
		
		for(int i=0; i < mNodes.length; i++) {
			System.out.format("%d:\t", i);
			for(int j=0; j < 3; j++) {
				System.out.format("%d\t", mNodes[i][j]);
			}
			System.out.println();
		}
	}

	public File getFile() {
		return file;
	}

	public int getnNodes() {
		return nNodes;
	}

	public int getnHubs() {
		return nHubs;
	}

	public int getWeightHub() {
		return weightHub;
	}

	public int[][] getmNodes() {
		return mNodes;
	}

	public List<CPHNode> getNodes() {
		return nodes;
	}

}
