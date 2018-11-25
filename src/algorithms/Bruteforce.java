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
import node.Node;
import node.State;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class is for a brute force path finding algorithms.
 * It implements the methods in the algorithms interface.
 */
public class Bruteforce implements Algorithm {

    private long time;

    private Node startNode;
    private Node currentNode;
    private Node endNode;

    private boolean useDiagonalNodes;

    private Queue<Node> toProcess;
    private Queue<Node> processed;

    private Timeline timeline;

    @Override
    public void runPath(Node startNode, Node endRectangle, boolean useDiagonalNodes) {
        // start timing the process
        this.time = System.nanoTime();

        this.toProcess = new LinkedList<>();
        this.processed = new LinkedList<>();

        this.startNode = startNode;
        this.endNode = endRectangle;
        this.currentNode = endRectangle;
        this.useDiagonalNodes = useDiagonalNodes;

        // animation code
        KeyFrame keyFrame = new KeyFrame(Duration.millis(100), e -> {
            this.runMap();
        });

        this.timeline = new Timeline(keyFrame);

        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();


        // mark all processed nodes as unvisited
        for (int i = 0; i < this.processed.size(); i++) {
            this.processed.forEach(Node::markAsUnvisited);
        }
    }

    private void runMap() {



        // 1. mark current rect as visited
        this.currentNode.markAsVisited();
        this.processed.add(this.currentNode);

        // 2. Add all connected rectangles (which are not marked as visited) to a "to do" list

        Node topRect = this.currentNode.top;
        if (topRect != null && topRect.canVisit()) {
            this.toProcess.add(topRect);
            topRect.setState(State.NOT_PROCESSED);
        }

        Node leftRect = this.currentNode.left;
        if (leftRect != null && leftRect.canVisit()) {
            this.toProcess.add(leftRect);
            leftRect.setState(State.NOT_PROCESSED);
        }

        Node rightRect = this.currentNode.right;
        if (rightRect != null && rightRect.canVisit()) {
            this.toProcess.add(rightRect);
            rightRect.setState(State.NOT_PROCESSED);
        }

        Node bottomRect = this.currentNode.bottom;
        if (bottomRect != null && bottomRect.canVisit()) {
            this.toProcess.add(bottomRect);
            bottomRect.setState(State.NOT_PROCESSED);
        }

        if (this.useDiagonalNodes) {

            Node topLeftRect = this.currentNode.topleft;
            if (topLeftRect != null && topLeftRect.canVisit()) {
                this.toProcess.add(topLeftRect);
                topLeftRect.setState(State.NOT_PROCESSED);
            }

            Node topRightRect = this.currentNode.topright;
            if (topRightRect != null && topRightRect.canVisit()) {
                this.toProcess.add(topRightRect);
                topRightRect.setState(State.NOT_PROCESSED);
            }

            Node bottomLeftRect = this.currentNode.bottomleft;
            if (bottomLeftRect != null && bottomLeftRect.canVisit()) {
                this.toProcess.add(bottomLeftRect);
                bottomLeftRect.setState(State.NOT_PROCESSED);
            }

            Node bottomRightRect = this.currentNode.bottomright;
            if (bottomRightRect != null && bottomRightRect.canVisit()) {
                this.toProcess.add(bottomRightRect);
                bottomRightRect.setState(State.NOT_PROCESSED);
            }
        }

        // 3. Set current vertex to be a vertex off the "to do" list
        Node newRect = this.toProcess.poll();


        // 4. If current vertex == destination, we're done! EXIT
        if (newRect.getState() == State.END) {
            System.out.println("done!");

            // stop animation!
            this.timeline.stop();

            // calculate the total time it took
            this.time = System.nanoTime() - this.time;
        } else {
            newRect.setState(State.PROCESSED);
            this.currentNode = newRect;
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
