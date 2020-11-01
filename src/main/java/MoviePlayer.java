public class MoviePlayer extends Product implements MultimediaControl {

  Screen screen;
  MonitorType monitorType;

  MoviePlayer( String name, String manufacturer, Screen screen, MonitorType monitor ) {
    super(name, manufacturer, ItemType.VISUAL);
    this.screen = screen;
    monitorType = monitor;
  }

  public void play( ) {
    System.out.println("Playing movie");
  }

  public void stop( ) {
    System.out.println("Stopping movie");
  }

  public void previous( ) {
    System.out.println("Previous movie");
  }

  public void next( ) {
    System.out.println("Next movie");
  }

  @Override
  public String toString( ) {

    return
        super.toString() + "\n" +
            "Screen: " + screen + "\n" +
            "Monitor Type: " + monitorType;
  }
}
