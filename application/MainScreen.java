package application;

import javafx.application.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.*;

/**
 * This class displays the main screen that allows the user to read from a
 * specific file or add any file for the reports.
 * 
 * @author kavya
 *
 */
public class MainScreen extends Application {

	private static final int WINDOW_WIDTH = 350;
	private static final int WINDOW_HEIGHT = 200;
	private static final String APP_TITLE = "Final Project";
	private static final String APP_LABEL = "Welcome to Chalet Cheese Factory!";

	private Button addDataButton;
	private Button exitButton;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage pStage) throws Exception {

		Label label = new Label(APP_LABEL);

		// button that adds data from user
		addDataButton = new Button();
		addDataButton.setText("Add Data");
		addDataButton.setOnAction(e -> AddDataScreen.displayAddData());

		exitButton = new Button();
		exitButton.setText("Exit");
		exitButton.setOnAction(e -> {
			Platform.exit();
			System.exit(0);
		});

		BorderPane root = new BorderPane();
		root.setStyle(" -fx-background-color:#5F9EA0;");
		root.setTop(label);
		BorderPane.setAlignment(label, Pos.TOP_CENTER);

		HBox hBox = new HBox();
		hBox.getChildren().addAll(addDataButton);
		hBox.setPadding(new Insets(75, 100, 10, 135));
		hBox.setSpacing(10);
		root.setCenter(hBox);
		BorderPane.setAlignment(hBox, Pos.CENTER);

		HBox hBox2 = new HBox();
		hBox2.getChildren().addAll(exitButton);
		hBox2.setPadding(new Insets(0, 0, 0, 315));
		root.setBottom(hBox2);
		BorderPane.setAlignment(hBox2, Pos.BOTTOM_RIGHT);

		Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

		pStage.setTitle(APP_TITLE);
		pStage.setScene(mainScene);
		pStage.show();

	}

}
