/**
 * Forces all classes to implement the screen's specs.
 * @author Jaysson Balbuena
 */
public interface ScreenSpec {

  /**
   * unimplemented method that gets the resolution of the screen.
   * @return a String containing the screen's resolution.
   */
  String getResolution();

  /**
   * unimplemented method that gets the refresh rate of the screen.
   * @return an int containing the screen's refresh rate.
   */
  int getRefreshRate();

  /**
   * unimplemented method that gets the response time of the screen.
   * @return an int containing the screen's response time.
   */
  int getResponseTime();

}