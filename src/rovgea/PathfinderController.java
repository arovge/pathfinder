/*
 * Project: Pathfinder
 * Author: Austin Rovge
 * Date: 3/26/2018
 */

package rovgea;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * This is the controller for the Pathfinder JavaFX file.
 * It holds all of the methods found in the Pathfinder.fxml file.
 */
public class PathfinderController {

    private final int x = 19;
    private final int y = 15;
    private Rectangle[][] rectangles = new Rectangle[x][y];

    /** Label object used for storing the time taken to run the path finding algorithm. */
    @FXML
    private Label timeLabel;

    /** Label object used for displaying the current mode. */
    @FXML
    private Label modeLabel;

    @FXML
    private Pane pane;

    @FXML
    public void initialize() {
        final double width = 31.0;
        final double height = 31.0;

        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {

                MapRectangle mapRect = new MapRectangle(i * width, j * height, width, height);

                mapRect.setFill(Paint.valueOf("#e0e0e0"));

                mapRect.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
                    mapRect.setFill(Paint.valueOf("#b8b7b7"));
                });

                mapRect.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
                    if (!mapRect.isActive()) {
                        mapRect.setFill(Paint.valueOf("#e0e0e0"));
                    }
                });

                mapRect.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
                    mapRect.toggleActive();
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
