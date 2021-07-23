/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Gabriel Fernandez
 */

package ucf.assignments;

import javafx.stage.FileChooser;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileManager {
    public void saveFile(InventoryListModel listModel) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TSV file (.txt)", "*.txt"),
                new FileChooser.ExtensionFilter("HTML file (.html)", "*.html")
        );
        fileChooser.setTitle("Save Inventory List");
        fileChooser.setInitialFileName("InventoryList");

        File file = fileChooser.showSaveDialog(null);

        if (fileChooser.getSelectedExtensionFilter() != null
                && fileChooser.getSelectedExtensionFilter().getExtensions() != null) {
            List<String> selectedExtension = fileChooser.getSelectedExtensionFilter().getExtensions();

            if (selectedExtension.contains("*.txt")) {
                writeToText(file.getAbsolutePath(), listModel);
            }
            else if (selectedExtension.contains("*.html")) {
                writeToHTML(file.getAbsolutePath(), listModel);
            }
            // JSON?
        }
    }

    public void writeToText(String path, InventoryListModel listModel) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            bw.write(String.format("%s\t%s\t%s", "Value", "Serial Number", "Name"));
            bw.write("\n");
            for (InventoryItem items: listModel.getItems()) {
                bw.write(String.format("$%s\t%s\t%s",
                        items.getPrice(), items.getSerialNumber(), items.getName()));
                bw.write("\n");
            }
            bw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToHTML(String path, InventoryListModel listModel) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            bw.write("<html><head>");
            bw.write("<style>table, th, td {border: 1px solid black;}</style>");
            bw.write("</head><body>");
            bw.write("<table><tr><th>Value ($)</th>" +
                    "<th>Serial Number</th>" +
                    "<th>Name</th></tr>");
            for (int i = 0; i < listModel.getItems().size(); i++) {
                bw.write("<tr><td>" + listModel.getItems().get(i).getPrice() + "</td>" +
                        "<td>" + listModel.getItems().get(i).getSerialNumber() + "</td>" +
                        "<td>" + listModel.getItems().get(i).getName() + "</td></tr>");
            }
            bw.write("</body></table></html>");
            bw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFile() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TSV file (.txt)", "*.txt"),
                new FileChooser.ExtensionFilter("HTML file (.html)", "*.html")
        );
        fileChooser.setTitle("Load Inventory List");
        File file = fileChooser.showOpenDialog(null);

        // Find a way to differentiate between file types
        
    }
}
