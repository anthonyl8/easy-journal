package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Image;
import javax.swing.ImageIcon;

import java.util.List;

public class TestEvent {

    Image grouse;
    Event e1;
    Tag t1;
    Tag t2;

    @BeforeEach
    void runBefore() {
        grouse = new ImageIcon("images/grouse-mountain.jpg").getImage();
        e1 = new Event("Grouse Trip!", 7, "Climbed up, watched Indigenous dances, saw first bear!", grouse);
        t1 = new Tag("outdoors");
        t2 = new Tag("winter");
    }

    @Test
    void testConstructor() {
        assertEquals("Grouse Trip!", e1.getTitle());
        assertTrue(e1.getTags().isEmpty());
        assertEquals(7, e1.getRating());
        assertFalse(e1.isStarred());
        assertEquals("Climbed up, watched Indigenous dances, saw first bear!", e1.getQuote());
        assertEquals(grouse, e1.getImage());
    }

    @Test
    void testFlipStar() {
        e1.flipStar();
        assertTrue(e1.isStarred());
        e1.flipStar();
        assertFalse(e1.isStarred());
    }

    @Test
    void testAddTag() {
        List<Tag> e1Tags;
        assertTrue(e1.addTag(t1));
        assertEquals(1, t1.getNumEvents());
        e1Tags = e1.getTags();
        assertEquals(t1, e1Tags.get(0));
        assertEquals(1, e1Tags.size());
        assertTrue(e1.addTag(t2));
        assertEquals(1, t2.getNumEvents());
        e1Tags = e1.getTags();
        assertEquals(t1, e1Tags.get(0));
        assertEquals(t2, e1Tags.get(1));
        assertEquals(2, e1Tags.size());
        assertFalse(e1.addTag(t1));
        assertEquals(1, t1.getNumEvents());
        e1Tags = e1.getTags();
        assertEquals(t1, e1Tags.get(0));
        assertEquals(t2, e1Tags.get(1));
        assertEquals(2, e1Tags.size());
    }

    @Test
    void testSortTags() {
        t1.addNewEvent();
        t2.addNewEvent();
        t2.addNewEvent();
        e1.addTag(t1);
        e1.addTag(t2);
        e1.sortTags();
        List<Tag> e1Tags = e1.getTags();
        assertEquals(t2, e1Tags.get(0));
        assertEquals(t1, e1Tags.get(1));
        assertEquals(2, e1Tags.size());
    }


}
