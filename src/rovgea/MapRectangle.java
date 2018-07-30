package rovgea;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * This class is a MapRectangle. It extends the Rectangle object and introduces
 * several additional variables used in logic in the Pathfinder Controller class.
 */
public class MapRectangle extends Rectangle {

    /** Constant static Paint objects used for easily changing values throughout the program. */
    public final static Paint activeColor = Paint.valueOf("#B8B7B7");
    public final static Paint inActiveColor = Paint.valueOf("#E0E0E0");
    public final static Paint startColor = Paint.valueOf("#25A2FF");
    public final static Paint endColor = Paint.valueOf("#FF2525");

    public final static Paint failedPath = Paint.valueOf("#CC6666");
    public final static Paint winPath = Paint.valueOf("81A2BE");

    /** This boolean stores the state of the rectangle. */
    private boolean state;

    /** This boolean is used for knowing if the rectangle is a start or end rectangle. */
    private boolean isStartOrEnd;

    private boolean isVisited;

    public int arrayX;
    public int arrayY;

    /**
     * This is the only constructor for a MapRectangle. It calls super with
     * all parameters passed into the constructor. It also sets the rectangle to be inactive.
     * @param x coordinate for the x position of the rectangle object
     * @param y coordinate for the y position of the rectangle object
     * @param width width of the rectangle object
     * @param height height of the rectangle object.
     */
    public MapRectangle(double x, double y, double width, double height, int arX, int arY) {
        super(x, y, width, height);
        this.state = false;
        this.isStartOrEnd = false;
        this.setFill(MapRectangle.inActiveColor);

        this.arrayX = arX;
        this.arrayY = arY;
        this.isVisited = false;
    }

    public void markAsVisited() {
        this.isVisited = true;
    }

    public void markAsUnvisited() {
        this.isVisited = false;
    }

    public boolean isVisited() {
        return this.isVisited;
    }

    /**
     * This method sets the rectangle to be the start rectangle.
     */
    public void setStart() {
        this.isStartOrEnd = true;
        this.setFill(MapRectangle.startColor);
    }

    /**
     * This method sets the rectangle to be the end rectangle.
     */
    public void setEnd() {
        this.isStartOrEnd = true;
        this.setFill(MapRectangle.endColor);
    }

    /**
     * This method returns the state of the rectangle.
     * @return boolean of the state
     */
    public boolean getState() {
        return this.state;
    }

    /**
     * This method sets the state and color to be inactive.
     */
    public void removeStartOrEnd() {
        this.isStartOrEnd = false;
        this.state = false;
        this.setColor();
    }

    /**
     * This method is used for seeing if the rectangle is the start or end rectangle.
     * @return boolean depending on if it is start or end.
     */
    public boolean isStartOrEnd() {
        return this.isStartOrEnd;
    }

    /**
     * This method toggles the active status of the rectangle.
     * @param bool the boolean to set the state to.
     */
    public void setState(boolean bool) {
        if (this.state != bool) {
            this.state = !this.state;
            this.setColor();
        }
    }

    /**
     * This method sets the color of the rectangle depending on the state of the rectangle.
     */
    private void setColor() {
        if (this.state) {
            this.setFill(MapRectangle.activeColor);
        } else {
            this.setFill(MapRectangle.inActiveColor);
        }
    }
}
