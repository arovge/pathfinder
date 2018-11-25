/*
 * Project: Pathfinder
 * Author: Austin Rovge
 * Date: 8/3//2018
 */

package gui;

import algorithms.Algorithm;
import algorithms.Bruteforce;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import node.Node;
import node.State;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

/**
 * This is the controller for the Main JavaFX file.
 */
public class Controller {

    /** Information for the node 2D array */
    private final int x = 20;
    private final int y = 15;
    private Node[][] nodes = new Node[this.x][this.y];

    /** Label object used for storing the time taken to run the path finding algorithms. */
    @FXML
    private Label timeLabel;

    /** Label object used for displaying the current mode. */
    @FXML
    private Label modeLabel;

    /** Run MenuItem used for enabling when an algorithms is selected. */
    @FXML
    private MenuItem runMenuItem;

    /** Pane object used for pushing the rectangles on to. */
    @FXML
    private Pane pane;

    /** This stores information about the rectangles the algorithms starts and ends at. */
    private Node startNode;
    private Node endNode;

    private boolean useDiagonalNodes = false;

    private boolean showSteps = true;

    private Algorithm algorithm = new Bruteforce();

    /**
     * This method runs when the JavaFX window is initialized.
     * It adds all of the rectangles to the pane with event handlers.
     */
    @FXML
    public void initialize() {

        this.addPaneEventFilters();
        this.generateNodes();
        this.ConnectNodes();
    }

    /**
     * This method runs the current algorithms against the map.
     */
    public void run() {
        this.algorithm.runPath(this.startNode, this.endNode, this.useDiagonalNodes);
        this.formatTime(this.algorithm.getLastOperationTime());
    }

    /**
     * This is a rudimentary method for brute forcing through the map.
     */
    @FXML
    private void bruteforce() {
        this.algorithm = new Bruteforce();
    }

    /**
     * This method shows the intermediate steps the algorithms goes through to find the correct path.
     */
    public void toggleSteps() {
        this.showSteps = !this.showSteps;
    }

    /**
     * This method enables diagonal tiles to be drawn on the map.
     */
    public void toggleDiagonal() {
        this.useDiagonalNodes = !this.useDiagonalNodes;
    }

    /**
     * This method adds event filters to the pane.
     */
    private void addPaneEventFilters() {
        // enable dragging functionality on the pane
        this.pane.setOnMouseDragged(e -> {
            final int paneX = (int) (e.getX() / Node.WIDTH);
            final int paneY = (int) (e.getY() / Node.HEIGHT);

            // prevents an ArrayIndexOutOfBoundsException and is cheaper resource wise than try/catch
            if (0 <= paneX && paneX < this.x && 0 <= paneY && paneY < this.y) {
                Node mapRect = this.nodes[paneX][paneY];

                if (e.getButton() == MouseButton.PRIMARY) {
                    mapRect.setState(State.WALL);
                } else if (e.getButton() == MouseButton.SECONDARY) {
                    mapRect.setState(State.NORMAL);
                }
            }
        });
    }

