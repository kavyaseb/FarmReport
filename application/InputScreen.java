package application;

import java.text.SimpleDateFormat;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class allows the user to input the specific data based on the report the
 * user chose.
 * 
 * @author kavya
 *
 */
public class InputScreen {
	private static Button submitButton;
	private static String farmID, year, month, startDate, endDate;
	private static Button backButton;
	private static Button exitButton;
	private static int flag;

	@SuppressWarnings("unused")
	public static void displayInput(String report) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);

		Label lblFarmID = new Label("Enter farm ID:");
		TextField textFarmID = new TextField();
		textFarmID.setPromptText("Farm 0");

		Label lblYear = new Label("Enter year:");
		TextField textYear = new TextField();
		textYear.setPromptText("2019");
		textYear.setPrefColumnCount(10);

		Label lblMonth = new Label("Enter month:");
		TextField textMonth = new TextField();
		textMonth.setPromptText("01");
		textMonth.setPrefColumnCount(10);
		textMonth.getText();

		Label lblStartDate = new Label("Enter start date:");
		TextField textStartDate = new TextField();
		textStartDate.setPromptText("yyyy-mm-dd");
		textStartDate.setPrefColumnCount(10);
		textStartDate.getText();

		Label lblEndDate = new Label("Enter end date:");
		TextField textEndDate = new TextField();
		textEndDate.setPromptText("yyyy-mm-dd");
		textEndDate.setPrefColumnCount(10);
		textEndDate.getText();

		backButton = new Button();
		backButton.setText("Menu");
		backButton.setOnAction(e -> MenuScreen.displayMenu());

		exitButton = new Button();
		exitButton.setText("Exit");
		exitButton.setOnAction(e -> {
			Platform.exit();
			System.exit(0);
		});

		submitButton = new Button();
		submitButton.setText("Submit");
		submitButton.setOnAction(e -> {
			if (report.equals("Farm Report")) {
				farmID = textFarmID.getText();
				year = textYear.getText();
				try {
					//check if user input starts with Farm 
					boolean b = farmID.startsWith("Farm ");
					Double num = Double.parseDouble(year);
					if (b == false) {
						throw new Exception();
					}
					InputParams ip = new InputParams();
					ip.setFarmID(farmID);
					ip.setYear(year);
					ReportInterface ri = new FarmReportScreen();
					ri.displayReport(ip);
				} catch (NumberFormatException e1) {
					flag = 2;
					alert(flag);
				} catch (Exception e1) {
					flag = 1;
					alert(flag);
				}

			}
			if (report.equals("Annual Report")) {
				year = textYear.getText();
				try {
					Double num = Double.parseDouble(year);
					InputParams ip = new InputParams();
					ip.setYear(year);
					ReportInterface ri = new AnnualReportScreen();
					ri.displayReport(ip);
				} catch (NumberFormatException e1) {
					flag = 2;
					alert(flag);
				}
			}
			if (report.equals("Monthly Report")) {
				year = textYear.getText();
				month = textMonth.getText();
				try {
					Double num = Double.parseDouble(month);
					Double num1 = Double.parseDouble(year);
					InputParams ip = new InputParams();
					ip.setMonth(month);
					ip.setYear(year);
					ReportInterface ri = new MonthlyReportScreen();
					ri.displayReport(ip);
				} catch (NumberFormatException e1) {
					flag = 3;
					alert(flag);
				}
			}

			if (report.equals("Date Range Report")) {
				startDate = textStartDate.getText();
				endDate = textEndDate.getText();
				SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-mm-dd");
				sdfrmt.setLenient(false);

				try {
					java.util.Date javaStartDate = sdfrmt.parse(startDate);
					java.util.Date javaEndDate = sdfrmt.parse(endDate);
					//check if range is correct
					if(javaEndDate.before(javaStartDate)) {
						throw new Exception();
					}
					InputParams ip = new InputParams();
					ip.setStartDate(startDate);
					ip.setEndDate(endDate);
					ReportInterface ri = new DateRangeReportScreen();
					ri.displayReport(ip);
				} catch (Exception p) {
					flag = 4;
					alert(flag);
				}

			}
		});

		if (report.equals("Farm Report")) {
			textMonth.setDisable(true);
			textStartDate.setDisable(true);
			textEndDate.setDisable(true);

		}
		if (report.equals("Annual Report")) {
			textFarmID.setDisable(true);
			textMonth.setDisable(true);
			textStartDate.setDisable(true);
			textEndDate.setDisable(true);
		}
		if (report.equals("Monthly Report")) {
			textFarmID.setDisable(true);
			textStartDate.setDisable(true);
			textEndDate.setDisable(true);
		}
		if (report.equals("Date Range Report")) {
			textYear.setDisable(true);
			textFarmID.setDisable(true);
			textMonth.setDisable(true);

		}

		BorderPane root = new BorderPane();
		root.setStyle(" -fx-background-color:#5F9EA0;");

		VBox vbox = new VBox();
		vbox.setSpacing(18);
		vbox.setPadding(new Insets(5, 5, 5, 5));
		vbox.getChildren().addAll(lblFarmID, lblYear, lblMonth, lblStartDate,
				lblEndDate);
		root.setCenter(vbox);

		VBox vbox1 = new VBox();
		vbox1.setSpacing(8);
		vbox1.setPadding(new Insets(5, 5, 5, 5));
		vbox1.getChildren().addAll(textFarmID, textYear, textMonth, textStartDate,
				textEndDate);
		root.setCenter(vbox1);

		HBox hBox = new HBox();
		hBox.getChildren().addAll(vbox, vbox1);
		// Insets(double top, double right, double bottom, double left)
		hBox.setPadding(new Insets(5, 5, 5, 5));
		root.setCenter(hBox);
		BorderPane.setAlignment(hBox, Pos.TOP_CENTER);

		HBox hBox1 = new HBox();
		hBox1.getChildren().addAll(backButton, submitButton, exitButton);
		hBox1.setSpacing(108);
		// Insets(double top, double right, double bottom, double left)
		hBox1.setPadding(new Insets(0, 0, 0, 0));
		root.setBottom(hBox1);
		BorderPane.setAlignment(hBox1, Pos.BOTTOM_CENTER);

		Scene mainScene = new Scene(root, 350, 200);

		window.setScene(mainScene);
		window.showAndWait();
	}

	/**
	 * This method creates a pop up alert if the user inputs incorrect data
	 * 
	 * @param flag
	 */
	public static void alert(int flag) {
		Stage window = new Stage();
		BorderPane root = new BorderPane();
		root.setStyle(" -fx-background-color:#5F9EA0;");

		Label alertLbl = null;
		if (flag == 1) {
			alertLbl = new Label("Improper format!\nPlease follow format: Farm X");
		}
		if (flag == 2) {
			alertLbl = new Label("Improper format!\nPlease follow format: yyyy");
		}
		if (flag == 3) {
			alertLbl = new Label(
					"Improper format!\nPlease follow format for year: yyyy"
							+ "\nPlease follow format for month: mm");
		}
		if (flag == 4) {
			alertLbl = new Label(
					"Improper format or incorrect range!\nPlease follow format: yyyy-mm-dd");
		}
		
		root.setCenter(alertLbl);
		Scene alertScene = new Scene(root, 200, 100);
		window.setTitle("Error");
		window.setScene(alertScene);
		window.showAndWait();
	}
}
