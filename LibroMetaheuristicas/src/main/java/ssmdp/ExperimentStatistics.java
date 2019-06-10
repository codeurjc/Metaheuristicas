package ssmdp;


public class ExperimentStatistics {

    private int numInstances;
    private int numConfs;

    private float[][] table;
    private int[] numBest;
    private float[] meanDeviation;

    private boolean calculatedStats = false;

    public ExperimentStatistics(int numInstances, int numConfs){
        this.numInstances = numInstances;
        this.numConfs = numConfs;
        this.table = new float[numInstances][numConfs];
        this.numBest = new int[numConfs];
        this.meanDeviation = new float[numConfs];
    }

    public void setExperimentSol(int instanceNumber, int confNumber, float solWeight) {
        this.table[instanceNumber][confNumber] = solWeight;
        calculatedStats = false;
    }

    public void calculateStats() {
        int[] numValues = new int[numConfs];
        float[] deviationSum = new float[numConfs];

        for(int instance=0; instance<numInstances; instance++){

            float bestValue = 0;

            for(int conf=0; conf<numConfs; conf++){
                if(table[instance][conf] > bestValue){
                    bestValue = table[instance][conf];
                }
            }

            for(int conf=0; conf<numConfs; conf++){

                deviationSum[conf] += (bestValue - table[instance][conf])/bestValue;

                numValues[conf]++;

                if(bestValue == table[instance][conf]){
                    numBest[conf]++;
                }
            }
        }

        for(int conf=0; conf<numConfs; conf++){
            meanDeviation[conf] = deviationSum[conf] / numValues[conf];
        }
        calculatedStats = true;
    }

    public int[] getNumBest(){
        if(!calculatedStats){
            calculateStats();
        }
        return numBest;
    }

    public float[] getMeanDeviation(){
        if(!calculatedStats){
            calculateStats();
        }
        return meanDeviation;
    }

}
