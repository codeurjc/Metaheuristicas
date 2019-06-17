
package Common;

public class ElementPrint{

	private String nameFile;
	private double result;
	private String representation;
	
	public ElementPrint(String nameFile, double result) {
		this.nameFile = nameFile;
		this.result = result;
	}

	public ElementPrint(String nameFile, double result, String representation) {
		super();
		this.nameFile = nameFile;
		this.result = result;
		this.representation = representation;
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

	public String getRepresentation() {
		return representation;
	}

	public void setRepresentation(String representation) {
		this.representation = representation;
	}
}