    /**
     * This method creates all of the MapRectangles and adds them to the pane.
     */
    private void generateNodes() {
        // generate all rectangles and add them to the pane
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {

                Node mapRect = new Node(i * Node.WIDTH, j * Node.HEIGHT,
                        Node.WIDTH, Node.HEIGHT);

                this.addRectangleEventFilters(mapRect);

                this.pane.getChildren().add(mapRect);
                this.nodes[i][j] = mapRect;
            }
        }
    }

    /**
     * This method adds all of the event filters to the Node object
     * passed into the method.
     * @param node Node object to add event filters to
     */
    private void addRectangleEventFilters(Node node) {
        // this changes the color when hovered over
        node.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
            if (node.getState() == State.NORMAL) {
                if (e.isShiftDown()) {
                    node.setState(State.HOVER_START);
                } else if (e.isControlDown()) {
                    node.setState(State.HOVER_END);
                } else {
                    node.setState(State.HOVER_WALL);
                }
            }
        });

        // this changes the color when not hovered over
        node.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
            if (node.getState() == State.HOVER_WALL ||
                node.getState() == State.HOVER_START ||
                node.getState() == State.HOVER_END) {
                node.setState(State.NORMAL);
            }
        });

        // this handles making the node active when clicking on it
        node.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton() == MouseButton.PRIMARY) {

                if (e.isShiftDown()) {
                    if (this.startNode != null) {
                        this.startNode.setState(State.NORMAL);
                    }
                    node.setState(State.START);
                    this.startNode = node;

                    if (this.endNode != null) {
                        this.runMenuItem.setDisable(false);
                    }

                } else if (e.isControlDown()) {
                    if (this.endNode != null) {
                        this.endNode.setState(State.NORMAL);
                    }
                    node.setState(State.END);
                    this.endNode = node;

                    if (this.startNode != null) {
                        this.runMenuItem.setDisable(false);
                    }
                } else {
                    node.setState(State.WALL);
                }
            } else if (e.getButton() == MouseButton.SECONDARY) {
                node.setState(State.NORMAL);
            }
        });
    }

    /**
     * This method connects all the rectangles to each other.
     * It loops through the 2D array and references each node to
     * each other depending on boundary conditions.
     */
    private void ConnectNodes() {
        // loop through all rectangles in the 2D array
        for (int j = 0; j < this.y; j++) {
            for (int i = 0; i < this.x; i++) {
                Node mapRect = this.nodes[i][j];

                // top
                if (0 < j) {
                    mapRect.top = this.nodes[i][j - 1];
                }

                // left
                if (0 < i) {
                    mapRect.left = this.nodes[i - 1][j];
                }

                // right
                if (i < this.x - 1) {
                    mapRect.right = this.nodes[i + 1][j];
                }

                // bottom
                if (j < this.y - 1) {
                    mapRect.bottom = this.nodes[i][j + 1];
                }

                // top left
                if (0 < j && 0 < i) {
                    mapRect.topleft  = this.nodes[i - 1][j - 1];
                }

                // top right
                if (0 < j && i < this.x - 1) {
                    mapRect.topright  = this.nodes[i + 1][j - 1];
                }

                // bottom left
                if (j < this.y - 1 && 0 < i) {
                    mapRect.bottomleft  = this.nodes[i - 1][j + 1];
                }

                // bottom right
                if (j < this.y - 1 && i < this.x - 1) {
                    mapRect.bottomright  = this.nodes[i + 1][j + 1];
                }
            }
        }
    }

    /**
     * This is a helper method used for helping format time in the time Label object.
     * @param time the time to be formatted in nanoseconds
     */
    private void formatTime(long time) {

        // used for unit conversion
        final double siUnitDiff = 1000;
        final int minInHour = 60;
        final int secInMin = 60;
        final int milliInSec = 1000;

        // custom string added to time label object
        String timeStr = "";

        DecimalFormat threeDigitFormat = new DecimalFormat("###");
        DecimalFormat multipleUnitFormat = new DecimalFormat("00");

        // if statements to determine how to display time in what units
        if (time < siUnitDiff) {

            // nanoseconds
            timeStr = threeDigitFormat.format(time) + " nanoseconds";
        } else if (time / siUnitDiff < siUnitDiff) {

            // microseconds
            timeStr = TimeUnit.NANOSECONDS.toMicros(time) + "." +
                    threeDigitFormat.format(time % siUnitDiff) + " microseconds";
        } else if (time / (siUnitDiff * siUnitDiff) < siUnitDiff) {

            // milliseconds
            timeStr = TimeUnit.NANOSECONDS.toMillis(time) + "." +
                    threeDigitFormat.format(TimeUnit.NANOSECONDS.toMicros((long)
                            (time % (siUnitDiff * siUnitDiff)))) + " milliseconds";
        } else if (time / (siUnitDiff * siUnitDiff * siUnitDiff) < siUnitDiff) {

            // larger than nano, micro, and milli
            timeStr = multipleUnitFormat.format(TimeUnit.NANOSECONDS.toMinutes(time)
                    % minInHour) + ":" +
                    multipleUnitFormat.format(TimeUnit.NANOSECONDS.toSeconds(time)
                            % secInMin) + "." +
                    multipleUnitFormat.format(TimeUnit.NANOSECONDS.toMillis(time) % milliInSec);

        }

        this.timeLabel.setText("Time elapsed: " + timeStr);
    }
}
