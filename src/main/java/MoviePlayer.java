public class MoviePlayer extends Product implements MultimediaControl {

  MonitorType screen;
  MonitorType monitorType;

  MoviePlayer(String name, String manufacturer, MonitorType screen, MonitorType monitor){
    super(name, manufacturer, ItemType.VISUAL);
    this.screen = screen;
    monitorType = monitor;
  }

  public void play(){
    System.out.println("Playing");
  }
  public void stop(){
    System.out.println("Stop");
  }
  public void previous(){
    System.out.println("Previous");
  }
  public void next(){
    System.out.println("Next");
  }

  @Override
  public String toString( ) {

    return
        super.toString() + "\n" +
        "Screen = " + screen + "\n" +
        "MonitorType = " + monitorType;
  }
}
