/*
 * Project: Pathfinder
 * Author: Austin Rovge
 * Date: 8/3/2018
 */

package node;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is a Node. It extends the Rectangle object and introduces
 * several additional variables used in logic in the Pathfinder Controller class.
 */
public class Node extends Rectangle {

    private Types.PrimaryState primaryState;
    private Types.SecondaryState secondaryState;

    private Map<Types.Neighbor, Node> neighborsNodeMap;

    public static final double WIDTH = 30.0;
    public static final double HEIGHT = 30.0;

    /* Constant static Paint objects used for easily changing values throughout the program. */
    private final static Paint WALL_COLOR = Paint.valueOf("#B8B7B7");
    private final static Paint NORMAL_COLOR = Paint.valueOf("#E0E0E0");
    private final static Paint START_COLOR = Paint.valueOf("#25A2FF");
    private final static Paint END_COLOR = Paint.valueOf("#CC6666");
    private final static Paint PROCESSED_COLOR = Paint.valueOf("#BEAC52");
    private final static Paint NOT_PROCESSED_COLOR = Paint.valueOf("#81BE81");

    private boolean isVisited;

    /**
     * This is the only constructor for a Node. It calls super with
     * all parameters passed into the constructor. It also sets the node to be inactive.
     * @param x coordinate for the x position of the node object
     * @param y coordinate for the y position of the node object
     * @param width width of the node object
     * @param height height of the node object.
     */
    public Node(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.setState(Types.PrimaryState.NORMAL);
        this.neighborsNodeMap = new HashMap<>();

        this.isVisited = false;
    }

    public void markAsVisited() {
        this.isVisited = true;
    }

    public void markAsUnvisited() {
        this.isVisited = false;
    }

    public boolean canVisit() {
        return this.primaryState != Types.PrimaryState.WALL && this.secondaryState != Types.SecondaryState.PROCESSED && !this.isVisited;
    }

    public void setNeighbor(Types.Neighbor neighborType, Node neighbor) {
        this.neighborsNodeMap.put(neighborType, neighbor);
    }

    public Node getNeighbor(Types.Neighbor neighborType) {
        return this.neighborsNodeMap.get(neighborType);
    }

    /**
     * This method returns the primary state of the node.
     * @return Node states enum value
     */
    public Types.PrimaryState getPrimaryState() {
        return this.primaryState;
    }

    /**
     * This method returns the secondary state of the node.
     * @return Node states enum value
     */
    public Types.SecondaryState getSecondaryState() {
        return this.secondaryState;
    }

    /**
     * This method sets the state of the node.
     * It takes in an enum and changes the state and sets the color based on the state
     * @param destinationState MapRectangles states enum value
     */
    public void setState(Types.PrimaryState destinationState) {
        if (this.primaryState != destinationState) {
            this.primaryState = destinationState;
            this.setColor();
        }
    }

    public void setSecondaryState(Types.SecondaryState destinationState) {
        if (this.secondaryState != destinationState) {
            this.secondaryState = destinationState;
            this.setColor();
        }
    }

    /**
     * This method sets the color of the node depending on the state of the node.
     * The method is called from within the class and is dependent on the currently set state.
     */
    private void setColor() {
        if (this.secondaryState != null) {
            switch(this.secondaryState) {
                case NOT_PROCESSED:
                    if (this.primaryState != Types.PrimaryState.END) {
                        this.setFill(Node.NOT_PROCESSED_COLOR);
                    }
                    break;
                case PROCESSED:
                    this.setFill(Node.PROCESSED_COLOR);
                    break;
            }
        } else {
            switch(this.primaryState) {
                case NORMAL:
                    this.setFill(Node.NORMAL_COLOR);
                    this.setStroke(Node.NORMAL_COLOR);
                    break;
                case WALL:
                    this.setFill(Node.WALL_COLOR);
                    break;
                case HOVER_WALL:
                    this.setStroke(Node.WALL_COLOR);
                    break;
                case START:
                    this.setFill(Node.START_COLOR);
                    break;
                case HOVER_START:
                    this.setStroke(Node.START_COLOR);
                    break;
                case END:
                    this.setFill(Node.END_COLOR);
                    break;
                case HOVER_END:
                    this.setStroke(Node.END_COLOR);
                    break;
            }
        }
    }
}
