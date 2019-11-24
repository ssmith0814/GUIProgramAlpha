package sample;

public interface Item {

//    int getId();
    void setName(String name);
    String getName();
    void setManufacturer(String manufacturer);

    static String getManufacturer() {
        return null;
    }

}