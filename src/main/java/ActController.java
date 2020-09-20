/*-------------------------------------------------------------------------
file name: ActController.java
by: Jaysson Balbuena
organization: COP 3003, fall 2020
for: A production management system that adds new records to a database
---------------------------------------------------------------------------*/

//import required packages
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;

/**
 * Represents the creation of a database.
 * Many products can be inserted into the database
 * @author Jaysson Balbuena
 */
public class ActController {
  //JDBC driver name and database URL
  static final String JDBC_DRIVER = "org.h2.Driver";
  static final String DB_URL = "jdbc:h2:./res/prodMgt";

  // Database credentials
  static final String USER = "";
  static final String PASS = "";

  @FXML
  private Tab prodLineTab;

  @FXML
  private TextField txtFieldName;

  @FXML
  private TextField txtFieldManufacturer;

  @FXML
  private ChoiceBox<String> cbItems;

  @FXML
  private Tab produceTab;

  @FXML
  private ComboBox<String> cmbQuantity;

  @FXML
  private Tab prodLogTab;

  @FXML
  void buttonAction(ActionEvent event) {
    connectToDataBase();
  }

  @FXML
  void recProd(ActionEvent event) {
  }

  /**
   *initialize is called automatically when the controller loads.
   * and set a default value for the combobox and choicebox
   */
  public void initialize() {

    String[] prodTypes = {"Audio", "Visual", "Memory", "Software", "Video games"};
    for (int i = 0; i < prodTypes.length; i++) {
      cbItems.getItems().add(i, prodTypes[i]);
    }
    cbItems.getSelectionModel().selectFirst();

    for (int i = 1; i <= 10; i++) {
      cmbQuantity.getItems().add(Integer.toString(i));
    }
    cmbQuantity.getSelectionModel().selectFirst();
  }

  /**
   * It creates a record and insert it into a database.
   * */
  public void connectToDataBase() {

    Connection conn = null;
    PreparedStatement pStmt = null;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //SQL insert statement
      String insertSql = " INSERT INTO PRODUCT(NAME, TYPE, MANUFACTURER)"
          + " VALUES ( ?, ?, ? )";

      //Get values entered by the user
      String prodName = txtFieldName.getText();
      String prodType = cbItems.getValue();

      //STEP 3: Execute a PreparedStatement query
      pStmt = conn.prepareStatement(insertSql);
      pStmt.setString(1, prodName);
      pStmt.setString(2, prodType);

      String prodManufacturer = txtFieldManufacturer.getText();
      pStmt.setString(3, prodManufacturer);

      System.out.println("Product has been added to the database!");
      System.out.println("Products list\n");

      //SLQ select statement
      String sql = "SELECT * FROM PRODUCT";
      pStmt = conn.prepareStatement(sql);
      ResultSet result = pStmt.executeQuery();
      //
      while (result.next()) {
        //Display value to the console
        System.out.print(result.getString(2) + " ");
        System.out.print(result.getString(3) + " ");
        System.out.println(result.getString(4));
      }

      // STEP 4: Clean-up environment
      result.close();
      pStmt.close();
      conn.close();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}

