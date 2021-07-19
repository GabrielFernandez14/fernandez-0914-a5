/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Gabriel Fernandez
 */

package ucf.assignments;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.IOException;

// This is Dr. Hollander's code for launching an application using
// a SceneManager
public class InventoryManager extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        SceneManager sceneManager = new SceneManager();
        sceneManager.load();

        Scene scene = sceneManager.getScene("MainWindow");

        primaryStage.setTitle("Inventory Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
