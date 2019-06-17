package CWP;

public class CWPNode implements Comparable<CWPNode>{
	private int index;
	private CWPInstance instance;

	public CWPNode(int index, CWPInstance instance) {
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

	public CWPInstance getInstance() {
		return instance;
	}

	public void setInstance(CWPInstance instance) {
		this.instance = instance;
	}

	public int isVertex(CWPNode n) {
		return instance.getWeight(index, n.getIndex());
	}

	@Override
	public boolean equals(Object obj) {
		CWPNode n = (CWPNode) obj;

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
	public int compareTo(CWPNode n) {
		return this.index - n.index;
	}

}
