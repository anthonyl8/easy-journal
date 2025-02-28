package persistence;

import model.Day;
import model.Event;
import model.Journal;
import model.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// SOURCE: Tests based on JSON Serialization Demo
class TestJsonReader extends TestJson {

    Journal jr;

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            jr = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyJournal() {
        JsonReader reader = new JsonReader("./data/persistence/testReaderEmptyJournal.json");
        try {
            jr = reader.read();
            assertTrue(jr.getDays().isEmpty());
            assertTrue(jr.getTags().isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralJournal() {
        JsonReader reader = new JsonReader("./data/persistence/testReaderGeneralJournal.json");
        try {
            jr = reader.read();

            Day d1 = new Day(2024, 5, 12);
            Day d2 = new Day(2024, 6, 15);
            Day d3 = new Day(2024, 7, 7);
            
            Tag t1 = new Tag("feeling-okay");
            Tag t2 = new Tag("travel");
            Tag t3 = new Tag("competition");
            Tag t4 = new Tag("melancholy");
            Tag t5 = new Tag("views");
            Tag t6 = new Tag("core-memory");

            List<Day> expectedDays = new ArrayList<Day>();

            Event e1 = new Event("Pre-Presentation", 5, "Presenting to judges after Cansat crashed...");
            d1.addEvent(e1);
            e1.addTag(t1);

            Event e2 = new Event("Cansat Win!", 9, "We won! Europe-bound!");
            d1.addEvent(e2);
            e2.setStar(true);
            e2.addTag(t2);
            e2.addTag(t3);

            Event e3 = new Event("Amsterdam Centraal", 8, "Amsterdam train station!");
            d2.addEvent(e3);
            e3.addTag(t2);

            Event e4 = new Event("Cathedrale Notre-Dame", 7, "Couldn't go inside, of course, but looks like repairs are going well!");
            d3.addEvent(e4);
            e4.addTag(t4);

            Event e5 = new Event("Arc de Triomphe", 9, "What a view...");
            d3.addEvent(e5);
            e5.setStar(true);
            e5.addTag(t2);
            e5.addTag(t5);

            Event e6 = new Event("La Tour Eiffel", 10, "Finally up on the Eiffel Tower!");
            d3.addEvent(e6);
            e6.setStar(true);
            e6.addTag(t2);
            e6.addTag(t5);
            e6.addTag(t6);
            
            expectedDays.add(d1);
            expectedDays.add(d2);
            expectedDays.add(d3);

            List<Day> actualDays = jr.getDays();
            assertEquals(expectedDays.size(), actualDays.size());
            for (int i = 0; i < expectedDays.size(); i++) {
                checkDayEquality(expectedDays.get(i), actualDays.get(i));
            }

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}