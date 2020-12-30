package application;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.*;

/**
 * This class allows the user to choose the specific report for the data.
 * 
 * @author kavya
 *
 */
public class MenuScreen {

	private static final int WINDOW_WIDTH = 350;
	private static final int WINDOW_HEIGHT = 200;
	private static final String APP_LABEL = "Which report would you like?";
	private static ToggleGroup reportGroup;
	private static Button submitButton;
	private static String report = null;
	private static RadioButton farmReportRB, annualReportRB, monthlyReportRB,
			dateRangeReportRB;
	private static VBox vBox;
	private static Button exitButton;
	private static Button backButton;

	public static void displayMenu() {

		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);

		Label label = new Label(APP_LABEL);

		reportGroup = new ToggleGroup();

		farmReportRB = new RadioButton("Farm Report");
		farmReportRB.setToggleGroup(reportGroup);

		annualReportRB = new RadioButton("Annual Report");
		annualReportRB.setToggleGroup(reportGroup);

		monthlyReportRB = new RadioButton("Monthly Report");
		monthlyReportRB.setToggleGroup(reportGroup);

		dateRangeReportRB = new RadioButton("Date Range Report");
		dateRangeReportRB.setToggleGroup(reportGroup);

		backButton = new Button();
		backButton.setText("Back");
		backButton.setOnAction(e -> AddDataScreen.displayAddData());

		exitButton = new Button();
		exitButton.setText("Exit");
		exitButton.setOnAction(e -> {
			Platform.exit();
			System.exit(0);
		});
		// button that closes window when clicked
		submitButton = new Button();
		submitButton.setText("Submit");

		// disables the submit button until a radio button is chosen
		if (reportGroup.getSelectedToggle() == null) {
			submitButton.setDisable(true);
		}

		farmReportRB.setOnAction(e -> {
			submitButton.setDisable(false);
		});
		annualReportRB.setOnAction(e -> {
			submitButton.setDisable(false);
		});
		monthlyReportRB.setOnAction(e -> {
			submitButton.setDisable(false);
		});
		dateRangeReportRB.setOnAction(e -> {
			submitButton.setDisable(false);
		});

		submitButton.setOnAction(e -> {

			if (farmReportRB.isSelected()) {
				report = farmReportRB.getText();
			}
			if (annualReportRB.isSelected()) {
				report = annualReportRB.getText();
			}
			if (monthlyReportRB.isSelected()) {
				report = monthlyReportRB.getText();
			}
			if (dateRangeReportRB.isSelected()) {
				report = dateRangeReportRB.getText();
			}
			InputScreen.displayInput(report);
		});

		BorderPane root = new BorderPane();
		root.setStyle(" -fx-background-color:#5F9EA0;");

		// aligning the label in the top pane
		root.setTop(label);
		BorderPane.setAlignment(label, Pos.TOP_CENTER);

		vBox = new VBox();
		vBox.setPadding(new Insets(10, 0, 0, 100));
		vBox.getChildren().addAll(farmReportRB, annualReportRB, monthlyReportRB,
				dateRangeReportRB);
		vBox.setSpacing(10);
		vBox.getChildren().addAll(submitButton);

		root.setCenter(vBox);
		BorderPane.setAlignment(vBox, Pos.CENTER);

		HBox hBox = new HBox();
		hBox.getChildren().addAll(backButton, exitButton);
		hBox.setSpacing(275);
		// Insets(double top, double right, double bottom, double left)
		hBox.setPadding(new Insets(0, 0, 0, 0));
		root.setBottom(hBox);
		BorderPane.setAlignment(hBox, Pos.BOTTOM_RIGHT);

		Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

		window.setTitle("Menu");
		window.setScene(mainScene);
		window.showAndWait();

	}

}