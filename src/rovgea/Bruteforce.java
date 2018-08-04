/*
 * Project: Pathfinder
 * Author: Austin Rovge
 * Date: 8/4/2018
 */

package rovgea;

import java.util.ArrayList;

public class Bruteforce implements Algorithm {

    private long time;

    @Override
    public void runPath(MapRectangle currentRectangle, boolean useDiagonalRectangles) {
        this.time = System.nanoTime();

        boolean done = false;

        ArrayList<MapRectangle> todo = new ArrayList<>();
        ArrayList<MapRectangle> toReset = new ArrayList<>();

        while (!done) {

            // 1. mark current rect as visited
            currentRectangle.markAsVisited();
            toReset.add(currentRectangle);

            // 2. Add all connected rectangles (which are not marked as visited) to a "to do" list

            MapRectangle topRect = currentRectangle.neighborRectangles.get(MapRectangle.neighbors.TOP);
            if (topRect != null && topRect.canVisit()) {
                todo.add(topRect);
            }

            MapRectangle leftRect = currentRectangle.neighborRectangles.get(MapRectangle.neighbors.LEFT);
            if (leftRect != null && leftRect.canVisit()) {
                todo.add(leftRect);
            }

            MapRectangle rightRect = currentRectangle.neighborRectangles.get(MapRectangle.neighbors.RIGHT);
            if (rightRect != null && rightRect.canVisit()) {
                todo.add(rightRect);
            }

            MapRectangle bottomRect = currentRectangle.neighborRectangles.get(MapRectangle.neighbors.BOTTOM);
            if (bottomRect != null && bottomRect.canVisit()) {
                todo.add(bottomRect);
            }

            if (useDiagonalRectangles) {

                MapRectangle topLeftRect = currentRectangle.neighborRectangles.get(MapRectangle.neighbors.TOPLEFT);
                if (topLeftRect != null && topLeftRect.canVisit()) {
                    todo.add(topLeftRect);
                }

                MapRectangle topRightRect = currentRectangle.neighborRectangles.get(MapRectangle.neighbors.TOPRIGHT);
                if (topRightRect != null && topRightRect.canVisit()) {
                    todo.add(topRightRect);
                }

                MapRectangle bottomLeftRect = currentRectangle.neighborRectangles.get(MapRectangle.neighbors.BOTTOMLEFT);
                if (bottomLeftRect != null && bottomLeftRect.canVisit()) {
                    todo.add(bottomLeftRect);
                }

                MapRectangle bottomRightRect = currentRectangle.neighborRectangles.get(MapRectangle.neighbors.BOTTOMRIGHT);
                if (bottomRightRect != null && bottomRightRect.canVisit()) {
                    todo.add(bottomRightRect);
                }
            }

            // 3. Set current vertex to be a vertex off the "to do" list
            MapRectangle newRect = todo.remove(0);


            // 4. If current vertex == destination, we're done! EXIT
            if (newRect.getState() == MapRectangle.states.END) {
                System.out.println("done!");
                done = true;
            } else {
                newRect.setState(MapRectangle.states.FAILED);
                currentRectangle = newRect;
            }

            // 5. Goto 1
        }

        // mark all triangles as unvisited

        for (int i = 0; i < toReset.size(); i++) {
            toReset.get(i).markAsUnvisited();
        }

        this.time = System.nanoTime() - this.time;
    }

    @Override
    public long getLastOperationTime() {
        return this.time;
    }
}
