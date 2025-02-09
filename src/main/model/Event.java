package model;

import java.util.List;
import java.awt.Image;

// Represents an event that a user can record with an ID, a title, tags, a rating,
// a starred status, a quote, an image, and a hindsight comment.
// Number of events recorded is kept track of at the class level.
public class Event {

    // EFFECTS: Creates a new event with the given title, rating (out of 10), quote, 
    //          and image. Event is initially unstarred, has an empty list of tags, 
    //          and has no hindsight comment. Adds 1 to number of events recorded so 
    //          far, and assigns this new number to be this event's id. Updates 
    //          average number of recorded events accordingly.
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

    public void setTitle(String title) {
        // stub
    }
    public int getRating() {
        // stub
        return 0;
    }

    public void setRating(int rating) {
        // stub
    }

    public List<Tag> getTags() {
        // stub
        return null;
    }

    // MODIFIES: this
    // EFFECTS: adds given tag to list of tags if it is not already in list of tags,
    //          ensuring that list of tags is sorted from most used to least used tag
    public void addTag(Tag tag) {
        // stub
    }

    public String getQuote() {
        // stub
        return "";
    }

    public void setQuote(String quote) {
        // stub
    }

    public Image getImage() {
        // stub
        return null;
    }

    public boolean isStarred() {
        return false;
    }

    // MODIFIES: this
    // EFFECTS: stars event
    public void markAsStarred() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: unstars (removes star from) event
    public void markAsUnstarred() {
        // stub
    }

    public String getHindsight() {
        // stub
        return "";
    }

    public void setHindsight(String hindsight) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: reorders list of tags by the number of events under each tag
    public void sortTagsByPopularity() {
        // stub
    }
}
