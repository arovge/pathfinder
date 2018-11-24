/*
 * Project: Pathfinder
 * Author: Austin Rovge
 * Date: 8/3/2018
 */

package gui;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is a MapRectangle. It extends the Rectangle object and introduces
 * several additional variables used in logic in the Pathfinder Controller class.
 */
public class MapRectangle extends Rectangle {

    /**
     * This is an enum that contains all of the possible states for the MapRectangle.
     */
    public enum states {
        BASE,
        WALL,
        START,
        END
    }

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

    private MapRectangle.states state;
    public Map<MapRectangle.neighbors, MapRectangle> neighborRectangles;

    public static final double width = 30.0;
    public static final double height = 30.0;

    /** Constant static Paint objects used for easily changing values throughout the program. */
    public final static Paint wallColor = Paint.valueOf("#B8B7B7");
    public final static Paint baseColor = Paint.valueOf("#E0E0E0");
    public final static Paint startColor = Paint.valueOf("#25A2FF");
    public final static Paint endColor = Paint.valueOf("#FF2525");

    public final static Paint processedPath = Paint.valueOf("#CC6666");
    public final static Paint notProcessedPath = Paint.valueOf("81A2BE");

    private boolean isVisited;

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
        this.setState(states.BASE);

        this.isVisited = false;

        this.neighborRectangles = new HashMap<>();
    }

    public void markAsVisited() {
        this.isVisited = true;
    }

    public void markAsUnvisited() {
        this.isVisited = false;
    }

    public boolean canVisit() {
        return this.state != MapRectangle.states.WALL && !this.isVisited;
    }

    /**
     * This method returns the state of the rectangle.
     * @return MapRectangle states enum value
     */
    public MapRectangle.states getState() {
        return this.state;
    }

    /**
     * This method sets the state of the rectangle.
     * It takes in an enum and changes the state and sets the color based on the state
     * @param state MapRectangles states enum value
     */
    public void setState(MapRectangle.states state) {
        if (this.state != state) {
            this.state = state;
            this.setColor();
        }
    }

    /**
     * This method sets the color of the rectangle depending on the state of the rectangle.
     * The method is called from within the class and is dependent on the currently set state.
     */
    private void setColor() {
        if (this.state == MapRectangle.states.BASE) {
            this.setFill(MapRectangle.baseColor);
        } else if (this.state == MapRectangle.states.WALL) {
            this.setFill(MapRectangle.wallColor);
        } else if (this.state == MapRectangle.states.START) {
            this.setFill(MapRectangle.startColor);
        } else if (this.state == MapRectangle.states.END) {
            this.setFill(MapRectangle.endColor);
        }
    }
}
