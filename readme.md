# Help Window
### Gabriel Fernandez COP3330
#### For Dr. Hollander, Juan, and Savannah, who are cool

### Using the Add Button
Pressing the add button will be one of the first things a rational user should attempt upon seeing this
program. Once pressed, the application will launch a new window where the user is prompted with
three TextFields, and a confirm button. The user can then enter their desired input and press the confirm 
button; however, if the input entered is invalid according to application specifications. The user will be
prompted with an appropriate error message at the bottom of the window. Once the user enters
input that is valid, the program will close the window, and the addition will become visible on the main
window's TableView.
#### Errors
- The user cannot commit their input if any (or all) of the TextFields are empty
- The Price TextField must be either an Integer or Double, (i.e. either 10 or 10.50 or 10.18022471401427).
- The Price TextField may only have one decimal, and can have no special characters
- The Serial Number TextField cannot be a duplicate with an already existing Serial Number in the
main window's TableView
- The Serial Number must follow the format specification of XXXXXXXXXX (length 10), where X is either a letter or
  a number. (Special characters are not allowed)
- The Name TextField must be 2 - 256 characters (inclusive), if the TextField is not within this character range, an
error will occur

### Using the Delete Button
The delete button is quite straightforward, once the user has entered at least one item into the list, the
user can then use their mouse to select the item on the TableView. Once an item has been selected, the user
can press the delete button on the bottom of the main window, and the selected item on the list will be removed.

### Using the Edit Button
The Edit Button works similarly to the Add Button, but it operates slightly differently. Primarily, the Edit window
will not launch unless the user has selected an item inside the TableView for editing. Once the user has selected
an item that they wish to edit, they can then press the Edit Item button, and a new window will pop up. This window is 
initialized with the selected item's data inside of the TextFields. The user can then make any desired changes and press the 
confirm button to close the Edit window and update the selected item's data on the selected location of the TableView. 
Like the Add Window, the user will not be able to proceed if any of the data does not meet the application's error 
specifications. 

### Sorting the Table
If the user wishes to sort the list by value, serial number or name, they must first populate the list. Then, the user can
press the Sort List button on the bottom of the main program. This will launch a separate window with three
buttons, "Sort by Value", "Sort by Serial Number", and "Sort by Name". These buttons do exactly as the names
imply, and once one of them has been pressed, the pop up window will close, and the items in the TableView
will be sorted accordingly.

### Search Bar
The Search Bar function is somewhat complicated, but operates as intended. In order to search an item, the user must first
populate the list to the desired extent. Once the list has been populated, the user can begin typing into the search bar.
Once satisfied, the user can press the Search Button.
#### The Search Button
Upon pressing the Search button, the input that the user has inputted into the TextField will be cleared, and then used 
to find the items that contain the inputted String. In simpler terms, if the program finds that the value of a serial number 
or name inside the list contains the string that the user has entered, the item will be displayed.
#### The Reset Button
Press this button to clear the search bar's field and display the original list in its entirety.

### The File Menu
To save the program, the user must first navigate to the menu bar at the top of the program and select the File
item. Once selected, the user will be prompted with three choices: "Save As...", "Open...", and "Quit".
#### Save As...
Upon selecting the Save As option, a FileChooser window will open. The user then will be able to save the file 
as either a TSV (Tab-Separated-Value) file as a .txt, or an HTML file (.html). Upon selecting the directory that
they wish to save their application, the user will select their choice of extension, and be able to save their file
to the desired location.
#### Open...
The open option menu launches another FileChooser window. Once launched, the user can then navigate through their
directories to find a previously saved application file of either a .txt or .html extension type. Once the user has
selected the file they wish to open, they can press the open button, and the file's contents will be loaded into the
application.
#### Quit
Closes the main window.
