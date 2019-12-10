/***************************************************
 * Provides a template for AudioPlayers object.
 * Extends Product to use it as template for object.
 * Implements MultimediaControl to display:
 * play, stop, previous, next.
 *
 * @author Shane Smith
 */
package sample;

public class AudioPlayer extends Product implements MultimediaControl {

  private String supportedAudioFormats;
  private String supportedPlaylistFormats;

  /***************************************************
   * Sets up constructor for an AudioPlayer object.
   * @author Shane Smith
   * @param name name of the AudioPlayer object.
   * @param manufacturer manufacturer of the AudioPlayer object.
   * @param supportedAudioFormats supported audio format of the AudioPlayer.
   * @param supportedPlaylistFormats supported playlist format of the AudioPlayer.
   */
  AudioPlayer(String name, String manufacturer, String supportedAudioFormats,
              String supportedPlaylistFormats) {
    super(name, manufacturer, ItemType.AUDIO.code);
    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  }

  /***************************************************
   * Provides a console output that displays the AudioPlayer object.
   * @author Shane Smith
   * @return the output string to console.
   */
  public String toString() {
    return super.toString() + "\nSupported Audio Formats: " + supportedAudioFormats
        + "\nSupported Playlist Formats: " + supportedPlaylistFormats;
  }

  /***************************************************
   * Prints to console "Playing" when called.
   * @author Shane Smith
   */
  @Override
  public void play() {
    System.out.println("Playing");
  }

  /***************************************************
   * Prints to console "Stopping" when called.
   * @author Shane Smith
   */
  @Override
  public void stop() {
    System.out.println("Stopping");
  }

  /***************************************************
   * Prints to console "Previous" when called.
   * @author Shane Smith
   */
  @Override
  public void previous() {
    System.out.println("Previous");
  }

  /***************************************************
   * Prints to console "Next" when called.
   * @author Shane Smith
   */
  @Override
  public void next() {
    System.out.println("Next");
  }
}
