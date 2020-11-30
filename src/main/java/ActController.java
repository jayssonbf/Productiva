/*-------------------------------------------------------------------------
file name: ActController.java
by: Jaysson Balbuena
organization: COP 3003, fall 2020
Instructor: Scott Vanselow
Date:  09/19/2020
for: A production management system that adds new records to a database
---------------------------------------------------------------------------*/

//import required packages

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Manipulates and controls the scene builder containers through individual fx:id's and methods.
 * @author Jaysson Balbuena
 */
public class ActController {

  //JDBC driver name and database URL
  static final String JDBC_DRIVER = "org.h2.Driver";
  static final String DB_URL = "jdbc:h2:./res/prodMgt";

  // Database credentials
  static final String USER = "";
  static final String PASS = reverseString(getDBPassword());

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
  public Label nameLbl;

  @FXML
  public Label usernameLbl;

  @FXML
  public Label emailLbl;

  @FXML
  private Label emptyProdNameFieldLbl;

  @FXML
  private Label emptyManufacturerFieldLbl;

  @FXML
  private Label prodNotSelectedLbl;

  /**
   * disables error message shown when user does not select a product from the listView.
   * @param event contains a number of events that is utilized when an action is executed.
   */
  @FXML
  void prodSelectedListview(MouseEvent event) {
    prodNotSelectedLbl.setText("");
    recordedProductLbl.setText("");
  }

  /**
   * disables error message shown when user does not type anything in the manufacturer field.
   * @param event contains a number of events that is utilized when an action is executed.
   */
  @FXML
  void manufacturerTyped(KeyEvent event) {
    emptyManufacturerFieldLbl.setText("");
  }

  /**
   * disables error message shown when user does not type anything in the product name's field.
   * @param event contains a number of events that is utilized when an action is executed.
   */
  @FXML
  void prodNameTyped(KeyEvent event) {
    emptyProdNameFieldLbl.setText("");
  }

  /**
   * calls addProduct() method where users can add products into the system.
   * @param event contains a number of events that is utilized when an action is executed.
   */
  @FXML
  void buttonAction(ActionEvent event) {
    addProduct();
  }

  /**
   * calls recordProductionBtn() method which records products to the production log tab.
   * @param event contains a number of events that is utilized when an action is executed.
   */
  @FXML
  void recProd(ActionEvent event) {
    recordProductionBtn();
  }

  /**
   * allows users to log out from the system.
   * @param event contains a number of events that is utilized when an action is executed.
   * @throws IOException is thrown
   */
  @FXML
  void logout(ActionEvent event) throws IOException {
    Stage stageTest = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Parent root = FXMLLoader.load(getClass().getResource("sample2.fxml"));
    Scene scene = new Scene(root, 500, 650);
    stageTest.setScene(scene);
    scene.getStylesheets().add("stylish.css");
  }

  @FXML
  private Label recordedProductLbl;

  //Observable list
  final ObservableList<Widget> productLine = FXCollections.observableArrayList();
  private Connection conn = null;
  private PreparedStatement pstmt = null;


