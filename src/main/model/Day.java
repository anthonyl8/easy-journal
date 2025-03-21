package model;

import persistence.Writeable;

import java.util.List;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

// Represents a unique day with a year, month, date, and list of events under this day
public class Day implements Writeable {
    
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

    public int getNumEvents() {
        return events.size();
    }

    // REQUIRES: event is not already in list of events recorded under day
    // EFFECTS: adds event to list of events recorded under day
    public void addEvent(Event event) {
        events.add(event);
    }

    // REQUIRES: event is in list of events recorded under day
    // EFFECTS: destroys data in event and removes event from list of events recorded 
    //          under day
    public void removeEvent(Event event) {
        event.removeAllTags();
        events.remove(event);
    }

    // MODIFIES: this
    // EFFECTS: removes all events from list of events
    public void removeAllEvents() {
        for (int i = events.size() - 1; i >= 0; i--) {
            removeEvent(events.get(i));
        }
    }

    // EFFECTS: returns the event whose title matches the given event title, or null 
    //          if no event's title matches the given event title
    public Event getEventFromTitle(String eventTitle) {
        for (Event event : events) {
            if (event.getTitle().equals(eventTitle)) {
                return event;
            }
        }
        return null;
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
            average = Math.round(average * 100.0) / 100.0;
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("year", year);
        json.put("month", month);
        json.put("date", date);
        json.put("events", eventsToJson());
        return json;
    }

    // EFFECTS: returns events in this day as a JSON array
    private JSONArray eventsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Event event : events) {
            jsonArray.put(event.toJson());
        }
        return jsonArray;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + year;
        result = prime * result + month;
        result = prime * result + date;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        } 
        if (getClass() != obj.getClass()) {
            return false;
        }
        Day other = (Day) obj;
        if (year != other.year) {
            return false;
        }
        if (month != other.month) {
            return false;
        }
        if (date != other.date) {
            return false;
        }
        return true;
    }

    
}
