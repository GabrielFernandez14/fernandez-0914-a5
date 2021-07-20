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
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.LocalDateStringConverter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Formatter;
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
    private JFXButton addItemButton;
    @FXML
    private JFXButton deleteItemButton;
    @FXML
    private MenuItem saveAsMenuItem;
    @FXML
    private MenuItem openMenuItem;
    @FXML
    private MenuItem quitMenuItem;
    @FXML
    private MenuItem searchNameMenuItem;
    @FXML
    private MenuItem searchSNMenuItem;

    private SceneManager sceneManager;
    private InventoryListModel listModel;
    private ObservableList<InventoryItem> items;

    public InventoryManagerController(InventoryListModel listModel, SceneManager sceneManager) {
        this.listModel = listModel;
        this.sceneManager = sceneManager;

        items = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        valueTableColumn.setCellValueFactory(new PropertyValueFactory<InventoryItem, BigDecimal>("value"));
        valueTableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));

        serialNumberTableColumn.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("serialNumber"));
        serialNumberTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        nameTableColumn.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("name"));
        nameTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        inventoryTable.getSortOrder().add(valueTableColumn);
        inventoryTable.getSortOrder().add(serialNumberTableColumn);
        inventoryTable.getSortOrder().add(nameTableColumn);
    }

    @FXML
    public void saveAsMenuItemClicked(ActionEvent actionEvent) {
    }
    @FXML
    public void openMenuItemClicked(ActionEvent actionEvent) {
    }
    @FXML
    public void quitMenuItemClicked(ActionEvent actionEvent) {
    }
    @FXML
    public void searchNameMenuItemClicked(ActionEvent actionEvent) {
    }
    @FXML
    public void searchSNMenuItemClicked(ActionEvent actionEvent) {
    }
    @FXML
    public void addItemButtonClicked(ActionEvent actionEvent) {
    }
    @FXML
    public void deleteItemButtonClicked(ActionEvent actionEvent) {
    }
}
