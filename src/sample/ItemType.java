/***************************************************
 * Adds the different ItemTypes of products.
 * @Author: Shane Smith
 */

package sample;

public enum ItemType {

  AUDIO("AU"), VISUAL("VI"), AUDIO_MOBILE("AM"), VISUAL_MOBILE("VM");

  String code;

  /***************************************************
   * Constructor that makes the abbreviations accessible.
   * @Author: Shane Smith
   * @param c gives access to hte abbreviations.
   */

  ItemType(String c) {
    code = c;
  }

  /***************************************************
   * Getter of itemType Code.
   * @Author: Shane Smith
   * @return the abbreviation of the itemType.
   */

  public String getType() {
    return code;

  }
}