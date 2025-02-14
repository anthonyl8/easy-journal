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
                try {
                    sdf.parse(yearMonthDate);
                    recordedDay = dateRecord(yearMonthDate);
                    if (recordedDay == null) {
                        throw new NotYetAddedException();
                    } else {
                        enterDayMenu(recordedDay);
                        break;
                    }
                } catch (ParseException e) {
                    System.out.println("You typed an invalid date!");
                } catch (NotYetAddedException e) {
                    System.out.println("You haven't added that date yet!");
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
            printDivider();
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
        System.out.print("\n");
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
                System.out.print(event.getTitle());
                if (event.isStarred()) {
                    System.out.println(" *");
                } else {
                    System.out.println();
                }
            }
            System.out.println();
        }
        printDivider();
    }

    // MODIFIES: this
    // EFFECTS: uses user input to create a new event under the given day
    public void addEvent(Day day) {
        printDivider();
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
        // stub
    }

    // EFFECTS: prints the characteristics of the event
    public void viewEvent(Event event) {
        // stub
    }

    // EFFECTS: obtains and enters into the most highly rated event recorded under this day
    public void viewHighlight(Day day) {
        // stub
    }

    // EFFECTS: displays a list of commands that can be used in the menu provided when
    //          under a specific event
    public void displayEventMenu() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: processes the user's input in the event menu
    public void handleEventCommands(String input, Event event) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: adds a tag to the given event, creating a new tag if the tag doesn't
    //          already exist. if given event is already tagged under tag, then 
    //          does nothing (does not add a duplicate tag)
    public void addTag(Event event) {
        // stub
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