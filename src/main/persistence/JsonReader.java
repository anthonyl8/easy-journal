package persistence;

import model.Day;
import model.Event;
import model.Journal;
import model.Tag;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// SOURCE: JSON Serialization Demo

// Represents a reader that reads journal from JSON data stored in file
public class JsonReader {

    private String source;
    private Journal journal;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
        this.journal = new Journal();
    }

    // MODIFIES: this
    // EFFECTS: reads journal from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Journal read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseJournal(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // MODIFIES: this
    // EFFECTS: parses journal from JSON object and returns it
    private Journal parseJournal(JSONObject jsonObject) {
        addTags(jsonObject);
        addDays(jsonObject);
        return journal;
    }

    // MODIFIES: this
    // EFFECTS: parses tags from JSON object and adds them to journal
    private void addTags(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("tags");
        for (Object json : jsonArray) {
            JSONObject nextTag = (JSONObject) json;
            addTag(nextTag);
        }
    }

    // MODIFIES: this
    // EFFECTS: parses tag from JSON object and adds it to journal
    private void addTag(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Tag tag = new Tag(name);
        journal.addTag(tag);
    }

    // MODIFIES: this
    // EFFECTS: parses days from JSON object and adds them to journal
    private void addDays(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("days");
        for (Object json : jsonArray) {
            JSONObject nextDay = (JSONObject) json;
            addDay(nextDay);
        }
    }

    // MODIFIES: this
    // EFFECTS: parses day from JSON object and adds it to journal
    private void addDay(JSONObject jsonObject) {
        int year = jsonObject.getInt("year");
        int month = jsonObject.getInt("month");
        int date = jsonObject.getInt("date");
        Day day = new Day(year, month, date);
        addEvents(day, jsonObject);
        journal.addDay(day);
    }

    // MODIFIES: this, day
    // EFFECTS: parses events from JSON object and adds them to day
    private void addEvents(Day day, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("events");
        for (Object json : jsonArray) {
            JSONObject nextEvent = (JSONObject) json;
            addEvent(day, nextEvent);
        }
    }

    // MODIFIES: this, day
    // EFFECTS: parses event from JSON object and adds it to day
    private void addEvent(Day day, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        int rating = jsonObject.getInt("rating");
        String quote = jsonObject.getString("quote");
        Event event = new Event(title, rating, quote);
        boolean starred = jsonObject.getBoolean("starred");
        event.setStar(starred);
        addTags(event, jsonObject);
        day.addEvent(event);
    }

    // MODIFIES: this, event
    // EFFECTS: parses tags from JSON object and adds them to event
    private void addTags(Event event, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("tags");
        for (Object json : jsonArray) {
            JSONObject nextTag = (JSONObject) json;
            addTag(event, nextTag);
        }
    }

    // MODIFIES: this, event
    // EFFECTS: parses event from JSON object and adds it to event
    private void addTag(Event event, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Tag tag = journal.getTagFromName(name);
        event.addTag(tag);
    }
}