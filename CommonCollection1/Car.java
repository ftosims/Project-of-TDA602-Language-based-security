package serial;

import java.io.Serializable;

public class Car implements Serializable {
	private static final long serialVersionUID = 1L;
	String model;
	int year;
	
	public Car (String model, int year) {
		this.model = model;
		this.year = year;
	}
	
	public String toString() {
		return "Car: { model: '" + model + "', year: " + year + " }";
	}
}
