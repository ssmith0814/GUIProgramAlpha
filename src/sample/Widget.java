package sample;

/***************************************************
 * Extends Product to create a Product object.
 * @author Shane Smith
 */

class Widget extends Product {

  /***************************************************
   * Creates a product object through call to super.
   * @author Shane Smith
   * @param name name of Product.
   * @param manufacturer Manufacturer of Product.
   * @param type type of Product.
   */

  Widget(String name, String manufacturer, String type) {
    super(name, manufacturer, type);
  }
}