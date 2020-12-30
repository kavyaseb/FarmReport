
package application;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
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
 * This class displays the monthly report data in a table with the user input
 * month and year.
 *
 * @author kavya
 *
 */
public class MonthlyReportScreen implements ReportInterface {

	private static TableView<Farm> table;
	private static Button exitButton;
	private static Button backButton;
	private static ObservableList<Farm> farmdata;

	public static class Farm {
		private SimpleStringProperty farmID;
		private double weight;
		private double percent;

		public Farm(String farmID, double weight, double percent) {
			this.farmID = new SimpleStringProperty(farmID);

			this.weight = weight;
			this.percent = percent;

		}

		public String getFarmID() {
			return farmID.get();
		}

		public void setPercent(String tmpfarmID) {
			farmID.set(tmpfarmID);
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
		String month = inputParams.getMonth();
		String year = inputParams.getYear();
		FarmADT monthreport = FarmADT.getInstance();
		ArrayList<Data> montharr = monthreport.getDataForMonthlyReport(month,
				year);
		farmdata = FXCollections.observableArrayList();
		for (int i = 0; i < montharr.size(); i++) { // adding data to the table
			farmdata.add(new Farm(montharr.get(i).getFarmID(),
					montharr.get(i).getWeight(), montharr.get(i).getPercent()));
		}

		Label lblMonth = new Label("Month: " + month);
		Label lblYear = new Label("Year: " + year);

		table = new TableView<Farm>();
		table.setEditable(true);
		TableColumn<Farm, Integer> farmIDCol = new TableColumn<Farm, Integer>(
				"Farm ID");
		TableColumn<Farm, Integer> weightCol = new TableColumn<Farm, Integer>(
				"Weight of milk");
		TableColumn<Farm, String> percentCol = new TableColumn<Farm, String>(
				"% of milk");

		farmIDCol.setCellValueFactory(
				new PropertyValueFactory<Farm, Integer>("farmID"));

		weightCol.setCellValueFactory(
				new PropertyValueFactory<Farm, Integer>("weight"));
		percentCol.setCellValueFactory(
				new PropertyValueFactory<Farm, String>("percent"));

		table.getColumns().setAll(farmIDCol, weightCol, percentCol);
		table.setItems(farmdata);
		farmIDCol.setSortType(TableColumn.SortType.ASCENDING);
		weightCol.setSortable(true);

		exitButton = new Button();
		exitButton.setText("Exit");
		exitButton.setOnAction(e -> {
			Platform.exit();
			System.exit(0);
		});
		backButton = new Button();
		backButton.setText("Menu");
		backButton.setOnAction(e -> MenuScreen.displayMenu());

		BorderPane root = new BorderPane();
		root.setStyle(" -fx-background-color:#5F9EA0;");

		VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 23, 10, 23));
		vbox.getChildren().addAll(lblMonth, lblYear, table);
		root.setCenter(vbox);

		HBox hBox = new HBox();
		hBox.getChildren().addAll(backButton, exitButton);
		hBox.setSpacing(268);
		// Insets(double top, double right, double bottom, double left)
		hBox.setPadding(new Insets(0, 0, 0, 0));
		root.setBottom(hBox);
		BorderPane.setAlignment(hBox, Pos.BOTTOM_RIGHT);

		Stage window = new Stage();
		Scene mainScene = new Scene(root, 350, 200);

		window.setTitle("Monthly Report");
		window.setScene(mainScene);
		window.showAndWait();
	}
}
