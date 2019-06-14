package CPH;

public class CPHNode implements Comparable<CPHNode>{
	private int index;
	private CPHInstance instance;

	public CPHNode(int index, CPHInstance instance) {
		super();
		this.index = index;
		this.instance = instance;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public CPHInstance getInstance() {
		return instance;
	}

	public void setInstance(CPHInstance instance) {
		this.instance = instance;
	}
	
	public int getDistanceToNode(CPHNode node) {
		// Euclide distance between two nodes sqrt((X2- X1)^2 - (Y2-Y1)^2)
		double calculateX = Math.pow(instance.getmNodes()[node.index][0] - instance.getmNodes()[this.index][0], 2);
		double calculateY = Math.pow(instance.getmNodes()[node.index][1] - instance.getmNodes()[this.index][1], 2);
		
		return (int)Math.sqrt(calculateX + calculateY);
	}
	
	@Override
	public boolean equals(Object obj) {
		CPHNode n = (CPHNode) obj;

		if (this.index == n.index)
			return true;
		else
			return false;
	}
	
	@Override
	public String toString() {
		return Integer.toString(index);
	}

	@Override
	public int compareTo(CPHNode n) {
		return this.index - n.index;
	}

}
