/**
 * Forces all classes to implement the product's details.
 * @author Jaysson Balbuena
 */
public interface Item {

  /**
   * unimplemented getId method that gets the product id number.
   * @return A integer containing the product id number.
   */
  int getId();

  /**
   * unimplemented name method that changes the name of the product.
   * @param name A String containing the product's name.
   */
  void setName(String name);

  /**
   * unimplemented getName method that gets the product's name.
   * @return The product's name.
   */
  String getName();

  /**
   * unimplemented setManufacturer method that changes the manufacturer brand's name.
   * @param manufacturer A String containing the manufacturer name.
   */
  void setManufacturer(String manufacturer);

  /**
   * unimplemented getManufacturer method that gets the manufacturer's name.
   * @return The name of the manufacturer
   */
  String getManufacturer();

}
