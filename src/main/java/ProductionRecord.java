import java.util.Date;

public class ProductionRecord<G> {

  int productionNumber;
  int productID;
  String serialNumber;
  Date dateProduced;

  ProductionRecord( int productID ) {
    this.productID = productID;
    this.productionNumber = 0;
    this.serialNumber = null;
    this.dateProduced = new Date();
  }

  ProductionRecord( int productionNumber, int productID, String serialNumber, Date dateProduced ) {
    this.productionNumber = 0;
    this.serialNumber = "0";
    this.productID = productID;
    this.dateProduced = new Date();
  }

  public ProductionRecord( Product product, int itemsCount ) {
    this.productionNumber = 0;
    this.productID = 0;
    this.dateProduced = new Date();
    this.serialNumber = product.manufacturer.substring(0, 3) + product.type.getCode() + "0000" + itemsCount;

  }


  @Override
  public String toString( ) {
    return "Prod. Num: " + productionNumber + " " + "Product ID: " + productID +
        " " + "Serial Num: " + serialNumber + " " + "Date: " + dateProduced;
  }
}
