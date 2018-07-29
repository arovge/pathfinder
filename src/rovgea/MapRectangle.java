package rovgea;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class MapRectangle extends Rectangle {

    public final static Paint activeColor = Paint.valueOf("#b8b7b7");
    public final static Paint inActiveColor = Paint.valueOf("#e0e0e0");

    private boolean isActive;

    public MapRectangle(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.isActive = false;
        this.setFill(MapRectangle.inActiveColor);
    }

    public boolean isActive() {
        return this.isActive;
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
