/**
 * Represents a movie player with a variety of display panel.
 * A movie player has play, stop, previous, and next controls.
 * Movie player objects inherit the properties and methods from the Product's class.
 * @author Jaysson Balbuena
 */
public class MoviePlayer extends Product implements MultimediaControl {

  final Screen screen;
  final MonitorType monitorType;

  /**
   * Class constructor creates a movie player object by passing name, manufacturer, screen, and
   * monitor type.
   * @param name the product's name.
   * @param manufacturer the product's manufacturer.
   * @param screen the screen type of the product.
   * @param monitor the monitor type of the product.
   */
  MoviePlayer(String name, String manufacturer, Screen screen, MonitorType monitor) {
    super(name, manufacturer, ItemType.VISUAL);
    this.screen = screen;
    monitorType = monitor;
  }

  /**
   * implementing the play() method from the multimedia interface.
   */
  public void play() {
    System.out.println("Playing movie");
  }

  /**
   * implementing the stop() method from the multimedia interface.
   */
  public void stop() {
    System.out.println("Stopping movie");
  }

  /**
   * implementing the previous() method from the multimedia interface.
   */
  public void previous() {
    System.out.println("Previous movie");
  }

  /**
   * implementing the next() method from the multimedia interface.
   */
  public void next() {
    System.out.println("Next movie");
  }

  /**
   * overrides the toString class and displays the screen and monitor type.
   * @return the content of the screen and monitorType.
   */
  @Override
  public String toString() {

    return
        super.toString() + "\n"
            + "Screen: " + screen + "\n"
            + "Monitor Type: " + monitorType;
  }
}
