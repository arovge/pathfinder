package rovgea;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * This class is a MapRectangle. It extends the Rectangle object and introduces
 * several additional variables used in logic in the Pathfinder Controller class.
 */
public class MapRectangle extends Rectangle {

    public enum MapRectangleState {
        BASE,
        WALL,
        START,
        END,
        SUCCESS,
        FAILED
    }

    private MapRectangleState state;

    /** Constant static Paint objects used for easily changing values throughout the program. */
    protected final static Paint wallColor = Paint.valueOf("#B8B7B7");
    protected final static Paint baseColor = Paint.valueOf("#E0E0E0");
    protected final static Paint startColor = Paint.valueOf("#25A2FF");
    protected final static Paint endColor = Paint.valueOf("#FF2525");

    protected final static Paint failedPath = Paint.valueOf("#CC6666");
    protected final static Paint successPath = Paint.valueOf("81A2BE");

    private MapRectangle topRect;
    private MapRectangle bottomRect;
    private MapRectangle leftRect;
    private MapRectangle rightRect;
    private MapRectangle topLeftRect;
    private MapRectangle topRightRect;
    private MapRectangle bottomLeftRect;
    private MapRectangle bottomRightRect;

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
        this.state = MapRectangleState.BASE;
        this.isStartOrEnd = false;
        this.setColor();

        this.arrayX = arX;
        this.arrayY = arY;
        this.isVisited = false;

        this.topRect = null;
        this.bottomRect = null;
        this.leftRect = null;
        this.rightRect = null;
        this.topLeftRect = null;
        this.topRightRect = null;
        this.bottomLeftRect = null;
        this.bottomRightRect = null;
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
     * This method returns the state of the rectangle.
     * @return boolean of the state
     */
    public MapRectangleState getState() {
        return this.state;
    }

    public void setState(MapRectangleState state) {
        if (this.state != state) {
            this.state = state;
            this.setColor();
        }
    }

    /**
     * This method sets the color of the rectangle depending on the state of the rectangle.
     */
    private void setColor() {
        if (this.state == MapRectangleState.BASE) {
            this.setFill(MapRectangle.baseColor);
        } else if (this.state == MapRectangleState.WALL) {
            this.setFill(MapRectangle.wallColor);
        } else if (this.state == MapRectangleState.START) {
            this.setFill(MapRectangle.startColor);
        } else if (this.state == MapRectangleState.END) {
            this.setFill(MapRectangle.endColor);
        } else if (this.state == MapRectangleState.SUCCESS) {
            this.setFill(MapRectangle.successPath);
        } else if (this.state == MapRectangleState.FAILED) {
            this.setFill(MapRectangle.failedPath);
        }
    }
}
