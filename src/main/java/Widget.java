
/**
 * Represents a copy of the Product abstract class.
 * allows users to access the product's abstract class properties and methods.
 * @author Jaysson Balbuena
 */
public class Widget extends Product {

  /**
   * class constructor creates a widget object that access the properties and methods
   * of the Product's abstract class.
   * @param name A String containing the product's name.
   * @param manufacturer A String containing the product's manufacturer.
   * @param item an item type containing the product's name.
   */
  public Widget(int id, String name, String manufacturer, ItemType item) {
    super(name, manufacturer, item);
    this.id = id;
    this.name = name;
    this.manufacturer = manufacturer;
    type = item;
  }


}
