package model;

import java.util.List;
import java.util.ArrayList;
import java.awt.Image;

// Represents an event that a user can record with a title, tags, a rating,
// a starred status, a quote, and an image.
public class Event {

    private String title;
    private List<Tag> tags;
    private int rating;
    private boolean starred;
    private String quote;
    private Image image;

    // EFFECTS: Creates a new event with the given title, rating (out of 10), quote, 
    //          and image. Event is initially unstarred and has an empty list of tags.
    public Event(String title, int rating, String quote /* , Image image */) {
        this.title = title;
        this.tags = new ArrayList<Tag>();
        this.rating = rating;
        starred = false;
        this.quote = quote;
        // this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public int getRating() {
        return rating;
    }

    public List<Tag> getTags() {
        return tags;
    }

    // MODIFIES: this
    // EFFECTS: adds given tag to list of tags if it is not already in list of tags,
    //          returning true if added or false if already in list of tags. resorts
    //          tags in list of tags and updates number of events tagged in tag
    public boolean addTag(Tag tag) {
        if (!(tags.contains(tag))) {
            tags.add(tag);
            tag.addNewEvent();
            return true;
        } else {
            return false;
        }
    }

    public String getQuote() {
        return quote;
    }

    // public Image getImage() {
    //     return image;
    // }

    public boolean isStarred() {
        return starred;
    }

    // MODIFIES: this
    // EFFECTS: flips starred status of event
    public void flipStar() {
        starred = !starred;
    }

    // MODIFIES: this
    // EFFECTS: sorts tags from most used to least used tag
    public void sortTags() {
        tags.sort((Tag t1, Tag t2) -> {
            return t2.getNumEvents() - t1.getNumEvents();
        });
    }
}
