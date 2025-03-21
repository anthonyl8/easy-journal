package persistence;

import model.Day;
import model.Event;
import model.Journal;
import model.Tag;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// SOURCE: Tests based on JSON Serialization Demo
class TestJsonWriter extends TestJson {

    Journal jr;

    @Test
    void testWriterInvalidFile() {
        try {
            jr = new Journal();
            JsonWriter writer = new JsonWriter("./data/persistence/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            jr = new Journal();
            JsonWriter writer = new JsonWriter("./data/persistence/testWriterEmptyJournal.json");
            writer.open();
            writer.write(jr);
            writer.close();

            JsonReader reader = new JsonReader("./data/persistence/testWriterEmptyJournal.json");
            jr = reader.read();
            assertTrue(jr.getDays().isEmpty());
            assertTrue(jr.getTags().isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralJournal() {
        try {
            Journal jr = new Journal();

            Day d1 = new Day(2024, 5, 12);
            Day d2 = new Day(2024, 6, 15);
            Day d3 = new Day(2024, 7, 7);
            
            Tag t1 = new Tag("feeling-okay");
            Tag t2 = new Tag("travel");
            Tag t3 = new Tag("competition");
            Tag t4 = new Tag("melancholy");
            Tag t5 = new Tag("views");
            Tag t6 = new Tag("core-memory");

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

            Event e6 = new Event("La Tour Eiffel", 10, "Finally up on the Eiffel Tower!", "./data/images/eiffel-tower.jpg");
            d3.addEvent(e6);
            e6.setStar(true);
            e6.addTag(t2);
            e6.addTag(t5);
            e6.addTag(t6);

            jr.addDay(d1);
            jr.addDay(d2);
            jr.addDay(d3);

            JsonWriter writer = new JsonWriter("./data/persistence/testWriterGeneralJournal.json");
            writer.open();
            writer.write(jr);
            writer.close();

            JsonReader reader = new JsonReader("./data/persistence/testWriterGeneralJournal.json");
            jr = reader.read();

            List<Day> expectedDays = new ArrayList<Day>();
            expectedDays.add(d1);
            expectedDays.add(d2);
            expectedDays.add(d3);

            List<Day> actualDays = jr.getDays();
            assertEquals(expectedDays.size(), actualDays.size());
            for (int i = 0; i < expectedDays.size(); i++) {
                checkDayEquality(expectedDays.get(i), actualDays.get(i));
            }

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}