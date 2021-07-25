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

    public AddItemController(InventoryListModel listModel, SceneManager sceneManager) {
        this.listModel = listModel;
        this.sceneManager = sceneManager;
    }

    @FXML
    private void confirmButtonClicked(ActionEvent actionEvent) {
        String price = priceTextField.getText();
        String serialNumber = serialNumberTextField.getText();
        String name = nameTextField.getText();

        if (inputIsValid(price, serialNumber, name)) {
            commitToList(price, serialNumber, name);
        }
    }

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

    public void addToList(BigDecimal price, String serialNumber, String name) {
        listModel.getItems().add(new InventoryItem(price, serialNumber, name));
    }

    public boolean inputIsValid(String price, String serialNumber, String name) {
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
        else if (price.matches(alphaRegex) || priceSpecialCharacters) {
            printError(priceFormatError);
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
        else if (serialNumber.length() != 10 || serialNumberSpecialCharacters) {
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
        errorLabel.setText(prompt);
    }
}
