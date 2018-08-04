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

public class Bruteforce implements Algorithm {

    private long time;

    private boolean done = false;

    private MapRectangle currentRectangle;
    private boolean useDiagonalRectangles;

    private ArrayList<MapRectangle> processQueue = new ArrayList<>();
    private ArrayList<MapRectangle> processed = new ArrayList<>();

    private Timeline timeline;


    @Override
    public void runPath(MapRectangle currentRectangle, boolean useDiagonalRectangles) {
        this.time = System.nanoTime();

        this.currentRectangle = currentRectangle;
        this.useDiagonalRectangles = useDiagonalRectangles;

        KeyFrame keyFrame = new KeyFrame(Duration.millis(100), e -> {
            this.runMap();
        });

        this.timeline = new Timeline(keyFrame);

//        while (!this.done) {
//            timeline.play();
//        }

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();


        // mark all triangles as unvisited
        for (int i = 0; i < processed.size(); i++) {
            processed.get(i).markAsUnvisited();
        }

        this.time = System.nanoTime() - this.time;
    }

    private void runMap() {
        // 1. mark current rect as visited
        currentRectangle.markAsVisited();
        processed.add(currentRectangle);

        // 2. Add all connected rectangles (which are not marked as visited) to a "to do" list

        MapRectangle topRect = currentRectangle.neighborRectangles.get(MapRectangle.neighbors.TOP);
        if (topRect != null && topRect.canVisit()) {
            processQueue.add(topRect);
            topRect.setFill(MapRectangle.notProcessedPath);
        }

        MapRectangle leftRect = currentRectangle.neighborRectangles.get(MapRectangle.neighbors.LEFT);
        if (leftRect != null && leftRect.canVisit()) {
            processQueue.add(leftRect);
            leftRect.setFill(MapRectangle.notProcessedPath);
        }

        MapRectangle rightRect = currentRectangle.neighborRectangles.get(MapRectangle.neighbors.RIGHT);
        if (rightRect != null && rightRect.canVisit()) {
            processQueue.add(rightRect);
            rightRect.setFill(MapRectangle.notProcessedPath);
        }

        MapRectangle bottomRect = currentRectangle.neighborRectangles.get(MapRectangle.neighbors.BOTTOM);
        if (bottomRect != null && bottomRect.canVisit()) {
            processQueue.add(bottomRect);
            bottomRect.setFill(MapRectangle.notProcessedPath);
        }

        if (useDiagonalRectangles) {

            MapRectangle topLeftRect = currentRectangle.neighborRectangles.get(MapRectangle.neighbors.TOPLEFT);
            if (topLeftRect != null && topLeftRect.canVisit()) {
                processQueue.add(topLeftRect);
                topLeftRect.setFill(MapRectangle.notProcessedPath);
            }

            MapRectangle topRightRect = currentRectangle.neighborRectangles.get(MapRectangle.neighbors.TOPRIGHT);
            if (topRightRect != null && topRightRect.canVisit()) {
                processQueue.add(topRightRect);
                topRightRect.setFill(MapRectangle.notProcessedPath);
            }

            MapRectangle bottomLeftRect = currentRectangle.neighborRectangles.get(MapRectangle.neighbors.BOTTOMLEFT);
            if (bottomLeftRect != null && bottomLeftRect.canVisit()) {
                processQueue.add(bottomLeftRect);
                bottomLeftRect.setFill(MapRectangle.notProcessedPath);
            }

            MapRectangle bottomRightRect = currentRectangle.neighborRectangles.get(MapRectangle.neighbors.BOTTOMRIGHT);
            if (bottomRightRect != null && bottomRightRect.canVisit()) {
                processQueue.add(bottomRightRect);
                bottomRightRect.setFill(MapRectangle.notProcessedPath);
            }
        }

        // 3. Set current vertex to be a vertex off the "to do" list
        MapRectangle newRect = processQueue.remove(0);


        // 4. If current vertex == destination, we're done! EXIT
        if (newRect.getState() == MapRectangle.states.END) {
            System.out.println("done!");
            done = true;
            timeline.stop();
        } else {
            newRect.setFill(MapRectangle.processedPath);
            currentRectangle = newRect;
        }

        // 5. Goto 1
    }

    @Override
    public long getLastOperationTime() {
        return this.time;
    }
}
