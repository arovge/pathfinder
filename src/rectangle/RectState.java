/*
 * Project: Pathfinder
 * Author: Austin Rovge
 * Date: 11/24/2018
 */

package rectangle;

/**
 * This is an enum that contains all of the possible states for the MapRectangle.
 */
public enum RectState {
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