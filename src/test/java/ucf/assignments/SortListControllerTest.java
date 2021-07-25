/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Gabriel Fernandez
 */

package ucf.assignments;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class SortListControllerTest {
    public SceneManager sceneManager;

    @Test
    void sortByValue_check_that_sort_occurred_by_checking_indices_of_listModel() {
        // given
        InventoryListModel listModel = new InventoryListModel();
        SortListController sort = new SortListController(listModel, sceneManager);
        InventoryItem item1 = new InventoryItem(BigDecimal.valueOf(200.01), "18237ASBJF", "I go last");
        InventoryItem item2 = new InventoryItem(BigDecimal.valueOf(10.01), "AUD1082245", "I go in the middle");
        InventoryItem item3 = new InventoryItem(BigDecimal.valueOf(5.05), "AAAAAAAAAA", "I go first");

        // add items to listModel
        listModel.getItems().add(item1);
        listModel.getItems().add(item2);
        listModel.getItems().add(item3);

        // sort by price
        sort.sortByValue(listModel);

        // assert that the sort occurred by checking the appropriate indices
        assertSame(listModel.getItems().get(0), item3);
        assertSame(listModel.getItems().get(1), item2);
        assertSame(listModel.getItems().get(2), item1);
    }

    @Test
    void sortBySerialNumber_check_that_sort_occurred_by_checking_indices_of_listModel() {
        // given
        InventoryListModel listModel = new InventoryListModel();
        SortListController sort = new SortListController(listModel, sceneManager);
        InventoryItem item1 = new InventoryItem(BigDecimal.valueOf(1412.45), "18237ASBJF", "This should be first");
        InventoryItem item2 = new InventoryItem(BigDecimal.valueOf(132.42), "AUD1082245", "This should be last");
        InventoryItem item3 = new InventoryItem(BigDecimal.valueOf(71.42), "AAAAAAAAAA", "This should be in the middle");

        // add items to listModel
        listModel.getItems().add(item1);
        listModel.getItems().add(item2);
        listModel.getItems().add(item3);

        // sort by serial number
        sort.sortBySerialNumber(listModel);

        // assert that the sort occurred by checking the appropriate indices
        assertSame(listModel.getItems().get(0), item1);
        assertSame(listModel.getItems().get(1), item3);
        assertSame(listModel.getItems().get(2), item2);
    }

    @Test
    void sortByName_check_that_sort_occurred_by_checking_indices_of_listModel() {
        // given
        InventoryListModel listModel = new InventoryListModel();
        SortListController sort = new SortListController(listModel, sceneManager);
        InventoryItem item1 = new InventoryItem(BigDecimal.valueOf(2), "18237ASBJF", "Mrs. Shelly likes cupcakes");
        InventoryItem item2 = new InventoryItem(BigDecimal.valueOf(3), "AUD1082245", "Run");
        InventoryItem item3 = new InventoryItem(BigDecimal.valueOf(1), "AAAAAAAAAA", "Mark Zuckerberg scares me");

        // add items to listModel
        listModel.getItems().add(item1);
        listModel.getItems().add(item2);
        listModel.getItems().add(item3);

        // sort by name
        sort.sortByName(listModel);

        // assert that the sort occurred by checking the appropriate indices
        assertSame(listModel.getItems().get(0), item3);
        assertSame(listModel.getItems().get(1), item1);
        assertSame(listModel.getItems().get(2), item2);
    }
}