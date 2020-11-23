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
  private Label acctCreatedLBL;

  @FXML
  void backHyperlink(ActionEvent event) throws IOException {
    AnchorPane pane = FXMLLoader.load(getClass().getResource("sample2.fxml"));
    rootPane.getChildren().setAll(pane);
  }

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
  }

  @FXML
  void disableErrorMsgForPassword(KeyEvent event) {
    lblCreatePassword.setText("");
  }

  @FXML
  void createAcctButton(ActionEvent event) throws IOException {
      registerUser();
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

    String username = userNameTxt.getText();
    String password = passwordTxt.getText();


    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("sample.fxml"));
    Parent tableviewParent = loader.load();

    ActController controller = loader.getController();

    boolean existingAcct = controller.loadEmployee(username, password);

      if (existingAcct){

        System.out.println("True");
        System.out.println("You've been successfully logged in");

        try {
          TabPane pane = FXMLLoader.load(getClass().getResource("sample.fxml"));
          rootPane.getChildren().setAll(pane);

        } catch (Exception e) {
          e.printStackTrace();
        }

      }
      else{
        errorMessage.setText("Incorrect username or password");
        System.out.println("false");
      }

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
    }*/


  }

  private void registerUser( ) throws IOException {

    boolean validation = true;
    Parent tableviewParent;

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

    validation = true;
    String name = createName.getText();
    String password = createPassword.getText();
    Employee emp  = new Employee(name, password);

    while(validation){
      if (emp.name.toString().matches("^[a-zA-Z]{3,}[ ][a-zA-Z]+$") &&
          emp.password.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[#?!@$%^&*-]).{3,}$")) {
          System.out.println("Your account has been successfully created");

          validation = false;

      }else if(!emp.name.toString().matches("^[a-zA-Z]{3,}.[ ][a-zA-Z]+$")){
        lblCreateName.setText("Enter your full name [e.g. Pedro Martinez]");

        return;
      }
      else if(!emp.name.toString().matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[#?!@$%^&*-]).{3,}$")){
        lblCreatePassword.setText("Password must contain an uppercase\nand a lowercase letter, and "
            + "a special\ncharacter [e.g. Oop$]");
        return;
     }

    }//end whileLoop

    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("sample.fxml"));
    tableviewParent = loader.load();

    ActController controller = loader.getController();
    controller.addEmployee(emp);

    acctCreatedLBL.setText(
        "Your account has been successfully created\n"
        + "Username: " + emp.userName + "\n"
            + "To login, go back to the main page");

    lblCreatePassword.setText("");

    }//end registerUser method

  }//end Controller class


