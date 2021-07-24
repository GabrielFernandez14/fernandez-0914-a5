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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
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

        if (inputIsValid(price, serialNumber, name)) {
            editErrorLabel.setText("");
            commitToList(price, serialNumber, name);
        }
    }

    private void commitToList(String price, String serialNumber, String name) {
        BigDecimal priceBigDecimal = BigDecimal.valueOf(Double.parseDouble(price))
                .setScale(2, RoundingMode.HALF_UP);

        selectedItem.setPrice(priceBigDecimal);
        selectedItem.setSerialNumber(serialNumber);
        selectedItem.setName(name);

        Stage stage = (Stage) editNameTextField.getScene().getWindow();
        stage.close();
    }

    private boolean inputIsValid(String price, String serialNumber, String name) {
        String alphaRegex = ".*[a-zA-Z].*";
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");

        Matcher serialNumberMatcher = pattern.matcher(serialNumber);
        boolean containsSpecialCharacters = serialNumberMatcher.find();

        ArrayList<String> serialNumbers = new ArrayList<>();
        for (int i = 0; i < listModel.getItems().size(); i++) {
            serialNumbers.add(listModel.getItems().get(i).getSerialNumber());
            // serialNumbers should exclude the selectedItem's serial number, or else
            // the user would have to change the SN every time they wanted to edit
            serialNumbers.removeIf(s -> s.contains(selectedItem.getSerialNumber()));
        }

        String priceFormatError = "Error: Price is not formatted correctly.";

        if (price.equals("") && serialNumber.equals("") && name.equals("")) {
            printError("Error: Cannot confirm edit for a blank item, please fill in the blanks.");
            return false;
        }
        else if (price.equals("") && serialNumber.equals("")) {
            printError("Error: Price and serial number are empty.");
            return false;
        }
        else if (price.equals("") && name.equals("")) {
            printError("Error: Price and name are empty.");
            return false;
        }
        else if (serialNumber.equals("") && name.equals("")) {
            printError("Error: Serial number and name are empty.");
            return false;
        }
        else if (price.equals("")) {
            printError("Error: Price is empty.");
            return false;
        }
        else if (serialNumber.equals("")) {
            printError("Error: Serial number is empty.");
            return false;
        }
        else if (name.equals("")) {
            printError("Error: Name is empty.");
            return false;
        }
        else if (name.length() < 2) {
            printError("Error: The new name must be at least two characters.");
            return false;
        }
        else if (name.length() > 256) {
            printError("Error: The new name exceeds the character limit.");
            return false;
        }
        else if (price.matches(alphaRegex) || containsSpecialCharacters) {
            printError(priceFormatError);
            return false;
        }
        else if (serialNumber.length() != 10 || containsSpecialCharacters) {
            printError("Error: Serial number is not formatted correctly.");
            return false;
        }
        else if (serialNumbers.contains(serialNumber)) {
            printError("Error: Serial number already exists.");
            return false;
        }

        return true;
    }

    private void printError(String prompt) {
        editErrorLabel.setText(prompt);
    }
}
