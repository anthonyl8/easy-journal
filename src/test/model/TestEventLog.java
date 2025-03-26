package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the EventLog class
 */
public class TestEventLog {
	private EventToLog e1;
	private EventToLog e2;
	private EventToLog e3;
	
	@BeforeEach
	public void loadEventsToLog() {
		e1 = new EventToLog("A1");
		e2 = new EventToLog("A2");
		e3 = new EventToLog("A3");
		EventLog el = EventLog.getInstance();
		el.logEventToLog(e1);
		el.logEventToLog(e2);
		el.logEventToLog(e3);
	}
	
	@Test
	public void testLogEventToLog() {	
		List<EventToLog> l = new ArrayList<EventToLog>();
		
		EventLog el = EventLog.getInstance();
		for (EventToLog next : el) {
			l.add(next);
		}
		
		assertTrue(l.contains(e1));
		assertTrue(l.contains(e2));
		assertTrue(l.contains(e3));
	}

	@Test
	public void testClear() {
		EventLog el = EventLog.getInstance();
		el.clear();
		Iterator<EventToLog> itr = el.iterator();
		assertTrue(itr.hasNext());   // After log is cleared, the clear log event is added
		assertEquals("Event log cleared.", itr.next().getDescription());
		assertFalse(itr.hasNext());
	}
}
