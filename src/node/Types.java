/*
 * Project: Pathfinder
 * Author: Austin Rovge
 * Date: 11/24/2018
 */

package node;

/**
 * This types class holds many enums together for organization.
 */
public class Types {

    /**
     * This is an enum that contains all of the possible primary states for the Node.
     */
    public enum PrimaryState {
        NORMAL,
        HOVER_WALL,
        WALL,
        START,
        HOVER_START,
        END,
        HOVER_END
    }

    /**
     * This is an enum that contains all of the possible secondary states for the Node.
     */
    public enum SecondaryState {
        NOT_PROCESSED,
        PROCESSED
    }

    /**
     * This enum stores the different types of neighbors a node can have.
     */
    public enum Neighbor {
        TOP,
        LEFT,
        RIGHT,
        BOTTOM,
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT
    }
}
