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

    private void writeToText(String path, InventoryListModel listModel) {
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

    private void writeToHTML(String path, InventoryListModel listModel) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            bw.write("<html><head>\n");
            bw.write("<style>table, th, td {border: 1px solid black;}</style>\n");
            bw.write("</head><body>\n");
            bw.write("<table><tr><th>Value ($)</th>" +
                    "<th>Serial Number</th>" +
                    "<th>Name</th></tr>\n");
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

        // Find a way to differentiate between file types
        if (file != null) {
            try {
                fileExtension = Files.probeContentType(file.toPath());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        //System.out.println(fileExtension);
        if (fileExtension.equals("text/plain")) {
            getData = loadFromText(file.getAbsolutePath(), listModel);
        }
        else if (fileExtension.equals("text/html")) {
            getData = loadFromHTML(file.getAbsolutePath(), listModel);
        }

        return getData;
    }

    private ArrayList<String> loadFromText(String path, InventoryListModel listModel) {
        ArrayList<String> loadItems = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
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

        return loadItems;
    }

    private ArrayList<String> loadFromHTML(String path, InventoryListModel listModel) {
        ArrayList<String> loadItems = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String garbage = br.readLine();
            garbage = br.readLine();
            garbage = br.readLine();
            garbage = br.readLine();
            while(garbage != null) {
                if (garbage.equals("</td></tr>")) {
                    break;
                }
                garbage = br.readLine();
                String curLine = br.readLine().replace("</td><td>", "\t");
                loadItems.add(curLine);
                garbage = br.readLine();
                /*
                "<tr><td>\n" + listModel.getItems().get(i).getPrice() + "</td>" +
                "<td>" + listModel.getItems().get(i).getSerialNumber() + "</td>" +
                "<td>" + listModel.getItems().get(i).getName() + "\n</td></tr>\n");
                 */
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return loadItems;
    }
}
