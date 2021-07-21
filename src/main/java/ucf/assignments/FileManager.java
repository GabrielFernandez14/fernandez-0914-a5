package ucf.assignments;

import javafx.stage.FileChooser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
    private InventoryListModel listModel;

    public String saveFile() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter tsvExtFilter = new FileChooser.ExtensionFilter
                ("TSV file (*.txt)", "*.txt");
        FileChooser.ExtensionFilter htmlExtFilter = new FileChooser.ExtensionFilter
                ("HTML file (.html)", ".html");

        fileChooser.getExtensionFilters().addAll(tsvExtFilter, htmlExtFilter);
        fileChooser.setTitle("Save Inventory List");
        fileChooser.setInitialFileName("InventoryList");

        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            return file.getAbsolutePath();
        }

        return "";
    }

    public static void writeToFile(String path, InventoryListModel listModel) {
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
}
