/***************************************************
 * Gets implemented by Product class.
 * @author Shane Smith
 */
package sample;

interface Item {

  /***************************************************
   * Setter of name.
   * @author Shane Smith
   * @param name name of product from implementation.
   */
  void setName(String name);

  /***************************************************
   * Getter of name.
   * @author Shane Smith
   */
  String getName();

  /***************************************************
   * Setter of manufacturer.
   * @author Shane Smith
   * @param manufacturer manufacturer of product from implementation.
   */
  void setManufacturer(String manufacturer);

  /***************************************************
   * Getter of manufacturer.
   * @author Shane Smith
   */
  static String getManufacturer() {
    return null;
  }

}