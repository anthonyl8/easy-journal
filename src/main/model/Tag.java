package model;

import java.util.List;

// Represents a tag that users can categorize their events under, with 
// a name and a list of events categorized under this tag
public class Tag {
    
    // EFFECTS: Creates a new tag with given name and no events tagged
    public Tag(String name) {
        // constructor
    }

    public String getName() {
        // stub
        return "";
    }

    public void setName(String name) {
        // stub
    }
    public List<Event> getEvents() {
        // stub
        return null;
    }

    // MODIFIES: this
    // EFFECTS: adds event to list of events if it is not already in list of events.
    //          for each event, resorts list of tags from most used to least used tag
    //          if necessary
    public void addEvent(Event event) {
        // stub
    }

    // REQUIRES: total number of events listed > 0
    // EFFECTS: returns percentage of events under this tag
    public double getPercentTagged() {
        return 0.0;
    }
}