  /**
   * This method is called automatically when the controller loads, and sets a default value for the
   * combobox and choice box.
   */
  public void initialize() {

    connectToDataBase();
    setupProductLineTable();
    loadProductList();
    loadProductionLog();
    getDBPassword();

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
   * this method is utilized to display all the production records into the production log
   * once the program starts.
   */
  private void loadProductionLog() {
    try {
      //SLQ select statement
      String sql = "SELECT * FROM PRODUCTIONRECORD";
      pstmt = conn.prepareStatement(sql);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {

        ProductionRecord productionRecord = new ProductionRecord(rs.getInt(1), rs.getString(2),
            rs.getString(3), rs.getTimestamp(4));

        txtAreaProdLog.appendText(productionRecord.toString() + "\n");
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * this method is called and generates cell values for the TableView.
   */
  private void setupProductLineTable() {

    //Table columns associated with the cell value factory
    prodNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

    manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));

    itemTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

    prodIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

  }

  /**
   * This method is called once users clicked on RecordProduction.
   * it records products into the Production Log tab.
   */
  private void recordProductionBtn() {

    int inc = 1;

    int quantity = Integer.parseInt(cmbQuantity.getSelectionModel().getSelectedItem());

    Product selectedItem = listView.getSelectionModel().getSelectedItem();

    ArrayList<ProductionRecord> productionRun = new ArrayList<>();

    boolean itemSelected = listView.getSelectionModel().getSelectedItem() == null;
    String existedItem;
    if (itemSelected) {

      prodNotSelectedLbl.setText("Please choose a product from the list");
      return;
    } else {
      recordedProductLbl.setText("Product has been successfully recorded!");

      existedItem = productSearch(selectedItem);

      if (!existedItem.equals("")) {
        int serial = Integer.parseInt(existedItem.replaceAll("[^\\d.]", ""));
        serial++;
        for (int i = 0; i < quantity; i++) {
          productionRun.add(new ProductionRecord(selectedItem, serial++));
        }
      } else {
        for (int i = 0; i < quantity; i++) {
          productionRun.add(new ProductionRecord(selectedItem, inc++));
        }
      }
    }

    addProductionDB(productionRun);
    txtAreaProdLog.clear();
    loadProductionLog();

  }

  /**
   * search for exiting products to avoid duplicate serial numbers.
   * @param searchItem contains a product to be searched in the database.
   * @return an empty String if product was not found, otherwise, return an existing serial number.
   */
  private String productSearch(Product searchItem) {

    String result = "";
    try {
      //SLQ select statement
      String sql = "SELECT * FROM PRODUCTIONRECORD";
      pstmt = conn.prepareStatement(sql);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {

        if (rs.getString(2).equals(searchItem.name)) {
          result = rs.getString(3);
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * inserts production record into the data base.
   * @param productionRun an ArrayList that contains a list of production records.
   */
  private void addProductionDB(ArrayList<ProductionRecord> productionRun) {

    for (ProductionRecord productionRecord : productionRun) {

      try {
        String sql = " INSERT INTO PRODUCTIONRECORD(PRODUCT_ID, SERIAL_NUM, DATE_PRODUCED)"
            + " VALUES ( ?, ?, ? )";
        pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, productionRecord.productID);
        pstmt.setString(2, productionRecord.serialNumber);
        pstmt.setTimestamp(3, productionRecord.dateProduced);

        pstmt.executeUpdate();

        pstmt.close();
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
  }

  /**
   * selects product list from the database and displays it into the table and listView.
   */
  private void loadProductList() {

    ItemType itemType;

    try {
      //SLQ select statement
      String sql = "SELECT * FROM PRODUCT";
      pstmt = conn.prepareStatement(sql);
      ResultSet result = pstmt.executeQuery();

      //Extract data from result set
      while (result.next()) {
        itemType = ItemType.valueOf(result.getString(3));

        Widget product = new Widget(result.getInt(1),result.getString(2), result.getString(4),
            itemType);

        //Save to observable list
        productLine.add(product);

        table.setItems(productLine);
        listView.setItems(productLine);



      }
      pstmt.close();
      result.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * this method connects to the data base.
   */
  private void connectToDataBase() {

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
  public void addProduct() {

    try { //SQL insert statement
      String sql = " INSERT INTO PRODUCT(NAME, TYPE, MANUFACTURER)"
          + " VALUES ( ?, ?, ? )";

      //STEP 3: Execute a PreparedStatement query
      pstmt = conn.prepareStatement(sql);

      boolean validation = true;

      while (validation) {
        if (txtFieldName.getText().isEmpty() && txtFieldManufacturer.getText().isEmpty()) {
          emptyProdNameFieldLbl.setText("Enter a product");
          emptyManufacturerFieldLbl.setText("Provide a manufacturer");
          return;
        } else if (txtFieldName.getText().isEmpty()) {
          emptyProdNameFieldLbl.setText("Enter a product");
          return;
        } else if (txtFieldManufacturer.getText().isEmpty()) {
          emptyManufacturerFieldLbl.setText("Provide a manufacturer");
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
  }

  /**
   * receives an employee object and adds it to the employee database.
   * @param emp contains employee's details.
   */
  public void addEmployee(Employee emp) {
    System.out.println("INSIDE addEmployee: " + emp);

    try {
      String sql = " INSERT INTO EMPLOYEE(NAME, USERNAME, EMAIL, PASSWORD)"
          + " VALUES ( ?, ?, ?, ? )";

      //Execute a PreparedStatement query
      pstmt = conn.prepareStatement(sql);

      pstmt.setString(1, emp.name.toString());
      pstmt.setString(2, emp.userName);
      pstmt.setString(3, emp.email);
      pstmt.setString(4, emp.password);

      pstmt.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * receives an employee's username and password and verifies if the employee's account exist.
   * @param userName this employee's username.
   * @param password this employee's password.
   * @return true if the employee's account exist, otherwise, it returns false.
   */
  public boolean loadEmployee(String userName, String password) {

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

        if (empUsername.equals(userName) && empPassword.equals(password)) {
          accountExist = true;

          nameLbl.setText(empName);
          usernameLbl.setText(empUsername);
          emailLbl.setText(empEmail);
        }
      }

      rs.close();
      pstmt.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return accountExist;
  }

  /**
   * reverses a string.
   * @param id A String containing a password to be reversed
   * @return This password reversed.
   */
  private static String reverseString(String id) {
    return id.isEmpty() ? id : reverseString(id.substring(1)) + id.charAt(0);
  }

  /**
   * gets password from a txt field.
   * @return this database's password.
   */
  public static String getDBPassword(){

    String pass = "";
    try{

      BufferedReader reader = Files.newBufferedReader(Paths.get("src/main/java/pass.txt"));

      pass = reader.readLine();

    }catch(IOException e){
      e.printStackTrace();
    }
    return pass;
  }

}




