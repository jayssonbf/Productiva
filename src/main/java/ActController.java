/*-------------------------------------------------------------------------
file name: ActController.java
by: Jaysson Balbuena
organization: COP 3003, fall 2020
Instructor: Scott Vanselow
Date:  09/19/2020
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
  private TextField txtFieldName;

  @FXML
  private TextField txtFieldManufacturer;

  @FXML
  private ChoiceBox<String> cbItems;

  @FXML
  private ComboBox<String> cmbQuantity;

  @FXML
  void buttonAction(ActionEvent event) {
    connectToDataBase();
  }

  @FXML
  void recProd(ActionEvent event) {
  }

  /**
   *This method is called automatically when the controller loads.
   * and sets a default value for the combobox and choicebox
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
   * This method will use the text entered in the text field of the.
   * interface for the prepared statement to insert a product in the database
   * table
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
      int rowAffected = 0;
      try {
        //STEP 3: Execute a PreparedStatement query
        pStmt = conn.prepareStatement(insertSql);

        String prodName = txtFieldName.getText();
        pStmt.setString(1, prodName);

        String prodType = cbItems.getValue();
        pStmt.setString(2, prodType);

        String prodManufacturer = txtFieldManufacturer.getText();
        pStmt.setString(3, prodManufacturer);

        System.out.println("Product has been added to the database!");
        System.out.println("Products list\n");
        rowAffected = pStmt.executeUpdate();
      } catch (Exception e) {

        e.printStackTrace();
      } finally {
        if (pStmt != null) {
          pStmt.close();
        }
      }

      try {  //SLQ select statement
        String sql = "SELECT * FROM PRODUCT";
        pStmt = conn.prepareStatement(sql);
        ResultSet result = pStmt.executeQuery();

        if (rowAffected > 0) {
          while (result.next()) {
            //Display value to the console
            System.out.print(result.getString(2) + " ");
            System.out.print(result.getString(3) + " ");
            System.out.println(result.getString(4));
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        pStmt.close();
      }
      // STEP 4: Clean-up environment
      conn.close();
      pStmt.close();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
