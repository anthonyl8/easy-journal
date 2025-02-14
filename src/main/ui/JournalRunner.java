package ui;

import model.Day;

public class JournalRunner {

    // EFFECTS: creates an instance of the JournalRunner console ui application
    public JournalRunner() {

    }

    // MODIFIES: this
    // EFFECTS: initializes the application with the starting values
    public void init() {

    }

    // EFFECTS: displays and processes inputs for the main menu
    public void handleMenu() {

    }

    // EFFECTS: displays a list of commands that can be used in the main menu
    public void displayMenu() {
        
    }

    // EFFECTS: processes the user's input in the main menu
    public void processMenuCommands(String input) {

    }

    // MODIFIES: this
    // EFFECTS: adds today to the list of days
    public void addToday() {

    }

    // MODIFIES: this
    // EFFECTS: adds a day to the list of days
    public void addNewDay() {

    }

    // REQUIRES: date is entered in the valid YYYY-MM-DD format
    // EFFECTS: creates a new day with the given year, month, and date, and adds this day
    //          to list of days. if day has already been added, do not add
    public void recordDay(String yearMonthDate) {

    }

    // EFFECTS: allows user to select a day that has already been added
    public void selectDay() {

    }

    // EFFECTS: enters the loop for the day menu, displaying statistics for the day
    //          and handling/processing inputs for the day menu
    public void enterDayMenu(Day day) {

    }

    // EFFECTS: displays a list of commands that can be used in the menu provided when
    //          under a specific day
    public void displayDayMenu() {

    }

    // MODIFIES: this
    // EFFECTS: processes the user's input in the day menu
    public void handleDayCommands(String input, Day day) {

    }

    // EFFECTS: prints all the events recorded under the given day. if nothing recorded
    //          yet, print that nothing was recorded so far.
    public void viewAllEventTitles(Day day) {

    }

    // MODIFIES: this
    // EFFECTS: uses user input to create a new event under the given day
    public void addEvent(Day day) {

    }

    // MODIFIES: this
    // EFFECTS: prints a closing message and marks the program as not running
    public void quitApplication() {

    }

    // EFFECTS: prints out a line of dashes to act as a divider
    private void printDivider() {

    }

}