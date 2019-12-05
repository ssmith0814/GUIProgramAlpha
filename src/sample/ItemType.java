/***************************************************
 * Adds the different ItemTypes of products.
 * @author Shane Smith
 */

package sample;

public enum ItemType {

  AUDIO("AU"), VISUAL("VI"), AUDIO_MOBILE("AM"), VISUAL_MOBILE("VM");

  String code;

  /***************************************************
   * Constructor that makes the abbreviations accessible.
   * @author Shane Smith
   * @param code gives access to hte abbreviations.
   */

  ItemType(String code) {
    this.code = code;
  }

  /***************************************************
   * Getter of itemType Code.
   * @author Shane Smith
   * @return the abbreviation of the itemType.
   */

  public String getType() {
    return code;

  }
}