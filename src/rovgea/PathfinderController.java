/*
 * Project: Pathfinder
 * Author: Austin Rovge
 * Date: 3/26/2018
 */

package rovgea;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * This is the controller for the Pathfinder JavaFX file.
 * It holds all of the methods found in the Pathfinder.fxml file.
 */
public class PathfinderController {

    private final int x = 19;
    private final int y = 15;
    private MapRectangle[][] rectangles = new MapRectangle[x][y];

    /** Label object used for storing the time taken to run the path finding algorithm. */
    @FXML
    private Label timeLabel;

    /** Label object used for displaying the current mode. */
    @FXML
    private Label modeLabel;

    @FXML
    private Pane pane;

    private MapRectangle startRectangle;
    private MapRectangle endRectangle;

    @FXML
    public void initialize() {
        final double width = 31.0;
        final double height = 31.0;

        // enable dragging functionality on the pane
        this.pane.setOnMouseDragged(e -> {
            final int paneX = (int) (e.getX() / width);
            final int paneY = (int) (e.getY() / height);

            // prevents an ArrayIndexOutOfBoundsException and is probably cheaper resource wise than try/catch
            if (0 <= paneX && paneX < this.x && 0 <= paneY && paneY < this.y) {
                MapRectangle mapRect = this.rectangles[paneX][paneY];

                if (e.getButton() == MouseButton.PRIMARY) {
                    mapRect.toggleActive(true);
                } else if (e.getButton() == MouseButton.SECONDARY) {
                    mapRect.toggleActive(false);
                }
            }
        });

        // generate all rectangles and add them to the pane
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {

                MapRectangle mapRect = new MapRectangle(i * width, j * height, width, height);

                // this changes the color when hovered over
                mapRect.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
                    if (!mapRect.isActive() && !mapRect.isStartOrEnd()) {
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
                    if (!mapRect.isActive() && !mapRect.isStartOrEnd()) {
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
                        } else if (e.isControlDown()) {
                            if (this.endRectangle != null) {
                                this.endRectangle.removeStartOrEnd();
                            }
                            mapRect.setEnd();
                            this.endRectangle = mapRect;
                        } else {
                            mapRect.toggleActive(true);
                        }
                    } else if (e.getButton() == MouseButton.SECONDARY) {
                        mapRect.toggleActive(false);
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
