package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;

public class PersonTest {

    @Test
    public void testAddMeetingAndIsBusy() throws Exception {
        Person p = new Person("Bob");
        Room r = new Room("2A01");
        Meeting m = new Meeting(12, 15, 10, 12, new ArrayList<Person>(), r, "Design");
        p.addMeeting(m);
        assertTrue(p.isBusy(12, 15, 10, 12));
    }

    @Test
    public void testAddOverlappingMeeting_shouldPropagateConflict() throws Exception {
        Person p = new Person("Carol");
        Room r = new Room("2A02");
        Meeting a = new Meeting(6, 10, 10, 12, new ArrayList<Person>(), r, "A");
        p.addMeeting(a);

        Meeting b = new Meeting(6, 10, 11, 13, new ArrayList<Person>(), r, "B");
        try {
            p.addMeeting(b);
            fail("Should throw TimeConflictException for attendee");
        } catch (TimeConflictException e) {
            assertTrue(e.getMessage().startsWith("Conflict for attendee"));
        }
    }
}
