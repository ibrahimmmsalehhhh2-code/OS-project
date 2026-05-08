
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/input.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("CPU Scheduling Simulator - SRTF vs Priority");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: Could not load input.fxml. Check the file path.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}