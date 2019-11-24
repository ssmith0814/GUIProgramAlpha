package sample;

public class Screen {

    private String resolution;
    private int refreshrate;
    private int responsetime;

    Screen (String resolution, int refreshrate, int responsetime){
        this.resolution = resolution;
        this.refreshrate = refreshrate;
        this. responsetime = responsetime;
    }

    public String toString(){
        return "Screen:" + "\n" + "Resolution: " + resolution + "\n" + "Refresh rate: " + refreshrate + "\n" + "Response time: " + responsetime;
    }

}
