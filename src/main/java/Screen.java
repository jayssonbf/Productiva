public class Screen implements ScreenSpec{

  String resolution;
  int refreshRate;
  int responseTime;

  @Override
  public String getResolution( ) {
    return resolution;
  }

  @Override
  public int getRefreshRate( ) {
    return refreshRate;
  }

  @Override
  public int getResponseTime( ) {
    return responseTime;
  }

  @Override
  public String toString( ) {
    return "Resolution = " + resolution + "\n" +
           "Refresh rate = " + refreshRate + "\n" +
           "Response time = " + responseTime;
  }
}
