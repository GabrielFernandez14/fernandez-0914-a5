/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Gabriel Fernandez
 */

package ucf.assignments;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class AddItemControllerTest {
    public SceneManager sceneManager;

    @Test
    void addToList_check_that_valid_input_is_added_to_the_list_set1() {
        // The reason I'm only entering valid input is because the input
        // checker would have prevented it from being added if it was false
        // already, so this just checks if it gets added
        InventoryListModel listModel = new InventoryListModel();
        AddItemController add = new AddItemController(listModel, sceneManager);
        add.addToList(BigDecimal.valueOf(5000.99), "ASU1HG4BGG", "Nintendo Scam");
        assertEquals(1, listModel.getItems().size());
    }

    @Test
    void addToList_check_that_valid_input_is_added_to_the_list_set2() {
        InventoryListModel listModel = new InventoryListModel();
        AddItemController add = new AddItemController(listModel, sceneManager);
        // The program would have already rounded by this point, so this checks out
        add.addToList(BigDecimal.valueOf(84.18), "813GFFBAJL", "XBOX Trash");
        assertEquals(1, listModel.getItems().size());
    }

    @Test
    void addToList_check_that_valid_input_is_added_to_the_list_set3() {
        InventoryListModel listModel = new InventoryListModel();
        AddItemController add = new AddItemController(listModel, sceneManager);

        // ooh spicy, what if there's ALREADY another item in the list, wow
        listModel.getItems().add(new InventoryItem
                (BigDecimal.valueOf(10), "ABCDE12345", "YEP"));

        add.addToList(BigDecimal.valueOf(123.74), "8124GBAFAL", "Play-To-Win-Station");
        assertEquals(2, listModel.getItems().size());
    }

    @Test
    void inputIsValid_check_that_input_is_not_empty() {
        InventoryListModel listModel = new InventoryListModel();
        AddItemController add = new AddItemController(listModel, sceneManager);
        assertEquals(1, add.inputIsValid(listModel, "", "", ""));
    }

    @Test
    void inputIsValid_check_that_serial_number_is_ten_characters() {
        InventoryListModel listModel = new InventoryListModel();
        assertFalse(inputIsValidTest(listModel, "125.99", "THISISWAYLONGERTHANTENCHARACTERSWOOOOOOOOOOOO", "yep"));
    }

    @Test
    void inputIsValid_check_that_serial_number_has_no_special_chars() {
        InventoryListModel listModel = new InventoryListModel();
        assertFalse(inputIsValidTest(listModel,"125.99", "ABCD#12345", "yep"));
    }

    @Test
    void inputIsValid_check_that_serial_number_is_not_a_duplicate() {
        InventoryListModel listModel = new InventoryListModel();
        listModel.getItems().add(new InventoryItem(BigDecimal.valueOf(420.00), "ABCDEFGHIJ", "ruh roh duplicate"));
        assertFalse(inputIsValidTest(listModel,"125.99", "ABCDEFGHIJ", "yep"));
    }

    @Test
    void inputIsValid_check_that_serial_number_is_not_empty() {
        InventoryListModel listModel = new InventoryListModel();
        assertFalse(inputIsValidTest(listModel,"100", "", "yep"));
    }

    @Test
    void inputIsValid_check_that_serial_number_has_no_non_alphanumeric_chars_and_has_length_ten() {
        InventoryListModel listModel = new InventoryListModel();
        assertTrue(inputIsValidTest(listModel,"125.99", "ABCDE12345", "yep"));
    }

    @Test
    void inputIsValid_check_that_value_has_no_special_chars() {
        InventoryListModel listModel = new InventoryListModel();
        assertFalse(inputIsValidTest(listModel,"$@31&*(", "@&*##@(#**", "yep"));
    }

    @Test
    void inputIsValid_check_that_value_has_no_alpha_chars() {
        InventoryListModel listModel = new InventoryListModel();
        assertFalse(inputIsValidTest(listModel,"akfbksf", "ABCDE12345", "yep"));
    }

    @Test
    void inputIsValid_check_that_value_has_no_non_numeric_chars() {
        InventoryListModel listModel = new InventoryListModel();
        assertFalse(inputIsValidTest(listModel,"aslfjh^@&(#123", "ABCDE12345", "yep"));
    }

    @Test
    void inputIsValid_check_that_value_is_not_empty() {
        InventoryListModel listModel = new InventoryListModel();
        assertFalse(inputIsValidTest(listModel,"", "ABCDE12345", "yep"));
    }

    @Test
    void inputIsValid_check_that_value_is_valid_set1() {
        InventoryListModel listModel = new InventoryListModel();
        assertTrue(inputIsValidTest(listModel,"10", "ABCDE12345", "yep"));
    }

    @Test
    void inputIsValid_check_that_value_is_valid_set2() {
        InventoryListModel listModel = new InventoryListModel();
        assertTrue(inputIsValidTest(listModel,"10.724816946791246194621794", "ABCDE12345", "yep"));
    }

    @Test
    void inputIsValid_check_that_name_is_within_size_bounds_set1() {
        InventoryListModel listModel = new InventoryListModel();
        assertFalse(inputIsValidTest(listModel,"100", "ABCDE12345", "A"));
    }

    @Test
    void inputIsValid_check_that_name_is_within_size_bounds_set2() {
        InventoryListModel listModel = new InventoryListModel();
        assertFalse(inputIsValidTest(listModel,"100", "ABCDE12345", "ASGFASDVFHASGFASDVFHASFASHFALSFHVBASFLHVDAHFASDF" +
                "AFDHSDFHAADFHHASFHVASDFHASFKLASHKFDHKLAFHKLHKFLDASGFASDVFHASFASHFALSFHVBASFLHVDAHFASDFAFDHSDFHAADFHHASFHVASDFHASFKLASHKFDHKL" +
                "AFHKLHKFLDASGFASDVFHASFASHFALSFHVBASFLHVDAHFASDFAFDHSDFHAADFHHASFHVASDFHASFKLASHKFDHKLAFHKLHKFLDASGFASDVFHASFASHFALSFHVBASFL" +
                "HVDAHFASDFAFDHSDFHAADFHHASFHVASDFHASFKLASHKFDHKLAFHKLHKFLDASGFASDVFHASFASHFALSFHVBASFLHVDAHFASDFAFDHSDFHAADFHHASFHVASDFHASFK" +
                "LASHKFDHKLAFHKLHKFLDASGFASDVFHASFASHFALSFHVBASFLHVDAHFASDFAFDHSDFHAADFHHASFHVASDFHASFKLASHKFDHKLAFHKLHKFLDASGFASDVFHASFASHFA" +
                "LSFHVBASFLHVDAHFASDFAFDHSDFHAADFHHASFHVASDFHASFKLASHKFDHKLAFHKLHKFLDASGFASDVFHASFASHFALSFHVBASFLHVDAHFASDFAFDHSDFHAADFHHASFH" +
                "VASDFHASFKLASHKFDHKLAFHKLHKFLDASGFASDVFHASFASHFALSFHVBASFLHVDAHFASDFAFDHSDFHAADFHHASFHVASDFHASFKLASHKFDHKLAFHKLHKFLDASGFASDV" +
                "FHASFASHFALSFHVBASFLHVDAHFASDFAFDHSDFHAADFHHASFHVASDFHASFKLASHKFDHKLAFHKLHKFLDASFASHFALSFHVBASFLHVDAHFASDFAFDHSDFHAADFHHASFH" +
                "VASDFHASFKLASHKFDHKLAFHKLHKFLD"));
    }

    @Test
    void inputIsValid_check_that_name_is_not_empty() {
        InventoryListModel listModel = new InventoryListModel();
        assertFalse(inputIsValidTest(listModel,"100", "ABCDE12345", ""));
    }

    @Test
    void inputIsValid_check_that_name_is_valid() {
        InventoryListModel listModel = new InventoryListModel();
        assertTrue(inputIsValidTest(listModel,"100", "ABCDE12345", "Yep I'm valid"));
    }

    @Test
    void inputIsValid_multiple_errors_set1() {
        InventoryListModel listModel = new InventoryListModel();
        assertFalse(inputIsValidTest(listModel,"1%$@00", "ABC^#(5", "Y"));
    }

    @Test
    void inputIsValid_multiple_errors_set2() {
        InventoryListModel listModel = new InventoryListModel();
        assertFalse(inputIsValidTest(listModel,"I'm not a number", "ABCDE12345", ""));
    }

    @Test
    void inputIsValid_multiple_errors_set3() {
        InventoryListModel listModel = new InventoryListModel();
        assertFalse(inputIsValidTest(listModel,"100.", "*()ABCDEH1", "I"));
    }

    @Test
    void inputIsValid_multiple_errors_set4() {
        InventoryListModel listModel = new InventoryListModel();
        assertFalse(inputIsValidTest(listModel,"80741,9812", "123456789#", ""));
    }

    // Just the inputIsValid() function from my add method but with the printError() calls removed
    // and a new InventoryListModel class instantiated by the tests, pls no take points off ;(
    public boolean inputIsValidTest(InventoryListModel listModel, String price, String serialNumber, String name) {
        String alphaRegex = ".*[a-zA-Z].*";
        Pattern patternPrice = Pattern.compile("[^0-9.]");
        Pattern patternSerialNumber = Pattern.compile("[^a-zA-Z0-9]");

        Matcher priceNumberMatcher = patternPrice.matcher(price);
        boolean priceSpecialCharacters = priceNumberMatcher.find();

        Matcher serialNumberMatcher = patternSerialNumber.matcher(serialNumber);
        boolean serialNumberSpecialCharacters = serialNumberMatcher.find();

        ArrayList<String> serialNumbers = new ArrayList<>();
        for (int i = 0; i < listModel.getItems().size(); i++) {
            serialNumbers.add(listModel.getItems().get(i).getSerialNumber());
        }

        if (price.equals("") && serialNumber.equals("") && name.equals("")) {
            return false;
        }
        else if (price.equals("") && serialNumber.equals("")) {
            return false;
        }
        else if (price.equals("") && name.equals("")) {
            return false;
        }
        else if (serialNumber.equals("") && name.equals("")) {
            return false;
        }
        else if (price.equals("")) {
            return false;
        }
        else if (price.matches(alphaRegex) || priceSpecialCharacters) {
            return false;
        }
        else if (serialNumber.equals("")) {
            return false;
        }
        else if (name.equals("")) {
            return false;
        }
        else if (name.length() < 2) {
            return false;
        }
        else if (name.length() > 256) {
            return false;
        }
        else if (serialNumber.length() != 10 || serialNumberSpecialCharacters) {
            return false;
        }
        else return !serialNumbers.contains(serialNumber);
    }
}