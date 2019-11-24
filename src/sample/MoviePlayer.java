package sample;

public class MoviePlayer extends Product implements MultimediaControl {

    private Screen screen;
    private MonitorType monitorType;

    MoviePlayer(String name, String manufacturer, Screen screen, MonitorType monitorType){
        super(name,manufacturer,ItemType.VISUAL.toString());
        this.screen = screen;
        this.monitorType = monitorType;
    }

    public String toString(){
        return super.toString() + "\n" + screen.toString() + "Monitor Type: " + monitorType;
    }

    @Override
    public void play() {
        System.out.println("Playing movie");
    }

    @Override
    public void stop() {
        System.out.println("Stopping movie");
    }

    @Override
    public void previous() {
        System.out.println("Previous movie");
    }

    @Override
    public void next() {
        System.out.println("Next movie");
    }
}
