/**
 * Represents the specifications of a product.
 * An employee can add products into the system by providing these specs.
 * Implements the Item class's methods which become available for the Product's child class
 * @author Jaysson Balbuena
 */
public abstract class Product implements Item {

  int id;
  String name;
  String manufacturer;
  ItemType type;

  /**
   * class constructor creates a product object by providing the name, the manufacturer, and
   * the item type.
   * @param name the product's name.
   * @param manufacturer the products manufacturer.
   * @param type the product's item type.
   */
  Product(String name, String manufacturer, ItemType type) {
    this.name = name;
    this.manufacturer = manufacturer;
    this.type = type;
  }

  /**
   * gets the id of this product.
   * @return this product's id.
   */
  public int getId() {
    return id;
  }

  /**
   * changes the name of the product.
   * @param name A String containing the product's name.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * gets the name of the product.
   * @return this product's name.
   */
  public String getName() {
    return name;
  }

  /**
   * changes the manufacturer of the product.
   * @param manufacturer A String containing the manufacturer name.
   */
  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  /**
   * gets the manufacturer of the product.
   * @return this product's manufacturer
   */
  public String getManufacturer() {
    return manufacturer;
  }

  /**
   * sets the id of the product.
   * @param id an int containing the id of this product.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * gets the item type of the product.
   * @return this product's item type.
   */
  public ItemType getType() {
    return type;
  }

  /**
   * changes the item type of the product.
   * @param type an item type containing this product's item type.
   */
  public void setType(ItemType type) {
    this.type = type;
  }

  /**
   * overrides the toString class and displays the product's name, manufacturer, and type.
   * @return the content of the product's name, manufacturer, and type.
   */
  @Override
  public String toString() {
    return "\nName: " + name + "\n"
        + "Manufacturer: " + manufacturer + "\n"
        + "Type: " + type;
  }
}

