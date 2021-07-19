/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Gabriel Fernandez
 */

package ucf.assignments;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class InventoryManagerController {
    @FXML
    private TableView inventoryTable;
    @FXML
    private TableColumn valueTableColumn;
    @FXML
    private TableColumn serialNumberTableColumn;
    @FXML
    private TableColumn nameTableColumn;
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
}
