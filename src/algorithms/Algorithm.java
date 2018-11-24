/*
 * Project: Pathfinder
 * Author: Austin Rovge
 * Date: 8/4/2018
 */

package algorithms;

import gui.MapRectangle;

/**
 * This is an interface for an algorithms object.
 * It has two methods, one for path finding and another for returning the operation time.
 */
public interface Algorithm {
    void runPath(MapRectangle currentRectangle, boolean useDiagonalRectangles);
    long getLastOperationTime();
}
