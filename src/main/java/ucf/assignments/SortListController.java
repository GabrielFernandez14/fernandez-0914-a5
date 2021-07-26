/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Gabriel Fernandez
 */

package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.util.Comparator;

public class SortListController {
    private InventoryListModel listModel;
    private SceneManager sceneManager;

    @FXML
    private Button sortByValueButton;
    @FXML
    private Button sortBySNButton;
    @FXML
    private Button sortByNameButton;

    // Get data from the SceneManager
    public SortListController(InventoryListModel listModel, SceneManager sceneManager) {
        this.listModel = listModel;
        this.sceneManager = sceneManager;
    }

    @FXML
    private void sortByValueButtonClicked(ActionEvent actionEvent) {
        sortByValue(listModel);

        // Close the window
        Stage stage = (Stage) sortByValueButton.getScene().getWindow();
        stage.close();
    }

    // Sort the list by value
    public void sortByValue(InventoryListModel listModel) {
        Comparator<InventoryItem> comparator = Comparator.comparing(InventoryItem::getPrice);
        FXCollections.sort(listModel.getItems(), comparator);
    }

    @FXML
    private void sortBySerialNumberButtonClicked(ActionEvent actionEvent) {
        sortBySerialNumber(listModel);

        // Close the window
        Stage stage = (Stage) sortBySNButton.getScene().getWindow();
        stage.close();
    }

    // Sort the list by serial number
    public void sortBySerialNumber(InventoryListModel listModel) {
        Comparator<InventoryItem> comparator = Comparator.comparing(InventoryItem::getSerialNumber);
        FXCollections.sort(listModel.getItems(), comparator);
    }

    @FXML
    private void sortByNameButtonClicked(ActionEvent actionEvent) {
        sortByName(listModel);

        // Close the window
        Stage stage = (Stage) sortByNameButton.getScene().getWindow();
        stage.close();
    }

    // Sort the list by name
    public void sortByName(InventoryListModel listModel) {
        Comparator<InventoryItem> comparator = Comparator.comparing(InventoryItem::getName);
        FXCollections.sort(listModel.getItems(), comparator);
    }
}
