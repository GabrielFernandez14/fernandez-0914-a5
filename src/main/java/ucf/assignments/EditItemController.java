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

public class EditItemController {
    private InventoryListModel listModel;
    private InventoryItem selectedItem;
    private InventoryManagerController controller;

    @FXML
    private TextField editPriceTextField;
    @FXML
    private TextField editSerialNumberTextField;
    @FXML
    private TextField editNameTextField;
    @FXML
    private Label editErrorLabel;

    public void setData(InventoryManagerController parent, InventoryListModel listModel, InventoryItem selectedItem) {
        this.controller = parent;
        this.listModel = listModel;
        this.selectedItem = selectedItem;

        this.editPriceTextField.setText(selectedItem.getPrice().toString());
        this.editSerialNumberTextField.setText(selectedItem.getSerialNumber());
        this.editNameTextField.setText(selectedItem.getName());
    }

    @FXML
    private void editConfirmButtonClicked(ActionEvent actionEvent) {
        String price = editPriceTextField.getText();
        String serialNumber = editSerialNumberTextField.getText();
        String name = editNameTextField.getText();

        if (inputIsValid(listModel, selectedItem, price, serialNumber, name) == 1) {
            printError("Error: Cannot confirm edit for a blank item, please fill in the blanks.");
        }
        else if (inputIsValid(listModel, selectedItem, price, serialNumber, name) == 2) {
            printError("Error: Price and serial number are empty.");
        }
        else if (inputIsValid(listModel, selectedItem, price, serialNumber, name) == 3) {
            printError("Error: Price and name are empty.");
        }
        else if (inputIsValid(listModel, selectedItem, price, serialNumber, name) == 4) {
            printError("Error: Serial number and name are empty.");
        }
        else if (inputIsValid(listModel, selectedItem, price, serialNumber, name) == 5) {
            printError("Error: Price is empty.");
        }
        else if (inputIsValid(listModel, selectedItem, price, serialNumber, name) == 6) {
            printError("Error: Serial number is empty.");
        }
        else if (inputIsValid(listModel, selectedItem, price, serialNumber, name) == 7) {
            printError("Error: Name is empty.");
        }
        else if (inputIsValid(listModel, selectedItem, price, serialNumber, name) == 8) {
            printError("Error: Price is not formatted correctly.");
        }
        else if (inputIsValid(listModel, selectedItem, price, serialNumber, name) == 9) {
            printError("Error: Serial number is not formatted correctly.");
        }
        else if (inputIsValid(listModel, selectedItem, price, serialNumber, name) == 10) {
            printError("Error: Serial number already exists.");
        }
        else if (inputIsValid(listModel, selectedItem, price, serialNumber, name) == 11) {
            printError("Error: The new name must be at least two characters.");
        }
        else if (inputIsValid(listModel, selectedItem, price, serialNumber, name) == 12) {
            printError("Error: The new name exceeds the character limit.");
        }
        // return is 0
        else {
            commitToList(price, serialNumber, name);
        }
    }

    private void commitToList(String price, String serialNumber, String name) {
        BigDecimal priceBigDecimal = BigDecimal.valueOf(Double.parseDouble(price))
                .setScale(2, RoundingMode.HALF_UP);

        // For testing purposes
        commitEditItem(selectedItem, priceBigDecimal, serialNumber, name);

        Stage stage = (Stage) editNameTextField.getScene().getWindow();
        stage.close();
    }

    public void commitEditItem(InventoryItem selectedItem, BigDecimal price, String serialNumber, String name) {
        selectedItem.setPrice(price);
        selectedItem.setSerialNumber(serialNumber);
        selectedItem.setName(name);
    }

    // This function is a clone of inputIsValid() in AddItemController() except
    // the selectedItem's serial number is excluded from the ArrayList that checks for duplicates
    public int inputIsValid(InventoryListModel listModel, InventoryItem selectedItem, String price, String serialNumber, String name) {
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
            // serialNumbers should exclude the selectedItem's serial number, or else
            // the user would have to change the SN every time they wanted to edit
            serialNumbers.removeIf(s -> s.contains(selectedItem.getSerialNumber()));
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

        return 0;
    }

    private void printError(String prompt) {
        editErrorLabel.setText(prompt);
    }
}
