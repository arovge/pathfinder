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
import node.PrimaryState;
import node.SecondaryState;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This class is for a brute force path finding algorithms.
 * It implements the methods in the algorithms interface.
 */
public class Bruteforce implements Algorithm {

    private long time;

    private Node currentNode;

    private Queue<Node> toProcess;
    private LinkedList<Node> processed;

    private Timeline timeline;

    @Override
    public void runPath(Node currentNode, boolean useDiagonalNodes) {
        // start timing the process
        this.time = System.nanoTime();

        this.toProcess = new LinkedList<>();
        this.processed = new LinkedList<>();

        this.currentNode = currentNode;

        // animation code
        KeyFrame keyFrame = new KeyFrame(Duration.millis(100), e -> this.runMap(useDiagonalNodes));

        this.timeline = new Timeline(keyFrame);

        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();

        // mark all processed nodes as unvisited
//        for (int i = 0; i < this.processed.size(); i++) {
//            this.processed.forEach(Node::markAsUnvisited);
//        }
    }

    public void runMap(boolean useDiagonalNodes) {

        // 1. mark current rect as visited
        this.currentNode.markAsVisited();
        this.processed.add(this.currentNode);

        // 2. Add all connected rectangles (which are not marked as visited) to a "to do" list

        Node topNode = this.currentNode.top;
        if (topNode != null && topNode.canVisit() && !this.toProcess.contains(topNode)) {
            this.toProcess.add(topNode);
            topNode.setSecondaryState(SecondaryState.NOT_PROCESSED);
        }

        Node leftNode = this.currentNode.left;
        if (leftNode != null && leftNode.canVisit() && !this.toProcess.contains(leftNode)) {
            this.toProcess.add(leftNode);
            leftNode.setSecondaryState(SecondaryState.NOT_PROCESSED);
        }

        Node rightNode = this.currentNode.right;
        if (rightNode != null && rightNode.canVisit() && !this.toProcess.contains(rightNode)) {
            this.toProcess.add(rightNode);
            rightNode.setSecondaryState(SecondaryState.NOT_PROCESSED);
        }

        Node bottomNode = this.currentNode.bottom;
        if (bottomNode != null && bottomNode.canVisit() && !this.toProcess.contains(bottomNode)) {
            this.toProcess.add(bottomNode);
            bottomNode.setSecondaryState(SecondaryState.NOT_PROCESSED);
        }

        if (useDiagonalNodes) {

            Node topLeftNode = this.currentNode.topleft;
            if (topLeftNode != null && topLeftNode.canVisit() && !this.toProcess.contains(topLeftNode)) {
                this.toProcess.add(topLeftNode);
                topLeftNode.setSecondaryState(SecondaryState.NOT_PROCESSED);
            }

            Node topRightNode = this.currentNode.topright;
            if (topRightNode != null && topRightNode.canVisit() && !this.toProcess.contains(topRightNode)) {
                this.toProcess.add(topRightNode);
                topRightNode.setSecondaryState(SecondaryState.NOT_PROCESSED);
            }

            Node bottomLeftNode = this.currentNode.bottomleft;
            if (bottomLeftNode != null && bottomLeftNode.canVisit() && !this.toProcess.contains(bottomLeftNode)) {
                this.toProcess.add(bottomLeftNode);
                bottomLeftNode.setSecondaryState(SecondaryState.NOT_PROCESSED);
            }

            Node bottomRightNode = this.currentNode.bottomright;
            if (bottomRightNode != null && bottomRightNode.canVisit() && !this.toProcess.contains(bottomRightNode)) {
                this.toProcess.add(bottomRightNode);
                bottomRightNode.setSecondaryState(SecondaryState.NOT_PROCESSED);
            }
        }

        // 3. Set current vertex to be a vertex off the "to do" list
        Node newNode = this.toProcess.poll();


        // 4. If current vertex == destination, we're done! EXIT
        if (newNode.getPrimaryState() == PrimaryState.END) {

            // stop animation!
            this.timeline.stop();

            // calculate the total time it took
            this.time = System.nanoTime() - this.time;
        } else {
            newNode.setSecondaryState(SecondaryState.PROCESSED);
            this.currentNode = newNode;
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
