/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Gabriel Fernandez
 */

package ucf.assignments;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// Also Dr. Hollander's code, may update later if I decide to do more windows
public class SceneManager {
    Map<String, Scene> scenes = new HashMap<>();

    public void load() {
        InventoryListModel listModel = new InventoryListModel();

        InventoryManagerController inventoryManagerController = new InventoryManagerController(listModel, this);
        AddItemController addItemController = new AddItemController(listModel, this);
        SortListController sortListController = new SortListController(listModel, this);

        Parent root;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        loader.setController(inventoryManagerController);
        try {
            root = loader.load();
            scenes.put("MainWindow.fxml", new Scene(root));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        loader = new FXMLLoader(getClass().getResource("AddItemWindow.fxml"));
        loader.setController(addItemController);
        try {
            root = loader.load();
            scenes.put("AddItemWindow.fxml", new Scene(root));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        loader = new FXMLLoader(getClass().getResource("SortListWindow.fxml"));
        loader.setController(sortListController);
        try {
            root = loader.load();
            scenes.put("SortListWindow.fxml", new Scene(root));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Scene getScene(String name) {
        return scenes.get(name);
    }
}
