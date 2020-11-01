import java.util.Date;

public class ProductionRecord<G> {

  int productionNumber;
  G productID;
  String serialNumber;
  Date dateProduced;

  ProductionRecord( G productID ) {
    this.productID = productID;
    this.productionNumber = 0;
    this.serialNumber = null;
    this.dateProduced = new Date();
  }

  ProductionRecord( int productionNumber, G productID, String serialNumber, Date dateProduced ) {
    this.productionNumber = 0;
    this.serialNumber = "0";
    this.productID = productID;
    this.dateProduced = new Date();
  }

  public ProductionRecord( Product product, int itemsCount ) {
    this.serialNumber = product.manufacturer.substring(0, 3) + product.type.getCode() + itemsCount;

  }

  public int getProductionNumber( ) {
    return productionNumber;
  }

  public void setProductionNumber( int productionNumber ) {
    this.productionNumber = productionNumber;
  }

  public G getProductID( ) {
    return productID;
  }

  public void setProductID( G productID ) {
    this.productID = productID;
  }

  public String getSerialNumber( ) {
    return serialNumber;
  }

  public void setSerialNumber( String serialNumber ) {
    this.serialNumber = serialNumber;
  }

  public Date getDateProduced( ) {
    return dateProduced;
  }

  public void setDateProduced( Date dateProduced ) {
    this.dateProduced = dateProduced;
  }

  @Override
  public String toString( ) {
    return "Prod. Num: " + productionNumber + " " + "Product ID: " + productID +
        " " + "Serial Num: " + serialNumber + " " + "Date: " + dateProduced;
  }
}
