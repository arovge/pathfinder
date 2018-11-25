/*
 * Project: Pathfinder
 * Author: Austin Rovge
 * Date: 11/24/2018
 */

package node;

/**
 * This is an enum that contains all of the possible states for the Node.
 */
public enum State {
    NOT_PROCESSED,
    PROCESSED,
    NORMAL,
    HOVER_WALL,
    WALL,
    START,
    HOVER_START,
    END,
    HOVER_END
}
