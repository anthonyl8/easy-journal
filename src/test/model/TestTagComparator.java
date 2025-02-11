package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// SOURCE: Practical Midterm 1 Practice Question #3

public class TestTagComparator {
    
    TagComparator testComparator;
    Tag t1;
    Tag t2;

    @BeforeEach
    void runBefore() {
        testComparator = new TagComparator();
        t1 = new Tag("outdoors");
        t2 = new Tag("winter");
        t1.addNewEvent();
        t2.addNewEvent();
        t2.addNewEvent();
    }

    @Test
    void testLessThan() {
        assertTrue(testComparator.compare(t1, t2) == -1);
    }

    @Test
    void testEqualTo() {
        t1.addNewEvent();
        assertTrue(testComparator.compare(t1, t2) == 0);
    }

    @Test
    void testGreaterThan() {
        assertTrue(testComparator.compare(t2, t1) == 1);
    }
}
