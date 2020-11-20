/**
 * Represents a audio player that supports a variety of formats. A audio player has play, stop,
 * previous, and next controls.
 * @author Jaysson Balbuena
 */
public class AudioPlayer extends Product implements MultimediaControl {

  private String supportedAudioFormats;
  private String supportedPlaylistFormats;

  /**
   * Class constructor creates a audio player object by passing a product's name, manufacturer,
   * supported audio formats and playlist formats.
   *
   * @param name                     describes the name of the product.
   * @param manufacturer             describes the product's manufacturer.
   * @param supportedAudioFormats    indicates the audio supported formats.
   * @param supportedPlaylistFormats indicates the playlist audio format.
   */
  public AudioPlayer(String name, String manufacturer, String supportedAudioFormats,
      String supportedPlaylistFormats) {
    super(name, manufacturer, ItemType.AUDIO);
    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  }

  /**
   * Displays "Playing" when an audio is on.
   */
  public void play() {
    System.out.println("Playing");
  }

  /**
   * Displays "Stopping" when an audio is about to stop.
   */
  public void stop() {
    System.out.println("Stopping");
  }

  /**
   * Displays "Previous" when audio player plays the previous audio.
   */
  public void previous() {
    System.out.println("Previous");
  }

  /**
   * Displays "Next" when audio player plays the next audio.
   */
  public void next() {
    System.out.println("Next");
  }

  /**
   * Overrides toString method to display the supported audio formats.
   * @return this supported audio and list formats.
   */
  @Override
  public String toString() {
    return super.toString()
        + "\nSupported Audio Formats: " + supportedAudioFormats + "\n"
        + "Supported Playlist Formats: " + supportedPlaylistFormats;

  }
}
