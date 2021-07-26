/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Gabriel Fernandez
 */

package ucf.assignments;

import javafx.stage.FileChooser;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    // Open a FileChooser so the user can save their file
    public void saveFile(InventoryListModel listModel) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TSV file (.txt)", "*.txt"),
                new FileChooser.ExtensionFilter("HTML file (.html)", "*.html")
        );

        fileChooser.setTitle("Save Inventory List");
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            if (fileChooser.getSelectedExtensionFilter() != null
                    && fileChooser.getSelectedExtensionFilter().getExtensions() != null) {
                List<String> selectedExtension = fileChooser.getSelectedExtensionFilter().getExtensions();

                // Go to the appropriate function depending on the extension chosen
                if (selectedExtension.contains("*.txt")) {
                    writeToText(file.getAbsolutePath(), listModel);
                }
                else if (selectedExtension.contains("*.html")) {
                    writeToHTML(file.getAbsolutePath(), listModel);
                }
            }
        }
    }

    // Write to the created .txt file
    public void writeToText(String path, InventoryListModel listModel) {
        // Create the .txt as a TSV (Tab-Separated-Value) file
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            bw.write(String.format("%s\t%s\t%s", "Value ($)", "Serial Number", "Name"));
            bw.write("\n");
            for (InventoryItem items: listModel.getItems()) {
                bw.write(String.format("%s\t%s\t%s",
                        items.getPrice(), items.getSerialNumber(), items.getName()));
                bw.write("\n");
            }
            bw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Write to the created HTML file
    public void writeToHTML(String path, InventoryListModel listModel) {
        // Create the HTML by hardcoding the strings needed
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            bw.write("<html><head>\n");
            bw.write("<style>table, th, td {border: 1px solid black;}</style>\n");
            bw.write("</head><body>\n");
            bw.write("<table><tr><th>Value ($)</th>" +
                    "<th>Serial Number</th>" +
                    "<th>Name</th></tr>\n");
            // Loop through each item of the list and add to the HTML
            for (int i = 0; i < listModel.getItems().size(); i++) {
                bw.write("<tr><td>\n" + listModel.getItems().get(i).getPrice() + "</td>" +
                        "<td>" + listModel.getItems().get(i).getSerialNumber() + "</td>" +
                        "<td>" + listModel.getItems().get(i).getName() + "\n</td></tr>\n");
            }
            bw.write("</body></table></html>");
            bw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Open a new FileChooser for where the user can load a file into their application
    public ArrayList<String> loadFile(InventoryListModel listModel) {
        String fileExtension = "";
        ArrayList<String> getData = new ArrayList<>();
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TSV file (.txt)", "*.txt"),
                new FileChooser.ExtensionFilter("HTML file (.html)", "*.html")
        );

        fileChooser.setTitle("Load Inventory List");
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            // Clear the list for the new item that is about to be read
            listModel.getItems().clear();

            try {
                fileExtension = Files.probeContentType(file.toPath());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Call the appropriate function for the file's extension
        if (fileExtension.equals("text/plain")) {
            getData = loadFromText(file.getAbsolutePath());
        }
        else if (fileExtension.equals("text/html")) {
            getData = loadFromHTML(file.getAbsolutePath());
        }

        // Return the ArrayList of items from the file
        return getData;
    }

    // Read the .txt file and store the data into an ArrayList<String>
    public ArrayList<String> loadFromText(String path) {
        ArrayList<String> loadItems = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            // Read the header into a garbage String
            String garbage = br.readLine();
            String curLine = br.readLine();

            while(curLine != null) {
                loadItems.add(curLine);
                curLine = br.readLine();
            }

            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // Return the ArrayList
        return loadItems;
    }

    // Read the data from the HTML and store into an ArrayList<String>
    public ArrayList<String> loadFromHTML(String path) {
        ArrayList<String> loadItems = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            // Store the first couple of lines as garbage so we can
            // get to the data
            String garbage = br.readLine();
            garbage = br.readLine();
            garbage = br.readLine();
            garbage = br.readLine();

            // Loop until the last line of the HTML is read into garbage
            while(garbage != null) {
                garbage = br.readLine();
                if (garbage.equals("</body></table></html>")) {
                    break;
                }
                // Replace "</td><td>" with "\t" so that they can be read by the read...() functions
                // in InventoryManagerController
                String curLine = br.readLine().replace("</td><td>", "\t");
                loadItems.add(curLine);
                garbage = br.readLine();
            }

            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // Return the ArrayList
        return loadItems;
    }
}
