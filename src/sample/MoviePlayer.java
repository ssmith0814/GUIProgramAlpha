/***************************************************
 * Template for MoviePlayers.
 * @author Shane Smith
 */

package sample;

public class MoviePlayer extends Product implements MultimediaControl {

  private Screen screen;
  private MonitorType monitorType;

  /***************************************************
   * Sets up constructor for a MoviePlayer object.
   * @author Shane Smith
   * @param name name of product object.
   * @param manufacturer manufacturer of product object.
   * @param screen screen of MoviePlayer object.
   * @param monitorType monitor type of MoviePlayer object.
   */

  MoviePlayer(String name, String manufacturer, Screen screen, MonitorType monitorType) {
    super(name, manufacturer, ItemType.VISUAL.code);
    this.screen = screen;
    this.monitorType = monitorType;
  }

  /***************************************************
   * Provides a console output that displays the MoviePlayer object.
   * @author Shane Smith
   * @return the output string to console
   */

  public String toString() {
    return super.toString() + "\n" + screen.toString() + "Monitor Type: " + monitorType;
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
