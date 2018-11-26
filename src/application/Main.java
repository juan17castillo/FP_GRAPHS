package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	Stage principal;

	@Override
	public void start(Stage primaryStage) {

		principal = primaryStage;
		try {
			Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			principal.setScene(scene);
			principal.initStyle(StageStyle.UNDECORATED);
			principal.show();
		} catch(Exception e) {
			e.printStackTrace();
		
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
