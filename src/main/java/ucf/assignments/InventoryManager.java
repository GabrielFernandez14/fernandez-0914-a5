/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Gabriel Fernandez
 */

package ucf.assignments;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

// This is Dr. Hollander's code for launching an application using
// a SceneManager
public class InventoryManager extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        SceneManager sceneManager = new SceneManager();
        sceneManager.load();

        Scene scene = sceneManager.getScene("MainWindow.fxml");

        primaryStage.setTitle("Inventory Manager");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
