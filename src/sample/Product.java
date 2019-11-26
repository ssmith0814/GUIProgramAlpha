/***************************************************
 * @Author: Shane Smith
 * @Description: Gives template for product and Widget to display the product
 ***************************************************/

package sample;

public abstract class Product implements Item {


    private String Type;
    private String Manufacturer;
    private String Name;

    Product(String name, String manufacturer, String type) {
        Name = name;
        Manufacturer = manufacturer;
        Type = type;
    }

    public String toString() {
        return "Name: " + Name + "\n" + "Manufacturer: " + Manufacturer + "\n" + "Type: "
                + Type;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        Manufacturer = manufacturer;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}

class Widget extends Product {

    Widget(String name, String manufacturer, String type) {
        super(name, manufacturer, type);
    }

}