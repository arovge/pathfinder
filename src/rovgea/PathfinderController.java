/*
 * Project: Pathfinder
 * Author: Austin Rovge
 * Date: 3/26/2018
 */

package rovgea;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * This is the controller for the Pathfinder JavaFX file.
 * It holds all of the methods found in the Pathfinder.fxml file.
 */
public class PathfinderController {

    /** Label object used for storing the time taken to run the path finding algorithm. */
    @FXML
    private Label timeLabel;

    /** Label object used for displaying the current mode. */
    @FXML
    private Label modeLabel;

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

    public void mouseEnter(MouseEvent e) {
        Rectangle rect = (Rectangle) e.getSource();
        System.out.println(rect);
        rect.setFill(Paint.valueOf("#b8b7b7"));
    }

    public void mouseExit(MouseEvent e) {
        Rectangle rect = (Rectangle) e.getSource();
        System.out.println(rect);
        rect.setFill(Paint.valueOf("#e0e0e0"));
    }
}
