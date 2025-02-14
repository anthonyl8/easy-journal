package ui;

import java.awt.Image;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.ImageIcon;

import model.Day;
import model.Event;
import model.Tag;

import exceptions.AlreadyAddedException;
import exceptions.NotYetAddedException;
import exceptions.RatingOutOfBoundsException;

// SOURCE: There are many in-class sources I could have based my code off of. 
//         I found Lab 4: Flashcard Reviewer particularly helpful, so that's 
//         the one I chose.

// Represents an instance of the console based UI for EasyJournal.
public class JournalRunner {

    private List<Day> days;
    private List<Tag> tags;

    private Scanner scanner;
    private boolean isRunning;
    private SimpleDateFormat sdf;

    // EFFECTS: creates an instance of the JournalRunner console ui application
    public JournalRunner() {
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
        days = new ArrayList<Day>();
        tags = new ArrayList<Tag>();
        scanner = new Scanner(System.in);
        isRunning = true;
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
    }

    // EFFECTS: displays and processes inputs for the main menu
    public void handleMenu() {
        displayMenu();
        String input = this.scanner.nextLine();
        processMenuCommands(input);
    }

    // EFFECTS: displays a list of commands that can be used in the main menu
    public void displayMenu() {
        System.out.println("Please select an option");
        System.out.println("[t]: Add today");
        System.out.println("[a]: Add a new day");
        System.out.println("[s]: Select an existing day");
        System.out.println("[n]: view event statistics");
        System.out.println("[q]: Quit the application");
        printDivider();
    }

