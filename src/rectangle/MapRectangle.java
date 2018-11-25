/*
 * Project: Pathfinder
 * Author: Austin Rovge
 * Date: 8/3/2018
 */

package rectangle;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * This class is a MapRectangle. It extends the Rectangle object and introduces
 * several additional variables used in logic in the Pathfinder Controller class.
 */
public class MapRectangle extends Rectangle {

    /**
     * This is an enum that contains different neighbors the rectangle could have.
     */
    public enum neighbors {
        TOP,
        LEFT,
        RIGHT,
        BOTTOM,
        TOPLEFT,
        TOPRIGHT,
        BOTTOMLEFT,
        BOTTOMRIGHT
    }

    private RectState state;

    public MapRectangle top;
    public MapRectangle right;
    public MapRectangle left;
    public MapRectangle bottom;
    public MapRectangle topleft;
    public MapRectangle topright;
    public MapRectangle bottomleft;
    public MapRectangle bottomright;

    public static final double WIDTH = 30.0;
    public static final double HEIGHT = 30.0;

    /** Constant static Paint objects used for easily changing values throughout the program. */
    private final static Paint WALL_COLOR = Paint.valueOf("#B8B7B7");
    private final static Paint NORMAL_COLOR = Paint.valueOf("#E0E0E0");
    private final static Paint START_COLOR = Paint.valueOf("#25A2FF");
    private final static Paint END_COLOR = Paint.valueOf("#FF2525");
    private final static Paint PROCESSED_COLOR = Paint.valueOf("#CC6666");
    private final static Paint NOT_PROCESSED_COLOR = Paint.valueOf("81A2BE");

    private boolean isVisited;

    private int x;
    private int y;

    /**
     * This is the only constructor for a MapRectangle. It calls super with
     * all parameters passed into the constructor. It also sets the rectangle to be inactive.
     * @param x coordinate for the x position of the rectangle object
     * @param y coordinate for the y position of the rectangle object
     * @param width width of the rectangle object
     * @param height height of the rectangle object.
     */
    public MapRectangle(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.setState(RectState.NORMAL);

        this.isVisited = false;

        this.x = (int) (x / MapRectangle.WIDTH);
        this.y = (int) (y / MapRectangle.HEIGHT);

    }

    public void markAsVisited() {
        this.isVisited = true;
    }

    public void markAsUnvisited() {
        this.isVisited = false;
    }

    public boolean canVisit() {
        return this.state != RectState.WALL && !this.isVisited;
    }

    /**
     * This method returns the state of the rectangle.
     * @return MapRectangle states enum value
     */
    public RectState getState() {
        return this.state;
    }

    /**
     * This method sets the state of the rectangle.
     * It takes in an enum and changes the state and sets the color based on the state
     * @param destinationState MapRectangles states enum value
     */
    public void setState(RectState destinationState) {
        if (this.state != destinationState) {
            this.state = destinationState;
            this.setColor();
        }
    }

    /**
     * This method sets the color of the rectangle depending on the state of the rectangle.
     * The method is called from within the class and is dependent on the currently set state.
     */
    private void setColor() {
        switch(this.state) {
            case NOT_PROCESSED:
                this.setFill(MapRectangle.NOT_PROCESSED_COLOR);
                break;
            case PROCESSED:
                this.setFill(MapRectangle.PROCESSED_COLOR);
                break;
            case NORMAL:
                this.setFill(MapRectangle.NORMAL_COLOR);
                this.setStroke(MapRectangle.NORMAL_COLOR);
                break;
            case WALL:
                this.setFill(MapRectangle.WALL_COLOR);
                break;
            case HOVER_WALL:
                this.setStroke(MapRectangle.WALL_COLOR);
                break;
            case START:
                this.setFill(MapRectangle.START_COLOR);
                break;
            case HOVER_START:
                this.setStroke(MapRectangle.START_COLOR);
                break;
            case END:
                this.setFill(MapRectangle.END_COLOR);
                break;
            case HOVER_END:
                this.setStroke(MapRectangle.END_COLOR);
                break;
        }
    }

    public int getXCoordinate() {
        return this.x;
    }

    public int getYCoordinate() {
        return this.y;
    }
}
