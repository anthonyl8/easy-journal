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
    Day d5;
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
        d5 = new Day(2025, 1, 19);
        e1 = new Event("Grouse Trip!", 7, "Climbed up, watched Indigenous dances, saw first bear!");
        e2 = new Event("La Tour Eiffel", 8, "Took the stairs, watched Parisian sunset!", "./data/images/eiffel-tower.jpg");
        e3 = new Event("Tokyo at Night", 9, "This is insane");
        e4 = new Event("At home", 10, "Look at us all together!");
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
    void testGetTags() {
        List<Tag> tempTags;
        assertTrue(j1.getTags().isEmpty());
        j1.addDay(d1);
        d1.addEvent(e1);
        e1.addTag(t1);
        tempTags = j1.getTags();
        assertEquals(t1, tempTags.get(0));
        assertEquals(1, tempTags.size());
        j1.addDay(d2);
        d2.addEvent(e2);
        e2.addTag(t1);
        tempTags = j1.getTags();
        assertEquals(t1, tempTags.get(0));
        assertEquals(1, tempTags.size());
        e1.addTag(t2);
        tempTags = j1.getTags();
        assertEquals(t1, tempTags.get(0));
        assertEquals(t2, tempTags.get(1));
        assertEquals(2, tempTags.size());
    }

    @Test
    void testAddDay() {
        List<Day> j1Days = j1.getDays();
        assertTrue(j1.addDay(d1));
        assertEquals(d1, j1Days.get(0));
        assertEquals(1, j1Days.size());
        assertTrue(j1.addDay(d2));
        assertEquals(d1, j1Days.get(0));
        assertEquals(d2, j1Days.get(1));
        assertEquals(2, j1Days.size());
        assertFalse(j1.addDay(d1));
        assertEquals(d1, j1Days.get(0));
        assertEquals(d2, j1Days.get(1));
        assertEquals(2, j1Days.size());
    }

    @Test
    void testRemoveDay() {
        List<Day> j1Days = j1.getDays();
        assertTrue(j1Days.isEmpty());
        assertFalse(j1.removeDay(d1));
        assertTrue(j1Days.isEmpty());
        j1.addDay(d1);
        assertTrue(j1.removeDay(d1));
        assertTrue(j1Days.isEmpty());
        j1.addDay(d1);
        j1.addDay(d2);
        assertTrue(j1.removeDay(d1));
        assertEquals(d2, j1Days.get(0));
        assertEquals(1, j1Days.size());
        assertFalse(j1.removeDay(d1));
        assertEquals(d2, j1Days.get(0));
        assertEquals(1, j1Days.size());
        assertTrue(j1.removeDay(d2));
        assertTrue(j1Days.isEmpty());
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
    void testGetTagFromName() {
        assertNull(j1.getTagFromName("outdoors"));
        j1.addDay(d1);
        d1.addEvent(e1);
        e1.addTag(t1);
        assertEquals(t1, j1.getTagFromName("outdoors"));
        assertNull(j1.getTagFromName("adventure"));
        j1.addDay(d2);
        d2.addEvent(e2);
        e1.addTag(t2);
        assertEquals(t1, j1.getTagFromName("outdoors"));
        assertEquals(t2, j1.getTagFromName("adventure"));
        assertNull(j1.getTagFromName("core memory"));
    }

    @Test
    void testSortDaysByTime() {
        List<Day> days;

        j1.addDay(d1);
        days = j1.sortDaysByTime();
        assertEquals(d1, days.get(0));
        assertEquals(1, days.size());
        
        j1.addDay(d2);
        days = j1.sortDaysByTime();
        assertEquals(d1, days.get(0));
        assertEquals(d2, days.get(1));
        assertEquals(2, days.size());

        j1.addDay(d3);
        days = j1.sortDaysByTime();
        assertEquals(d1, days.get(0));
        assertEquals(d3, days.get(1));
        assertEquals(d2, days.get(2));
        assertEquals(3, days.size());

        j1.addDay(d4);
        days = j1.sortDaysByTime();
        assertEquals(d1, days.get(0));
        assertEquals(d4, days.get(1));
        assertEquals(d3, days.get(2));
        assertEquals(d2, days.get(3));
        assertEquals(4, days.size());  

        j1.addDay(d5);
        days = j1.sortDaysByTime();
        assertEquals(d5, days.get(0));
        assertEquals(d1, days.get(1));
        assertEquals(d4, days.get(2));
        assertEquals(d3, days.get(3));
        assertEquals(d2, days.get(4));
        assertEquals(5, days.size()); 
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
        j1.addDay(d1);
        d1.addEvent(e1);
        e1.addTag(t1);
        topTags = j1.getTopTags();
        assertEquals(t1, topTags.get(0));
        assertEquals(1, topTags.size());

        t1.setNumEvents(3);
        e1.addTag(t2);
        topTags = j1.getTopTags();
        assertEquals(t1, topTags.get(0));
        assertEquals(t2, topTags.get(1));
        assertEquals(2, topTags.size());

        t2.setNumEvents(4);
        topTags = j1.getTopTags();
        assertEquals(t2, topTags.get(0));
        assertEquals(t1, topTags.get(1));
        assertEquals(2, topTags.size());

        t3.setNumEvents(2);
        j1.addDay(d2);
        d2.addEvent(e2);
        e2.addTag(t3);
        topTags = j1.getTopTags();
        assertEquals(t2, topTags.get(0));
        assertEquals(t1, topTags.get(1));
        assertEquals(t3, topTags.get(2));
        assertEquals(3, topTags.size());

        t3.setNumEvents(6);
        topTags = j1.getTopTags();
        assertEquals(t3, topTags.get(0));
        assertEquals(t2, topTags.get(1));
        assertEquals(t1, topTags.get(2));
        assertEquals(3, topTags.size());

        t4.setNumEvents(5);
        e2.addTag(t4);
        topTags = j1.getTopTags();
        assertEquals(t3, topTags.get(0));
        assertEquals(t4, topTags.get(1));
        assertEquals(t2, topTags.get(2));
        assertEquals(3, topTags.size());
    }

}