    // EFFECTS: processes the user's input in the main menu
    public void processMenuCommands(String input) {
        printDivider();
        switch (input) {
            case "t":
                addToday();
                break;
            case "a":
                addNewDay();
                break;
            case "s":
                selectDay();
                break;
            case "n":
                displayStatistics();
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
    // EFFECTS: adds today to the list of days
    public void addToday() {
        LocalDate yearMonthDate = LocalDate.now();
        try {
            recordDay(yearMonthDate.toString());
        } catch (AlreadyAddedException e) {
            System.out.println("Today has already been added!");
        }

    }

    // MODIFIES: this
    // EFFECTS: adds a day to the list of days
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
            } catch (AlreadyAddedException e) {
                System.out.println("That day has already been added!");
            }
            printDivider();
        }
    }

    // REQUIRES: date is entered in the valid YYYY-MM-DD format
    // EFFECTS: creates a new day with the given year, month, and date, and adds this day
    //          to list of days. if day has already been added, throws AlreadyAddedException
    public void recordDay(String yearMonthDate) throws AlreadyAddedException {
        if (dateRecord(yearMonthDate) != null) {
            throw new AlreadyAddedException();
        } else {
            String[] splitDate = yearMonthDate.split("-");
            int year = Integer.valueOf(splitDate[0]);
            int month = Integer.valueOf(splitDate[1]);
            int date = Integer.valueOf(splitDate[2]);

            Day day = new Day(year, month, date);
            days.add(day);
            System.out.println(day + " was successfully recorded!");
        }
    }

    // EFFECTS: returns corresponding day if given date has already been recorded,
    //          otherwise returns null
    public Day dateRecord(String yearMonthDate) {
        String[] splitDate = yearMonthDate.split("-");
        int year = Integer.valueOf(splitDate[0]);
        int month = Integer.valueOf(splitDate[1]);
        int date = Integer.valueOf(splitDate[2]);
        for (Day day : days) {
            if (year == day.getYear() && month == day.getMonth() && date == day.getDate()) {
                return day;
            }
        }
        return null;
    }

    // EFFECTS: allows user to select a day that has already been added
    public void selectDay() {
        if (days.isEmpty()) {
            System.out.println("No days to select!");
        } else {
            String yearMonthDate;
            Day recordedDay;
            while (true) {
                System.out.println("Select one of the following dates by typing it: ");
                for (Day day : days) {
                    System.out.println(day);
                }
                printDivider();
                yearMonthDate = this.scanner.nextLine();
                printDivider();
                try {
                    sdf.parse(yearMonthDate);
                    recordedDay = dateRecord(yearMonthDate);
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
    }

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

    // EFFECTS: displays a list of commands that can be used in the menu provided when
    //          under a specific day
    public void displayDayMenu() {
        System.out.println("Please select an option:\n");
        
        System.out.println("[event title]: view specified event");
        System.out.println("[i]: view most highly rated event in this day");
        System.out.println("[a]: add an event to this day");
        System.out.println("[q]: quit this day");
        printDivider();
    }

    // MODIFIES: this
    // EFFECTS: processes the user's input in the day menu
    public void handleDayCommands(String input, Day day) {
        printDivider();
        for (Event event : day.getEvents()) {
            String title = event.getTitle();
            if (title.equals(input)) {
                enterEventMenu(event);
                return;
            }
        }
        switch (input) {
            case "i":
                viewHighlight(day);
                break;
            case "a":
                addEvent(day);
                break;
            case "q":
                break;
            default:
                System.out.println("Invalid option inputted. Please try again.");
                break;
        }
        printDivider();
    }

    // EFFECTS: prints all the events recorded under the given day. if nothing recorded
    //          yet, print that nothing was recorded so far.
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

    // MODIFIES: this
    // EFFECTS: uses user input to create a new event under the given day
    public void addEvent(Day day) {
        System.out.println("Please fill out the following fields: ");
        System.out.print("Event title: ");
        String title = this.scanner.nextLine();
        int rating;
        while (true) {
            try {
                System.out.print("Rating (from 1 to 10): ");
                String input = this.scanner.nextLine();
                rating = Integer.valueOf(input);
                if (rating < 1 || rating > 10) {
                    throw new RatingOutOfBoundsException();
                }
                break;
            } catch (NumberFormatException | RatingOutOfBoundsException e) {
                System.out.println("You did not input an integer from 1 to 10. Try again.");
            }
        }

        System.out.print("Quote: ");
        String quote = this.scanner.nextLine();
        System.out.print("Image (file path): ");
        Image image = new ImageIcon(this.scanner.nextLine()).getImage();

        Event event = new Event(title, rating, quote, image);
        day.addEvent(event);
        System.out.println("Event successfully added!"); 
        printDivider();
    }

    // EFFECTS: enters the loop for the event menu, displaying the event's characteristics
    //          and handling/processing inputs for the event menu
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
        List<Tag> tags = event.getTags();
        if (!(tags.isEmpty())) {
            System.out.print("TAGGED UNDER: ");
            for (Tag tag : tags) {
                printTag(tag);
            }
            System.out.println();
        }
        System.out.println("Rating (out of 10): " + event.getRating());
        System.out.println("Quote: " + event.getQuote());
        // !!! IMAGE
        printDivider();
    }

    // EFFECTS: prints the event's title, including a star depending on whether the event
    //          is starred
    public void printTitle(Event event) {
        System.out.print(event.getTitle());
        if (event.isStarred()) {
            System.out.println(" *");
        } else {
            System.out.println();
        }
    }

    // EFFECTS: prints a tag and the number of events under the tagged
    public void printTag(Tag tag) {
        System.out.print(tag.getName() + " (" + tag.getNumEvents() + ")   ");
    }

    // EFFECTS: obtains and enters into the most highly rated event recorded under this day
    public void viewHighlight(Day day) {
        Event event = day.getMostHighlyRated();
        if (event == null) {
            System.out.println("Nothing recorded yet!");
        } else {
            enterEventMenu(event);
        }
    }

    // EFFECTS: displays a list of commands that can be used in the menu provided when
    //          under a specific event
    public void displayEventMenu() {
        System.out.println("Please select an option:\n");
        
        System.out.println("[a]: add a tag");
        System.out.println("[s]: star or unstar this event");
        System.out.println("[q]: quit this event");
        printDivider();
    }

    // MODIFIES: this
    // EFFECTS: processes the user's input in the event menu
    public void handleEventCommands(String input, Event event) {
        switch (input) {
            case "a":
                addTag(event);
                break;
            case "s":
                event.flipStar();
                break;
            case "q":
                break;
            default:
                System.out.println("Invalid option inputted!");
                break;
        }
        printDivider();
    }

    // MODIFIES: this
    // EFFECTS: adds a tag to the given event, creating a new tag if the tag doesn't
    //          already exist. if given event is already tagged under tag, then 
    //          does nothing (does not add a duplicate tag)
    public void addTag(Event event) {
        System.out.print("Enter the name of the tag: ");
        String tagName = this.scanner.nextLine();
        Tag tagToAdd = existingTag(tagName);
        if (tagToAdd == null) {
            tagToAdd = new Tag(tagName);
            tags.add(tagToAdd);
        }

        if (event.addTag(tagToAdd)) {
            System.out.println("Tag successfully added!");
        } else {
            System.out.println("Tag already added!");
        }
        
        printDivider();
    }

    // EFFECTS: returns the tag whose name matches the given tag name, or null if no 
    //          tag's name matches the given tag name
    public Tag existingTag(String tagName) {
        Tag existing = null;
        for (Tag tag : tags) {
            if (tag.getName().equals(tagName)) {
                existing = tag;
                break;
            }
        }
        return existing;
    }

    // EFFECTS: prints aggregate level statistics about user's journal
    public void displayStatistics() {
        displayMostEventfulDays();
        System.out.println();
        displayBestDays();
        System.out.println();
        displayMostUsedTags();
    }

    // EFFECTS: prints days in user's journal with most events recorded
    public void displayMostEventfulDays() {
        System.out.println("Most eventful days: ");
        if (days.isEmpty()) {
            System.out.println("No days recorded yet! Go wild!");
        } else {
            List<Day> busiestDays = getMostEventfulDays();
            for (Day day : busiestDays) {
                System.out.println(day + " (" + day.getNumEvents() + ")");
            }
        }
    }

    // REQUIRES: at least one day in list of days
    // EFFECTS: gets top 3 days with most events recorded under them, or all days in list 
    //          (depending on which one is smaller)
    public List<Day> getMostEventfulDays() {
        days.sort((Day d1, Day d2) -> {
            return d2.getNumEvents() - d1.getNumEvents();
        });
        int end = Math.min(3, days.size());
        return days.subList(0, end);
    }

    // EFFECTS: prints most highly rated days in user's journal
    public void displayBestDays() {
        System.out.println("Top days: ");
        if (days.isEmpty()) {
            System.out.println("No days recorded yet! Go wild!");
        } else {
            List<Day> topDays = getTopDays();
            for (Day day : topDays) {
                System.out.println(day + " (" + day.getAverageRating() + "/10)");
            }
        }
    }

    // REQUIRES: at least one day in list of days
    // EFFECTS: gets top 3 most highly rated days in list of days, or all days in list 
    //          (depending on which one is smaller)
    public List<Day> getTopDays() {
        days.sort((Day d1, Day d2) -> {
            return Double.compare(d2.getAverageRating(), d1.getAverageRating());
        });
        int end = Math.min(3, days.size());
        return days.subList(0, end);
    }

    // EFFECTS: prints most used tags for user's journal
    public void displayMostUsedTags() {
        System.out.println("Top tags: ");
        if (tags.isEmpty()) {
            System.out.println("No tags used yet! Go wild!");
        } else {
            List<Tag> topTags = getTopTags();
            for (Tag tag : topTags) {
                printTag(tag);
            }
            System.out.println();
        }
    }

    // REQUIRES: at least one tag in list of tags
    // EFFECTS: gets top 3 most used tags in list of tags, or all tags in list 
    //          (depending on which one is smaller)
    public List<Tag> getTopTags() {
        tags.sort((Tag t1, Tag t2) -> {
            return t2.getNumEvents() - t1.getNumEvents();
        });
        int end = Math.min(3, tags.size());
        return tags.subList(0, end);
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