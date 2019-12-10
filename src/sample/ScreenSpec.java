/***************************************************
 * Adds getters for the Screen Specs for MoviePlayers.
 * @author Shane Smith
 */
package sample;

public interface ScreenSpec {
  String getResolution();

  int getRefreshRate();

  int getResponseTime();
}
