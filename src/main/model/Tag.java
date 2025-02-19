package model;

import org.json.JSONObject;

import persistence.Writeable;

// Represents a tag that users can categorize their events under, with 
// a name and number of events categorized under this tag
public class Tag implements Writeable {
    
    private String name;
    private int numEvents;

    // EFFECTS: Creates a new tag with given name and no events tagged
    public Tag(String name) {
        this.name = name;
        numEvents = 0;
    }

    public String getName() {
        return name;
    }

    public int getNumEvents() {
        return numEvents;
    }

    public void setNumEvents(int num) {
        numEvents = num;
    }

    // MODIFIES: this
    // EFFECTS: adds one to number of events categorized under this tag
    public void addNewEvent() {
        numEvents++;
    }

    @Override
    public JSONObject toJson() {
        // stub
        return null;
    }
}
