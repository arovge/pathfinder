package rovgea;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class MapRectangle extends Rectangle {

    public final static Paint activeColor = Paint.valueOf("#B8B7B7");
    public final static Paint inActiveColor = Paint.valueOf("#E0E0E0");
    public final static Paint startColor = Paint.valueOf("#25A2FF");
    public final static Paint endColor = Paint.valueOf("#FF2525");

    private boolean isActive;
    private boolean isStartOrEnd;

    public MapRectangle(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.isActive = false;
        this.isStartOrEnd = false;
        this.setFill(MapRectangle.inActiveColor);
    }

    public void setStart() {
        this.isStartOrEnd = true;
        this.setFill(MapRectangle.startColor);
    }

    public void setEnd() {
        this.isStartOrEnd = true;
        this.setFill(MapRectangle.endColor);
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void removeStartOrEnd() {
        this.isStartOrEnd = false;
        this.isActive = false;
        this.setColor();
    }

    public boolean isStartOrEnd() {
        return this.isStartOrEnd;
    }

    public void toggleActive(boolean bool) {
        if (this.isActive != bool) {
            this.isActive = !this.isActive;
            this.setColor();
        }
    }

    private void setColor() {
        if (this.isActive) {
            this.setFill(MapRectangle.activeColor);
        } else {
            this.setFill(MapRectangle.inActiveColor);
        }
    }
}
