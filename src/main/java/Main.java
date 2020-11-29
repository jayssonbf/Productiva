/*-------------------------------------------------------------------------------------
File name: Main.java
By: Jaysson Balbuena
Organization: COP 3003, fall 2020
Instructor: Scott Vanselow
Date:  12/02/2020
Description: Productiva, a production management system, that allows users to control
             all aspects of production processing.
---------------------------------------------------------------------------------------*/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is the main method which makes use of start method.
 * @author Jaysson Balbuena
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
   * This abstract method provides the application's entry point.
   * @param primaryStage consists of visual elements. It specifies a container for the user
   *                     interface.
   * @throws Exception on input error.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("sample2.fxml"));
    Scene scene = new Scene(root, 500, 650);
    scene.getStylesheets().add("stylish.css");

    primaryStage.setTitle("Product Management System");
    primaryStage.setScene(scene);
    primaryStage.show();

  }
}
