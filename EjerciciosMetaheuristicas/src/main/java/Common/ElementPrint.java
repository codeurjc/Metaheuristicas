
package Common;

public class ElementPrint implements Comparable<ElementPrint>{

	private String nameFile;
	private double result;
	
	public ElementPrint(String nameFile, double result) {
		this.nameFile = nameFile;
		this.result = result;
	}

	public String getNameFile() {
		return nameFile;
	}

	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}

	public double getResult() {
		return result;
	}

	public void setResult(double result) {
		this.result = result;
	}

	@Override
	public int compareTo(ElementPrint e) {
		int f1 = Integer.parseInt(nameFile.split("_")[1]);
		int f2 = Integer.parseInt(e.getNameFile().split("_")[1]);
		
		return f1-f2;
	}
}
