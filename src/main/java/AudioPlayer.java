abstract class AudioPlayer extends Product implements MultimediaControl{

  private String supportedAudioFormats;
  private String supportedPlaylistFormats;

  public AudioPlayer(String name, String manufacturer, ItemType type, String supportedAudioFormats, String supportedPlaylistFormats){
    super(name, manufacturer, type);
    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;
    this.type = ItemType.AUDIO;
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
    return super.toString() +
        "Supported AudioFormats = " + supportedAudioFormats + "\n" +
        "Supported PlaylistFormats = " + supportedPlaylistFormats + "\n";
        /*"id = " + id + "\n" +
        "Name = " + name + "\n" +
        "Manufacturer = " + manufacturer + "\n" +
        "Type = " + type;*/
  }
}
