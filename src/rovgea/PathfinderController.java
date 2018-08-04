/*
 * Project: Pathfinder
 * Author: Austin Rovge
 * Date: 8/3//2018
 */

package rovgea;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

/**
 * This is the controller for the Pathfinder JavaFX file.
 * It holds all of the methods found in the Pathfinder.fxml file.
 */
public class PathfinderController {

    /** Information for the rectangles 2D array */
    private final int x = 20;
    private final int y = 15;
    private MapRectangle[][] rectangles = new MapRectangle[this.x][this.y];

    /** Label object used for storing the time taken to run the path finding algorithm. */
    @FXML
    private Label timeLabel;

    /** Label object used for displaying the current mode. */
    @FXML
    private Label modeLabel;

    @FXML
    private RadioMenuItem bruteforceMenuItem;

    @FXML
    private RadioMenuItem aStarMenuItem;

    /** Run MenuItem used for enabling when an algorithm is selected. */
    @FXML
    private MenuItem runMenuItem;

    /** Pane object used for pushing the rectangles on to. */
    @FXML
    private Pane pane;

    /** This stores information about the rectangles the algorithm starts and ends at. */
    private MapRectangle startRectangle;
    private MapRectangle endRectangle;

    private boolean useDiagonalRectangles = false;

    private boolean showSteps = true;

    private Algorithm algorithm = new Bruteforce();

    /**
     * This method runs when the JavaFX window is initialized.
     * It adds all of the rectangles to the pane with event handlers.
     */
    @FXML
    public void initialize() {

        this.addPaneEventFilters();
        this.generateRectangles();
        this.connectRectangles();
    }

    /**
     * This method runs the current algorithm against the map.
     */
    public void run() {
        this.algorithm.runPath(this.startRectangle, this.useDiagonalRectangles);
        this.formatTime(this.algorithm.getLastOperationTime());
    }

    /**
     * This is a rudimentary method for bruteforcing through the map.
     */
    @FXML
    private void bruteforce() {

        this.algorithm = new Bruteforce();
    }

    /**
     * This method shows the intermediate steps the algorithm goes through to find the correct path.
     */
    public void toggleSteps() {
        this.showSteps = !this.showSteps;
    }

    /**
     * This method enables diagonal tiles to be drawn on the map.
     */
    public void toggleDiagonal() {
        this.useDiagonalRectangles = !this.useDiagonalRectangles;
    }

    /**
     * This method adds event filters to the pane.
     */
    private void addPaneEventFilters() {
        // enable dragging functionality on the pane
        this.pane.setOnMouseDragged(e -> {
            final int paneX = (int) (e.getX() / MapRectangle.width);
            final int paneY = (int) (e.getY() / MapRectangle.height);

            // prevents an ArrayIndexOutOfBoundsException and is probably cheaper resource wise than try/catch
            if (0 <= paneX && paneX < this.x && 0 <= paneY && paneY < this.y) {
                MapRectangle mapRect = this.rectangles[paneX][paneY];

                if (e.getButton() == MouseButton.PRIMARY) {
                    mapRect.setState(MapRectangle.states.WALL);
                } else if (e.getButton() == MouseButton.SECONDARY) {
                    mapRect.setState(MapRectangle.states.BASE);
                }
            }
        });
    }

    /**
     * This method creates all of the MapRectangles and adds them to the pane.
     */
    private void generateRectangles() {
        // generate all rectangles and add them to the pane
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {

                MapRectangle mapRect = new MapRectangle(i * MapRectangle.width, j * MapRectangle.height, MapRectangle.width, MapRectangle.height);

                this.addRectangleEventFilters(mapRect);

                this.pane.getChildren().add(mapRect);
                this.rectangles[i][j] = mapRect;
            }
        }
    }

    /**
     * This method adds all of the event filters to the MapRectangle object
     * passed into the method.
     * @param mapRectangle MapRectangle object to add event filters to
     */
    private void addRectangleEventFilters(MapRectangle mapRectangle) {
        // this changes the color when hovered over
        mapRectangle.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
            if (mapRectangle.getState() == MapRectangle.states.BASE) {
                if (e.isShiftDown()) {
                    mapRectangle.setFill(MapRectangle.startColor);
                } else if (e.isControlDown()) {
                    mapRectangle.setFill(MapRectangle.endColor);
                } else {
                    mapRectangle.setFill(MapRectangle.wallColor);
                }
            }
        });

        // this changes the color when not hovered over
        mapRectangle.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
            if (mapRectangle.getState() == MapRectangle.states.BASE) {
                mapRectangle.setFill(MapRectangle.baseColor);
            }
        });

        // this handles making the rectangle active when clicking on it
        mapRectangle.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton() == MouseButton.PRIMARY) {

                if (e.isShiftDown()) {
                    if (this.startRectangle != null) {
                        this.startRectangle.setState(MapRectangle.states.BASE);
                    }
                    mapRectangle.setState(MapRectangle.states.START);
                    this.startRectangle = mapRectangle;

                    if (this.endRectangle != null) {
                        this.runMenuItem.setDisable(false);
                    }

                } else if (e.isControlDown()) {
                    if (this.endRectangle != null) {
                        this.endRectangle.setState(MapRectangle.states.BASE);
                    }
                    mapRectangle.setState(MapRectangle.states.END);
                    this.endRectangle = mapRectangle;

                    if (this.startRectangle != null) {
                        this.runMenuItem.setDisable(false);
                    }
                } else {
                    mapRectangle.setState(MapRectangle.states.WALL);
                }
            } else if (e.getButton() == MouseButton.SECONDARY) {
                mapRectangle.setState(MapRectangle.states.BASE);
            }
        });
    }

    /**
     * This method connects all the rectangles to each other.
     * It loops through the 2D array and references each rectangle to
     * each other depending on boundary conditions.
     */
    private void connectRectangles() {
        // loop through all rectangles in the 2D array
        for (int j = 0; j < this.y; j++) {
            for (int i = 0; i < this.x; i++) {
                MapRectangle mapRect = this.rectangles[i][j];

                // top
                if (0 < j) {
                    mapRect.neighborRectangles.put(MapRectangle.neighbors.TOP, this.rectangles[i][j - 1]);
                }

                // left
                if (0 < i) {
                    mapRect.neighborRectangles.put(MapRectangle.neighbors.LEFT, this.rectangles[i - 1][j]);
                }

                // right
                if (i < this.x - 1) {
                    mapRect.neighborRectangles.put(MapRectangle.neighbors.RIGHT, this.rectangles[i + 1][j]);
                }

                // bottom
                if (j < this.y - 1) {
                    mapRect.neighborRectangles.put(MapRectangle.neighbors.BOTTOM, this.rectangles[i][j + 1]);
                }

                // top left
                if (0 < j && 0 < i) {
                    mapRect.neighborRectangles.put(MapRectangle.neighbors.TOPLEFT, this.rectangles[i - 1][j - 1]);
                }

                // top right
                if (0 < j && i < this.x - 1) {
                    mapRect.neighborRectangles.put(MapRectangle.neighbors.TOPRIGHT, this.rectangles[i + 1][j - 1]);
                }

                // bottom left
                if (j < this.y - 1 && 0 < i) {
                    mapRect.neighborRectangles.put(MapRectangle.neighbors.BOTTOMLEFT, this.rectangles[i - 1][j + 1]);
                }

                // bottom right
                if (j < this.y - 1 && i < this.x - 1) {
                    mapRect.neighborRectangles.put(MapRectangle.neighbors.BOTTOMRIGHT, this.rectangles[i + 1][j + 1]);
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
