/***************************************************
 * Adds getters for the Screen Specs for MoviePlayers.
 * @Author: Shane Smith
 */

package sample;

public interface ScreenSpec {
  String getResolution();

  int getRefreshRate();

  int getResponseTime();
}
