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
    void sortByValue() {
        // given
        InventoryListModel listModel = new InventoryListModel();
        SortListController sort = new SortListController(listModel, sceneManager);
        InventoryItem item1 = new InventoryItem(BigDecimal.valueOf(200.01), "18237ASBJF", "Yep Sort Test");
        InventoryItem item2 = new InventoryItem(BigDecimal.valueOf(10.01), "AUD1082245", "We Sorting");

        // add items to listModel
        listModel.getItems().add(item1);
        listModel.getItems().add(item2);

        // sort by price
        sort.sortByValue(listModel);

        // assert that the sort occurred by checking the appropriate indices
        assertSame(listModel.getItems().get(0), item2);
        assertSame(listModel.getItems().get(1), item1);
    }

    @Test
    void sortBySerialNumber() {
    }

    @Test
    void sortByName() {
    }
}