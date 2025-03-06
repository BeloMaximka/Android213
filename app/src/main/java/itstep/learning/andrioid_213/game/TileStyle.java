package itstep.learning.andrioid_213.game;

public class TileStyle {
    private final int backgroundColor;
    private final int textColor;
    private final float textSize;
    private final String text;

    public TileStyle(int backgroundColor, int textColor, float textSize, String text) {
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.textSize = textSize;
        this.text = text;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getTextColor() {
        return textColor;
    }


    public float getTextSize() {
        return textSize;
    }

    public String getText() {
        return text;
    }
}
