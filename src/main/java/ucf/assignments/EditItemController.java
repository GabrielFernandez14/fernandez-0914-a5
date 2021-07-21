/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Gabriel Fernandez
 */

package ucf.assignments;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditItemController {
    private InventoryListModel listModel;
    private SceneManager sceneManager;

    private final String error1 = "Error: Cannot confirm edit for a blank item, please fill in the blanks.";
    private final String error2 = "Error: Price and serial number are empty.";
    private final String error3 = "Error: Price and name are empty.";
    private final String error4 = "Error: Serial number and name are empty.";
    private final String error5 = "Error: Price is empty.";
    private final String error6 = "Error: Serial number is empty.";
    private final String error7 = "Error: Name is empty.";
    private final String error8 = "Error: The new name must be at least two characters.";
    private final String error9 = "Error: The new name exceeds the character limit.";
    private final String error10 = "Error: Price is not formatted correctly.";
    private final String error11 = "Error: Serial number is not formatted correctly.";

    @FXML
    private TextField editPriceTextField;
    @FXML
    private TextField editSerialNumberTextField;
    @FXML
    private TextField editNameTextField;
    @FXML
    private Label editErrorLabel;

    public EditItemController(InventoryListModel listModel, SceneManager sceneManager) {
        this.listModel = listModel;
        this.sceneManager = sceneManager;
    }

    //ToDo make it so that TextFields are initialized as the data retrieved from inventoryTable.selectionModel().getSelectedItem()

    @FXML
    public void editConfirmButtonClicked(ActionEvent actionEvent) {
        String price = editPriceTextField.getText();
        String serialNumber = editSerialNumberTextField.getText();
        String name = editNameTextField.getText();

        if (inputIsValid(price, serialNumber, name)) {
            commitToList(price, serialNumber, name);
        }
    }

    private void commitToList(String price, String serialNumber, String name) {
        BigDecimal priceBigDecimal = BigDecimal.valueOf(Double.parseDouble(price))
                .setScale(2, RoundingMode.HALF_UP);

        //ToDo make it so that the items are being added to the correct index
        listModel.getItems().add(new InventoryItem(priceBigDecimal, serialNumber, name));

        editPriceTextField.clear();
        editSerialNumberTextField.clear();
        editNameTextField.clear();

        Stage stage = (Stage) editNameTextField.getScene().getWindow();
        stage.close();
    }

    private boolean inputIsValid(String price, String serialNumber, String name) {
        String alphaRegex = ".*[a-zA-Z].*";
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");

        Matcher serialNumberMatcher = pattern.matcher(serialNumber);
        boolean containsSpecialCharacters = serialNumberMatcher.find();

        if (price.equals("") && serialNumber.equals("") && name.equals("")) {
            printError(error1);
            return false;
        }
        else if (price.equals("") && serialNumber.equals("")) {
            printError(error2);
            return false;
        }
        else if (price.equals("") && name.equals("")) {
            printError(error3);
            return false;
        }
        else if (serialNumber.equals("") && name.equals("")) {
            printError(error4);
            return false;
        }
        else if (price.equals("")) {
            printError(error5);
            return false;
        }
        else if (serialNumber.equals("")) {
            printError(error6);
            return false;
        }
        else if (name.equals("")) {
            printError(error7);
            return false;
        }
        else if (name.length() <= 2) {
            printError(error8);
            return false;
        }
        else if (name.length() > 256) {
            printError(error9);
            return false;
        }
        else if (price.matches(alphaRegex)) {
            printError(error10);
            return false;
        }
        else if (serialNumber.length() != 10 || containsSpecialCharacters) {
            printError(error11);
            return false;
        }

        // This is a very overcomplicated way of checking that
        // the input is valid as a BigDecimal
        try {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator('.');
            DecimalFormat df = new DecimalFormat("#0.00", symbols);
            df.setParseBigDecimal(true);
            df.parse(price);
        }
        catch (ParseException e) {
            printError(error10);
            return false;
        }

        return true;
    }

    private void printError(String prompt) {
        editErrorLabel.setText(prompt);
    }
}
