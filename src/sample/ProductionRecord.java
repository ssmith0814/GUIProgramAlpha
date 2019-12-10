/***************************************************
 * Displays the Production Record and formats the serial number.
 * @author Shane Smith
 */
package sample;

import java.util.Date;

class ProductionRecord {
  private int productionNumber;
  private int productID;
  private String serialNumber;
  private Date dateProduced;

  /***************************************************
   * Constructor for ProductionRecord.
   * @author Shane Smith
   * @param productionNumber production number of selected product.
   * @param productID product ID of selected product.
   * @param serialNumber serial number of selected product.
   * @param dateProduced timestamp of recording the product creation.
   */
  ProductionRecord(int productionNumber, int productID, String serialNumber, Date dateProduced) {
    this.productionNumber = productionNumber;
    this.productID = productID;
    this.serialNumber = serialNumber;
    this.dateProduced = dateProduced;
  }

  /***************************************************
   * Constructor for ProductionRecord.
   * @author Shane Smith
   * @param i production number.
   */
  public ProductionRecord(int i) {
    this.productionNumber = i;
    serialNumber = "0";
    dateProduced = new Date();
    productionNumber = 0;
  }

  /***************************************************
   * Constructor for ProductionRecord.
   * @author Shane Smith
   * @param prod this is the object Product.
   * @param numProduced This is the number of products being recorded.
   */
  ProductionRecord(Product prod, int numProduced) {
    dateProduced = new Date();
    String newNumProduced = String.format("%05d", numProduced);
    String firstThree;
    String prodType = String.valueOf(ItemType.valueOf(prod.getType()).code);
    if (prod.getManufacturer().length() > 3) {
      firstThree = prod.getManufacturer().substring(0, 3);
    } else {
      firstThree = prod.getManufacturer();
    }
    serialNumber = firstThree + prodType + newNumProduced;
  }

  /***************************************************
   * Provides a console output that displays the ProductionRecord object.
   * @author Shane Smith
   * @return the output string to console.
   */
  @Override
  public String toString() {
    return "Prod. Num: " + productionNumber + " Product ID: "
        + productID + " Serial Num: " + serialNumber + " Date: " + dateProduced;
  }

  /***************************************************
   * Getter of Production Number.
   * @author Shane Smith
   * @return production number of recorded product.
   */
  public int getProductionNumber() {
    return productionNumber;
  }

  /***************************************************
   * Setter of Production Number.
   * @author Shane Smith
   * @param productionNumber production number of recorded product.
   */
  public void setProductionNumber(int productionNumber) {
    this.productionNumber = productionNumber;

  }

  /***************************************************
   * Getter of product ID.
   * @author Shane Smith
   * @return the product's ID.
   */
  int getProductID() {
    return productID;
  }

  /***************************************************
   * Setter of the product's ID.
   * @author Shane Smith
   * @param productID product ID of recorded product.
   */
  public void setProductID(int productID) {
    this.productID = productID;
  }

  /***************************************************
   * Getter of product's Serial Number.
   * @author Shane Smith
   * @return the product's Serial Number.
   */
  String getSerialNumber() {
    return serialNumber;
  }

  /***************************************************
   * Setter of product's Serial Number.
   * @author Shane Smith
   * @param serialNumber serial number of recorded product.
   */
  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  /***************************************************
   * Getter of date produced.
   * @author Shane Smith
   * @return the timestamp that the production was recorded.
   */
  Date getDateProduced() {
    return dateProduced;
  }

}
