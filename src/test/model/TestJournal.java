package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestJournal {

    Journal j1;
    Day d1;
    Day d2;
    Day d3;
    Day d4;
    Event e1;
    Event e2;
    Event e3;
    Event e4;
    Tag t1;
    Tag t2;
    Tag t3;
    Tag t4;

    @BeforeEach
    void runBefore() {
        j1 = new Journal();
        d1 = new Day(2025, 1, 20);
        d2 = new Day(2023, 1, 2);
        d3 = new Day(2024, 10, 3);
        d4 = new Day(2024, 11, 19);
        // grouse = new ImageIcon("images/grouse-mountain.jpg").getImage();
        // eiffelTower = new ImageIcon("images/eiffel-tower.jpg").getImage();
        // tokyoStreet = new ImageIcon("images/tokyo-street.webp").getImage();
        e1 = new Event("Grouse Trip!", 7, "Climbed up, watched Indigenous dances, saw first bear!" /* , grouse */);
        e2 = new Event("La Tour Eiffel", 8, "Took the stairs, watched Parisian sunset!" /* , eiffelTower */);
        e3 = new Event("Tokyo at Night", 9, "This is insane" /* , tokyoStreet */);
        e4 = new Event("At home", 10, "Look at us all together!" /* cool image */ );
        t1 = new Tag("outdoors");
        t2 = new Tag("adventure");
        t3 = new Tag("core memory");
        t4 = new Tag("living!");
    }

    @Test
    void testConstructor() {
        assertTrue(j1.getDays().isEmpty());
        assertTrue(j1.getTags().isEmpty());
    }

    @Test
    void testAddDay() {
        List<Day> j1Days;
        assertTrue(j1.addDay(d1));
        j1Days = j1.getDays();
        assertEquals(d1, j1Days.get(0));
        assertEquals(1, j1Days.size());
        assertTrue(j1.addDay(d2));
        j1Days = j1.getDays();
        assertEquals(d1, j1Days.get(0));
        assertEquals(d2, j1Days.get(1));
        assertEquals(2, j1Days.size());
        assertFalse(j1.addDay(d1));
        j1Days = j1.getDays();
        assertEquals(d1, j1Days.get(0));
        assertEquals(d2, j1Days.get(1));
        assertEquals(2, j1Days.size());
    }

    @Test
    void testDateRecord() {
        assertNull(j1.dateRecord(d1.toString()));
        j1.addDay(d1);
        assertNull(j1.dateRecord("2024-01-20"));
        assertNull(j1.dateRecord("2025-02-20"));
        assertNull(j1.dateRecord("2025-01-21"));
        assertEquals(d1, j1.dateRecord(d1.toString()));
        assertNull(j1.dateRecord(d2.toString()));
        j1.addDay(d2);
        assertEquals(d1, j1.dateRecord(d1.toString()));
        assertEquals(d2, j1.dateRecord(d2.toString()));
        assertNull(j1.dateRecord(d3.toString()));
    }

    @Test
    void testAddTagTagParameter() {
        List<Tag> j1Tags;
        assertTrue(j1.addTag(t1));
        j1Tags = j1.getTags();
        assertEquals(t1, j1Tags.get(0));
        assertEquals(1, j1Tags.size());
        assertTrue(j1.addTag(t2));
        j1Tags = j1.getTags();
        assertEquals(t1, j1Tags.get(0));
        assertEquals(t2, j1Tags.get(1));
        assertEquals(2, j1Tags.size());
        assertFalse(j1.addTag(t1));
        j1Tags = j1.getTags();
        assertEquals(t1, j1Tags.get(0));
        assertEquals(t2, j1Tags.get(1));
        assertEquals(2, j1Tags.size());
    }

    @Test
    void testAddTagStringParameter() {
        List<Tag> j1Tags;
        assertEquals(t1.getName(), j1.addTag(t1.getName()).getName());
        j1Tags = j1.getTags();
        assertEquals(t1.getName(), j1Tags.get(0).getName());
        assertEquals(1, j1Tags.size());
        assertEquals(t2.getName(), j1.addTag(t2.getName()).getName());
        j1Tags = j1.getTags();
        assertEquals(t1.getName(), j1Tags.get(0).getName());
        assertEquals(t2.getName(), j1Tags.get(1).getName());
        assertEquals(2, j1Tags.size());
        assertEquals(j1Tags.get(0), j1.addTag(t1.getName()));
        assertEquals(t1.getName(), j1Tags.get(0).getName());
        assertEquals(t2.getName(), j1Tags.get(1).getName());
        assertEquals(2, j1Tags.size());
    }

    @Test
    void testGetTagFromName() {
        assertNull(j1.getTagFromName("outdoors"));
        j1.addTag(t1);
        assertEquals(t1, j1.getTagFromName("outdoors"));
        assertNull(j1.getTagFromName("adventure"));
        j1.addTag(t2);
        assertEquals(t1, j1.getTagFromName("outdoors"));
        assertEquals(t2, j1.getTagFromName("adventure"));
        assertNull(j1.getTagFromName("core memory"));
    }

    @Test
    void testGetMostEventfulDays() {
        List<Day> eventfulDays;
        assertTrue(j1.getMostEventfulDays().isEmpty());

        j1.addDay(d1);
        eventfulDays = j1.getMostEventfulDays();
        assertEquals(d1, eventfulDays.get(0));
        assertEquals(1, eventfulDays.size());
        
        j1.addDay(d2);
        eventfulDays = j1.getMostEventfulDays();
        assertEquals(d1, eventfulDays.get(0));
        assertEquals(d2, eventfulDays.get(1));
        assertEquals(2, eventfulDays.size());

        d2.addEvent(e1);
        eventfulDays = j1.getMostEventfulDays();
        assertEquals(d2, eventfulDays.get(0));
        assertEquals(d1, eventfulDays.get(1));
        assertEquals(2, eventfulDays.size());

        j1.addDay(d3);
        eventfulDays = j1.getMostEventfulDays();
        assertEquals(d2, eventfulDays.get(0));
        assertEquals(d1, eventfulDays.get(1));
        assertEquals(d3, eventfulDays.get(2));
        assertEquals(3, eventfulDays.size());

        d3.addEvent(e2);
        d3.addEvent(e3);
        eventfulDays = j1.getMostEventfulDays();
        assertEquals(d3, eventfulDays.get(0));
        assertEquals(d2, eventfulDays.get(1));
        assertEquals(d1, eventfulDays.get(2));
        assertEquals(3, eventfulDays.size());

        j1.addDay(d4);
        eventfulDays = j1.getMostEventfulDays();
        assertEquals(d3, eventfulDays.get(0));
        assertEquals(d2, eventfulDays.get(1));
        assertEquals(d1, eventfulDays.get(2));
        assertEquals(3, eventfulDays.size());

        d4.addEvent(e4);
        eventfulDays = j1.getMostEventfulDays();
        assertEquals(d3, eventfulDays.get(0));
        assertEquals(d2, eventfulDays.get(1));
        assertEquals(d4, eventfulDays.get(2));
        assertEquals(3, eventfulDays.size());
    }

    @Test
    void testGetTopDays() {
        List<Day> topDays;
        assertTrue(j1.getTopDays().isEmpty());

        j1.addDay(d1);
        topDays = j1.getTopDays();
        assertEquals(d1, topDays.get(0));
        assertEquals(1, topDays.size());
        
        j1.addDay(d2);
        topDays = j1.getTopDays();
        assertEquals(d1, topDays.get(0));
        assertEquals(d2, topDays.get(1));
        assertEquals(2, topDays.size());

        d2.addEvent(e1);
        topDays = j1.getTopDays();
        assertEquals(d2, topDays.get(0));
        assertEquals(d1, topDays.get(1));
        assertEquals(2, topDays.size());

        j1.addDay(d3);
        topDays = j1.getTopDays();
        assertEquals(d2, topDays.get(0));
        assertEquals(d1, topDays.get(1));
        assertEquals(d3, topDays.get(2));
        assertEquals(3, topDays.size());

        d3.addEvent(e2);
        d3.addEvent(e3);
        topDays = j1.getTopDays();
        assertEquals(d3, topDays.get(0));
        assertEquals(d2, topDays.get(1));
        assertEquals(d1, topDays.get(2));
        assertEquals(3, topDays.size());

        j1.addDay(d4);
        topDays = j1.getTopDays();
        assertEquals(d3, topDays.get(0));
        assertEquals(d2, topDays.get(1));
        assertEquals(d1, topDays.get(2));
        assertEquals(3, topDays.size());

        d4.addEvent(e4);
        topDays = j1.getTopDays();
        assertEquals(d4, topDays.get(0));
        assertEquals(d3, topDays.get(1));
        assertEquals(d2, topDays.get(2));
        assertEquals(3, topDays.size());
    }

    @Test
    void testGetTopTags() {
        List<Tag> topTags;
        assertTrue(j1.getTopTags().isEmpty());

        j1.addTag(t1);
        topTags = j1.getTopTags();
        assertEquals(t1, topTags.get(0));
        assertEquals(1, topTags.size());

        t1.setNumEvents(3);
        j1.addTag(t2);
        topTags = j1.getTopTags();
        assertEquals(t1, topTags.get(0));
        assertEquals(t2, topTags.get(1));
        assertEquals(2, topTags.size());

        t2.setNumEvents(4);
        topTags = j1.getTopTags();
        assertEquals(t2, topTags.get(0));
        assertEquals(t1, topTags.get(1));
        assertEquals(2, topTags.size());

        t3.setNumEvents(4);
        j1.addTag(t3);
        topTags = j1.getTopTags();
        assertEquals(t2, topTags.get(0));
        assertEquals(t3, topTags.get(1));
        assertEquals(t1, topTags.get(2));
        assertEquals(3, topTags.size());

        t3.setNumEvents(1);
        topTags = j1.getTopTags();
        assertEquals(t2, topTags.get(0));
        assertEquals(t1, topTags.get(1));
        assertEquals(t3, topTags.get(2));
        assertEquals(3, topTags.size());

        t4.setNumEvents(5);
        j1.addTag(t4);
        topTags = j1.getTopTags();
        assertEquals(t4, topTags.get(0));
        assertEquals(t2, topTags.get(1));
        assertEquals(t1, topTags.get(2));
        assertEquals(3, topTags.size());
    }

}