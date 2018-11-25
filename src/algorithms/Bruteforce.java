/*
 * Project: Pathfinder
 * Author: Austin Rovge
 * Date: 8/4/2018
 */

package algorithms;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;
import node.Node;
import node.Types;
import node.Types.PrimaryState;
import node.Types.SecondaryState;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class is for a brute force path finding algorithms.
 * It extends the methods in the abstract algorithms class.
 */
public class Bruteforce extends Algorithm {

    private Label timeLabel;
    private Node currentNode;

    private Timeline timeline;

    private Queue<Node> toProcess;
    private LinkedList<Node> processed;

    @Override
    public void runPath(Node currentNode, boolean useDiagonalNodes, Label timeLabel) {
        // start timing the process
        this.time = System.nanoTime();

        this.timeLabel = timeLabel;

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

        Node topNode = this.currentNode.getNeighbor(Types.Neighbor.TOP);
        if (topNode != null && topNode.canVisit() && !this.toProcess.contains(topNode)) {
            this.toProcess.add(topNode);
            topNode.setSecondaryState(SecondaryState.NOT_PROCESSED);
        }

        Node leftNode = this.currentNode.getNeighbor(Types.Neighbor.LEFT);
        if (leftNode != null && leftNode.canVisit() && !this.toProcess.contains(leftNode)) {
            this.toProcess.add(leftNode);
            leftNode.setSecondaryState(SecondaryState.NOT_PROCESSED);
        }

        Node rightNode = this.currentNode.getNeighbor(Types.Neighbor.RIGHT);
        if (rightNode != null && rightNode.canVisit() && !this.toProcess.contains(rightNode)) {
            this.toProcess.add(rightNode);
            rightNode.setSecondaryState(SecondaryState.NOT_PROCESSED);
        }

        Node bottomNode = this.currentNode.getNeighbor(Types.Neighbor.BOTTOM);
        if (bottomNode != null && bottomNode.canVisit() && !this.toProcess.contains(bottomNode)) {
            this.toProcess.add(bottomNode);
            bottomNode.setSecondaryState(SecondaryState.NOT_PROCESSED);
        }

        if (useDiagonalNodes) {

            Node topLeftNode = this.currentNode.getNeighbor(Types.Neighbor.TOP_LEFT);
            if (topLeftNode != null && topLeftNode.canVisit() && !this.toProcess.contains(topLeftNode)) {
                this.toProcess.add(topLeftNode);
                topLeftNode.setSecondaryState(SecondaryState.NOT_PROCESSED);
            }

            Node topRightNode = this.currentNode.getNeighbor(Types.Neighbor.TOP_RIGHT);
            if (topRightNode != null && topRightNode.canVisit() && !this.toProcess.contains(topRightNode)) {
                this.toProcess.add(topRightNode);
                topRightNode.setSecondaryState(SecondaryState.NOT_PROCESSED);
            }

            Node bottomLeftNode = this.currentNode.getNeighbor(Types.Neighbor.BOTTOM_LEFT);
            if (bottomLeftNode != null && bottomLeftNode.canVisit() && !this.toProcess.contains(bottomLeftNode)) {
                this.toProcess.add(bottomLeftNode);
                bottomLeftNode.setSecondaryState(SecondaryState.NOT_PROCESSED);
            }

            Node bottomRightNode = this.currentNode.getNeighbor(Types.Neighbor.BOTTOM_RIGHT);
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
            this.formatTime(this.timeLabel);
        } else {
            newNode.setSecondaryState(SecondaryState.PROCESSED);
            this.currentNode = newNode;
        }
    }
}
