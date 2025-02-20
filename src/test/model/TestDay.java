package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// import java.awt.Image;
// import javax.swing.ImageIcon;

import java.util.List;

public class TestDay {

    // Image grouse;
    // Image eiffelTower;
    // Image tokyoStreet;
    Day d1;
    Day d2;
    Day d3;
    Day d4;
    Event e1;
    Event e2;
    Event e3;

    @BeforeEach
    void runBefore() {
        d1 = new Day(2025, 1, 20);
        d2 = new Day(2023, 1, 2);
        d3 = new Day(2024, 10, 3);
        d4 = new Day(2024, 11, 19);
        // grouse = new ImageIcon("images/grouse-mountain.jpg").getImage();
        // eiffelTower = new ImageIcon("images/eiffel-tower.jpg").getImage();
        // tokyoStreet = new ImageIcon("images/tokyo-street.webp").getImage();
        e1 = new Event("Grouse Trip!", 7, "Climbed up, watched Indigenous dances, saw first bear!" /* , grouse */);
        e2 = new Event("La Tour Eiffel", 8, "Took the stairs, watched Parisian sunset!" /* , eiffelTower */);
        e3 = new Event("Tokyo at Night", 8, "This is insane" /* , tokyoStreet */);
    }

    @Test
    void testConstructor() {
        assertEquals(2025, d1.getYear());
        assertEquals(1, d1.getMonth());
        assertEquals(20, d1.getDate());
        assertTrue(d1.getEvents().isEmpty());
    }

    @Test
    void testGetNumEvents() {
        assertEquals(0, d1.getNumEvents());
        d1.addEvent(e1);
        assertEquals(1, d1.getNumEvents());
        d1.addEvent(e2);
        assertEquals(2, d1.getNumEvents());
    }

    @Test
    void testAddEvent() {
        List<Event> d1Events = d1.getEvents();
        List<Event> d2Events = d2.getEvents();
        d1.addEvent(e1);
        assertEquals(e1, d1Events.get(0));
        assertEquals(1, d1Events.size());
        d1.addEvent(e2);
        assertEquals(e1, d1Events.get(0));
        assertEquals(e2, d1Events.get(1));
        assertEquals(2, d1Events.size());
        d2.addEvent(e3);
        assertEquals(e3, d2Events.get(0));
        assertEquals(1, d2Events.size());
    }

    @Test
    void testRemoveEvent() {
        List<Event> d1Events = d1.getEvents();
        List<Event> d2Events = d2.getEvents();
        d1.addEvent(e1);
        assertEquals(e1, d1Events.get(0));
        assertEquals(1, d1Events.size());
        d1.addEvent(e2);
        assertEquals(e1, d1Events.get(0));
        assertEquals(e2, d1Events.get(1));
        assertEquals(2, d1Events.size());
        d1.removeEvent(e1);
        assertEquals(e2, d1Events.get(0));
        assertEquals(1, d1Events.size());
        d1.removeEvent(e2);
        assertTrue(d1Events.isEmpty());
        d2.addEvent(e3);
        assertEquals(e3, d2Events.get(0));
        assertEquals(1, d2Events.size());
        d2.removeEvent(e3);
        assertTrue(d1Events.isEmpty());
    }

    @Test
    void testGetEventFromTitle() {
        assertNull(d1.getEventFromTitle("My home"));
        assertNull(d1.getEventFromTitle("Grouse Trip!"));
        d1.addEvent(e1);
        assertEquals(e1, d1.getEventFromTitle("Grouse Trip!"));
        assertNull(d1.getEventFromTitle("La Tour Eiffel"));
        d1.addEvent(e2);
        assertEquals(e1, d1.getEventFromTitle("Grouse Trip!"));
        assertEquals(e2, d1.getEventFromTitle("La Tour Eiffel"));
        assertNull(d1.getEventFromTitle("Tokyo at Night"));
    }

    @Test
    void testGetMostHighlyRated() {
        assertEquals(null, d1.getMostHighlyRated());
        d1.addEvent(e1);
        assertEquals(e1, d1.getMostHighlyRated());
        d1.addEvent(e2);
        assertEquals(e2, d1.getMostHighlyRated());
        d1.addEvent(e3);
        assertEquals(e2, d1.getMostHighlyRated());
    }

    @Test
    void testGetAverageRating() {
        assertEquals(0, d1.getAverageRating());
        d1.addEvent(e1);
        assertEquals(7, d1.getAverageRating());
        d1.addEvent(e2);
        assertEquals(7.5, d1.getAverageRating());
        d1.addEvent(e3);
        assertEquals(7.67, d1.getAverageRating());
    }

    @Test
    void testToString() {
        assertEquals("2025-01-20", d1.toString());
        assertEquals("2023-01-02", d2.toString());
        assertEquals("2024-10-03", d3.toString());
        assertEquals("2024-11-19", d4.toString());
    }


}