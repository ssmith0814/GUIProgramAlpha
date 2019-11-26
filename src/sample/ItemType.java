package sample;

public enum ItemType {

    AUDIO("AU"), VISUAL("VI"), AUDIO_MOBILE("AM"), VISUAL_MOBILE("VM");


    String code;

    ItemType(String c) {
        code = c;
    }

    public String getType() {
        return code;

    }
}