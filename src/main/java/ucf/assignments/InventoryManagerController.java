/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Gabriel Fernandez
 */

package ucf.assignments;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Filter;

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
    private JFXButton searchButton;
    @FXML
    private MenuItem saveAsMenuItem;
    @FXML
    private MenuItem openMenuItem;
    @FXML
    private MenuItem quitMenuItem;
    @FXML
    private MenuItem searchNameMenuItem;

    private SceneManager sceneManager;
    private InventoryListModel listModel;
    private ObservableList<InventoryItem> items = FXCollections.observableArrayList();

    public InventoryManagerController (InventoryListModel listModel, SceneManager sceneManager) {
        this.listModel = listModel;
        this.sceneManager = sceneManager;
    }

    @Override
    public void initialize (URL location, ResourceBundle resources) {
        valueTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        serialNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    }


    @FXML
    public void addItemButtonClicked (ActionEvent actionEvent) {
        Stage stage = new Stage();
        stage.setTitle("Add Item");
        stage.setResizable(false);
        stage.setScene(sceneManager.getScene("AddItemWindow.fxml"));
        stage.show();

        inventoryTable.setItems(listModel.getItems());

        /* Can't get CSS working (because you need an actual CSS file dumbass)
        ScrollBar scrollBar = (ScrollBar) inventoryTable.lookup(".scroll-bar:horizontal");
        if (scrollBar != null) {
            scrollBar.setStyle("-fx-background-color: transparent;");
        }
         */
    }

    @FXML
    public void deleteItemButtonClicked (ActionEvent actionEvent) {
        if (inventoryTable.getSelectionModel().getSelectedItem() != null) {
            listModel.getItems().remove(inventoryTable.getSelectionModel().getSelectedItem());
        }
    }

    // ToDo need to make it so that it's not just an add method
    @FXML
    public void editItemButtonClicked (ActionEvent actionEvent) {
        if (inventoryTable.getSelectionModel().getSelectedItem() != null) {
            Stage stage = new Stage();
            stage.setTitle("Edit Item");
            stage.setResizable(false);
            stage.setScene(sceneManager.getScene("EditItemWindow.fxml"));
            stage.show();
        }

        //listModel.getItems().remove(inventoryTable.getSelectionModel().getSelectedItem());

        //inventoryTable.setItems(listModel.getItems());
    }

    @FXML
    public void sortListButtonClicked (ActionEvent actionEvent) {
        Stage stage = new Stage();
        stage.setTitle("Sort List");
        stage.setResizable(false);
        stage.setScene(sceneManager.getScene("SortListWindow.fxml"));
        stage.show();

        inventoryTable.setItems(listModel.getItems());
    }

    @FXML
    public void saveAsMenuItemClicked (ActionEvent actionEvent) {
        FileManager save = new FileManager();
        save.saveFile(listModel);
    }

    @FXML
    public void openMenuItemClicked (ActionEvent actionEvent) {
        FileManager open = new FileManager();

        ArrayList<String> data = open.loadFile(listModel);

        listModel.getItems().clear();

        for (int i = 0; i < data.size(); i++) {
            BigDecimal curPrice = readValue(data, i);
            String curSerialNumber = readSerialNumber(data, i);
            String curName = readName(data, i);

            listModel.getItems().add(new InventoryItem(curPrice, curSerialNumber, curName));
        }

        inventoryTable.setItems(listModel.getItems());
    }

    private BigDecimal readValue (ArrayList<String> fileItem, int index) {
        String[] split = fileItem.get(index).split("\t");
        return BigDecimal.valueOf(Double.parseDouble(split[0].trim()))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private String readSerialNumber (ArrayList<String> fileItem, int index) {
        String[] split = fileItem.get(index).split("\t");
        return split[1].trim();
    }

    private String readName (ArrayList<String> fileItem, int index) {
        String[] split = fileItem.get(index).split("\t");
        return split[2].trim();
    }

    /*
    @FXML
    public void searchBarTextFieldTyped(ActionEvent actionEvent) {
    }
     */

    // ToDo close, but not quite working yet
    @FXML
    public void searchBarTextFieldClicked (ActionEvent actionEvent) {
        SortedList<InventoryItem> sortedList = filterList();

        sortedList.comparatorProperty().bind(inventoryTable.comparatorProperty());
        inventoryTable.setItems(sortedList);
    }

    private SortedList<InventoryItem> filterList() {
        FilteredList<InventoryItem> filteredList = new FilteredList<>(items, b -> true);

        searchBarTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            SortedList<InventoryItem> sortedList = filterItems(filteredList, newValue);
            sortedList.comparatorProperty().bind(inventoryTable.comparatorProperty());
            inventoryTable.setItems(sortedList);
        });

        return new SortedList<>(filteredList);
    }

    private SortedList<InventoryItem> filterItems(FilteredList<InventoryItem> filteredList, String newValue) {
        filteredList.setPredicate(inventoryItem -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String toLowerCase = newValue.toLowerCase();

            if (inventoryItem.getSerialNumber().contains(toLowerCase)) {
                return true;
            }
            else if (inventoryItem.getName().contains(toLowerCase)) {
                return true;
            }
            return false;
        });

        return new SortedList<>(filteredList);
    }

    @FXML
    public void clearButtonClicked(ActionEvent actionEvent) {
        searchBarTextField.setText("");
    }

    @FXML
    public void quitMenuItemClicked (ActionEvent actionEvent) {
        // Only closes the main window, if other windows are open they need
        // to be closed manually
        Stage stage = (Stage) addItemButton.getScene().getWindow();
        stage.close();
    }
}
