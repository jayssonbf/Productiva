import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class Controller {


  @FXML
  AnchorPane rootPane;

  @FXML
  private TextField userNameTxt;

  @FXML
  private TextField passwordTxt;

  @FXML
  private Label lblUsername;

  @FXML
  private Label lblPassword;

  @FXML
  private Label msgCreatePassword;

  @FXML
  private TextField createName;

  @FXML
  private TextField createPassword;

  @FXML
  private Label lblCreateName;

  @FXML
  private Label lblCreatePassword;

  @FXML
  private Label errorMessage;



  @FXML
  void loginBtn( ActionEvent event ) throws IOException {
    loginScreen();
  }
  @FXML
  void usernameKeyReleased( KeyEvent event) {
      lblUsername.setText("");
  }
  @FXML
  void passwordKeyReleased(KeyEvent event) {
    lblPassword.setText("");

  }
  @FXML
  void disableErrorMsgForName(KeyEvent event) {
    lblCreateName.setText("");
    msgCreatePassword.setText("Please enter your full\nname and create a\npassword that contains\n"
        + "at least a lowercase,\nuppercase letter,\nand a special\n character e.g.(!@#$%^&*)");
  }

  @FXML
  void disableErrorMsgForPassword(KeyEvent event) {
    lblCreatePassword.setText("");
  }

  @FXML
  void createAcctButton(ActionEvent event) {
      registerUser();
  }

  private void registerUser( ) {
    boolean validation = true;

    while (validation) {
      if (createName.getText().isEmpty() && createPassword.getText().isEmpty()) {
        lblCreateName.setText("Name is empty");
        lblCreatePassword.setText("Password is empty");
        return;
      } else if (createName.getText().isEmpty()) {
        lblCreateName.setText("username must be filled out");
        return;
      } else if (createPassword.getText().isEmpty()) {
        lblCreatePassword.setText("Password must be filled out");
        return;
      }
      validation = false;
    }
  }

  @FXML
  void createAccount(ActionEvent event) {
    loadCreateAcctScreen();
  }

  /**
   * When this method is called, it checks for valid input
   * @throws IOException
   */
  public void loginScreen( ) throws IOException {

    boolean validation = true;

    while (validation) {
      if (userNameTxt.getText().isEmpty() && passwordTxt.getText().isEmpty()) {
        lblUsername.setText("Username must be filled out");
        lblPassword.setText("Password must be filled out");
        return;
      } else if (userNameTxt.getText().isEmpty()) {
        lblUsername.setText("username must be filled out");
        return;
      } else if (passwordTxt.getText().isEmpty()) {
        lblPassword.setText("Password must be filled out");
        return;
      }
      validation = false;
    }

    System.out.println("You've been successfully logged in");

    String username = userNameTxt.getText();
    String password = passwordTxt.getText();

    Employee employee = new Employee(username, password);

    //System.out.println(employee);

    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("sample.fxml"));
    Parent tableviewParent = loader.load();



    ActController controller = loader.getController();

    boolean existingAcct = controller.loadEmployee(username, password);

    if (existingAcct){

      System.out.println("True");

      try {
        TabPane pane = FXMLLoader.load(getClass().getResource("sample.fxml"));
        rootPane.getChildren().setAll(pane);

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    else{
      errorMessage.setStyle("-fx-text-fill: red");
      errorMessage.setText("Incorrect username or password");
      System.out.println("false");
    }

    //controller.addEmployee(employee);




  }



  private void loadCreateAcctScreen( ) {
    try {
      AnchorPane pane = FXMLLoader.load(getClass().getResource("sample3.fxml"));
      rootPane.getChildren().setAll(pane);

      System.out.println("Creating account...");
    } catch (Exception e) {
      e.printStackTrace();
    }

    /*boolean validation = true;

    while (validation) {
      if (createName.getText().isEmpty() && createPassword.getText().isEmpty()) {
        lblCreateName.setText("Name is empty");
        lblCreatePassword.setText("Password is empty");
        return;
      } else if (createName.getText().isEmpty()) {
        lblCreateName.setText("username must be filled out");
        return;
      } else if (createPassword.getText().isEmpty()) {
        lblCreatePassword.setText("Password must be filled out");
        return;
      }
      validation = false;
    }
    */

  }

}
