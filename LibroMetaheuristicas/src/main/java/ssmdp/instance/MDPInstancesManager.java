package ssmdp.instance;

import java.util.ArrayList;
import java.util.List;

public class MDPInstancesManager {

    public MDPInstance type1_1 = new MDPInstance("Type1.1.txt","Type 1 1",10);
    public MDPInstance type1_2 = new MDPInstance("Type1.2.txt","Type 1 2",10);
    public MDPInstance type1_3 = new MDPInstance("Type1.3.txt","Type 1 3",15);
    public MDPInstance type1_4 = new MDPInstance("Type1.4.txt","Type 1 4",15);
    public MDPInstance type1_5 = new MDPInstance("Type1.5.txt","Type 1 5",20);
    public MDPInstance type1_6 = new MDPInstance("Type1.6.txt","Type 1 6",20);
    public MDPInstance type1_7 = new MDPInstance("Type1.7.txt","Type 1 7",25);
    public MDPInstance type1_8 = new MDPInstance("Type1.8.txt","Type 1 8",25);
    public MDPInstance type1_9 = new MDPInstance("Type1.9.txt","Type 1 9",30);
    public MDPInstance type1_10 = new MDPInstance("Type1.10.txt","Type 1 10",30);

    public MDPInstance type2_1 = new MDPInstance("Type2.1.txt","Type 2 1",10);
    public MDPInstance type2_2 = new MDPInstance("Type2.1.txt","Type 2 2",10);
    public MDPInstance type2_3 = new MDPInstance("Type2.1.txt","Type 2 3",15);
    public MDPInstance type2_4 = new MDPInstance("Type2.1.txt","Type 2 4",15);
    public MDPInstance type2_5 = new MDPInstance("Type2.1.txt","Type 2 5",20);
    public MDPInstance type2_6 = new MDPInstance("Type2.1.txt","Type 2 6",20);
    public MDPInstance type2_7 = new MDPInstance("Type2.1.txt","Type 2 7",25);
    public MDPInstance type2_8 = new MDPInstance("Type2.1.txt","Type 2 8",25);
    public MDPInstance type2_9 = new MDPInstance("Type2.1.txt","Type 2 9",30);
    public MDPInstance type2_10 = new MDPInstance("Type2.1.txt","Type 2 10",30);

    private List<MDPInstance> type1Instances = new ArrayList<MDPInstance>();
    private List<MDPInstance> type2Instances = new ArrayList<MDPInstance>();

    private List<MDPInstance> allInstances = new ArrayList<MDPInstance>();

    public MDPInstancesManager() {

        type1Instances.add(type1_1);
        type1Instances.add(type1_2);
        type1Instances.add(type1_3);
        type1Instances.add(type1_4);
        type1Instances.add(type1_5);
        type1Instances.add(type1_6);
        type1Instances.add(type1_7);
        type1Instances.add(type1_8);
        type1Instances.add(type1_9);
        type1Instances.add(type1_10);

        type2Instances.add(type2_1);
        type2Instances.add(type2_2);
        type2Instances.add(type2_3);
        type2Instances.add(type2_4);
        type2Instances.add(type2_5);
        type2Instances.add(type2_6);
        type2Instances.add(type2_7);
        type2Instances.add(type2_8);
        type2Instances.add(type2_9);
        type2Instances.add(type2_10);

        allInstances.addAll(type1Instances);
        allInstances.addAll(type2Instances);

    }

    public List<MDPInstance> getAllInstances() {
        return allInstances;
    }

    public List<MDPInstance> getType1Instances() {
        return type1Instances;
    }

    public List<MDPInstance> getType2Instances() {
        return type2Instances;
    }

}
