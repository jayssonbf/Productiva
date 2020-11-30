/**
 * Represents the specifications of a screen type. implements the ScreenSpec class's methods.
 * @author Jaysson Balbuena
 */
public class Screen implements ScreenSpec {

  final String resolution;
  final int refreshRate;
  final int responseTime;

  /**
   * class constructor that creates a screen object by passing a resolution, a refresh rate, and a
   * response time.
   * @param resolution   contains the resolution of this screen.
   * @param refreshRate  contains the refresh rate of this screen.
   * @param responseTime contains the response time of this screen.
   */
  Screen(String resolution, int refreshRate, int responseTime) {
    this.resolution = resolution;
    this.refreshRate = refreshRate;
    this.responseTime = responseTime;
  }

  /**
   * implementation of the getResolution method from the ScreenSpec interface's class that gets the
   * resolution of this screen.
   * @return this screen's resolution.
   */
  @Override
  public String getResolution() {
    return resolution;
  }

  /**
   * implementation of the getRefreshRate method from the ScreenSpec interface's class that gets the
   * refresh rate of this screen.
   * @return this screen's refresh rate.
   */
  @Override
  public int getRefreshRate() {
    return refreshRate;
  }

  /**
   * implementation of the getResponseTime method from the ScreenSpec interface's class that gets
   * the response time of this screen.
   * @return this screen's response time.
   */
  @Override
  public int getResponseTime() {
    return responseTime;
  }

  /**
   * overrides the toString class and displays the screen's resolution, refresh rate, and, response
   * time.
   * @return the content of the screen's resolution, refresh rate, and response time.
   */
  @Override
  public String toString() {
    return "\nResolution: " + resolution + "\n"
        + "Refresh rate: " + refreshRate + "\n"
        + "Response time: " + responseTime;
  }
}
