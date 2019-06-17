
package Common;

public class ElementPrint{

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
}
