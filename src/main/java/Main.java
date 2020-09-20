/*-------------------------------------------------------------------------
file name: Main.java
by: Jaysson Balbuena
organization: COP 3003, fall 2020
for: A production management system that adds new records to a database
---------------------------------------------------------------------------*/
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        Scene scene = new Scene(root, 400, 300);
        scene.getStylesheets().add("stylish.css");

        primaryStage.setTitle("Product Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
