package model;

// Represents a tag that users can categorize their events under, with 
// a name and number of events categorized under this tag
public class Tag {
    
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

    // MODIFIES: this
    // EFFECTS: adds one to number of events categorized under this tag
    public void addNewEvent() {
        numEvents++;
    }
}
