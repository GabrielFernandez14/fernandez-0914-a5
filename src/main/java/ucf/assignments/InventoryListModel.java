package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.event.ListDataListener;

// Last time I use Dr. Hollander's code I swear
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
