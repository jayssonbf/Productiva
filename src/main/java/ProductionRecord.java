import java.sql.Timestamp;

public class ProductionRecord {

  int productionNumber;
  int productID;
  String serialNumber;
  Timestamp dateProduced;

  public ProductionRecord( int productID ) {
    this.productID = productID;
    this.productionNumber = 0;
    this.serialNumber = null;
    this.dateProduced = new Timestamp(System.currentTimeMillis());
  }

  public ProductionRecord( int productionNumber, int productID, String serialNumber, Timestamp dateProduced ) {
    this.productionNumber = productionNumber;
    this.serialNumber = serialNumber;
    this.productID = productID;
    this.dateProduced = dateProduced;
  }

  public ProductionRecord( Product product, int itemsCount ) {
    this.productionNumber = 0;
    this.productID = 0;
    this.dateProduced = new Timestamp(System.currentTimeMillis());
    this.serialNumber = product.manufacturer.substring(0, 3) + product.type.getCode() + "0000" + itemsCount;
  }

  @Override
  public String toString( ) {
    return "Prod. Num: " + productionNumber + " " + "Product ID: " + productID +
        " " + "Serial Num: " + serialNumber + " " + "Date: " + dateProduced;
  }
}
