/***************************************************
 * Gives template for product and Widget to display the product.
 * @author Shane Smith
 */
package sample;

public abstract class Product implements Item {

  private String type;
  private String manufacturer;
  private String name;

  /***************************************************
   * Creates constructor for making a Product object.
   * @author Shane Smith
   * @param name name of the Product.
   * @param manufacturer manufacturer of the Product.
   * @param type type of the Product.
   */
  Product(String name, String manufacturer, String type) {
    this.name = name;
    this.manufacturer = manufacturer;
    this.type = type;
  }

  /***************************************************
   * Provides an output to console of the Product object info.
   * @author Shane Smith
   * @return the output of Product object into console.
   */
  public String toString() {
    return "Name: " + name + "\n" + "Manufacturer: " + manufacturer + "\n" + "Type: "
        + type;
  }

  /***************************************************
   * Getter of product Type.
   * @author Shane Smith
   * @return type of Product.
   */
  public String getType() {
    return type;
  }

  /***************************************************
   * Setter of product Type.
   * @author Shane Smith
   * @param type sets the type of Product.
   */
  public void setType(String type) {
    this.type = type;
  }

  /***************************************************
   * Getter of product Manufacturer.
   * @author Shane Smith
   * @return manufacturer of Product
   */
  public String getManufacturer() {
    return manufacturer;
  }

  /***************************************************
   * Setter of product Manufacturer.
   * @author Shane Smith
   * @param manufacturer sets the manufacturer of Product.
   */
  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  /***************************************************
   * Getter of product Name.
   * @author Shane Smith
   * @return name of Product.
   */
  public String getName() {
    return name;
  }

  /***************************************************
   * Setter of product Name.
   * @author Shane Smith
   * @param name sets the name of Product.
   */
  public void setName(String name) {
    this.name = name;
  }
}