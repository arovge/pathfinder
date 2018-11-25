/*
 * Project: Pathfinder
 * Author: Austin Rovge
 * Date: 8/3/2018
 */

package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * This is the driver class for the Pathfinder program. It starts the JavaFX window and sets up the logger.
 */
public class Main extends Application {

    /** This is the logger object used by all classes in the program */
    public static final Logger logger = Logger.getLogger(Main.class.getName());

    /**
     * This is the start method for the application.
     * The information about the window is created here and so is information about the logger.
     * @param primaryStage Stage object that is created based on the FXML file
     * @throws Exception generic Exception object if an error is occurred.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        final int stageWidth = 600;
        final int stageHeight = 500;

        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Pathfinder");
        primaryStage.setScene(new Scene(root, stageWidth, stageHeight));
        primaryStage.setResizable(false);

        File dir = new File(System.getProperty("user.dir") + File.separator + "logs");

        if (!dir.exists()) {
            dir.mkdir();
            Main.logger.info("Log directory created.");
        }

        /* add filehandler to logger */
        try {
            FileHandler handler = new FileHandler(dir + File.separator + new Date().toString().replaceAll(" |:", "-") + ".log");
            Main.logger.addHandler(handler);
            Main.logger.setUseParentHandlers(false);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> handler.close() ));
        } catch (IOException e) {
            Main.logger.severe("Could not add handler to logger");
        }

        primaryStage.show();
    }

    /**
     * This is the main method for the program. It starts the FX application.
     * @param args String array passed into launch()
     */
    public static void main(String[] args) {
        launch(args);
    }
}
