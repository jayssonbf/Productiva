/**
 * Represents pre-set values for an item type.
 * Each item type contains a specific code.
 * @author Jaysson Balbuena
 */
public enum ItemType {
  AUDIO("AU"), VISUAL("VI"), AUDIO_MOBILE("AM"), VISUAL_MOBILE("VM");

  private final String code;

  /**
   * Class constructor that is invoked whenever the enum value is accessed.
   * @param code a String containing a specific value that is then assigned to code.
   */
  ItemType(String code) {
    this.code = code;
  }

  /**
   * Gets the ItemType's code.
   * @return The code of this ItemType.
   */
  public String getCode() {
    return code;
  }
}
