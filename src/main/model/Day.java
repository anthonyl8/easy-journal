package model;

import java.util.List;

// Represents a unique day with a year, month, date, and list of events under this day
public class Day {
    
    // EFFECTS: creates a new day with given year, month, and date, and no events recorded
    public Day(int year, int month, int date) {
        // constructor
    }

    public int getYear() {
        return 0;
    }

    public int getMonth() {
        return 0;
    }

    public int getDate() {
        return 0;
    }

    public List<Event> getEvents() {
        // stub
        return null;
    }

    // EFFECTS: returns the event with the highest rating, or 1st event reached if 
    //          multiple are rated the same. if no events, return null
    public Event getMostHighlyRated() {
        // stub
        return null;
    }

    // EFFECTS: returns average rating of all events recorded under day, or 0 if no
    //          events recorded
    public int getAverageRating() {
        // stub
        return 0;
    }
    
    // REQUIRES: given month, year, and date form a valid date
    // EFFECTS: returns a string representation of the date in the form "YYYY-MM-DD"
    public String toString() {
        // stub
        return "";
    }

}
