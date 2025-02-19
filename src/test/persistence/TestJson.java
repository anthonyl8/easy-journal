package persistence;

import model.Day;
import model.Event;
import model.Tag;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

// SOURCE: Test helpers based on JSON Serialization Demo
public class TestJson {
    
    protected void checkDayEquality(Day expected, Day actual) {
        assertEquals(expected.getYear(), actual.getYear());
        assertEquals(expected.getMonth(), actual.getMonth());
        assertEquals(expected.getDate(), actual.getDate());

        List<Event> expectedEvents = expected.getEvents();
        List<Event> actualEvents = actual.getEvents();
        assertEquals(expectedEvents.size(), actualEvents.size());
        for (int i = 0; i < expectedEvents.size(); i++) {
            checkEventEquality(expectedEvents.get(i), actualEvents.get(i));
        }
    }

    protected void checkEventEquality(Event expected, Event actual) {
        assertEquals(expected.getTitle(), actual.getTitle());
        
        List<Tag> expectedTags = expected.getTags();
        List<Tag> actualTags = actual.getTags();
        assertEquals(expectedTags.size(), actualTags.size());
        for (int i = 0; i < expectedTags.size(); i++) {
            checkTagEquality(expectedTags.get(i), actualTags.get(i));
        }

        assertEquals(expected.getRating(), actual.getRating());
        assertEquals(expected.isStarred(), actual.isStarred());
        assertEquals(expected.getQuote(), actual.getQuote());
    }

    protected void checkTagEquality(Tag expected, Tag actual) {
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getNumEvents(), actual.getNumEvents());
    }
}