/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Gabriel Fernandez
 */

package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// Basically just creates an ObservableList<InventoryItem>, thanks prof
public class InventoryListModel {
    public ObservableList<InventoryItem> items;

    public InventoryListModel() {
        this.items = FXCollections.observableArrayList();
    }

    public InventoryListModel(ObservableList<InventoryItem> items) {
        this.items = items;
    }

    public ObservableList<InventoryItem> getItems() {
        return items;
    }
}
