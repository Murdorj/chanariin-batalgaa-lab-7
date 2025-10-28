package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;

public class RoomTest {

    @Test
    public void testAddOverlap_shouldThrow_roomMessage() throws Exception {
        Room r = new Room("2A03");
        Meeting a = new Meeting(5, 21, 9, 12, new ArrayList<Person>(), r, "A");
        r.addMeeting(a);

        Meeting b = new Meeting(5, 21, 11, 13, new ArrayList<Person>(), r, "B");
        try {
            r.addMeeting(b);
            fail("Should throw overlap for room");
        } catch (TimeConflictException e) {
            assertTrue(e.getMessage().startsWith("Conflict for room 2A03"));
        }
    }

    @Test
    public void testPrintAgenda_containsDescription() throws Exception {
        Room r = new Room("2A04");
        Meeting a = new Meeting(7, 1, 10, 11, new ArrayList<Person>(), r, "Daily");
        r.addMeeting(a);
        String agenda = r.printAgenda(7, 1);
        assertTrue(agenda.contains("Daily"));
        assertTrue(agenda.contains("10 - 11"));
    }
}
