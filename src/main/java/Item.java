/**
 * Forces all classes to implement the product's details.
 * @author Jaysson Balbuena
 */
public interface Item {

  /**
   * Unimplemented getId method that gets the product id number.
   * @return A integer containing the product id number.
   */
  int getId();

  /**
   * Unimplemented name method that changes the name of the product.
   * @param name A String containing the product's name.
   */
  void setName(String name);

  /**
   * Unimplemented getName method that gets the product's name.
   * @return The product's name.
   */
  String getName();

  /**
   * Unimplemented setManufacturer method that changes the manufacturer brand's name.
   * @param manufacturer A String containing the manufacturer name.
   */
  void setManufacturer(String manufacturer);

  /**
   * Unimplemented getManufacturer method that gets the manufacturer's name.
   * @return The name of the manufacturer
   */
  String getManufacturer();

}
