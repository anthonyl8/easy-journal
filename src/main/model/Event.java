package model;

import persistence.Writeable;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

// Represents an event that a user can record with a title, tags, a rating,
// a starred status, a quote, and an image.
public class Event implements Writeable {

    private String title;
    private List<Tag> tags;
    private int rating;
    private boolean starred;
    private String quote;
    private String imagePath;

    // EFFECTS: Creates a new event with the given title, rating (out of 10), and quote. 
    //          Event is initially unstarred and has an empty list of tags.
    public Event(String title, int rating, String quote /* , Image image */) {
        this.title = title;
        this.tags = new ArrayList<Tag>();
        this.rating = rating;
        starred = false;
        this.quote = quote;
        this.imagePath = null;
    }

    // REQUIRES: file path for image is a valid file path that points to an image that exists.
    // EFFECTS: Creates a new event with the given title, rating (out of 10), quote, 
    //          and image file path. Event is initially unstarred and has an empty list of tags.
    public Event(String title, int rating, String quote, String imagePath) {
        this.title = title;
        this.tags = new ArrayList<Tag>();
        this.rating = rating;
        starred = false;
        this.quote = quote;
        this.imagePath = imagePath;
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
    //          returning true if added or false if already in list of tags. updates 
    //          number of events tagged in tag if tag was added to event
    public boolean addTag(Tag tag) {
        if (!(tags.contains(tag))) {
            tags.add(tag);
            tag.addNewEvent();
            EventLog.getInstance().logEventToLog(new EventToLog("Tag added to event!"));
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: removes given tag from list of tags if it is in list of tags, returning 
    //          true if removed or false if it was not in list of tags to begin with. 
    //          updates number of events tagged in tag
    public boolean removeTag(Tag tag) {
        boolean removed = tags.remove(tag);
        if (removed) {
            tag.removeEvent();
            EventLog.getInstance().logEventToLog(new EventToLog("Tag removed from event!"));
        }
        return removed;
    }

    // MODIFIES: this
    // EFFECTS: removes all tags from list of tags
    public void removeAllTags() {
        for (int i = tags.size() - 1; i >= 0; i--) {
            removeTag(tags.get(i));
        }
    }

    public String getQuote() {
        return quote;
    }

    public String getImagePath() {
        return imagePath;
    }

    public boolean isStarred() {
        return starred;
    }

    public void setStar(boolean star) {
        starred = star;
    }

    // MODIFIES: this
    // EFFECTS: flips starred status of event
    public void flipStar() {
        EventLog.getInstance().logEventToLog(new EventToLog("Event's star status flipped from " + starred + " to " + !starred + "!"));
        starred = !starred;
    }

    // MODIFIES: this
    // EFFECTS: sorts tags from most used to least used tag
    public void sortTags() {
        tags.sort((Tag t1, Tag t2) -> {
            return t2.getNumEvents() - t1.getNumEvents();
        });
    }

    @Override
    // EFFECTS: returns event as a Json object with title, list of tags, rating,
    //          starred, quote, and image (if image exists)
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("tags", tagsToJson());
        json.put("rating", rating);
        json.put("starred", starred);
        json.put("quote", quote);
        if (imagePath != null) {
            json.put("image", imagePath);
        }
        return json;
    }

    // EFFECTS: returns tags associated with this event as a JSON array
    private JSONArray tagsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Tag tag : tags) {
            jsonArray.put(tag.toJson());
        }
        return jsonArray;
    }
}
