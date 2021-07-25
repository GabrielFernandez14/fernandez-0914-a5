/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Gabriel Fernandez
 */

package ucf.assignments;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {
    // Dear tester, if you want to test multiple times (for some ungodly reason), please be sure
    // to erase the contents of files writtenToTextTest.txt and writtenToHTMLTest.html upon each test,
    // the tests will always return as passed regardless of whether you do or don't, just thought you should know

    @Test
    void writeToText_test_that_correct_input_is_written_to_txt_file_and_that_load_loads_up_in_correct_format() {
        // given
        FileManager file = new FileManager();
        InventoryListModel listModel = new InventoryListModel();
        String expectedPath = "src\\test\\resources\\writeToTextTest.txt";

        // populate listModel
        listModel.getItems().add(new InventoryItem
                (BigDecimal.valueOf(100.99), "YEPYEPYEPN", "That's a lotta yep"));
        listModel.getItems().add(new InventoryItem
                (BigDecimal.valueOf(2410.42), "ASKjbfa132", "Kinda weird SerialNumber not gonna lie"));
        listModel.getItems().add(new InventoryItem
                (BigDecimal.valueOf(38471.12), "01huaosjbc", "Still weird"));
        listModel.getItems().add(new InventoryItem
                (BigDecimal.valueOf(9.23), "Ewwwwwwwww", "Disgustang"));
        listModel.getItems().add(new InventoryItem
                (BigDecimal.valueOf(4122.99), "YEPYEPYEPP", "Back to yep I guess"));

        // write to file
        file.writeToText(expectedPath, listModel);

        // create an expected ArrayList<String> of how the file should display
        // the results
        ArrayList<String> expectedFileList = new ArrayList<>();
        expectedFileList.add("100.99\tYEPYEPYEPN\tThat's a lotta yep");
        expectedFileList.add("2410.42\tASKjbfa132\tKinda weird SerialNumber not gonna lie");
        expectedFileList.add("38471.12\t01huaosjbc\tStill weird");
        expectedFileList.add("9.23\tEwwwwwwwww\tDisgustang");
        expectedFileList.add("4122.99\tYEPYEPYEPP\tBack to yep I guess");

        // assert that the expected ArrayList<String> matches the ArrayList<String> returned
        // by loadFromText(), (see InventoryListModel for how the program reads the data into the table)
        assertEquals(expectedFileList, file.loadFromText(expectedPath));
    }

    @Test
    void writeToHTML_test_that_correct_input_is_written_to_html_file_and_that_load_loads_up_in_correct_format() {
        // given
        FileManager file = new FileManager();
        InventoryListModel listModel = new InventoryListModel();
        String expectedPath = "src\\test\\resources\\writeToHTMLTest.html";

        // populate listModel
        listModel.getItems().add(new InventoryItem
                (BigDecimal.valueOf(14.12), "TstForHTML", "Yeah, we testing"));
        listModel.getItems().add(new InventoryItem
                (BigDecimal.valueOf(194.21), "asudgIWHB2", "You thought I'd put normal names?"));
        listModel.getItems().add(new InventoryItem
                (BigDecimal.valueOf(1199.99), "0123yhanfa", "You thought wrong"));
        listModel.getItems().add(new InventoryItem
                (BigDecimal.valueOf(125.55), "12gebsesad2", "Despite the suffering this project caused me"));
        listModel.getItems().add(new InventoryItem
                (BigDecimal.valueOf(91.45), "12372gSIKK", "I'm still alive"));

        // write to file
        file.writeToHTML(expectedPath, listModel);

        // create an expected ArrayList<String> of how the file should display
        // the results after it is loaded
        ArrayList<String> expectedFileList = new ArrayList<>();
        expectedFileList.add("14.12\tTstForHTML\tYeah, we testing");
        expectedFileList.add("194.21\tasudgIWHB2\tYou thought I'd put normal names?");
        expectedFileList.add("1199.99\t0123yhanfa\tYou thought wrong");
        expectedFileList.add("125.55\t12gebsesad2\tDespite the suffering this project caused me");
        expectedFileList.add("91.45\t12372gSIKK\tI'm still alive");

        // assert that the expected ArrayList<String> matches the ArrayList<String> returned
        // by loadFromText(), (see InventoryListModel for how the program reads the data into the table)
        assertEquals(expectedFileList, file.loadFromHTML(expectedPath));
    }
}