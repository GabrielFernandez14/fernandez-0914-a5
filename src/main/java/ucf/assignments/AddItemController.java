/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Gabriel Fernandez
 */

package ucf.assignments;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddItemController {
    private InventoryListModel listModel;
    private SceneManager sceneManager;

    @FXML
    private TextField priceTextField;
    @FXML
    private TextField serialNumberTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private Label errorLabel;

    // Get data from the SceneManager
    public AddItemController(InventoryListModel listModel, SceneManager sceneManager) {
        this.listModel = listModel;
        this.sceneManager = sceneManager;
    }

    @FXML
    private void confirmButtonClicked(ActionEvent actionEvent) {
        String price = priceTextField.getText();
        String serialNumber = serialNumberTextField.getText();
        String name = nameTextField.getText();

        // Check if any errors are in the input
        if (inputIsValid(listModel, price, serialNumber, name) == 1) {
            printError("Error: Cannot confirm edit for a blank item, please fill in the blanks.");
        }
        else if (inputIsValid(listModel, price, serialNumber, name) == 2) {
            printError("Error: Price and serial number are empty.");
        }
        else if (inputIsValid(listModel, price, serialNumber, name) == 3) {
            printError("Error: Price and name are empty.");
        }
        else if (inputIsValid(listModel, price, serialNumber, name) == 4) {
            printError("Error: Serial number and name are empty.");
        }
        else if (inputIsValid(listModel, price, serialNumber, name) == 5) {
            printError("Error: Price is empty.");
        }
        else if (inputIsValid(listModel, price, serialNumber, name) == 6) {
            printError("Error: Serial number is empty.");
        }
        else if (inputIsValid(listModel, price, serialNumber, name) == 7) {
            printError("Error: Name is empty.");
        }
        else if (inputIsValid(listModel, price, serialNumber, name) == 8) {
            printError("Error: Price is not formatted correctly.");
        }
        else if (inputIsValid(listModel, price, serialNumber, name) == 9) {
            printError("Error: Serial number is not formatted correctly.");
        }
        else if (inputIsValid(listModel, price, serialNumber, name) == 10) {
            printError("Error: Serial number already exists.");
        }
        else if (inputIsValid(listModel, price, serialNumber, name) == 11) {
            printError("Error: The new name must be at least two characters.");
        }
        else if (inputIsValid(listModel, price, serialNumber, name) == 12) {
            printError("Error: The new name exceeds the character limit.");
        }
        else {
            commitToList(price, serialNumber, name);
        }
    }

    // Reset values, close the window, and commit the new item to the list
    private void commitToList(String price, String serialNumber, String name) {
        BigDecimal priceBigDecimal = BigDecimal.valueOf(Double.parseDouble(price))
                .setScale(2, RoundingMode.HALF_UP);

        addToList(priceBigDecimal, serialNumber, name);

        priceTextField.clear();
        serialNumberTextField.clear();
        nameTextField.clear();
        errorLabel.setText("");

        Stage stage = (Stage) nameTextField.getScene().getWindow();
        stage.close();
    }

    // For testing purposes
    public void addToList(BigDecimal price, String serialNumber, String name) {
        listModel.getItems().add(new InventoryItem(price, serialNumber, name));
    }

    // Check that the input is valid, and return a corresponding int depending on the
    // error that occurred
    // (Yes I realize how disgusting this is, but it makes it really easy to test)
    public int inputIsValid(InventoryListModel listModel, String price, String serialNumber, String name) {
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

        // Because regex allows periods, need to check for duplicates
        int counter = 0;

        char[] charArray = new char[price.length()];

        for (int i = 0; i < price.length(); i++) {
            charArray[i] = price.charAt(i);
        }

        for (int i = 0; i < price.length(); i++) {
            if (charArray[i] == '.') {
                counter++;
            }
        }

        // all fields are empty
        if (price.equals("") && serialNumber.equals("") && name.equals("")) {
            return 1;
        }
        // value and sn are empty
        else if (price.equals("") && serialNumber.equals("")) {
            return 2;
        }
        // value and name are empty
        else if (price.equals("") && name.equals("")) {
            return 3;
        }
        // sn and name are empty
        else if (serialNumber.equals("") && name.equals("")) {
            return 4;
        }
        // value is empty
        else if (price.equals("")) {
            return 5;
        }
        // sn is empty
        else if (serialNumber.equals("")) {
            return 6;
        }
        // name is empty
        else if (name.equals("")) {
            return 7;
        }
        // price is incorrectly formatted
        else if (price.matches(alphaRegex) || priceSpecialCharacters || counter > 1) {
            return 8;
        }
        // sn is incorrectly formatted
        else if (serialNumber.length() != 10 || serialNumberSpecialCharacters) {
            return 9;
        }
        // sn is a duplicate
        else if (serialNumbers.contains(serialNumber)) {
            return 10;
        }
        // name is too small
        else if (name.length() < 2) {
            return 11;
        }
        // name is too large
        else if (name.length() > 256) {
            return 12;
        }

        // input is valid
        return 0;
    }

    private void printError(String prompt) {
        errorLabel.setText(prompt);
    }
}
