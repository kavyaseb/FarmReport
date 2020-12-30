package application;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class displays the farm report data in a table with the user input farm
 * and year.
 * 
 * @author kavya
 *
 */
public class FarmReportScreen implements ReportInterface {

	private static TableView<Farm> table;
	private static Button exitButton;
	private static Button backButton;
	private static Button calcButton;

	private static ObservableList<Farm> farmdata;

	private static double min = 0, max = 0, avg = 0, total = 0;

	/**
	 * Inner class for adding the data into the table
	 *
	 */
	public static class Farm {
		private int month;
		private double weight;
		private double percent;

		public Farm(int month, double weight, double percent) {
			this.month = month;
			this.weight = weight;
			this.percent = percent;
		}

		public int getMonth() {
			return month;
		}

		public void setMonth(int tmpMonth) {
			this.month = tmpMonth;
		}

		public double getWeight() {
			return weight;
		}

		public void setWeight(double tmpweight) {
			this.weight = tmpweight;
		}

		public double getPercent() {
			return percent;
		}

		public void setPercent(double tmpercent) {
			this.percent = tmpercent;
		}

	}

	@SuppressWarnings("unchecked")
	public void displayReport(InputParams inputParams) {

		String farmID = inputParams.getFarmID();
		String year = inputParams.getYear();
		FarmADT farmreport = FarmADT.getInstance();
		Data[] farmArr = farmreport.getDataForFarmReport(farmID, year);
		farmdata = FXCollections.observableArrayList();
		min = farmArr[0].getWeight();
		for (int i = 0; i < farmArr.length; i++) { // calculating the farm statistics
			if (min > farmArr[i].getWeight()) {
				min = farmArr[i].getWeight();
			}
			if (max < farmArr[i].getWeight()) {
				max = farmArr[i].getWeight();
			}
			total += farmArr[i].getWeight();
			farmdata.add(
					new Farm(i + 1, farmArr[i].getWeight(), farmArr[i].getPercent()));
		}
		avg = total / 12;
		avg = Math.round(avg * 10.0) ;

		Label lblFarmID = new Label("FarmID: " + farmID);
		Label lblYear = new Label("Year: " + year);

		table = new TableView<Farm>(); // formatting the table
		table.setEditable(true);
		TableColumn<Farm, Integer> monthCol = new TableColumn<Farm, Integer>(
				"Month");
		TableColumn<Farm, Integer> weightCol = new TableColumn<Farm, Integer>(
				"Weight of milk");
		TableColumn<Farm, String> percentCol = new TableColumn<Farm, String>(
				"% of milk");

		monthCol.setCellValueFactory(
				new PropertyValueFactory<Farm, Integer>("month"));
		weightCol.setCellValueFactory(
				new PropertyValueFactory<Farm, Integer>("weight"));
		percentCol.setCellValueFactory(
				new PropertyValueFactory<Farm, String>("percent"));

		table.getColumns().setAll(monthCol, weightCol, percentCol);
		table.setItems(farmdata);
		monthCol.setSortType(TableColumn.SortType.ASCENDING);

		exitButton = new Button();
		exitButton.setText("Exit");
		exitButton.setOnAction(e -> {
			Platform.exit();
			System.exit(0);
		});

		backButton = new Button();
		backButton.setText("Menu");
		backButton.setOnAction(e -> MenuScreen.displayMenu());

		calcButton = new Button();
		calcButton.setText("Stats");
		calcButton.setOnAction(e -> displayStats(farmID, year, min, max, avg));

		BorderPane root = new BorderPane();
		root.setStyle(" -fx-background-color:#5F9EA0;");

		VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 23, 10, 23));
		vbox.getChildren().addAll(lblFarmID, lblYear, table);
		root.setCenter(vbox);

		HBox hBox = new HBox();
		hBox.getChildren().addAll(backButton, calcButton, exitButton);
		hBox.setSpacing(113);
		// Insets(double top, double right, double bottom, double left)
		hBox.setPadding(new Insets(0, 0, 0, 0));
		root.setBottom(hBox);
		BorderPane.setAlignment(hBox, Pos.BOTTOM_RIGHT);

		Stage window = new Stage();
		Scene mainScene = new Scene(root, 350, 200);
		window.setTitle("Farm Report");
		window.setScene(mainScene);
		window.showAndWait();
	}

	/**
	 * This method displays the farm statistics window to display the minimum,
	 * maximum and average milk for the year.
	 * 
	 * @param farmID
	 * @param year
	 * @param min
	 * @param max
	 * @param avg
	 */
	public static void displayStats(String farmID, String year, double min,
			double max, double avg) {
		Stage window = new Stage();
		BorderPane root = new BorderPane();
		root.setStyle(" -fx-background-color:#5F9EA0;");

		Label statsLbl = new Label("Farm ID: " + farmID + "\nYear: " + year);

		root.setTop(statsLbl);
		BorderPane.setAlignment(statsLbl, Pos.TOP_CENTER);

		Label minLbl = new Label("Minimum milk weight : " + min);
		Label maxLbl = new Label("Maximum milk weight : " + max);
		Label avgLbl = new Label("Average milk weight: " + avg);

		Button closeButton = new Button();
		closeButton.setText("Close");
		closeButton.setOnAction(e -> window.close());
		root.setBottom(closeButton);
		BorderPane.setAlignment(closeButton, Pos.BOTTOM_RIGHT);

		VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 10, 10, 10));
		vbox.getChildren().addAll(avgLbl, minLbl, maxLbl);
		root.setCenter(vbox);

		Scene alertScene = new Scene(root, 250, 150);
		window.setScene(alertScene);
		window.setTitle("Statistics");
		window.showAndWait();
	}

}
