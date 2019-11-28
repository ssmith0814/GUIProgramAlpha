/***************************************************
 * Gets implemented by Product class.
 * @Author: Shane Smith
 */

package sample;

interface Item {

  /***************************************************
   * Setter of name.
   * @Author: Shane Smith
   * @param name name of product from implementation.
   */

  void setName(String name);

  /***************************************************
   * Getter of name.
   * @Author: Shane Smith
   */

  String getName();

  /***************************************************
   * Setter of manufacturer.
   * @Author: Shane Smith
   * @param manufacturer manufacturer of product from implementation.
   */

  void setManufacturer(String manufacturer);

  /***************************************************
   * Getter of manufacturer.
   * @Author: Shane Smith
   */

  static String getManufacturer() {
    return null;
  }

}