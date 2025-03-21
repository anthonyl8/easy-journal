package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
// import java.awt.Image;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

// import javax.swing.ImageIcon;

import model.Day;
import model.Event;
import model.Journal;
import model.Tag;
import persistence.JsonReader;
import persistence.JsonWriter;
import exceptions.RatingOutOfBoundsException;

// SOURCE: There are many in-class sources I could have based my code off of. 
//         I found Lab 4: Flashcard Reviewer particularly helpful, so that's 
//         the one I chose.

// Represents an instance of the console based UI for EasyJournal.
public class ConsoleJournalRunner {

    private Journal journal;

    private Scanner scanner;
    private boolean isRunning;
    private SimpleDateFormat sdf;
    private static final String JSON_STORE = "./data/persistence/journal.json";
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;


    // EFFECTS: creates an instance of the JournalRunner console UI application
    public ConsoleJournalRunner() {
        init();

        printDivider();
        System.out.println("Welcome back!");
        printDivider();

        while (isRunning) {
            handleMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the application with the starting values
    public void init() {
        journal = new Journal();
        scanner = new Scanner(System.in);
        isRunning = true;
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: displays and processes inputs for the main menu
    public void handleMenu() {
        displayMenu();
        String input = this.scanner.nextLine();
        processMenuCommands(input);
    }

    // EFFECTS: displays a list of commands that can be used in the main menu
    public void displayMenu() {
        System.out.println("Please select an option\n");
        System.out.println("[t]: Add today");
        System.out.println("[a]: Add a new day");
        System.out.println("[d]: Delete a day");
        System.out.println("[e]: Select an existing day");
        System.out.println("[n]: View event statistics");
        System.out.println("[s]: Save journal to file");
        System.out.println("[l]: Load journal from file");
        System.out.println("[q]: Quit the application");
        printDivider();
    }

    // MODIFIES: this
    // EFFECTS: processes the user's input in the main menu
    @SuppressWarnings("methodlength")
    public void processMenuCommands(String input) {
        printDivider();
        switch (input) {
            case "t":
                addToday();
                break;
            case "a":
                addNewDay();
                break;
            case "d":
                deleteDay();
                break;
            case "e":
                selectDay();
                break;
            case "n":
                displayStatistics();
                break;
            case "s":
                saveJournal();
                break;
            case "l":
                loadJournal();
                break;
            case "q":
                quitApplication();
                break;
            default:
                System.out.println("Invalid option inputted. Please try again.");
                break;
        }
        printDivider();
    }

    // MODIFIES: this
    // EFFECTS: adds today to the list of days in journal, unless today has already 
    //          been added
    public void addToday() {
        LocalDate yearMonthDate = LocalDate.now();
        recordDay(yearMonthDate.toString());
    }

    // MODIFIES: this
    // EFFECTS: adds inputted day to the list of days in journal. if inputted date 
    //          is invalid or has already been entered, prompts user to re-enter a date
    public void addNewDay() {
        while (true) {
            System.out.println("Please enter the date in the following format: YYYY-MM-DD: ");
            printDivider();
            String yearMonthDate = this.scanner.nextLine();
            printDivider();
            try {
                sdf.parse(yearMonthDate);
                recordDay(yearMonthDate);
                break;
            } catch (ParseException e) {
                System.out.println("You entered an invalid date!");
            }
            printDivider();
        }
    }

    // REQUIRES: date is entered in the valid YYYY-MM-DD format
    // MODIFIES: this
    // EFFECTS: creates a new day with the given year, month, and date, and adds
    //          this day to list of days in journal if this date has not already
    //          been added
    public void recordDay(String yearMonthDate) {
        Day day = journal.dateRecord(yearMonthDate);
        List<Day> days = journal.getDays();
        if (!days.contains(day)) {
            String[] splitDate = yearMonthDate.split("-");
            int year = Integer.valueOf(splitDate[0]);
            int month = Integer.valueOf(splitDate[1]);
            int date = Integer.valueOf(splitDate[2]);
            day = new Day(year, month, date);
            days.add(day);
            System.out.println(yearMonthDate + " was successfully recorded!");
        } else {
            System.out.println(yearMonthDate + " has already been added!");
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes user given day from the journal
    public void deleteDay() {
        List<Day> days = journal.getDays();
        if (days.isEmpty()) {
            System.out.println("No days recorded yet!");
        } else {
            System.out.println("These are the currently recorded days: ");
            for (Day day : journal.getDays()) {
                System.out.println(day);
            }
            System.out.print("\nPlease enter the day to delete: ");
            String date = this.scanner.nextLine();
            printDivider();
            try {
                sdf.parse(date);
                Day day = journal.dateRecord(date);
                if (day != null) {
                    journal.removeDay(day);
                    System.out.println("Day was successfully deleted!");
                } else {
                    System.out.println("This day was not recorded to begin with!");
                }
            } catch (ParseException e) {
                System.out.println("You entered an invalid date!");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to select a day that has already been added to journal. 
    //          if no days have been added, tell user there are no days to pick from
    public void selectDay() {
        if (journal.getDays().isEmpty()) {
            System.out.println("No days to select!");
        } else {
            handleDateInput();
        }
    }

    // MODIFIES: this
    // EFFECTS: if user inputs a valid date, sends the user to the corresponding
    //          day in journal. otherwise, prompts the user to re-enter a date
    @SuppressWarnings("methodlength")
    public void handleDateInput() {
        String yearMonthDate;
        Day recordedDay;
        while (true) {
            System.out.println("Select one of the following dates by typing it: ");
            for (Day day : journal.getDays()) {
                System.out.println(day);
            }
            printDivider();
            yearMonthDate = this.scanner.nextLine();
            printDivider();
            try {
                sdf.parse(yearMonthDate);
                recordedDay = journal.dateRecord(yearMonthDate);
                if (recordedDay == null) {
                    System.out.println("You haven't added that date yet!");
                } else {
                    enterDayMenu(recordedDay);
                    break;
                }
            } catch (ParseException e) {
                System.out.println("You typed an invalid date!");
            }
            printDivider();
        }
    }

    // MODIFIES: this, day
    // EFFECTS: enters the loop for the day menu, displaying statistics for the day
    //          and handling/processing inputs for the day menu
    public void enterDayMenu(Day day) {
        String input = "";
        while (!input.equals("q")) {
            System.out.println("Date: " + day);
            System.out.println();
            System.out.println("Average rating: " + day.getAverageRating() + "/10");
            System.out.println();
            viewAllEventTitles(day);
            displayDayMenu();
            input = scanner.nextLine();
            handleDayCommands(input, day);
        }
    }

    // EFFECTS: displays a list of commands that can be used in the menu provided
    //          when under a specific day
    public void displayDayMenu() {
        System.out.println("Please select an option:\n");

        System.out.println("[event title]: view specified event");
        System.out.println("[i]: view most highly rated event in this day");
        System.out.println("[a]: add an event to this day");
        System.out.println("[d]: delete an event from this day");
        System.out.println("[q]: quit this day");
        printDivider();
    }

    // MODIFIES: this, day
    // EFFECTS: processes the user's input in the day menu
    public void handleDayCommands(String input, Day day) {
        if (!handleEventTitle(input, day)) {
            switch (input) {
                case "i":
                    viewHighlight(day);
                    break;
                case "a":
                    addEvent(day);
                    break;
                case "d":
                    deleteEvent(day);
                    break;
                case "q":
                    break;
                default:
                    printDivider();
                    System.out.println("Invalid option inputted. Please try again.");
                    printDivider();
                    break;
            }
        }
    }

    // MODIFIES: this, day
    // EFFECTS: checks if user input in the day menu is an event title. if it is, enters
    //          corresponding event's menu and returns true. otherwise, returns false.
    public boolean handleEventTitle(String input, Day day) {
        for (Event event : day.getEvents()) {
            if (event.getTitle().equals(input)) {
                printDivider();
                enterEventMenu(event);
                return true;
            }
        }
        return false;
    }

    // EFFECTS: prints all the events recorded under the given day. if nothing
    //          recorded yet, print that nothing was recorded so far.
    public void viewAllEventTitles(Day day) {
        List<Event> events = day.getEvents();
        if (events.isEmpty()) {
            System.out.println("Nothing recorded so far. Go have some fun!");
        } else {
            System.out.println("Events recorded so far:");
            for (Event event : events) {
                printTitle(event);
            }
            System.out.println();
        }
        printDivider();
    }

    // MODIFIES: this, day
    // EFFECTS: uses user input to create a new event under the given day
    public void addEvent(Day day) {
        printDivider();
        System.out.println("Please fill out the following fields: \n");
        String title = handleTitleInput(day);
        int rating = handleRatingInput();

        System.out.print("Quote: ");
        String quote = this.scanner.nextLine();
        // System.out.print("Image (file path): ");
        // Image image = new ImageIcon(this.scanner.nextLine()).getImage();

        Event event = new Event(title, rating, quote /* , image */);
        day.addEvent(event);
        System.out.println("Event successfully added!");
        printDivider();
    }
    
    // EFFECTS: Handles the title input for an event that is being newly created.
    //          If title length is shorter than 2 characters, prompts the user
    //          to enter a new title. Otherwise, returns user inputted title. 
    public String handleTitleInput(Day day) {
        String title;
        while (true) {
            System.out.print("Event title (min. 2 characters): ");
            title = this.scanner.nextLine();
            if (title.length() >= 2) {
                return title;
            }
            System.out.println("-> Inputted title is too short. Try again.");
        }
    }

    // EFFECTS: Handles the rating input for an event that is being newly created.
    //          If rating is not an integer between 1 and 10, prompts user to 
    //          re-enter. Otherwise, returns user inputted rating.
    public int handleRatingInput() {
        int rating;
        while (true) {
            try {
                System.out.print("Rating (from 1 to 10): ");
                String input = this.scanner.nextLine();
                rating = Integer.valueOf(input);
                checkRatingIsValid(rating);
                return rating;
            } catch (NumberFormatException | RatingOutOfBoundsException e) {
                System.out.println("-> You did not input an integer from 1 to 10. Try again.");
            }
        }
    }

    // EFFECTS: throws exception if rating is out of bounds, otherwise do nothing
    public void checkRatingIsValid(int rating) throws RatingOutOfBoundsException {
        if (rating < 1 || rating > 10) {
            throw new RatingOutOfBoundsException();
        }
    }

    // MODIFIES: this, day
    // EFFECTS: deletes a user selected event from the given day
    public void deleteEvent(Day day) {
        printDivider();
        System.out.print("Please enter the name of the event to delete: ");
        String name = this.scanner.nextLine();
        printDivider();
        Event eventToRemove = day.getEventFromTitle(name);
        if (eventToRemove != null) {
            day.removeEvent(eventToRemove);
            System.out.println("Event was successfully deleted!");
        } else {
            System.out.println("No event with this title exists under this day!");
        }
        printDivider();
    }

    // MODIFIES: this, event
    // EFFECTS: enters the loop for the event menu, displaying the event's
    //          characteristics and handling/processing inputs for the event menu
    public void enterEventMenu(Event event) {
        String input = "";
        while (!(input.equals("q"))) {
            viewEvent(event);
            displayEventMenu();
            input = this.scanner.nextLine();
            handleEventCommands(input, event);
        }
    }

    // EFFECTS: prints the characteristics of the event
    public void viewEvent(Event event) {
        printTitle(event);
        event.sortTags();
        List<Tag> eventTags = event.getTags();
        if (!(eventTags.isEmpty())) {
            System.out.print("TAGGED UNDER: ");
            for (Tag tag : eventTags) {
                printTag(tag);
            }
            System.out.println();
        }
        System.out.println("Rating (out of 10): " + event.getRating());
        System.out.println("Quote: " + event.getQuote());
        // display image later on
        printDivider();
    }

    // EFFECTS: prints the event's title, including a star depending on whether the
    //          event is starred
    public void printTitle(Event event) {
        System.out.print(event.getTitle());
        if (event.isStarred()) {
            System.out.println(" *");
        } else {
            System.out.println();
        }
    }

    // EFFECTS: prints a tag and the number of events under the tag
    public void printTag(Tag tag) {
        System.out.print(tag.getName() + " (" + tag.getNumEvents() + ")   ");
    }

    // MODIFIES: this, day
    // EFFECTS: obtains and enters into the most highly rated event recorded under
    //          this day. if no events recorded so far, tell user that nothing has
    //          been recorded yet.
    public void viewHighlight(Day day) {
        printDivider();
        Event event = day.getMostHighlyRated();
        if (event == null) {
            System.out.println("Nothing recorded yet!");
        } else {
            enterEventMenu(event);
        }
        printDivider();
    }

    // EFFECTS: displays a list of commands that can be used in the menu provided
    //          when under a specific event
    public void displayEventMenu() {
        System.out.println("Please select an option:\n");

        System.out.println("[a]: add a tag");
        System.out.println("[d]: delete a tag");
        System.out.println("[s]: star or unstar this event");
        System.out.println("[q]: quit this event");
        printDivider();
    }

    // MODIFIES: this, event
    // EFFECTS: processes the user's input in the event menu
    public void handleEventCommands(String input, Event event) {
        switch (input) {
            case "a":
                addTag(event);
                break;
            case "d":
                deleteTag(event);
                break;
            case "s":
                event.flipStar();
                printDivider();
                break;
            case "q":
                break;
            default:
                printDivider();
                System.out.println("Invalid option inputted. Please try again.");
                printDivider();
                break;
        }
    }

    // MODIFIES: this, event
    // EFFECTS: adds a tag to the given event, creating a new tag if the tag doesn't
    //          already exist. if given event is already tagged under tag, then
    //          does nothing (does not add a duplicate tag)
    public void addTag(Event event) {
        printDivider();
        System.out.print("Enter the name of the tag: ");
        String tagName = this.scanner.nextLine();
        printDivider();
        Tag tagToAdd = journal.getTagFromName(tagName);
        if (tagToAdd == null) {
            event.addTag(new Tag(tagName));
            System.out.println("Tag successfully added!");
        } else if (event.addTag(tagToAdd)) {
            System.out.println("Tag successfully added!");
        } else {
            System.out.println("Tag already added!");
        }
        printDivider();
    }

    // MODIFIES: this, event
    // EFFECTS: removes tag from given event if tag is associated with given event.
    public void deleteTag(Event event) {
        printDivider();
        System.out.print("Enter the name of the tag: ");
        String tagName = this.scanner.nextLine();
        printDivider();
        Tag tagToRemove = journal.getTagFromName(tagName);
        if (tagToRemove != null && event.removeTag(tagToRemove)) {
            System.out.println("Tag successfully removed!");
        } else {
            System.out.println("Tag was not associated with this event to begin with!");
        }
        printDivider();
    }

    // EFFECTS: prints aggregate level statistics about user's journal
    public void displayStatistics() {
        displayMostEventfulDays();
        System.out.println();
        displayBestDays();
        System.out.println();
        displayMostUsedTags();
    }

    // EFFECTS: prints days in user's journal with most events recorded. if no days
    //          added so far, tell user that no days have been recorded yet.
    public void displayMostEventfulDays() {
        System.out.println("Most eventful days: ");
        if (journal.getDays().isEmpty()) {
            System.out.println("No days recorded yet! Go wild!");
        } else {
            List<Day> busiestDays = journal.getMostEventfulDays();
            for (Day day : busiestDays) {
                System.out.println(day + " (" + day.getNumEvents() + ")");
            }
        }
    }

    // EFFECTS: prints most highly rated days in user's journal. if no days added
    //          so far, tell user that no days have been recorded yet.
    public void displayBestDays() {
        System.out.println("Top days: ");
        if (journal.getDays().isEmpty()) {
            System.out.println("No days recorded yet! Go wild!");
        } else {
            List<Day> topDays = journal.getTopDays();
            for (Day day : topDays) {
                System.out.println(day + " (" + day.getAverageRating() + "/10)");
            }
        }
    }

    // EFFECTS: prints most used tags for user's journal. if no tags added
    //          so far, tell user that no tags have been recorded yet.
    public void displayMostUsedTags() {
        System.out.println("Top tags: ");
        if (journal.getTags().isEmpty()) {
            System.out.println("No tags used yet! Go wild!");
        } else {
            List<Tag> topTags = journal.getTopTags();
            for (Tag tag : topTags) {
                printTag(tag);
            }
            System.out.println();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads journal from file
    public void loadJournal() {
        try {
            journal = jsonReader.read();
            System.out.println("Loaded journal from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: saves the journal to file
    public void saveJournal() {
        try {
            jsonWriter.open();
            jsonWriter.write(journal);
            jsonWriter.close();
            System.out.println("Saved journal to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: prints a closing message and marks the program as not running
    public void quitApplication() {
        System.out.println("Thanks for using EasyJournal!");
        System.out.println("Have a good day!");
        this.isRunning = false;
    }

    // EFFECTS: prints out a line of dashes to act as a divider
    private void printDivider() {
        System.out.println("------------------------------------");
    }

}