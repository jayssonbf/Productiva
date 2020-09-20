import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;

public class ActController {

  @FXML
  private Tab prodLineTab;

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

  public void initialize(){
    String[] prodTypes = {"Audio", "Visual", "Memory", "Software", "Video games"};
    //cbItems.getItems().add();
    for(int i = 0; i < prodTypes.length; i++) {
      cbItems.getItems().add(i, prodTypes[i]);
    }
    cbItems.getSelectionModel().selectFirst();

    for (int i = 1; i <= 10; i++) {
      cmbQuantity.getItems().add(Integer.toString(i));
    }
    cmbQuantity.getSelectionModel().selectFirst();
  }

  public void connectToDataBase(){
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/prodMgt";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
    Connection conn = null;
    Statement stmt = null;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute a query
      stmt = conn.createStatement();

      String sql = "SELECT * FROM PRODUCT";

      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        System.out.print(rs.getString(2) + " ");
        System.out.print(rs.getString(3) + " ");
        System.out.println(rs.getString(4));
      }

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}

