package sample;

import java.util.Date;

public class ProductionRecord {
    private int productionNumber;
    private int productID;
    private String serialNumber;
    private Date dateProduced;
    private String firstThree;

    ProductionRecord(int productionNumber, int productID, String serialNumber, Date dateProduced){
        this.productionNumber = productionNumber;
        this.productID = productID;
        this.serialNumber = serialNumber;
        this.dateProduced = dateProduced;
    }

    public ProductionRecord(int i) {
        this.productionNumber = i;
        serialNumber = "0";
        dateProduced = new Date();
        productionNumber = 0;
    }



    public ProductionRecord(Product productCount, int numProduced) {
        dateProduced = new Date();
        String newNumProduced = String.format("%05d", numProduced);
        if (Product.getManufacturer().length() > 3){
            firstThree = Product.getManufacturer().substring(0,3);
        }
        else{
            firstThree = Product.getManufacturer();
        }
        serialNumber = firstThree + Product.getType() + newNumProduced;
    }

    @Override
    public String toString(){
        return "Prod. Num: " + productionNumber + " Product ID: " + productID + " Serial Num: " + serialNumber + " Date: " + dateProduced;
    }

    public int getProductionNumber() {
        return productionNumber;
    }

    public void setProductionNumber(int productionNumber) {
        this.productionNumber = productionNumber;

    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;

    }

    public Date getDateProduced() {
        return dateProduced;
    }

    public void setDateProduced(Date dateProduced) {
        this.dateProduced = dateProduced;

    }


}
