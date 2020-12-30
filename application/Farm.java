package application;

import java.util.Date;

/**
 * This class creates Farm objects which has the farmID, date and weight of the
 * milk.
 * 
 * @author kavya
 *
 */
public class Farm {
	private String farmID;
	private Date date;
	private int weight;

	public Farm() {

	}

	public String getFarmID() {
		return farmID;
	}

	public void setFarmID(String farmID) {
		this.farmID = farmID;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}
