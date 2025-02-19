package persistence;

import model.Day;
import model.Event;
import model.Journal;
import model.Tag;

import java.io.IOException;

import org.json.*;

// SOURCE: JSON Serialization Demo

// Represents a reader that reads journal from JSON data stored in file
public class JsonReader {

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: reads journal from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Journal read() throws IOException {
        // stub
        return null;
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        // stub
        return null;
    }

    // MODIFIES: this
    // EFFECTS: parses journal from JSON object and returns it
    private Journal parseJournal(JSONObject jsonObject) {
        // stub
        return null;
    }

    // MODIFIES: this
    // EFFECTS: parses tags from JSON object and adds them to journal
    private void addTags(JSONObject jsonObject) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: parses tag from JSON object and adds it to journal
    private void addTag(JSONObject jsonObject) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: parses days from JSON object and adds them to journal
    private void addDays(JSONObject jsonObject) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: parses day from JSON object and adds it to journal
    private void addDay(JSONObject jsonObject) {
        // stub
    }

    // MODIFIES: this, day
    // EFFECTS: parses events from JSON object and adds them to day
    private void addEvents(Day day, JSONObject jsonObject) {
        // stub
    }

    // MODIFIES: this, day
    // EFFECTS: parses event from JSON object and adds it to day
    private void addEvent(Day day, JSONObject jsonObject) {
        // stub
    }

    // MODIFIES: this, event
    // EFFECTS: parses tags from JSON object and adds them to event
    private void addTags(Event event, JSONObject jsonObject) {
        // stub
    }

    // MODIFIES: this, event
    // EFFECTS: parses event from JSON object and adds it to event
    private void addTag(Event event, JSONObject jsonObject) {
        // stub
    }
}