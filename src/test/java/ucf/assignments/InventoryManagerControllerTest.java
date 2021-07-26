/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Gabriel Fernandez
 */

package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class InventoryManagerControllerTest {
    public SceneManager sceneManager;

    @Test
    void deleteItem_test_true_set1() {
        InventoryListModel listModel = new InventoryListModel();

        BigDecimal price = BigDecimal.valueOf(1125.00);
        String serialNumber = "ABCDE12345";
        String name = "Samsung Smart Fridge";

        InventoryItem item = new InventoryItem(price, serialNumber, name);

        listModel.getItems().add(item);
        listModel.getItems().remove(item);

        assertEquals(0, listModel.getItems().size());
    }

    @Test
    void deleteItem_test_true_set2() {
        InventoryListModel listModel = new InventoryListModel();

        BigDecimal price1 = BigDecimal.valueOf(1125.00);
        String serialNumber1 = "ABCDE12345";
        String name1 = "Samsung Smart Fridge";

        BigDecimal price2 = BigDecimal.valueOf(525.00);
        String serialNumber2 = "12345ABCDE";
        String name2 = "XBOX-2000";

        BigDecimal price3 = BigDecimal.valueOf(325.00);
        String serialNumber3 = "A1B2C3D4E5";
        String name3 = "Nintendo Botch";

        InventoryItem item1 = new InventoryItem(price1, serialNumber1, name1);
        InventoryItem item2 = new InventoryItem(price2, serialNumber2, name2);
        InventoryItem item3 = new InventoryItem(price3, serialNumber3, name3);

        listModel.getItems().add(item1);
        listModel.getItems().add(item2);
        listModel.getItems().add(item3);

        listModel.getItems().remove(item2);

        assertEquals(2, listModel.getItems().size());
    }

    @Test
    void getFoundEntries_test_that_items_that_contain_entry_string_are_returned_set1() {
        // given
        InventoryListModel listModel = new InventoryListModel();
        InventoryManagerController getMatchingItems = new InventoryManagerController(listModel, sceneManager);
        ObservableList<InventoryItem> expectedList = FXCollections.observableArrayList();
        ObservableList<InventoryItem> actualList;
        ObservableList<InventoryItem> tempList = FXCollections.observableArrayList();
        String entry = "yep";

        // populate the list (tableview)
        // we don't care about price
        listModel.getItems().add(new InventoryItem(BigDecimal.valueOf(1), "A1B2C3D4E5", "Yep this here string"));
        listModel.getItems().add(new InventoryItem(BigDecimal.valueOf(2), "B134671924", "This string also has yep"));
        listModel.getItems().add(new InventoryItem(BigDecimal.valueOf(3), "123UVGASFK", "Nope I don't like this yep"));
        listModel.getItems().add(new InventoryItem(BigDecimal.valueOf(4), "4321ASFGHH", "Whoever is reading this, give Death's Door on Steam a try, it's really good"));
        listModel.getItems().add(new InventoryItem(BigDecimal.valueOf(5), "NMMMYEPB4J", "Nap-time"));

        // Expected ObservableList<InventoryItem> should contain all items' serial numbers or names
        // that have String entry in them
        expectedList.add(listModel.getItems().get(0));
        expectedList.add(listModel.getItems().get(1));
        expectedList.add(listModel.getItems().get(2));
        expectedList.add(listModel.getItems().get(4));

        // assert equals expected vs. actual
        actualList = getMatchingItems.getFoundEntries(listModel, tempList, entry);
        assertEquals(expectedList, actualList);
    }

    @Test
    void getFoundEntries_test_that_items_that_contain_entry_string_are_returned_set2() {
        // given
        InventoryListModel listModel = new InventoryListModel();
        InventoryManagerController getMatchingItems = new InventoryManagerController(listModel, sceneManager);
        ObservableList<InventoryItem> expectedList = FXCollections.observableArrayList();
        ObservableList<InventoryItem> actualList;
        ObservableList<InventoryItem> tempList = FXCollections.observableArrayList();
        String entry = "123";

        // populate the list (tableview)
        // we don't care about price
        listModel.getItems().add(new InventoryItem(BigDecimal.valueOf(1), "A1B2C3D4E5", "Yep this here string"));
        listModel.getItems().add(new InventoryItem(BigDecimal.valueOf(2), "B134671924", "This string also has yep"));
        listModel.getItems().add(new InventoryItem(BigDecimal.valueOf(3), "123UVGASFK", "Nope I don't like this yep"));
        listModel.getItems().add(new InventoryItem(BigDecimal.valueOf(4), "4321ASFGHH", "Whoever is reading this, give Death's Door on Steam a try, it's really good"));
        listModel.getItems().add(new InventoryItem(BigDecimal.valueOf(5), "NMMMYEPB4J", "Nap-time"));

        // Expected ObservableList<InventoryItem> should contain all items' serial numbers or names
        // that have String entry in them
        expectedList.add(listModel.getItems().get(2));

        // assert equals expected vs. actual
        actualList = getMatchingItems.getFoundEntries(listModel, tempList, entry);
        assertEquals(expectedList, actualList);
    }

    @Test
    void getFoundEntries_test_that_items_that_contain_entry_string_are_returned_set3() {
        // given
        InventoryListModel listModel = new InventoryListModel();
        InventoryManagerController getMatchingItems = new InventoryManagerController(listModel, sceneManager);
        ObservableList<InventoryItem> expectedList = FXCollections.observableArrayList();
        ObservableList<InventoryItem> actualList;
        ObservableList<InventoryItem> tempList = FXCollections.observableArrayList();
        String entry = "a";

        // populate the list (tableview)
        // we don't care about price
        listModel.getItems().add(new InventoryItem(BigDecimal.valueOf(1), "A1B2C3D4E5", "Yep this here string"));
        listModel.getItems().add(new InventoryItem(BigDecimal.valueOf(2), "B134671924", "This string also has yep"));
        listModel.getItems().add(new InventoryItem(BigDecimal.valueOf(3), "123UVGASFK", "Nope I don't like this yep"));
        listModel.getItems().add(new InventoryItem(BigDecimal.valueOf(4), "4321ASFGHH", "Whoever is reading this, give Death's Door on Steam a try, it's really good"));
        listModel.getItems().add(new InventoryItem(BigDecimal.valueOf(5), "NMMMYEPB4J", "Nap-time"));

        // Expected ObservableList<InventoryItem> should contain all items' serial numbers or names
        // that have String entry in them
        expectedList.add(listModel.getItems().get(0));
        expectedList.add(listModel.getItems().get(1));
        expectedList.add(listModel.getItems().get(2));
        expectedList.add(listModel.getItems().get(3));
        expectedList.add(listModel.getItems().get(4));

        // assert equals expected vs. actual
        actualList = getMatchingItems.getFoundEntries(listModel, tempList, entry);
        assertEquals(expectedList, actualList);
    }

    @Test
    void getFoundEntries_test_that_items_that_contain_entry_string_are_returned_set4() {
        // given
        InventoryListModel listModel = new InventoryListModel();
        InventoryManagerController getMatchingItems = new InventoryManagerController(listModel, sceneManager);
        ObservableList<InventoryItem> expectedList = FXCollections.observableArrayList();
        ObservableList<InventoryItem> actualList;
        ObservableList<InventoryItem> tempList = FXCollections.observableArrayList();
        String entry = "z";

        // populate the list (tableview)
        // we don't care about price
        listModel.getItems().add(new InventoryItem(BigDecimal.valueOf(1), "A1B2C3D4E5", "Yep this here string"));
        listModel.getItems().add(new InventoryItem(BigDecimal.valueOf(2), "B134671924", "This string also has yep"));
        listModel.getItems().add(new InventoryItem(BigDecimal.valueOf(3), "123UVGASFK", "Nope I don't like this yep"));
        listModel.getItems().add(new InventoryItem(BigDecimal.valueOf(4), "4321ASFGHH", "Whoever is reading this, give Death's Door on Steam a try, it's really good"));
        listModel.getItems().add(new InventoryItem(BigDecimal.valueOf(5), "NMMMYEPB4J", "Nap-time"));

        // Expected ObservableList<InventoryItem> had nothing added to it
        // because the entry was not found anywhere

        // assert equals expected vs. actual
        actualList = getMatchingItems.getFoundEntries(listModel, tempList, entry);
        assertEquals(expectedList, actualList);
    }
}