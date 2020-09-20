/*-------------------------------------------------------------------------
file name: Main.java
by: Jaysson Balbuena
organization: COP 3003, fall 2020
Instructor: Scott Vanselow
Date:  09/19/2020
for: A production management system that adds new records to a database
---------------------------------------------------------------------------*/
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *This is the main method which makes use of start method.
 * @author Jaysson Balbuena
 * @version
 */
public class Main extends Application {

  /**
     * it calls launch method and passees args as a parameter.
     * @param args command-line arguments.
     */
  public static void main(String[] args) {
    launch(args);
  }

  /**
     *This abstract method provides the application's entry point.
     * @param primaryStage consists of visual elements. It specifies a container
     *     for the user interface.
     * @throws Exception on input error.
     */
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
