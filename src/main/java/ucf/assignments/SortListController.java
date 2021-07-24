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

    public SortListController(InventoryListModel listModel, SceneManager sceneManager) {
        this.listModel = listModel;
        this.sceneManager = sceneManager;
    }

    @FXML
    private void sortByValueButtonClicked(ActionEvent actionEvent) {
        Comparator<InventoryItem> comparator = Comparator.comparing(InventoryItem::getPrice);
        FXCollections.sort(listModel.getItems(), comparator);

        Stage stage = (Stage) sortByValueButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void sortBySNButtonClicked(ActionEvent actionEvent) {
        Comparator<InventoryItem> comparator = Comparator.comparing(InventoryItem::getSerialNumber);
        FXCollections.sort(listModel.getItems(), comparator);

        Stage stage = (Stage) sortBySNButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void sortByNameButtonClicked(ActionEvent actionEvent) {
        Comparator<InventoryItem> comparator = Comparator.comparing(InventoryItem::getName);
        FXCollections.sort(listModel.getItems(), comparator);

        Stage stage = (Stage) sortByNameButton.getScene().getWindow();
        stage.close();
    }

}
