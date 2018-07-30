/*
 * Project: Pathfinder
 * Author: Austin Rovge
 * Date: 3/26/2018
 */

package rovgea;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

/**
 * This is the controller for the Pathfinder JavaFX file.
 * It holds all of the methods found in the Pathfinder.fxml file.
 */
public class PathfinderController {

    /** Information for the rectangles 2D array */
    private final int x = 20;
    private final int y = 15;
    private MapRectangle[][] rectangles = new MapRectangle[x][y];

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

    /**
     * This method runs when the JavaFX window is initialized.
     * It adds all of the rectangles to the pane with event handlers.
     */
    @FXML
    public void initialize() {
        final double width = 30.0;
        final double height = 30.0;

        // enable dragging functionality on the pane
        this.pane.setOnMouseDragged(e -> {
            final int paneX = (int) (e.getX() / width);
            final int paneY = (int) (e.getY() / height);

            // prevents an ArrayIndexOutOfBoundsException and is probably cheaper resource wise than try/catch
            if (0 <= paneX && paneX < this.x && 0 <= paneY && paneY < this.y) {
                MapRectangle mapRect = this.rectangles[paneX][paneY];

                if (e.getButton() == MouseButton.PRIMARY) {
                    mapRect.setState(true);
                } else if (e.getButton() == MouseButton.SECONDARY) {
                    mapRect.setState(false);
                }
            }
        });

        // generate all rectangles and add them to the pane
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {

                MapRectangle mapRect = new MapRectangle(i * width, j * height, width, height, i, j);

                // this changes the color when hovered over
                mapRect.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
                    if (!mapRect.getState() && !mapRect.isStartOrEnd()) {
                        if (e.isShiftDown()) {
                            mapRect.setFill(MapRectangle.startColor);
                        } else if (e.isControlDown()) {
                            mapRect.setFill(MapRectangle.endColor);
                        } else {
                            mapRect.setFill(MapRectangle.activeColor);
                        }
                    }
                });

                // this changes the color when not hovered over
                mapRect.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
                    if (!mapRect.getState() && !mapRect.isStartOrEnd()) {
                        mapRect.setFill(MapRectangle.inActiveColor);
                    }
                });

                // this handles making the rectangle active when clicking on it
                mapRect.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
                    if (e.getButton() == MouseButton.PRIMARY) {

                        if (e.isShiftDown()) {
                            if (this.startRectangle != null) {
                                this.startRectangle.removeStartOrEnd();
                            }
                            mapRect.setStart();
                            this.startRectangle = mapRect;

                            if (this.endRectangle != null) {
                                this.runMenuItem.setDisable(false);
                            }

                        } else if (e.isControlDown()) {
                            if (this.endRectangle != null) {
                                this.endRectangle.removeStartOrEnd();
                            }
                            mapRect.setEnd();
                            this.endRectangle = mapRect;

                            if (this.startRectangle != null) {
                                this.runMenuItem.setDisable(false);
                            }
                        } else {
                            mapRect.setState(true);
                        }
                    } else if (e.getButton() == MouseButton.SECONDARY) {
                        mapRect.setState(false);
                    }
                });

                this.pane.getChildren().add(mapRect);
                this.rectangles[i][j] = mapRect;
            }
        }
    }

    /**
     * This method runs the current algorithm against the map.
     */
    public void run() {
        if (this.bruteforceMenuItem.isSelected()) {
            System.out.println("Running brute force algorithm...");
            long time = System.nanoTime();
            this.bruteforce();
            time = System.nanoTime() - time;
            timeLabel.setText("Time elapsed: " + time + " nanoseconds");
        } else if (this.aStarMenuItem.isSelected()) {
            System.out.println("Running A* algorithm...");
        }
    }

    private void bruteforce() {

        System.out.println("running now");

        MapRectangle start = this.startRectangle;
        MapRectangle end = this.endRectangle;

        boolean notDone = true;

        MapRectangle currentRectangle = start;
        ArrayList<MapRectangle> todo = new ArrayList<>();

        while (notDone) {
            // 1. Mark current rectangle as visited
            currentRectangle.markAsVisited();

            // 2. Add all connected rectangles (which are not marked as visited) to a "to do" list
            if (currentRectangle.arrayX - 1 >= 0 && !this.rectangles[currentRectangle.arrayX - 1][currentRectangle.arrayY].getState() && !this.rectangles[currentRectangle.arrayX - 1][currentRectangle.arrayY].isVisited()) {
                todo.add(this.rectangles[currentRectangle.arrayX - 1][currentRectangle.arrayY]);
            }

            if (currentRectangle.arrayY - 1 >= 0 && !this.rectangles[currentRectangle.arrayX][currentRectangle.arrayY - 1].getState() && !this.rectangles[currentRectangle.arrayX][currentRectangle.arrayY - 1].isVisited()) {
                todo.add(this.rectangles[currentRectangle.arrayX][currentRectangle.arrayY - 1]);
            }

            if (currentRectangle.arrayX + 1 < this.x && !this.rectangles[currentRectangle.arrayX + 1][currentRectangle.arrayY].getState() && !this.rectangles[currentRectangle.arrayX + 1][currentRectangle.arrayY].isVisited()) {
                todo.add(this.rectangles[currentRectangle.arrayX + 1][currentRectangle.arrayY]);
            }

            if (currentRectangle.arrayY + 1 < this.y && !this.rectangles[currentRectangle.arrayX][currentRectangle.arrayY + 1].getState() && !this.rectangles[currentRectangle.arrayX][currentRectangle.arrayY + 1].isVisited()) {
                todo.add(this.rectangles[currentRectangle.arrayX][currentRectangle.arrayY + 1]);
            }

            // 3. Set current vertex to be a vertex off the "to do" list
            MapRectangle newRect = todo.remove(0);


            // 4. If current vertex == destination, we're done! EXIT
            if (newRect == this.endRectangle) {
                System.out.println("done!");
                notDone = false;
            } else {
                newRect.setFill(MapRectangle.failedPath);
                currentRectangle = newRect;
            }

            // 5. Goto 1
        }

        // mark all triangles as unvisited
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {
                this.rectangles[i][j].markAsUnvisited();
            }
        }
    }

    /**
     * This method shows the intermediate steps the algorithm goes through to find the correct path.
     */
    public void showSteps() {

    }

    /**
     * This method enables diagonal tiles to be drawn on the map.
     */
    public void enableDiagonal() {

    }
}
