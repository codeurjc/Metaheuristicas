package ssmdp.instance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import ssmdp.graph.MDPGraph;

public class MDPInstance {

    private String fileName;
    private int numSolutionNodes;
    private String name;

    public MDPInstance(String fileName, String name, int numSolNodes) {
        this.fileName = fileName;
        this.numSolutionNodes = numSolNodes;
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public int getNumSolutionNodes() {
        return numSolutionNodes;
    }

    public String getName(){
        return name;
    }

    public MDPGraph loadGraph(File instancesDirectory) throws IOException, FormatException {
        File pFile = new File(instancesDirectory, fileName);
        return loadGraph(name, numSolutionNodes, pFile);
    }

    private MDPGraph loadGraph(String name, int numSolutionNodes, File file) throws IOException,	FormatException {

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        try {

            String line = br.readLine();
            if (line == null) {
                throw new FormatException("El fichero está vacío");
            }

            int numberOfNodes = Integer.parseInt(line);

            float weights[][] = new float[numberOfNodes - 1][];
            for (int i = 0; i < numberOfNodes - 1; i++) {
                weights[i] = new float[i + 1];
            }

            line = br.readLine();
            while(line != null){

                StringTokenizer st = new StringTokenizer(line);
                int colNumber = Integer.parseInt(st.nextToken());
                int rowNumber = Integer.parseInt(st.nextToken());
                float weight = Float.parseFloat(st.nextToken());

                weights[rowNumber-1][colNumber] = weight;

                line = br.readLine();
            }

            return new MDPGraph(name,numSolutionNodes,weights);

        } catch (NumberFormatException e) {
            throw new FormatException("Error en el formato del fichero");
        }
    }

    public String toString(){
        return "Name: "+name+" FileName:"+fileName+" NumSolutionNodes:"+numSolutionNodes;
    }

}
