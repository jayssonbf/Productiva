public abstract class Product implements Item {

  int id;
  String name;
  String manufacturer;
  ItemType type;

  Product( String name, String manufacturer, ItemType type ) {
    this.name = name;
    this.manufacturer = manufacturer;
    this.type = type;
  }


  public int getId( ) {
    return id;
  }

  public void setName( String name ) {
    this.name = name;
  }

  public String getName( ) {
    return name;
  }

  public void setManufacturer( String manufacturer ) {
    this.manufacturer = manufacturer;
  }

  public String getManufacturer( ) {
    return manufacturer;
  }

  public void setId( int id ) {
    this.id = id;
  }

  public ItemType getType( ) {
    return type;
  }

  public void setType( ItemType type ) {
    this.type = type;
  }

  @Override
  public String toString( ) {
    return "\nName: " + name + "\n" +
        "Manufacturer: " + manufacturer + "\n" +
        "Type: " + type;
  }
}

class Widget extends Product {

  //Parameterized constructor
  Widget( String name, String manufacturer, ItemType item ) {
    super(name, manufacturer, item);
    this.name = name;
    this.manufacturer = manufacturer;
    type = item;
  }

}