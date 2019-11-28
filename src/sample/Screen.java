/***************************************************
 * Called by MoviePlayer to provide screen info.
 * @author Shane Smith
 */

package sample;

class Screen {

  private String resolution;
  private int refreshrate;
  private int responsetime;

  /***************************************************
   * Constructor for a screen object.
   * @author Shane Smith
   */

  Screen(String resolution, int refreshrate, int responsetime) {
    this.resolution = resolution;
    this.refreshrate = refreshrate;
    this.responsetime = responsetime;
  }

  /***************************************************
   * Provides a console output that displays the Screen object.
   * @author Shane Smith
   * @return the output string to console.
   */

  public String toString() {
    return "Screen:" + "\n" + "Resolution: " + resolution + "\n" + "Refresh rate: "
        + refreshrate + "\n" + "Response time: " + responsetime;
  }

}
