package model;

import java.util.List;
import java.awt.Image;

// Represents an event that a user can record with an ID, a title, tags, a rating,
// a starred status, a quote, an image, and a hindsight comment.
// Number of events recorded is kept track of at the class level.
public class Event {

    // EFFECTS: Creates a new event with the given title, rating (out of 10), quote, 
    //          and image. Event is initially unstarred and has an empty list of tags. 
    //          Adds 1 to number of events recorded so far, and assigns this new number 
    //          to be this event's id.
    public Event(String title, int rating, String quote, Image image) {
        // constructor
    }

    public static int getNumEvents() {
        // stub
        return 0;
    }

    public int getId() {
        // stub
        return 0;
    }

    public String getTitle() {
        // stub
        return "";
    }

    public int getRating() {
        // stub
        return 0;
    }

    public List<Tag> getTags() {
        // stub
        return null;
    }

    // MODIFIES: this
    // EFFECTS: adds given tag to list of tags if it is not already in list of tags,
    //          returning true if added or false if already in list of tags. resorts
    //          tags in list of tags and updates number of events tagged in tag
    public boolean addTag(Tag tag) {
        // stub
        return false;
    }

    public String getQuote() {
        // stub
        return "";
    }

    public Image getImage() {
        // stub
        return null;
    }

    public boolean isStarred() {
        // stub
        return false;
    }

    // MODIFIES: this
    // EFFECTS: flips starred status of event
    public void flipStar() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: sorts tags from most used to least used tag
    public void sortTags() {
        // stub
    }
}
