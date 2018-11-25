/*
 * Project: Pathfinder
 * Author: Austin Rovge
 * Date: 8/4/2018
 */

package algorithms;

import node.Node;

/**
 * This is an interface for an algorithms object.
 * It has two methods, one for path finding and another for returning the operation time.
 */
public interface Algorithm {
    void runPath(Node startNode, Node endNode, boolean useDiagonalNodes);
    long getLastOperationTime();
}
