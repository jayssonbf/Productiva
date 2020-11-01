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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javax.xml.crypto.Data;
import org.h2.table.Table;

/**
 * Represents the creation of a database. Many products can be inserted into the database
 *
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
  private TextArea txtAreaProdLog;

  @FXML
  private TableView<Widget> table;

  @FXML
  private TableColumn<Product, Integer> prodIDColumn;

  @FXML
  private TableColumn<Widget, String> prodNameColumn;

  @FXML
  private TableColumn<Widget, String> manufacturerColumn;

  @FXML
  private TableColumn<Widget, ItemType> itemTypeColumn;

  @FXML
  private ListView<Widget> listView;

  @FXML
  void buttonAction( ActionEvent event ) {
    connectToDataBase();
  }

  @FXML
  void recProd( ActionEvent event ) {
  }

  ObservableList<Widget> prodLine;

  /**
   * This method is called automatically when the controller loads. and sets a default value for the
   * combobox and choicebox
   */
  public void initialize( ) {

    AudioPlayer newAudioProduct = new AudioPlayer("DP-X1A", "Onkyo",
        "DSD/FLAC/ALAC/WAV/AIFF/MQA/Ogg-Vorbis/MP3/AAC", "M3U/PLS/WPL");

    Screen newScreen = new Screen("720x480", 40, 22);

    MoviePlayer newMovieProduct = new MoviePlayer("DBPOWER MK101", "OracleProduction", newScreen,
        MonitorType.LCD);

    ArrayList<MultimediaControl> productList = new ArrayList<MultimediaControl>();
    productList.add(newAudioProduct);
    productList.add(newMovieProduct);
    for (MultimediaControl p : productList) {
      System.out.println(p);
      p.play();
      p.stop();
      p.next();
      p.previous();
    }

    AudioPlayer newProduct = new AudioPlayer("DP-X1A", "Onkyo",
        "DSD/FLAC/ALAC/WAV/AIFF/MQA/Ogg-Vorbis/MP3/AAC", "M3U/PLS/WPL");
    System.out.println(newProduct);
    newProduct.play();
    newProduct.stop();
    newProduct.next();
    newProduct.previous();

    ArrayList<Widget> productLine = new ArrayList<>();
    productLine.add(new Widget("iPod", "Apple", ItemType.AUDIO));
    productLine.add(new Widget("Zune", "Microsoft", ItemType.AUDIO_MOBILE));
    for (Widget prod : productLine) {
      System.out.println(prod);
    }

    //for each loop that produce the item types
    for (ItemType it : ItemType.values()) {
      cbItems.getItems().addAll(String.valueOf(it));
    }
    cbItems.getSelectionModel().selectFirst();

    for (int i = 1; i <= 10; i++) {
      cmbQuantity.getItems().add(Integer.toString(i));
    }
    cmbQuantity.getSelectionModel().selectFirst();


  }


  /**
   * This method will use the text entered in the text field of the. interface for the prepared
   * statement to insert a product in the database table
   */
  public void connectToDataBase( ) {


    Connection conn = null;
    PreparedStatement pStmt = null;
    //Observable list
    prodLine = FXCollections.observableArrayList();

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //SQL insert statement
      String insertSql = " INSERT INTO PRODUCT(NAME, TYPE, MANUFACTURER)"
          + " VALUES ( ?, ?, ? )";

      int rowAffected = 0;
      ItemType data;

      try {
        //STEP 3: Execute a PreparedStatement query
        pStmt = conn.prepareStatement(insertSql);

        ArrayList<String> productLine = new ArrayList<>();
        productLine.add(txtFieldName.getText());
        pStmt.setString(1, productLine.get(0));

        productLine.add(cbItems.getValue());
        pStmt.setString(2, productLine.get(1));

        productLine.add(txtFieldManufacturer.getText());
        pStmt.setString(3, productLine.get(2));

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
            data = ItemType.valueOf(result.getString(3));
            prodLine.add(new Widget(result.getString(2), result.getString(4), data));

            prodNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));

            itemTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

            //Display all products in the Product Line Tab when add button is clicked
            table.setItems(prodLine);

            //Display all products in the Produce tab ListView
            listView.setItems(prodLine);


            txtAreaProdLog.appendText(toString() + "\n");
            //Display value to the console
            /*System.out.print(result.getString(2) + " ");
            System.out.print(result.getString(3) + " ");
            System.out.println(result.getString(4));*/
          }

        }


      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        pStmt.close();
      }
      // STEP 4: Clean-up environment
      conn.close();
      //pStmt.close();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
