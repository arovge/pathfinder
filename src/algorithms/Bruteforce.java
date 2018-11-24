/*
 * Project: Pathfinder
 * Author: Austin Rovge
 * Date: 8/4/2018
 */

package algorithms;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import gui.MapRectangle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class is for a brute force path finding algorithms.
 * It implements the methods in the algorithms interface.
 */
public class Bruteforce implements Algorithm {

    private long time;

    private MapRectangle currentRectangle;
    private boolean useDiagonalRectangles;

    private Queue<MapRectangle> processQueue = new LinkedList<>();
    private Queue<MapRectangle> processed = new LinkedList<>();

    private Timeline timeline;

    private MapRectangle startRectangle;
    private MapRectangle endRectangle;

    @Override
    public void runPath(MapRectangle startRectangle, MapRectangle endRectangle, boolean useDiagonalRectangles) {
        // start timing the process
        this.time = System.nanoTime();

        this.startRectangle = startRectangle;
        this.endRectangle = endRectangle;
        this.currentRectangle = startRectangle;
        this.useDiagonalRectangles = useDiagonalRectangles;

        // animation code
        KeyFrame keyFrame = new KeyFrame(Duration.millis(100), e -> {
            this.runMap();
        });

        this.timeline = new Timeline(keyFrame);

        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();


        // mark all triangles as unvisited
//        for (int i = 0; i < this.processed.size(); i++) {
//            this.processed.get(i).markAsUnvisited();
//        }

        // calculate the total time it took
        this.time = System.nanoTime() - this.time;
    }

    private void runMap() {



        // 1. mark current rect as visited
        this.currentRectangle.markAsVisited();
        this.processed.add(this.currentRectangle);

        // 2. Add all connected rectangles (which are not marked as visited) to a "to do" list

        MapRectangle topRect = this.currentRectangle.top;
        if (topRect != null && topRect.canVisit()) {
            this.processQueue.add(topRect);
            topRect.setState(MapRectangle.states.NOT_PROCESSED);
        }

        MapRectangle leftRect = this.currentRectangle.left;
        if (leftRect != null && leftRect.canVisit()) {
            this.processQueue.add(leftRect);
            leftRect.setState(MapRectangle.states.NOT_PROCESSED);
        }

        MapRectangle rightRect = this.currentRectangle.right;
        if (rightRect != null && rightRect.canVisit()) {
            this.processQueue.add(rightRect);
            rightRect.setState(MapRectangle.states.NOT_PROCESSED);
        }

        MapRectangle bottomRect = this.currentRectangle.bottom;
        if (bottomRect != null && bottomRect.canVisit()) {
            this.processQueue.add(bottomRect);
            bottomRect.setState(MapRectangle.states.NOT_PROCESSED);
        }

        if (this.useDiagonalRectangles) {

            MapRectangle topLeftRect = this.currentRectangle.topleft;
            if (topLeftRect != null && topLeftRect.canVisit()) {
                this.processQueue.add(topLeftRect);
                topLeftRect.setState(MapRectangle.states.NOT_PROCESSED);
            }

            MapRectangle topRightRect = this.currentRectangle.topright;
            if (topRightRect != null && topRightRect.canVisit()) {
                this.processQueue.add(topRightRect);
                topRightRect.setState(MapRectangle.states.NOT_PROCESSED);
            }

            MapRectangle bottomLeftRect = this.currentRectangle.bottomleft;
            if (bottomLeftRect != null && bottomLeftRect.canVisit()) {
                this.processQueue.add(bottomLeftRect);
                bottomLeftRect.setState(MapRectangle.states.NOT_PROCESSED);
            }

            MapRectangle bottomRightRect = this.currentRectangle.bottomright;
            if (bottomRightRect != null && bottomRightRect.canVisit()) {
                this.processQueue.add(bottomRightRect);
                bottomRightRect.setState(MapRectangle.states.NOT_PROCESSED);
            }
        }

        // 3. Set current vertex to be a vertex off the "to do" list
        MapRectangle newRect = this.processQueue.poll();


        // 4. If current vertex == destination, we're done! EXIT
        if (newRect.getState() == MapRectangle.states.END) {
            System.out.println("done!");

            // stop animation!
            this.timeline.stop();
        } else {
            newRect.setState(MapRectangle.states.PROCESSED);
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
