import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Manipulates and controls the scene builder containers through individual fx:id's and methods.
 * @author Jaysson Balbuena
 */
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
  private Label acctCreatedLbl;


  /**
   *  allows user to return to the login page.
   * @param event offers a series of ActionEvents such as "On mouse clicked" etc.
   * @throws IOException is thrown if something goes wrong when reading the fxml file.
   */
  @FXML
  void backHyperlink(ActionEvent event) throws IOException {
    AnchorPane pane = FXMLLoader.load(getClass().getResource("sample2.fxml"));
    rootPane.getChildren().setAll(pane);
  }

  /**
   * Once loginBtn is clicked, loginScreen method verifies user input and logs in if valid.
   * @param event event offers a series of ActionEvents such as "On mouse clicked" etc.
   * @throws IOException is thrown if something goes wrong when reading the fxml file.
   */
  @FXML
  void loginBtn(ActionEvent event) throws IOException {
    loginScreen(event);
  }

  /**
   * erases the username error message shown when incorrect username is entered.
   * @param event executes an action when the user type something into the TextField.
   */
  @FXML
  void usernameKeyReleased(KeyEvent event) {
    lblUsername.setText("");
    errorMessage.setText("");
  }

  /**
   * erases the password error message shown when incorrect password is entered.
   * @param event executes an action when the user type something into the TextField.
   */
  @FXML
  void passwordKeyReleased(KeyEvent event) {
    lblPassword.setText("");
    errorMessage.setText("");

  }

  /**
   * disables error message shown when user tries to create an account with an improper name.
   * @param event executes an action when the user type something into the TextField.
   */
  @FXML
  void disableErrorMsgForName(KeyEvent event) {
    lblCreateName.setText("");
  }

  /**
   * disables name error message shown when user tries to create an account with an improper name.
   * @param event executes an action when the user type something into the TextField.
   */
  @FXML
  void disableErrorMsgForPassword(KeyEvent event) {
    lblCreatePassword.setText("");
  }

  /**
   * call registerUser() method where allows a user to register into the system.
   * @param event executes an action when the user type something into the TextField.
   * @throws IOException is thrown if something goes wrong when reading the fxml file.
   */
  @FXML
  void createAcctButton(ActionEvent event) throws IOException {

    registerUser();
  }

  /**
   * calls loadCreateAcctScreen() method which loads the create account screen.
   * @param event executes an action when the user type something into the TextField.
   */
  @FXML
  void createAccount(ActionEvent event) {
    loadCreateAcctScreen();

  }

  /**
   * When this method is called, it checks for valid input and it logs in if input is corrected.
   * @param event contains an action that is executed when
   * @throws IOException is thrown if something goes wrong when reading the fxml file.
   */
  public void loginScreen(ActionEvent event) throws IOException {
    userNameTxt.requestFocus();
    boolean validation = true;

    while (validation) {
      if (userNameTxt.getText().isEmpty() && passwordTxt.getText().isEmpty()) {
        lblUsername.setText("Username must be filled out");
        lblPassword.setText("Password must be filled out");

        return;
      } else if (userNameTxt.getText().isEmpty()) {
        lblUsername.setText("username must be filled out");
        userNameTxt.requestFocus();
        return;
      } else if (passwordTxt.getText().isEmpty()) {
        lblPassword.setText("Password must be filled out");
        passwordTxt.requestFocus();
        return;
      }
      validation = false;
    }

    String username = userNameTxt.getText().toLowerCase();
    String password = passwordTxt.getText();

    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("sample.fxml"));
    Parent tableviewParent = loader.load();

    Scene scene = new Scene(tableviewParent, 500, 650);
    scene.getStylesheets().add("stylish.css");

    ActController controller = loader.getController();
    boolean existingAcct = controller.loadEmployee(username, password);

    if (existingAcct) {

      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(scene);
      window.show();

    } else {
      errorMessage.setText("Incorrect username or password");
    }

  } //ends loginScreen() method

  /**
   * Once the user click on create account, the create account window pops up.
   */
  private void loadCreateAcctScreen() {
    try {
      AnchorPane pane = FXMLLoader.load(getClass().getResource("sample3.fxml"));
      rootPane.getChildren().setAll(pane);

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * prompts user to enter valid input to create a new account.
   *
   * @throws IOException is thrown if something goes wrong when reading the fxml file.
   */
  private void registerUser() throws IOException {

    boolean validation = true;
    //Parent tableviewParent;

    while (validation) {
      if (createName.getText().isEmpty() && createPassword.getText().isEmpty()) {
        lblCreateName.setText("Name is empty");
        lblCreatePassword.setText("Password is empty");
        createName.requestFocus();
        return;
      } else if (createName.getText().isEmpty()) {
        lblCreateName.setText("name must be filled out");
        createName.requestFocus();
        return;
      } else if (createPassword.getText().isEmpty()) {
        lblCreatePassword.setText("Password must be filled out");
        createPassword.requestFocus();
        return;
      }
      validation = false;
    }

    validation = true;
    String name = createName.getText();
    String password = createPassword.getText();
    Employee emp = new Employee(name, password);

    while (validation) {
      if (emp.name.toString().matches("^[a-zA-Z]{3,}[ ][a-zA-Z]{3,}$")
          && emp.password.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[#?!@$%^&*-]).{3,}$")) {

        validation = false;

      } else if (!emp.name.toString().matches("^[a-zA-Z]{3,}[ ][a-zA-Z]{3,}$")) {
        lblCreateName.setText("Enter your full name without digits [e.g. Pedro Martinez]");
        createName.requestFocus();
        return;
      } else if (!emp.name.toString()
          .matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[#?!@$%^&*-]).{3,}$")) {
        lblCreatePassword
            .setText("Password must contain an uppercase,\nand a lowercase letter, and "
                + "a special\ncharacter [e.g. Oop$]");
        return;
      }

    } //end whileLoop

    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("sample.fxml"));
    loader.load();

    ActController controller = loader.getController();
    controller.addEmployee(emp);

    acctCreatedLbl.setText("Your account has been successfully created!\n"
        + "Username: " + emp.userName);


    lblCreatePassword.setText("");

  } //end registerUser method


} //end Controller class


