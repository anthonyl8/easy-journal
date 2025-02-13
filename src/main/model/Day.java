package model;

import java.util.List;
import java.util.ArrayList;

// Represents a unique day with a year, month, date, and list of events under this day
public class Day {
    
    private int year;
    private int month;
    private int date;
    private List<Event> events;

    // REQUIRES: given month, year, and date form a valid date
    // EFFECTS: creates a new day with given year, month, and date, and no events recorded
    public Day(int year, int month, int date) {
        this.year = year;
        this.month = month;
        this.date = date;
        events = new ArrayList<Event>();
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDate() {
        return date;
    }

    public List<Event> getEvents() {
        return events;
    }

    // REQUIRES: event is not already in list of events recorded under day
    // EFFECTS: adds event to list of events recorded under day
    public void addEvent(Event event) {
        events.add(event);
    }

    // EFFECTS: returns the event with the highest rating, or 1st event reached if 
    //          multiple are rated the same. if no events, return null
    public Event getMostHighlyRated() {
        if (events.isEmpty()) {
            return null;
        } else {
            Event highest = events.get(0);
            int highestRating = highest.getRating();
            Event current;
            int currentRating;
            for (int i = 1; i < events.size(); i++) {
                current = events.get(i);
                currentRating = current.getRating();
                if (highestRating < currentRating) {
                    highestRating = currentRating;
                    highest = current;
                }
            }
            return highest;
        }
    }

    // EFFECTS: returns average rating of all events recorded under day rounded to 
    //          2 decimal places, or 0 if no events recorded
    public double getAverageRating() {
        if (events.isEmpty()) {
            return 0.0;
        } else {
            int totalRating = 0;
            for (Event event : events) {
                totalRating += event.getRating();
            }
            double average = (double) totalRating / events.size();
            average = Math.round(average * 100.0)/100.0;
            return average;
        }
    }
    
    // EFFECTS: returns a string representation of the date in the form "YYYY-MM-DD"
    public String toString() {
        String year = String.valueOf(this.year);
        String month;
        String date;
        if (this.month < 10) {
            month = "" + 0 + this.month;
        } else {
            month = String.valueOf(this.month);
        }
        if (this.date < 10) {
            date = "" + 0 + this.date;
        } else {
            date = String.valueOf(this.date);
        }
        String finalDate = year + "-" + month + "-" + date;
        return finalDate;
    }

}
