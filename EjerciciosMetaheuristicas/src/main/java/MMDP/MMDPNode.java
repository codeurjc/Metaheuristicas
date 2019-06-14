package MMDP;

public class MMDPNode implements Comparable<MMDPNode>{
	private int index;
	private MMDPInstance instance;

	public MMDPNode(int index, MMDPInstance instance) {
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

	public MMDPInstance getInstance() {
		return instance;
	}

	public void setInstance(MMDPInstance instance) {
		this.instance = instance;
	}

	public double getDistanceToNode(MMDPNode n) {
		return instance.getWeight(index, n.getIndex());
	}

	@Override
	public boolean equals(Object obj) {
		MMDPNode n = (MMDPNode) obj;

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
	public int compareTo(MMDPNode n) {
		return this.index - n.index;
	}

}
