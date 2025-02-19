package model;

import persistence.Writeable;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

// Represents a journal with a list of days recorded and a list of tags used.
public class Journal implements Writeable {

    private List<Day> days;
    private List<Tag> tags;

    public Journal() {
        days = new ArrayList<Day>();
        tags = new ArrayList<Tag>();
    }

    public List<Day> getDays() {
        return days;
    }

    public List<Tag> getTags() {
        return tags;
    }

    // MODIFIES: this
    // EFFECTS: adds given day to list of days if it is not already in list of days,
    //          returning true if added or false if already in list of days.
    public boolean addDay(Day day) {
        if (dateRecord(day.toString()) != null) {
            return false;
        } else {
            days.add(day);
            return true;
        } 
    }


    // REQUIRES: date is given as a string in the valid "YYYY-MM-DD" format
    // EFFECTS: returns corresponding day if given date has already been recorded
    //          in journal, otherwise returns null
    public Day dateRecord(String yearMonthDate) {
        String[] splitDate = yearMonthDate.split("-");
        int year = Integer.valueOf(splitDate[0]);
        int month = Integer.valueOf(splitDate[1]);
        int date = Integer.valueOf(splitDate[2]);
        for (Day day : days) {
            if (year == day.getYear() && month == day.getMonth() && date == day.getDate()) {
                return day;
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: adds given tag to list of tags if it is not already in list of tags,
    //          returning true if added or false if already in list of tags.
    public boolean addTag(Tag tag) {
        if (!tags.contains(tag)) {
            tags.add(tag);
            return true;
        } else {
            return false;
        }
    }
    // MODIFIES: this
    // EFFECTS: given a tag name, creates a new tag with that name and adds it to 
    //          list of tags if list of tags does not already contain a tag with 
    //          that name, then returns this newly created tag. otherwise, returns
    //          tag in list of tags whose name matches the given tag name
    public Tag addTag(String tagName) {
        Tag tagToAdd = getTagFromName(tagName);
        if (tagToAdd == null) {
            tagToAdd = new Tag(tagName);
            tags.add(tagToAdd);
        }
        return tagToAdd;
    }

    // EFFECTS: returns the tag whose name matches the given tag name, or null if no
    //          tag's name matches the given tag name
    public Tag getTagFromName(String tagName) {
        for (Tag tag : tags) {
            if (tag.getName().equals(tagName)) {
                return tag;
            }
        }
        return null;
    }

    // REQUIRES: at least one day in list of days
    // EFFECTS: gets top 3 days with most events recorded under them, or all days in
    //          list (depending on which one is smaller)
    public List<Day> getMostEventfulDays() {
        days.sort((Day d1, Day d2) -> {
            return d2.getNumEvents() - d1.getNumEvents();
        });
        int end = Math.min(3, days.size());
        return days.subList(0, end);
    }

    // REQUIRES: at least one day in list of days
    // EFFECTS: gets top 3 most highly rated days in list of days, or all days in
    //          list (depending on which one is smaller)
    public List<Day> getTopDays() {
        days.sort((Day d1, Day d2) -> {
            return Double.compare(d2.getAverageRating(), d1.getAverageRating());
        });
        int end = Math.min(3, days.size());
        return days.subList(0, end);
    }

    // REQUIRES: at least one tag in list of tags
    // EFFECTS: gets top 3 most used tags in list of tags, or all tags in list
    //          (depending on which one is smaller)
    public List<Tag> getTopTags() {
        tags.sort((Tag t1, Tag t2) -> {
            return t2.getNumEvents() - t1.getNumEvents();
        });
        int end = Math.min(3, tags.size());
        return tags.subList(0, end);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("days", daysToJson());
        json.put("tags", tagsToJson());
        return json;
    }

    // EFFECTS: returns days in this journal as a JSON array
    private JSONArray daysToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Day day : days) {
            jsonArray.put(day.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: returns tags in this journal as a JSON array
    private JSONArray tagsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Tag tag : tags) {
            jsonArray.put(tag.toJson());
        }
        return jsonArray;
    }
}