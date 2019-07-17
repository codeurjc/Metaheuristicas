package CWP;

public class CWPWeight implements Comparable<CWPWeight> {
	private CWPNode node;
	private int value;

	public CWPWeight(CWPNode node, int value) {
		this.node = node;
		this.value = value;
	}

	public CWPNode getNode() {
		return node;
	}

	public void setNode(CWPNode node) {
		this.node = node;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "[" + node.getIndex() + "]= " + value;
	}

	@Override
	public int compareTo(CWPWeight o) {
		return this.value - o.value;
	}

	public static CWPWeight min(CWPWeight w1, CWPWeight w2) {
		return (w1.getValue() < w2.getValue()) ? w1 : w2;
	}

	public static CWPWeight max(CWPWeight w1, CWPWeight w2) {
		return (w1.getValue() > w2.getValue()) ? w1 : w2;
	}

}
