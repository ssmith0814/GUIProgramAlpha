/***************************************************
 * Gives template for product and Widget to display the product.
 * @Author: Shane Smith
 */

package sample;

public abstract class Product implements Item {

  private String Type;
  private String Manufacturer;
  private String Name;

  /***************************************************
   * Creates constructor for making a Product object.
   * @Author: Shane Smith
   * @param name name of the Product.
   * @param manufacturer manufacturer of the Product.
   * @param type type of the Product.
   */

  Product(String name, String manufacturer, String type) {
    Name = name;
    Manufacturer = manufacturer;
    Type = type;
  }

  /***************************************************
   * Provides an output to console of the Product object info.
   * @Author: Shane Smith
   * @return the output of Product object into console.
   */

  public String toString() {
    return "Name: " + Name + "\n" + "Manufacturer: " + Manufacturer + "\n" + "Type: "
        + Type;
  }

  /***************************************************
   * Getter of product Type.
   * @Author: Shane Smith
   * @return type of Product.
   */

  public String getType() {
    return Type;
  }

  /***************************************************
   * Setter of product Type.
   * @Author: Shane Smith
   * @param type sets the type of Product.
   */

  public void setType(String type) {
    Type = type;
  }

  /***************************************************
   * Getter of product Manufacturer.
   * @Author: Shane Smith
   * @return manufacturer of Product
   */

  public String getManufacturer() {
    return Manufacturer;
  }

  /***************************************************
   * Setter of product Manufacturer.
   * @Author: Shane Smith
   * @param manufacturer sets the manufacturer of Product.
   */

  public void setManufacturer(String manufacturer) {
    Manufacturer = manufacturer;
  }

  /***************************************************
   * Getter of product Name.
   * @Author: Shane Smith
   * @return name of Product.
   */

  public String getName() {
    return Name;
  }

  /***************************************************
   * Setter of product Name.
   * @Author: Shane Smith
   * @param name sets the name of Product.
   */

  public void setName(String name) {
    Name = name;
  }
}

/***************************************************
 * Extends Product to create a Product object.
 * @Author: Shane Smith
 */

class Widget extends Product {

  /***************************************************
   * Creates a product object through call to super.
   * @Author: Shane Smith
   * @param name name of Product.
   * @param manufacturer Manufacturer of Product.
   * @param type type of Product.
   */

  Widget(String name, String manufacturer, String type) {
    super(name, manufacturer, type);
  }
}