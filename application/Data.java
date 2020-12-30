package application;

/**
 * This class stored the final data into farmID, weight and percent as per the
 * user's report.
 * 
 * @author kavya
 *
 */
public class Data {

	private String farmID;
	private double weight;
	private double percent;

	public Data() {

	}

	public Data(String farmID, double weight, double percent) {
		this.farmID = farmID;
		this.weight = weight;
		this.percent = percent;
	}

	public String getFarmID() {
		return farmID;
	}

	public void setFarmID(String farmID) {
		this.farmID = farmID;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

}
