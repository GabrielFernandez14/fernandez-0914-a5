/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Gabriel Fernandez
 */

package ucf.assignments;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class EditItemControllerTest {
    // Only need one of these for the duplicate test
    private final InventoryItem selectedItem = new InventoryItem
            (BigDecimal.valueOf(1), "IsDuplicat", "Don't care");

    @Test
    void inputIsValid_all_fields_are_empty() {
        InventoryListModel listModel = new InventoryListModel();
        EditItemController edit = new EditItemController();
        assertEquals(1, edit.inputIsValid
                (listModel, selectedItem, "", "", ""));
    }

    @Test
    void inputIsValid_value_and_serial_number_are_empty() {
        InventoryListModel listModel = new InventoryListModel();
        EditItemController edit = new EditItemController();
        assertEquals(2, edit.inputIsValid
                (listModel, selectedItem, "", "", "Not empty"));
    }

    @Test
    void inputIsValid_value_and_name_are_empty() {
        InventoryListModel listModel = new InventoryListModel();
        EditItemController edit = new EditItemController();
        assertEquals(3, edit.inputIsValid
                (listModel, selectedItem, "", "ImNotEmpty", ""));
    }

    @Test
    void inputIsValid_serial_number_and_name_are_empty() {
        InventoryListModel listModel = new InventoryListModel();
        EditItemController edit = new EditItemController();
        assertEquals(4, edit.inputIsValid
                (listModel, selectedItem, "100.00", "", ""));
    }

    @Test
    void inputIsValid_value_is_empty() {
        InventoryListModel listModel = new InventoryListModel();
        EditItemController edit = new EditItemController();
        assertEquals(5, edit.inputIsValid
                (listModel, selectedItem, "", "ABCDE12345", "Not empty"));
    }

    @Test
    void inputIsValid_serial_number_is_empty() {
        InventoryListModel listModel = new InventoryListModel();
        EditItemController edit = new EditItemController();
        assertEquals(6, edit.inputIsValid
                (listModel, selectedItem, "100.00", "", "Not empty"));
    }

    @Test
    void inputIsValid_name_is_empty() {
        InventoryListModel listModel = new InventoryListModel();
        EditItemController edit = new EditItemController();
        assertEquals(7, edit.inputIsValid
                (listModel, selectedItem, "100.00", "ABCDE12345", ""));
    }

    @Test
    void inputIsValid_price_has_more_than_one_decimal() {
        InventoryListModel listModel = new InventoryListModel();
        EditItemController edit = new EditItemController();
        assertEquals(8, edit.inputIsValid
                (listModel, selectedItem, "100.00.00.00.", "ABCDE12345", "Not empty"));
    }

    @Test
    void inputIsValid_price_is_not_valid_and_has_alpha_chars() {
        InventoryListModel listModel = new InventoryListModel();
        EditItemController edit = new EditItemController();
        assertEquals(8, edit.inputIsValid
                (listModel, selectedItem, "100A.", "ABCDE12345", "Not empty"));
    }

    @Test
    void inputIsValid_price_is_not_valid_and_has_special_chars() {
        InventoryListModel listModel = new InventoryListModel();
        EditItemController edit = new EditItemController();
        assertEquals(8, edit.inputIsValid
                (listModel, selectedItem, "100#@*#&102.00", "ABCDE12345", "Not empty"));
    }

    @Test
    void inputIsValid_price_is_not_valid_and_is_non_numeric() {
        InventoryListModel listModel = new InventoryListModel();
        EditItemController edit = new EditItemController();
        assertEquals(8, edit.inputIsValid
                (listModel, selectedItem, "12ajdb&2jb1", "ABCDE12345", "Not empty"));
    }

    @Test
    void inputIsValid_price_is_not_valid_is_non_numeric_and_has_multiple_decimals() {
        InventoryListModel listModel = new InventoryListModel();
        EditItemController edit = new EditItemController();
        assertEquals(8, edit.inputIsValid
                (listModel, selectedItem, "12.ajdb.&2j..b1", "ABCDE12345", "Not empty"));
    }

    @Test
    void inputIsValid_price_is_valid_and_returns_zero() {
        InventoryListModel listModel = new InventoryListModel();
        EditItemController edit = new EditItemController();
        assertEquals(0, edit.inputIsValid
                (listModel, selectedItem, "12.00", "ABCDE12345", "Not empty"));
    }

    @Test
    void inputIsValid_serial_number_has_non_alphanumeric_characters() {
        InventoryListModel listModel = new InventoryListModel();
        EditItemController edit = new EditItemController();
        assertEquals(9, edit.inputIsValid
                (listModel, selectedItem, "10", "A@B(@cDE81", "Not empty"));
    }

    @Test
    void inputIsValid_serial_number_is_larger_than_ten() {
        InventoryListModel listModel = new InventoryListModel();
        EditItemController edit = new EditItemController();
        assertEquals(9, edit.inputIsValid
                (listModel, selectedItem, "10", "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "Not empty"));
    }

    @Test
    void inputIsValid_serial_number_is_smaller_than_ten() {
        InventoryListModel listModel = new InventoryListModel();
        EditItemController edit = new EditItemController();
        assertEquals(9, edit.inputIsValid
                (listModel, selectedItem, "10", "ABCD", "Not empty"));
    }

    @Test
    void inputIsValid_serial_number_is_not_length_ten_and_is_non_alphanumeric() {
        InventoryListModel listModel = new InventoryListModel();
        EditItemController edit = new EditItemController();
        assertEquals(9, edit.inputIsValid
                (listModel, selectedItem, "10", "A*@#_B183&^@(CD", "Not empty"));
    }

    @Test
    void inputIsValid_serial_number_a_duplicate_of_an_existing_serial_number() {
        InventoryListModel listModel = new InventoryListModel();
        EditItemController edit = new EditItemController();

        listModel.getItems().add(new InventoryItem
                (BigDecimal.valueOf(12), "ABCDEFGHIJ", "Duplicate check"));

        assertEquals(10, edit.inputIsValid
                (listModel, selectedItem, "10", "ABCDEFGHIJ", "Not empty"));
    }

    @Test
    void inputIsValid_serial_number_a_duplicate_of_item_that_is_being_edited_and_returns_zero() {
        InventoryListModel listModel = new InventoryListModel();
        EditItemController edit = new EditItemController();

        assertEquals(0, edit.inputIsValid
                (listModel, selectedItem, "10", "IsDuplicat", "Not empty"));
    }

    @Test
    void inputIsValid_serial_number_is_valid_and_returns_zero() {
        InventoryListModel listModel = new InventoryListModel();
        EditItemController edit = new EditItemController();

        assertEquals(0, edit.inputIsValid
                (listModel, selectedItem, "10", "ABCDE12345", "Not empty"));
    }

    @Test
    void inputIsValid_name_is_only_one_character() {
        InventoryListModel listModel = new InventoryListModel();
        EditItemController edit = new EditItemController();

        assertEquals(11, edit.inputIsValid
                (listModel, selectedItem, "10.00", "ABCDE12345", "N"));
    }

    @Test
    void inputIsValid_name_exceeds_256_char_limit() {
        InventoryListModel listModel = new InventoryListModel();
        EditItemController edit = new EditItemController();

        assertEquals(12, edit.inputIsValid
                (listModel, selectedItem, "10.00", "ABCDE12345", "DHJSAFASFAFASF" +
                        "ASFKDHAVSKFHADHJSAFASFAFASFASFKDHAVSKFHASKDFASDHJSAFASFAFASFASFKDHAVSKFHASKDFAS" +
                        "DHJSAFASFAFASFASFKDHAVSKFHASKDFASDHJSAFASFAFASFASFKDHAVSKFHASKDFASDHJSAFASFAFAS" +
                        "FASFKDHAVSKFHASKDFASDHJSAFASFAFASFASFKDHAVSKFHASKDFASDHJSAFASFAFASFASFKDHAVSKFH" +
                        "ASKDFASDHJSAFASFAFASFASFKDHAVSKFHASKDFASDHJSAFASFAFASFASFKDHAVSKFHASKDFASDHJSAF" +
                        "ASFAFASFASFKDHAVSKFHASKDFASDHJSAFASFAFASFASFKDHAVSKFHASKDFASDHJSAFASFAFASFASFKD" +
                        "HAVSKFHASKDFASDHJSAFASFAFASFASFKDHAVSKFHASKDFASDHJSAFASFAFASFASFKDHAVSKFHASKDFA" +
                        "SDHJSAFASFAFASFASFKDHAVSKFHASKDFASDHJSAFASFAFASFASFKDHAVSKFHASKDFASDHJSAFASFAFA" +
                        "SFASFKDHAVSKFHASKDFASDHJSAFASFAFASFASFKDHAVSKFHASKDFASDHJSAFASFAFASFASFKDHAVSKF" +
                        "HASKDFASDHJSAFASFAFASFASFKDHAVSKFHASKDFASDHJSAFASFAFASFASFKDHAVSKFHASKDFASDHJSA" +
                        "FASFAFASFASFKDHAVSKFHASKDFASDHJSAFASFAFASFASFKDHAVSKFHASKDFASDHJSAFASFAFASFASFK" +
                        "DHAVSKFHASKDFASDHJSAFASFAFASFASFKDHAVSKFHASKDFASSKDFAS"));
    }

    @Test
    void inputIsValid_name_is_valid_and_returns_zero() {
        InventoryListModel listModel = new InventoryListModel();
        EditItemController edit = new EditItemController();

        assertEquals(0, edit.inputIsValid
                (listModel, selectedItem, "10.00", "ABCDE12345", "Valid"));
    }

    @Test
    void inputIsValid_multiple_errors_check_that_error_hierarchy_is_maintained_set1() {
        InventoryListModel listModel = new InventoryListModel();
        EditItemController edit = new EditItemController();

        assertEquals(6, edit.inputIsValid
                (listModel, selectedItem, "1#$0.00", "", "Valid"));
    }

    @Test
    void inputIsValid_multiple_errors_check_that_error_hierarchy_is_maintained_set2() {
        InventoryListModel listModel = new InventoryListModel();
        EditItemController edit = new EditItemController();

        assertEquals(8, edit.inputIsValid
                (listModel, selectedItem, "1$@0.00", "ABCDE12345", "N"));
    }

    @Test
    void inputIsValid_multiple_errors_check_that_error_hierarchy_is_maintained_set3() {
        InventoryListModel listModel = new InventoryListModel();
        EditItemController edit = new EditItemController();

        assertEquals(9, edit.inputIsValid
                (listModel, selectedItem, "10.00", "ABC@!DE12345", "."));
    }

    @Test
    void inputIsValid_multiple_errors_check_that_error_hierarchy_is_maintained_set4() {
        InventoryListModel listModel = new InventoryListModel();
        EditItemController edit = new EditItemController();

        assertEquals(3, edit.inputIsValid
                (listModel, selectedItem, "", "*@&^#SJBDDH", ""));
    }

    @Test
    void commitEditItem_check_that_selectedItem_is_updated_with_the_validated_data_set1() {
        // given
        InventoryListModel listModel = new InventoryListModel();
        EditItemController edit = new EditItemController();

        BigDecimal price = BigDecimal.valueOf(100.23);
        String serialNumber = "ABCDE12345";
        String name = "Update item";

        InventoryItem selectedItemLocal = new InventoryItem
                (BigDecimal.valueOf(10.00), "A1B2C3D4E5", "Yep");

        // add to list
        listModel.getItems().add(selectedItemLocal);

        // Update selectedItemLocal with commitEditItem()
        edit.commitEditItem(selectedItemLocal, price, serialNumber, name);

        // set the index of selectedItemLocal with the new dat
        // this accomplishes what setItems() to the tableview does
        listModel.getItems().set(0, selectedItemLocal);

        // Check that the item was updated
        assertSame(price, listModel.getItems().get(0).getPrice());
        assertSame(serialNumber, listModel.getItems().get(0).getSerialNumber());
        assertSame(name, listModel.getItems().get(0).getName());
    }

    @Test
    void commitEditItem_check_that_selectedItem_is_updated_with_the_validated_data_set2() {
        // given
        InventoryListModel listModel = new InventoryListModel();
        EditItemController edit = new EditItemController();

        BigDecimal price = BigDecimal.valueOf(10023.45);
        String serialNumber = "810Yiahfa1";
        String name = "Update me pls";

        InventoryItem selectedItemLocal = new InventoryItem
                (BigDecimal.valueOf(1238.42), "WOOOOOOOOO", "Muckdate");

        // add to list
        listModel.getItems().add(selectedItemLocal);

        // Update selectedItemLocal with commitEditItem()
        edit.commitEditItem(selectedItemLocal, price, serialNumber, name);

        // set the index of selectedItemLocal with the new data
        listModel.getItems().set(0, selectedItemLocal);

        // Check that the item was updated
        assertSame(price, listModel.getItems().get(0).getPrice());
        assertSame(serialNumber, listModel.getItems().get(0).getSerialNumber());
        assertSame(name, listModel.getItems().get(0).getName());
    }

    @Test
    void commitEditItem_check_that_selectedItem_is_updated_with_the_validated_data_set3() {
        // given
        InventoryListModel listModel = new InventoryListModel();
        EditItemController edit = new EditItemController();

        BigDecimal price = BigDecimal.valueOf(134.65);
        String serialNumber = "PlsUpdteBB";
        String name = "woot";

        InventoryItem selectedItemLocal = new InventoryItem
                (BigDecimal.valueOf(123814421741248.74), "NopeNopeNo", "Actually, no");

        // add to list
        listModel.getItems().add(selectedItemLocal);

        // Update selectedItemLocal with commitEditItem()
        edit.commitEditItem(selectedItemLocal, price, serialNumber, name);

        // set the index of selectedItemLocal with the new data
        listModel.getItems().set(0, selectedItemLocal);

        // Check that the item was updated
        assertSame(price, listModel.getItems().get(0).getPrice());
        assertSame(serialNumber, listModel.getItems().get(0).getSerialNumber());
        assertSame(name, listModel.getItems().get(0).getName());
    }
}