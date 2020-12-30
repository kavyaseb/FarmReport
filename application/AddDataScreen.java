package application;

import java.io.File;
import java.io.IOException;

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
import javafx.stage.Stage;

/**
 * This class is used if the user wants to add data to the project using a file
 * path.
 * 
 * @author kavya
 *
 */
public class AddDataScreen {
	private static Button backButton;
	private static Button exitButton;
	private static Button submitButton;

	public static void displayAddData() {
		Stage window = new Stage();
		Label lbl = new Label("Enter file path:");
		BorderPane root = new BorderPane();
		root.setStyle(" -fx-background-color:#5F9EA0;");

		TextField textFilePath = new TextField();
		textFilePath.setPromptText("C:\\FinalProject\\csv\\small");

		exitButton = new Button();
		exitButton.setText("Exit");
		exitButton.setOnAction(e -> {
			Platform.exit();
			System.exit(0);
		});

		backButton = new Button();
		backButton.setText("Back");
		backButton.setOnAction(e -> window.close());

		submitButton = new Button();
		submitButton.setText("Submit");
		submitButton.setOnAction(e -> {
			int flag=0;
			String filePath = textFilePath.getText();
			File tmpDir = new File(filePath);
			if (tmpDir.exists()) { // check if directory exists
				FarmADT	f=FarmADT.getInstance();
				try {
				f.readFile(filePath);
				MenuScreen.displayMenu(); //go to menu screen
				}catch(MyException m) {
					flag=1;
					alert(flag,m.getMessage());
				} catch (IOException e1) {
					flag=2;
					alert(flag,e1.getMessage());
				}
			} 
			else {
				alert(flag,"");
			}
		});

		HBox hBox = new HBox();
		hBox.getChildren().addAll(backButton, exitButton);
		hBox.setSpacing(275);
		// Insets(double top, double right, double bottom, double left)
		hBox.setPadding(new Insets(0, 0, 0, 0));
		root.setBottom(hBox);
		BorderPane.setAlignment(hBox, Pos.BOTTOM_RIGHT);

		VBox vBox = new VBox();
		vBox.getChildren().addAll(lbl, textFilePath, submitButton);
		vBox.setPadding(new Insets(50, 100, 10, 100));
		vBox.setSpacing(10);
		root.setCenter(vBox);

		Scene mainScene = new Scene(root, 350, 200);

		window.setScene(mainScene);
		window.show();

	}

	/**
	 * This method creates a pop up window to alert when the file path is incorrect
	 */
	private static void alert(int flag,String message) {
		Label alertLbl = null;
		Stage window = new Stage();
		BorderPane root = new BorderPane();
		root.setStyle(" -fx-background-color:#5F9EA0;");

		if (flag==0) {
		 alertLbl = new Label("Incorrect file path!");
		}
		else if(flag==1){
		 alertLbl = new Label("Data cannot be processed.\n"
				+ message);
		}else {
			alertLbl = new Label("Error:\n"+message);
		}
		
		root.setCenter(alertLbl);
		Scene alertScene = new Scene(root, 250, 100);
		window.setScene(alertScene);
		window.setTitle("Error");

		window.showAndWait();
	}
}
