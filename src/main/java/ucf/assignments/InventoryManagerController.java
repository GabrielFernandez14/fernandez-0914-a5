/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Gabriel Fernandez
 */

package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class InventoryManagerController implements Initializable {
    @FXML
    private TableView<InventoryItem> inventoryTable;
    @FXML
    private TableColumn<InventoryItem, BigDecimal> valueTableColumn;
    @FXML
    private TableColumn<InventoryItem, String> serialNumberTableColumn;
    @FXML
    private TableColumn<InventoryItem, String> nameTableColumn;
    @FXML
    private TextField searchBarTextField;

    private SceneManager sceneManager;
    private InventoryListModel listModel;
    private ObservableList<InventoryItem> items = FXCollections.observableArrayList();
    private ObservableList<InventoryItem> tempList = FXCollections.observableArrayList();

    public InventoryManagerController(InventoryListModel listModel, SceneManager sceneManager) {
        this.listModel = listModel;
        this.sceneManager = sceneManager;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize inventoryTable's columns with PropertyValueFactories
        valueTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        serialNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    @FXML
    public void addItemButtonClicked(ActionEvent actionEvent) {
        // Open new scene using SceneManager
        Stage stage = new Stage();
        stage.setTitle("Add Item");
        stage.setResizable(false);
        stage.setScene(sceneManager.getScene("AddItemWindow.fxml"));
        stage.show();

        // Update inventoryTable with the new items
        inventoryTable.setItems(listModel.getItems());
    }

    @FXML
    public void deleteItemButtonClicked(ActionEvent actionEvent) {
        // Remove the selected item from the tableview (and therefore the list)
        if (inventoryTable.getSelectionModel().getSelectedItem() != null) {
            listModel.getItems().remove(inventoryTable.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    public void editItemButtonClicked(ActionEvent actionEvent) throws IOException {
        // Could not figure out how to make a part of SceneManager
        // while also being able to pass variables, so this makes
        // a new scene manually
        if (inventoryTable.getSelectionModel().getSelectedItem() != null) {
            InventoryItem selectedItem = inventoryTable.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditItemWindow.fxml"));
            Parent root = loader.load();

            EditItemController controller = loader.getController();
            controller.setData(this, listModel, selectedItem);

            Stage stage = new Stage();
            stage.setTitle("Edit Item");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Refresh the table so that the edits are shown
            inventoryTable.refresh();
        }
    }

    @FXML
    public void sortListButtonClicked(ActionEvent actionEvent) {
        // Get the SortListWindow scene from SceneManager and open
        Stage stage = new Stage();
        stage.setTitle("Sort List");
        stage.setResizable(false);
        stage.setScene(sceneManager.getScene("SortListWindow.fxml"));
        stage.show();

        // update the inventoryTable accordingly for what was sorted
        inventoryTable.setItems(listModel.getItems());
    }

    @FXML
    public void saveAsMenuItemClicked(ActionEvent actionEvent) {
        // Create a new instance of class FileManager and call saveFile
        FileManager save = new FileManager();
        save.saveFile(listModel);
    }

    @FXML
    public void openMenuItemClicked(ActionEvent actionEvent) {
        // Create a new instance of class FileManager and call loadFile
        FileManager open = new FileManager();
        ArrayList<String> data = open.loadFile(listModel);

        // Read in the data from the ArrayList
        for (int i = 0; i < data.size(); i++) {
            BigDecimal curPrice = readValue(data, i);
            String curSerialNumber = readSerialNumber(data, i);
            String curName = readName(data, i);

            // list will have already been cleared, so just add the new stuff
            listModel.getItems().add(new InventoryItem(curPrice, curSerialNumber, curName));
        }

        // update the inventoryTable accordingly
        inventoryTable.setItems(listModel.getItems());
    }

    private BigDecimal readValue(ArrayList<String> fileItem, int index) {
        // Parse the given string and return the price as a BigDecimal
        String[] split = fileItem.get(index).split("\t");
        return BigDecimal.valueOf(Double.parseDouble(split[0].trim()))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private String readSerialNumber(ArrayList<String> fileItem, int index) {
        // Parse the given string and return the serial number
        String[] split = fileItem.get(index).split("\t");
        return split[1].trim();
    }

    private String readName(ArrayList<String> fileItem, int index) {
        // Parse the given string and return the name
        String[] split = fileItem.get(index).split("\t");
        return split[2].trim();
    }

    @FXML
    public void searchButtonClicked(ActionEvent actionEvent) {
        // Clear the ObservableArrayLists so that duplicates don't occur
        // each time the button is pressed
        items.clear();
        tempList.clear();

        String entry = searchBarTextField.getText().toLowerCase().trim();

        if (!entry.equals("")) {
            // Find and set all entries that contain the inputted string
            items.setAll(getFoundEntries(listModel, tempList, entry));
            inventoryTable.setItems(items);
        }
    }

    // Check if the user's input matches any of the list's items (serial number or name only)
    public ObservableList<InventoryItem> getFoundEntries(InventoryListModel listModel,
                                                         ObservableList<InventoryItem> tempList, String entry) {
        for (int i = 0; i < listModel.getItems().size(); i++) {
            if (listModel.getItems().get(i).getSerialNumber().toLowerCase().contains(entry) ||
                    listModel.getItems().get(i).getName().toLowerCase().contains(entry)) {
                tempList.add(listModel.getItems().get(i));
            }
        }

        return tempList;
    }

    // Reset the search so that the user can view all items again
    @FXML
    public void resetButtonClicked(ActionEvent actionEvent) {
        searchBarTextField.setText("");

        tempList.clear();
        items.clear();

        inventoryTable.setItems(listModel.getItems());
    }

    // Close the main window of the application
    @FXML
    public void quitMenuItemClicked(ActionEvent actionEvent) {
        // Only closes the main window, if other windows are open they need
        // to be closed manually
        Stage stage = (Stage) inventoryTable.getScene().getWindow();
        stage.close();
    }
}
