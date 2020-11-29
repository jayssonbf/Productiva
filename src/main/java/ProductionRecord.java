import java.sql.Timestamp;

/**
 * Represents the record of each produced product.
 * allows users to keep track of products listed for production.
 * @author Jaysson Balbuena
 */
public class ProductionRecord {

  int productionNumber;
  String productID;
  String serialNumber;
  Timestamp dateProduced;

  /**
   * class constructor allows a derived class creates a product object by passing a product's name.
   * @param productID provides the id of this product.
   */
  public ProductionRecord(String productID) {
    this.productID = productID;
    this.productionNumber = 0;
    this.serialNumber = null;
    this.dateProduced = new Timestamp(System.currentTimeMillis());
  }

  /**
   * class constructor allows a derived class creates a product object by passing a product's
   *       production number, product id, serial number, and date produced.
   * @param productionNumber contains the production number of this product.
   * @param productID contains the product id of this product.
   * @param serialNumber contains the serial number of this product.
   * @param dateProduced contains the date product of each product.
   */
  public ProductionRecord(int productionNumber, String productID, String serialNumber,
                          Timestamp dateProduced) {
    this.productionNumber = productionNumber;
    this.serialNumber = serialNumber;
    this.productID = productID;
    this.dateProduced = new Timestamp(dateProduced.getTime());
  }

  /**
   * class constructor allows a derived class creates a product object emphasising in the serial
   * number by passing a product object, and an item count.
   * @param product  object that feeds part of the serial number's structure.
   * @param itemsCount form part of a unique serial number.
   */
  public ProductionRecord(Product product, int itemsCount) {
    this.productionNumber = 0;
    this.productID = product.name;
    this.dateProduced = new Timestamp(System.currentTimeMillis());
    this.serialNumber = product.manufacturer.substring(0, 3) + product.type.getCode()
        + String.format("%05d", itemsCount);
  }

  /**
   * overrides the toString class and displays the product's production number, name,
   * serial number, and date produced.
   * @return the content of the product's production number, name, serial no, and date produced.
   */
  @Override
  public String toString() {
    return "Prod. Num: " + productionNumber + " " + "Product Name: " + productID
        + " " + "Serial Num: " + serialNumber + " " + "Date: " + dateProduced;
  }
}
