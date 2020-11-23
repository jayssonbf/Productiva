/*-------------------------------------------------------------------------
file name: ActController.java
by: Jaysson Balbuena
organization: COP 3003, fall 2020
Instructor: Scott Vanselow
Date:  09/19/2020
for: A production management system that adds new records to a database
---------------------------------------------------------------------------*/

//import required packages

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import java.io.IOException;
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

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


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
  private Label nameLBL;

  @FXML
  private Label usernameLBL;

  @FXML
  private Label emailLBL;

  @FXML
  private Label passwordLBL;

  @FXML
  private Label test;

  @FXML
  private Label emptyProdNameFieldLBL;

  @FXML
  private Label emptyManufacturerFieldLBL;

  @FXML
  private Label prodNotSelectedLBL;

  @FXML
  void prodSelectedListview( MouseEvent event) {
    prodNotSelectedLBL.setText("");
  }

  @FXML
  void manufacturerTyped( KeyEvent event) {
    emptyManufacturerFieldLBL.setText("");
  }

  @FXML
  void prodNameTyped(KeyEvent event) {
    emptyProdNameFieldLBL.setText("");
  }

  @FXML
  void buttonAction( ActionEvent event ) {
    addProduct();
  }

  @FXML
  void recProd( ActionEvent event ) {
    recordProductionBtn();
  }

  @FXML
  void logout(ActionEvent event) throws IOException {

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

    ////Employee employee = new Employee("Jaysson Balbuena", "abcd!");
    //System.out.println(employee);

    connectToDataBase();
    setupProductLineTable();
    loadProductList();
    loadProductionLog();

    nameLBL.setText("Simple test");

   /* try{
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("sample.fxml"));
      Parent tableviewParent = loader.load();

      Controller controller = loader.getController();
      Employee emp = controller.loginScreen();

      usernameLBL.setText(emp.userName);

    }catch (Exception e){
      e.printStackTrace();
    }*/




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


  }

  private void recordProductionBtn( ) {

    inc = 1;
    int quantity = Integer.parseInt(cmbQuantity.getSelectionModel().getSelectedItem());

    Product selectedItem = listView.getSelectionModel().getSelectedItem();

    ArrayList<ProductionRecord> productionRun = new ArrayList<>();

    if (listView.getSelectionModel().getSelectedItem() == null){

      prodNotSelectedLBL.setText("Please choose a product from the list");
      return;
    }

    if (cmbQuantity.getSelectionModel().getSelectedIndex() + 1 > 1) {

      for (int i = 0; i < quantity; i++) {

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

      //----------------------------------------------------------------------------------------------------
      boolean validation = true;
      emptyProdNameFieldLBL.setStyle("-fx-text-fill: #ff0000");
      emptyManufacturerFieldLBL.setStyle("-fx-text-fill: red");

      while (validation) {
        if (txtFieldName.getText().isEmpty() && txtFieldManufacturer.getText().isEmpty()) {
          emptyProdNameFieldLBL.setText("Enter a product");
          emptyManufacturerFieldLBL.setText("Provide a manufacturer");
          return;
        } else if (txtFieldName.getText().isEmpty()) {
          emptyProdNameFieldLBL.setText("Enter a product");
          return;
        } else if (txtFieldManufacturer.getText().isEmpty()) {
          emptyManufacturerFieldLBL.setText("Provide a manufacturer");
          return;
        }
        validation = false;
      }
      ArrayList<String> productLine = new ArrayList<>();
      productLine.add(txtFieldName.getText());
      pstmt.setString(1, productLine.get(0));

      productLine.add(cbItems.getValue());
      pstmt.setString(2, productLine.get(1));

      productLine.add(txtFieldManufacturer.getText());
      pstmt.setString(3, productLine.get(2));

      pstmt.executeUpdate();
      table.getItems().clear();
      listView.getItems().clear();
      loadProductList();

    } catch (SQLException e) {
      e.printStackTrace();
    }
//------------------------------------------------------------------------------------------
  }

  public void addEmployee(Employee emp){
    System.out.println("INSIDE addEmployee: " + emp);

    try{
      String sql = " INSERT INTO EMPLOYEE(NAME, USERNAME, EMAIL, PASSWORD)"
          + " VALUES ( ?, ?, ?, ? )";

      //Execute a PreparedStatement query
      pstmt = conn.prepareStatement(sql);

      pstmt.setString(1, emp.name.toString());
      pstmt.setString(2, emp.userName);
      pstmt.setString(3, emp.email);
      pstmt.setString(4, emp.password);

      pstmt.executeUpdate();

      System.out.println("Employee " + emp.name + " has been successfully added to the database");
    }catch (SQLException e){
      e.printStackTrace();
    }
  }

  public boolean loadEmployee(String userName, String password){

    boolean accountExist = false;

    try {
      //SLQ select statement
      String sql = "SELECT * FROM EMPLOYEE";
      pstmt = conn.prepareStatement(sql);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {

        String empName = rs.getString(2);
        String empUsername = rs.getString(3);
        String empEmail = rs.getString(4);
        String empPassword = rs.getString(5);

        System.out.println("Username: " + empUsername);
        System.out.println("Password: " + empPassword);
        System.out.println("Name: " + empName);
        System.out.println("Email: " + empEmail);

        Parent root;
        if(empUsername.equals(userName) && empPassword.equals(password)){
          System.out.println("Account found");
          accountExist = true;

          //NOT WORKING -------------------------------------------------------
          nameLBL.setText(empName);
          usernameLBL.setText(empUsername);
          emailLBL.setText(empEmail);
          passwordLBL.setText(empPassword);
          //---------------------------------------------------------------------


          System.out.println("USER-ACCOUNT-DETAILS\n" + empName + "\n" + empUsername + "\n"
              + empEmail + "\n" + empPassword);

        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    test.setText("Test");
    return accountExist;
  }

}




