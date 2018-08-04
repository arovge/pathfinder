/*
 * Project: Pathfinder
 * Author: Austin Rovge
 * Date: 8/4/2018
 */

package rovgea;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.ArrayList;

/**
 * This class is for a brute force path finding algorithm.
 * It implements the methods in the algorithm interface.
 */
public class Bruteforce implements Algorithm {

    private long time;

    private MapRectangle currentRectangle;
    private boolean useDiagonalRectangles;

    private ArrayList<MapRectangle> processQueue = new ArrayList<>();
    private ArrayList<MapRectangle> processed = new ArrayList<>();

    private Timeline timeline;


    @Override
    public void runPath(MapRectangle currentRectangle, boolean useDiagonalRectangles) {
        // start timing the process
        this.time = System.nanoTime();

        this.currentRectangle = currentRectangle;
        this.useDiagonalRectangles = useDiagonalRectangles;

        // animation code
        KeyFrame keyFrame = new KeyFrame(Duration.millis(100), e -> {
            this.runMap();
        });

        this.timeline = new Timeline(keyFrame);

        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();


        // mark all triangles as unvisited
        for (int i = 0; i < this.processed.size(); i++) {
            this.processed.get(i).markAsUnvisited();
        }

        // calculate the total time it took
        this.time = System.nanoTime() - this.time;
    }

    private void runMap() {
        // 1. mark current rect as visited
        this.currentRectangle.markAsVisited();
        this.processed.add(this.currentRectangle);

        // 2. Add all connected rectangles (which are not marked as visited) to a "to do" list

        MapRectangle topRect = this.currentRectangle.neighborRectangles.get(MapRectangle.neighbors.TOP);
        if (topRect != null && topRect.canVisit()) {
            this.processQueue.add(topRect);
            topRect.setFill(MapRectangle.notProcessedPath);
        }

        MapRectangle leftRect = this.currentRectangle.neighborRectangles.get(MapRectangle.neighbors.LEFT);
        if (leftRect != null && leftRect.canVisit()) {
            this.processQueue.add(leftRect);
            leftRect.setFill(MapRectangle.notProcessedPath);
        }

        MapRectangle rightRect = this.currentRectangle.neighborRectangles.get(MapRectangle.neighbors.RIGHT);
        if (rightRect != null && rightRect.canVisit()) {
            this.processQueue.add(rightRect);
            rightRect.setFill(MapRectangle.notProcessedPath);
        }

        MapRectangle bottomRect = this.currentRectangle.neighborRectangles.get(MapRectangle.neighbors.BOTTOM);
        if (bottomRect != null && bottomRect.canVisit()) {
            this.processQueue.add(bottomRect);
            bottomRect.setFill(MapRectangle.notProcessedPath);
        }

        if (this.useDiagonalRectangles) {

            MapRectangle topLeftRect = this.currentRectangle.neighborRectangles.get(MapRectangle.neighbors.TOPLEFT);
            if (topLeftRect != null && topLeftRect.canVisit()) {
                this.processQueue.add(topLeftRect);
                topLeftRect.setFill(MapRectangle.notProcessedPath);
            }

            MapRectangle topRightRect = this.currentRectangle.neighborRectangles.get(MapRectangle.neighbors.TOPRIGHT);
            if (topRightRect != null && topRightRect.canVisit()) {
                this.processQueue.add(topRightRect);
                topRightRect.setFill(MapRectangle.notProcessedPath);
            }

            MapRectangle bottomLeftRect = this.currentRectangle.neighborRectangles.get(MapRectangle.neighbors.BOTTOMLEFT);
            if (bottomLeftRect != null && bottomLeftRect.canVisit()) {
                this.processQueue.add(bottomLeftRect);
                bottomLeftRect.setFill(MapRectangle.notProcessedPath);
            }

            MapRectangle bottomRightRect = this.currentRectangle.neighborRectangles.get(MapRectangle.neighbors.BOTTOMRIGHT);
            if (bottomRightRect != null && bottomRightRect.canVisit()) {
                this.processQueue.add(bottomRightRect);
                bottomRightRect.setFill(MapRectangle.notProcessedPath);
            }
        }

        // 3. Set current vertex to be a vertex off the "to do" list
        MapRectangle newRect = this.processQueue.remove(0);


        // 4. If current vertex == destination, we're done! EXIT
        if (newRect.getState() == MapRectangle.states.END) {
            System.out.println("done!");

            // stop animation!
            this.timeline.stop();
        } else {
            newRect.setFill(MapRectangle.processedPath);
            this.currentRectangle = newRect;
        }
    }

    /**
     * This method returns the last operation time. It is calculated when the runPath() method is called.
     * @return long of the time taken to run the path in nanoseconds
     */
    @Override
    public long getLastOperationTime() {
        return this.time;
    }
}
