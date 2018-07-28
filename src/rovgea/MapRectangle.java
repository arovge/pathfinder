package rovgea;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class MapRectangle extends Rectangle {

    private boolean isActive;

    public MapRectangle(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.isActive = false;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void toggleActive() {
        this.isActive = !this.isActive;
        this.setFill(Paint.valueOf("#b8b7b7"));
    }
}
