/*
 * Project: Pathfinder
 * Author: Austin Rovge
 * Date: 8/4/2018
 */

package rovgea;

/**
 * This is an interface for an algorithm object.
 * It has two methods, one for pathfinding and another for returning the operation time.
 */
public interface Algorithm {
    void runPath(MapRectangle currentRectangle, boolean useDiagonalRectangles);
    long getLastOperationTime();
}
