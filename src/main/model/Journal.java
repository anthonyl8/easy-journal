package model;

import persistence.Writeable;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

// Represents a journal with a list of days recorded and a list of tags used.
public class Journal implements Writeable {

    private List<Day> days;

    public Journal() {
        days = new ArrayList<Day>();
    }

    public List<Day> getDays() {
        return days;
    }

    // EFFECTS: returns a list of all tags recorded under any event in this journal,
    //          ensuring that there are no duplicates in the list
    public List<Tag> getTags() {
        List<Event> events;
        List<Tag> eventTags;
        List<Tag> tags = new ArrayList<Tag>();
        for (Day day : days) {
            events = day.getEvents();
            for (Event event : events) {
                eventTags = event.getTags();
                for (Tag tag : eventTags) {
                    if (!tags.contains(tag)) {
                        tags.add(tag);
                    }
                }
            }
        }
        return tags;
    }

    // MODIFIES: this
    // EFFECTS: adds given day to list of days if it is not already in list of days,
    //          returning true if added or false if already in list of days.
    public boolean addDay(Day day) {
        if (days.contains(day)) {
            return false;
        } else {
            days.add(day);
            return true;
        } 
    }

    // MODIFIES: this
    // EFFECTS: removes day corresponding to given date tfromlist of days if it is 
    //          in list of days, returning true if removed or false if day was not
    //          in list of days to begin
    public boolean removeDay(Day day) {
        day.removeAllEvents();
        return days.remove(day);
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

    // EFFECTS: returns the tag whose name matches the given tag name, or null if no
    //          tag's name matches the given tag name
    public Tag getTagFromName(String tagName) {
        List<Event> events;
        List<Tag> eventTags;
        for (Day day : days) {
            events = day.getEvents();
            for (Event event : events) {
                eventTags = event.getTags();
                for (Tag tag : eventTags) {
                    if (tagName.equals(tag.getName())) {
                        return tag;
                    }
                }
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
        List<Tag> tags = getTags();
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

}