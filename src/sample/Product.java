package sample;

public abstract class Product implements Item {


    private static String Type;
    private static String Manufacturer;
    private static String Name;



    Product(String name, String manufacturer, String type) {
        Name = name;
        Manufacturer = manufacturer;
        Type = type;
    }

    public String toString() {
        return "Name: " + Name + "\n" + "Manufacturer: " + Manufacturer + "\n" + "Type: "
                + Type;
    }

    public static String getType() {
        return Type;
    }

    public static void setType(String type) {
        Type = type;
    }

    static String getManufacturer() {
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