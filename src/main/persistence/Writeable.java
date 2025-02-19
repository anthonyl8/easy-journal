package persistence;

import org.json.JSONObject;

// SOURCE: Json Serialization Demo

// Represents an interface for which implementing classes can transform their data
// into a JSON object
public interface Writeable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
