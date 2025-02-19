package model;

import java.util.List;

public class Journal {

    public Journal() {
        // constructor
    }

    public List<Day> getDays() {
        // stub
        return null;
    }

    public List<Tag> getTags() {
        // stub
        return null;
    }

    // MODIFIES: this
    // EFFECTS: adds given day to list of days if it is not already in list of days,
    //          returning true if added or false if already in list of days.
    public boolean addDay(Day day) {
        // stub
        return false;
    }


    // REQUIRES: date is given as a string in the valid "YYYY-MM-DD" format
    // EFFECTS: returns corresponding day if given date has already been recorded
    //          in journal, otherwise returns null
    public Day dateRecord(String yearMonthDate) {
        // stub
        return null;
    }

    // MODIFIES: this
    // EFFECTS: adds given tag to list of tags if it is not already in list of tags,
    //          returning true if added or false if already in list of tags.
    public boolean addTag(Tag tag) {
        // stub
        return false;
    }

    // MODIFIES: this
    // EFFECTS: given a tag name, creates a new tag with that name and adds it to 
    //          list of tags if list of tags does not already contain a tag with 
    //          that name, then returns this newly created tag. otherwise, returns
    //          tag in list of tags whose name matches the given tag name
    public Tag addTag(String tagName) {
        // stub
        return null;
    }

    // EFFECTS: returns the tag whose name matches the given tag name, or null if no
    //          tag's name matches the given tag name
    public Tag getTagFromName(String tagName) {
        // stub
        return null;
    }

    // REQUIRES: at least one day in list of days
    // EFFECTS: gets top 3 days with most events recorded under them, or all days in
    //          list (depending on which one is smaller)
    public List<Day> getMostEventfulDays() {
        // stub
        return null;
    }

    // REQUIRES: at least one day in list of days
    // EFFECTS: gets top 3 most highly rated days in list of days, or all days in
    //          list (depending on which one is smaller)
    public List<Day> getTopDays() {
        // stub
        return null;
    }

    // REQUIRES: at least one tag in list of tags
    // EFFECTS: gets top 3 most used tags in list of tags, or all tags in list
    //          (depending on which one is smaller)
    public List<Tag> getTopTags() {
        // stub
        return null;
    }

}