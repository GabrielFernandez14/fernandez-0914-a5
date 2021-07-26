/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Gabriel Fernandez
 */

package ucf.assignments;

import com.jfoenix.controls.JFXButton;
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
    @FXML
    private JFXButton clearButton;
    @FXML
    private JFXButton addItemButton;
    @FXML
    private JFXButton deleteItemButton;
    @FXML
    private JFXButton editItemButton;
    @FXML
    private JFXButton sortListButton;
    @FXML
    private MenuItem saveAsMenuItem;
    @FXML
    private MenuItem openMenuItem;
    @FXML
    private MenuItem quitMenuItem;
    @FXML
    private ChoiceBox<String> choiceBox;

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
        valueTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        serialNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    }


    @FXML
    public void addItemButtonClicked(ActionEvent actionEvent) {
        Stage stage = new Stage();
        stage.setTitle("Add Item");
        stage.setResizable(false);
        stage.setScene(sceneManager.getScene("AddItemWindow.fxml"));
        stage.show();

        inventoryTable.setItems(listModel.getItems());
    }

    @FXML
    public void deleteItemButtonClicked(ActionEvent actionEvent) {
        if (inventoryTable.getSelectionModel().getSelectedItem() != null) {
            listModel.getItems().remove(inventoryTable.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    public void editItemButtonClicked(ActionEvent actionEvent) throws IOException {
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

            inventoryTable.refresh();
        }
    }

    @FXML
    public void sortListButtonClicked(ActionEvent actionEvent) {
        Stage stage = new Stage();
        stage.setTitle("Sort List");
        stage.setResizable(false);
        stage.setScene(sceneManager.getScene("SortListWindow.fxml"));
        stage.show();

        inventoryTable.setItems(listModel.getItems());
    }

    @FXML
    public void saveAsMenuItemClicked(ActionEvent actionEvent) {
        FileManager save = new FileManager();
        save.saveFile(listModel);
    }

    @FXML
    public void openMenuItemClicked(ActionEvent actionEvent) {
        FileManager open = new FileManager();

        ArrayList<String> data = open.loadFile(listModel);

        // Yes there's an issue here, don't really know how I can go
        // about fixing it since I screwed myself over bcs of how I coded this
        listModel.getItems().clear();

        for (int i = 0; i < data.size(); i++) {
            BigDecimal curPrice = readValue(data, i);
            String curSerialNumber = readSerialNumber(data, i);
            String curName = readName(data, i);

            listModel.getItems().add(new InventoryItem(curPrice, curSerialNumber, curName));
        }

        inventoryTable.setItems(listModel.getItems());
    }

    private BigDecimal readValue(ArrayList<String> fileItem, int index) {
        String[] split = fileItem.get(index).split("\t");
        return BigDecimal.valueOf(Double.parseDouble(split[0].trim()))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private String readSerialNumber(ArrayList<String> fileItem, int index) {
        String[] split = fileItem.get(index).split("\t");
        return split[1].trim();
    }

    private String readName(ArrayList<String> fileItem, int index) {
        String[] split = fileItem.get(index).split("\t");
        return split[2].trim();
    }

    @FXML
    public void searchButtonClicked(ActionEvent actionEvent) {
        items.clear();
        tempList.clear();

        String entry = searchBarTextField.getText().toLowerCase().trim();

        if (!entry.equals("")) {
            items.setAll(getFoundEntries(listModel, tempList, entry));
            inventoryTable.setItems(items);
        }
    }

    // For tests
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

    @FXML
    public void resetButtonClicked(ActionEvent actionEvent) {
        searchBarTextField.setText("");

        tempList.clear();
        items.clear();

        inventoryTable.setItems(listModel.getItems());
    }

    @FXML
    public void quitMenuItemClicked(ActionEvent actionEvent) {
        // Only closes the main window, if other windows are open they need
        // to be closed manually
        Stage stage = (Stage) inventoryTable.getScene().getWindow();
        stage.close();
    }
}
