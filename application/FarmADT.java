package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;


/**
 * This is a singleton class that reads the files and creates a data structure
 * for each report
 * 
 * @author kavya
 *
 */
public class FarmADT {

	public Hashtable<String, ArrayList<Farm>> farmMap = new Hashtable<String, ArrayList<Farm>>();
	public Hashtable<String, Double> annualFarmMap = new Hashtable<String, Double>();
	public Hashtable<String, Double> monthFarmMap = new Hashtable<String, Double>();
	public Hashtable<String, Double> dateFarmMap = new Hashtable<String, Double>();

	/**
	 * Private constructor to allow only on instance of the class
	 */
	private FarmADT() {
		
	}
	
	private static FarmADT instance = new FarmADT();

	/**
	 * This method gets the only instance of FarmADT
	 * 
	 * @return
	 */
	public static FarmADT getInstance() {
		return instance;
	}

	/**
	 * Adds the key as the year and month(201901) and the value as the Farm object
	 * to the farmMap hashtable
	 * 
	 * @param date
	 * @param farm
	 */
	private void addToFarmMap(Date date, Farm farm) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		cal.setTime(date);
		String curYear = Integer.toString(cal.get(Calendar.YEAR));
		String curMonth = Integer.toString(cal.get(Calendar.MONTH) + 1);
		if (curMonth.length() == 1) {
			curMonth = "0" + curMonth;
		}
		String key = curYear + curMonth; // constructing the key
		put(key, farm);
	}

	/**
	 * inserts into the farmMap hashtable
	 * 
	 * @param key
	 * @param farm
	 */
	private void put(String key, Farm farm) {
		if (farmMap.containsKey(key)) {
			farmMap.get(key).add(farm);
		} else {
			ArrayList<Farm> arr = new ArrayList<>();
			arr.add(farm);
			farmMap.put(key, arr);
		}

	}

	/**
	 * Creates an array of type Data which stores the weight and percent of the milk
	 * for each month for the farm report
	 * 
	 * @param farmID
	 * @param year
	 * @return
	 */
	public Data[] getDataForFarmReport(String farmID, String year) {

		Set<String> keys = farmMap.keySet();

		Data[] dataArr = new Data[12];
		for (int i = 0; i < 12; i++) {
			dataArr[i] = new Data();
		}
		for (String key : keys) { // iterating through the farmMap
			char[] keyarr = key.toCharArray();
			String curYear = "";
			for (int i = 0; i < 4; i++) {
				curYear += Character.toString(keyarr[i]);
			}
			if (!curYear.equals(year)) {
				continue;
			}
			int curMonth = 0;
			if (keyarr[4] == '0') {
				curMonth = Integer.parseInt(Character.toString(keyarr[5])) - 1;
			} else {
				curMonth = Integer.parseInt(
						Character.toString(keyarr[4]) + Character.toString(keyarr[5]))
						- 1;
			}

			ArrayList<Farm> tmpList = farmMap.get(key);
			Iterator<Farm> it = tmpList.iterator();
			double weight = 0, total = 0, percent = 0;
			while (it.hasNext()) { // iterating through the arraylist values of the farmMap
				Farm tmp = (Farm) it.next();
				total += tmp.getWeight();
				if (tmp.getFarmID().equals(farmID)) {
					weight += tmp.getWeight();
				}
			}
			// adding the weight and percent of each month to the array
			dataArr[curMonth].setWeight(weight);
			percent = (double) (weight / total) * 100;
			percent = Math.round(percent * 100.0) / 100.0;
			dataArr[curMonth].setPercent(percent);
		}

		return dataArr;
	}

	/**
	 * constructs a key of year and farmID(2019Farm 0) and stores it in an
	 * annualFarmMap with the value as the weight of the milk for that year and
	 * farmID
	 * 
	 * @param date
	 * @param farm
	 */
	private void addToAnnualFarmMap(Date date, Farm farm) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		cal.setTime(date);
		String curYear = Integer.toString(cal.get(Calendar.YEAR));
		String key = curYear + farm.getFarmID(); // constructing the key
		if (annualFarmMap.get(key) == null) {
			double weight = farm.getWeight();
			annualFarmMap.put(key, weight);
		} else {
			double weight = farm.getWeight() + annualFarmMap.get(key);
			annualFarmMap.put(key, weight);
		}
	}

	/**
	 * Creates an arraylist of type Data which stores the weight and percent of the
	 * milk for the user specific year
	 * 
	 * @param year
	 * @return
	 */
	public ArrayList<Data> getDataForAnnualReport(String year) {
		double weight = 0, percent = 0, annualtotal = 0;
		ArrayList<Data> annualArr = new ArrayList<Data>();
		Set<String> keys = annualFarmMap.keySet();
		for (String key : keys) { // iterating through the annualFarmMap
			char[] keyarr = key.toCharArray();
			String curYear = "", curFarmID = "";
			for (int i = 0; i < 4; i++) {
				curYear += Character.toString(keyarr[i]);
			}
			if (!curYear.equals(year)) {
				continue;
			}
			for (int i = 4; i < keyarr.length; i++) {
				curFarmID += Character.toString(keyarr[i]);
			}
			weight = annualFarmMap.get(key); // adding the weight to the arraylist
			annualtotal += weight;
			Data tmp = new Data(curFarmID, weight, percent);
			annualArr.add(tmp);
		}

		// adding the percent to the arraylist
		for (int i = 0; i < annualArr.size(); i++) {
			percent = annualArr.get(i).getWeight() / annualtotal * 100;
			percent = Math.round(percent * 100.0) / 100.0;
			annualArr.get(i).setPercent(percent);
		}

		return annualArr;
	}

	/**
	 * constructs a key of month,year and farmID(012019Farm 0) and stores it in a
	 * monthFarmMap with the value as the weight of the milk for that month,year and
	 * farmID
	 * 
	 * @param date
	 * @param farm
	 */
	private void addToMonthFarmMap(Date date, Farm farm) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		cal.setTime(date);
		String curYear = Integer.toString(cal.get(Calendar.YEAR));
		String curMonth = Integer.toString(cal.get(Calendar.MONTH) + 1);
		if (curMonth.length() == 1) {
			curMonth = "0" + curMonth;
		}
		String key = curMonth + curYear + farm.getFarmID(); // constructing the key
		if (monthFarmMap.get(key) == null) {
			double weight = farm.getWeight();
			monthFarmMap.put(key, weight);
		} else {
			double weight = farm.getWeight() + monthFarmMap.get(key);
			monthFarmMap.put(key, weight);
		}
	}

	/**
	 * Creates an arraylist of type Data which stores the weight and percent of the
	 * milk for the user specific month
	 * 
	 * @param month
	 * @param year
	 * @return
	 */
	public ArrayList<Data> getDataForMonthlyReport(String month, String year) {
		double weight = 0, monthtotal = 0;
		ArrayList<Data> monthArr = new ArrayList<Data>();
		Set<String> keys = monthFarmMap.keySet();
		for (String key : keys) { // iterating through the monthFarmMap
			char[] keyarr = key.toCharArray();
			String curMonth = "", curYear = "", curFarmID = "";
			for (int i = 0; i < 2; i++) {
				curMonth += Character.toString(keyarr[i]);
			}
			for (int i = 2; i < 6; i++) {
				curYear += Character.toString(keyarr[i]);
			}
			if (curYear.equals(year) && curMonth.equals(month)) {

				for (int i = 6; i < keyarr.length; i++) {
					curFarmID += Character.toString(keyarr[i]);
				}

				weight = monthFarmMap.get(key); // adding the weight to the arraylist
				monthtotal += weight;
				Data tmp = new Data(curFarmID, weight, 0);
				monthArr.add(tmp);
			}
		}

		// adding the percent to the arraylist
		for (int i = 0; i < monthArr.size(); i++) {
			double percent = monthArr.get(i).getWeight() / monthtotal * 100;
			percent = Math.round(percent * 100.0) / 100.0;
			monthArr.get(i).setPercent(percent);
		}

		return monthArr;
	}

	/**
	 * constructs a key of date and farmID and stores it in a dateFarmMap with the
	 * value as the weight of the milk for that date and farmID
	 * 
	 * @param date
	 * @param farm
	 */
	private void addToDateFarmMap(String date, Farm farm) {
		String key = date + farm.getFarmID(); // constructing the key
		if (dateFarmMap.get(key) == null) {
			double weight = farm.getWeight();
			dateFarmMap.put(key, weight);
		} else {
			double weight = farm.getWeight() + dateFarmMap.get(key);
			dateFarmMap.put(key, weight);
		}
	}

	/**
	 * Creates an arraylist of type Data which stores the weight and percent of the
	 * milk for the user specific range of dates
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public ArrayList<Data> getDataForDateRangeReport(String startDate,
			String endDate) {
		ArrayList<Data> dateArr = new ArrayList<Data>();
		Set<String> keys = dateFarmMap.keySet();
		double dateTotal = 0;
		for (String key : keys) { // iterating through the dateFarmMap

			char[] keyarr = key.toCharArray();
			String curFarmID = "", date = "";
			for (int i = 0; i < keyarr.length; i++) {
				if (keyarr[i] == 'F') {
					break;
				}
				date += Character.toString(keyarr[i]);
			}
			int flag1 = 0;
			try {
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd",
						Locale.ENGLISH); // parsing the dates
				Date tmpdate = format.parse(date);
				Date tmpstartDate = format.parse(startDate);
				Date tmpendDate = format.parse(endDate);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(tmpstartDate);
				calendar.add(Calendar.DATE, -1);
				tmpstartDate = calendar.getTime();
				calendar.setTime(tmpendDate);
				calendar.add(Calendar.DATE, 1);
				tmpendDate = calendar.getTime();
				// range between the dates
				if (tmpdate.after(tmpstartDate) && tmpdate.before(tmpendDate)) {
					for (int i = 0; i < keyarr.length; i++) {
						if (keyarr[i] == 'F') {
							flag1 = 1;
						}
						if (flag1 == 1) {
							curFarmID += Character.toString(keyarr[i]);
						}
					}
					int flag = 0;
					for (int i = 0; i < dateArr.size(); i++) {
						if (dateArr.get(i).getFarmID().equals(curFarmID)) {
							double weight = dateFarmMap.get(key) // adding the weight to the arraylist
									+ dateArr.get(i).getWeight();
							dateTotal += dateFarmMap.get(key);
							Data tmp = dateArr.get(i);
							tmp.setWeight(weight);
							flag = 1;
						}
					}
					if (flag == 0) {
						double weight = dateFarmMap.get(key);
						dateTotal += weight;
						Data tmp = new Data(curFarmID, weight, 0);
						dateArr.add(tmp);
					}
				}
			} catch (ParseException p) {

			}
		}
		for (int i = 0; i < dateArr.size(); i++) { // adding the percent to the arraylist
			double percent = dateArr.get(i).getWeight() / dateTotal * 100;
			percent = Math.round(percent * 100.0) / 100.0;
			dateArr.get(i).setPercent(percent);
		}
		return dateArr;
	}
	
	/**
	 * Checks two different formats of date for parsing
	 * 
	 * @param date
	 * @param lineIndex
	 * @return
	 * @throws MyException 
	 */
	private Date checkDateFormat(String date, int lineIndex)
			throws MyException {
		Date newDate = null;
		String message = "";
		try {
			DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			newDate = format1.parse(date);
			return newDate;
		} catch (ParseException p) {
		}
		try {
			DateFormat format1 = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
			newDate = format1.parse(date);
			return newDate;
		} catch (ParseException e) {
			message = "Error at line " + lineIndex + ": \n" + e.getMessage();
			throw new MyException(message);
		}
	}

	/**
	 * clears all the data structures
	 */
	private void clearData() {
		farmMap.clear();
		annualFarmMap.clear();
		monthFarmMap.clear();
		dateFarmMap.clear();
	}
	
	/**
	 * reads from the file and puts the data into each of the reports
	 * 
	 * @param filePath
	 * @throws MyException 
	 * @throws IOException 
	 */
	@SuppressWarnings("unused")
	public void readFile(String filePath) throws MyException, IOException {
		String line = "";
		String splitBy = ",",message="";
		int lineIndex = 0;
		clearData();
		try {
			File dir = new File(filePath); // user's file path
			for (File file : dir.listFiles()) { // reading the list of files from the directory
				BufferedReader br = new BufferedReader(new FileReader(file));
				int index = 1;
				while ((line = br.readLine()) != null) {
					if (index == 1) { // not reading the first line
						index++;
						lineIndex++;
						continue;
					}

					String[] str = line.split(splitBy); // use comma as separator
					Farm farm = new Farm();
					Date date=checkDateFormat(str[0],lineIndex);
					farm.setDate(date);
					farm.setFarmID(str[1]);
					Double weightnum = Double.parseDouble(str[2]);
					farm.setWeight(Integer.parseInt(str[2]));
					// adding data to each report data structure
					addToFarmMap(date, farm);
					addToAnnualFarmMap(date, farm);
					addToMonthFarmMap(date, farm);
					addToDateFarmMap(str[0], farm);
					index++;
					lineIndex++;
				}
				br.close();
			}
		}  catch (NumberFormatException e) {
			message="Error at line "+lineIndex+": \n"+e.getMessage();
			throw new MyException(message);
		} 
	}

}
