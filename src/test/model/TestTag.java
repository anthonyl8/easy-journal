package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestTag {

    Tag t1;
    Tag t2;
    Tag t3;

    @BeforeEach
    void runBefore() {
        t1 = new Tag("outdoors");
        t2 = new Tag("winter");
    }

    @Test
    void testConstructor() {
        assertEquals("outdoors", t1.getName());
        assertEquals(0, t1.getNumEvents());
    }

    @Test
    void testAddNewEventOnce() {
        t1.addNewEvent();
        assertEquals(1, t1.getNumEvents());
    }

    @Test
    void testAddNewEventTwiceToSameTag() {
        t1.addNewEvent();
        assertEquals(1, t1.getNumEvents());
        t1.addNewEvent();
        assertEquals(2, t1.getNumEvents());
    }

    @Test
    void testAddNewEventOnceToTwoDifferentTags() {
        t1.addNewEvent();
        assertEquals(1, t1.getNumEvents());
        t2.addNewEvent();
        assertEquals(1, t2.getNumEvents());
    }
}
