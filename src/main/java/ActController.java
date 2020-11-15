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
import java.util.ArrayList;
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
  private TableColumn<Product, Integer> prodIdColumn;

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
    addProduct();
  }

  @FXML
  void recProd( ActionEvent event ) {
    recordProductionBtn();
  }

  //Observable list
  ObservableList<Widget> productLine = FXCollections.observableArrayList();
  private Connection conn = null;
  private PreparedStatement pstmt = null;
  private int inc;



  /**
   * This method is called automatically when the controller loads. and sets a default value for the
   * combobox and choicebox
   */
  public void initialize( ) {

    Employee employee = new Employee("Jaysson Balbuena", "abcd!");
    System.out.println(employee);

    connectToDataBase();
    setupProductLineTable();
    loadProductList();
    loadProductionLog();



   /* AudioPlayer newAudioProduct = new AudioPlayer("DP-X1A", "Onkyo",
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
    }*/

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

  private void setupProductLineTable( ) {

    //Table columns associated with the cell value factory
    prodNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

    manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));

    itemTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

    /*table.getItems().add(new Widget("iphone 12", "Apple", ItemType.AUDIO));
    table.getItems().add(new Widget("iphone 13", "Apple", ItemType.AUDIO));*/

  }

  private void recordProductionBtn( ) {

    inc = 1;
    int size = Integer.parseInt(cmbQuantity.getSelectionModel().getSelectedItem());

    Product selectedItem = listView.getSelectionModel().getSelectedItem();

    ArrayList<ProductionRecord> productionRun = new ArrayList<>();

    if (cmbQuantity.getSelectionModel().getSelectedIndex() + 1 > 1) {

      for (int i = 0; i < size; i++) {

        productionRun.add(new ProductionRecord(selectedItem, inc++));
        txtAreaProdLog.appendText(productionRun.get(i).toString() + "\n");

      }
    } else {
      productionRun.add(new ProductionRecord(selectedItem, inc++));
      txtAreaProdLog.appendText(productionRun.get(0).toString() + "\n");
    }
    addProductionDB(productionRun);
    txtAreaProdLog.clear();
   loadProductionLog();
   // showProduction();
  }

  private void loadProductionLog( ) {
    try {
      //SLQ select statement
      String sql = "SELECT * FROM PRODUCTIONRECORD";
      pstmt = conn.prepareStatement(sql);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {

        ProductionRecord productionRecord = new ProductionRecord(rs.getInt(1), rs.getInt(2),
            rs.getString(3), rs.getTimestamp(4));

        txtAreaProdLog.appendText(productionRecord.toString() + "\n");
      }


    } catch (Exception e) {
        e.printStackTrace();
    }
  }

  private void addProductionDB( ArrayList<ProductionRecord> productionRun ) {

    for (int i = 0; i < productionRun.size(); i++) {

      try {
        String sql = " INSERT INTO PRODUCTIONRECORD(PRODUCT_ID, SERIAL_NUM, DATE_PRODUCED)"
            + " VALUES ( ?, ?, ? )";
        pstmt = conn.prepareStatement(sql);

        pstmt.setInt(1, productionRun.get(i).productID);
        pstmt.setString(2, productionRun.get(i).serialNumber);
        pstmt.setTimestamp(3, productionRun.get(i).dateProduced);

        pstmt.executeUpdate();

        pstmt.close();
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
  }

  private void loadProductList( ) {

    ItemType itemType;

    try {
      //SLQ select statement
      String sql = "SELECT * FROM PRODUCT";
      pstmt = conn.prepareStatement(sql);
      ResultSet result = pstmt.executeQuery();

      //Extract data from result set
      while (result.next()) {
        itemType = ItemType.valueOf(result.getString(3));

        Widget product = new Widget(result.getString(2), result.getString(4),
            itemType);

        //Save to observable list
        productLine.add(product);

        table.setItems(productLine);
        listView.setItems(productLine);

        //Display value to the console
          /*System.out.print(result.getString(2) + " ");
          System.out.print(result.getString(3) + " ");
          System.out.println(result.getString(4));*/
      }
      pstmt.close();
      result.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void connectToDataBase( ) {

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      System.out.println("Error: unable to load driver class!");
    }
  }
  /**
   * This method will use the text entered in the text field of the. interface for the prepared
   * statement to insert a product in the database table
   */
  public void addProduct( ) {

    try {//SQL insert statement
      String sql = " INSERT INTO PRODUCT(NAME, TYPE, MANUFACTURER)"
          + " VALUES ( ?, ?, ? )";

      //STEP 3: Execute a PreparedStatement query
      pstmt = conn.prepareStatement(sql);

      ArrayList<String> productLine = new ArrayList<>();
      productLine.add(txtFieldName.getText());
      pstmt.setString(1, productLine.get(0));

      productLine.add(cbItems.getValue());
      pstmt.setString(2, productLine.get(1));

      productLine.add(txtFieldManufacturer.getText());
      pstmt.setString(3, productLine.get(2));

      System.out.println("Product has been added to the database!");

      pstmt.executeUpdate();
      table.getItems().clear();
      listView.getItems().clear();
      loadProductList();

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

}




