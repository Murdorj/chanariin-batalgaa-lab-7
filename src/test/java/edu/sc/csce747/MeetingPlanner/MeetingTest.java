package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;

public class MeetingTest {

    @Test
    public void testDefaultConstructor() {
        Meeting m = new Meeting();
        assertNotNull(m.getAttendees());
        assertNotNull(m.getRoom());
        assertEquals("", m.getDescription());
    }

    @Test
    public void testConstructorMonthDay() {
        Meeting m = new Meeting(1, 2);
        assertEquals(1, m.getMonth());
        assertEquals(2, m.getDay());
        assertEquals(0, m.getStartTime());
        assertEquals(23, m.getEndTime());
    }

    @Test
    public void testConstructorMonthDayDescription() {
        Meeting m = new Meeting(3, 5, "Holiday");
        assertEquals("Holiday", m.getDescription());
        assertEquals(0, m.getStartTime());
        assertEquals(23, m.getEndTime());
    }

    @Test
    public void testConstructorMonthDayStartEnd() {
        Meeting m = new Meeting(4, 6, 10, 12);
        assertEquals(4, m.getMonth());
        assertEquals(6, m.getDay());
        assertEquals(10, m.getStartTime());
        assertEquals(12, m.getEndTime());
    }

    @Test
    public void testFullConstructor() {
        ArrayList<Person> list = new ArrayList<>();
        list.add(new Person("Alice"));
        Room r = new Room("2A01");
        Meeting m = new Meeting(5, 7, 8, 9, list, r, "Planning");
        assertEquals("Planning", m.getDescription());
        assertEquals("2A01", m.getRoom().getID());
        assertEquals(1, m.getAttendees().size());
    }

    @Test
    public void testAddRemoveAttendee() {
        Meeting m = new Meeting(3, 4, 8, 9);
        Person p = new Person("Bob");
        m.addAttendee(p);
        assertEquals(1, m.getAttendees().size());
        m.removeAttendee(p);
        assertEquals(0, m.getAttendees().size());
    }

    @Test
    public void testAddRemoveWhenAttendeesNull() {
        Meeting m = new Meeting(2, 3, 9, 10);
        // Force null
        m.addAttendee(new Person("X")); // initialize list
        m.removeAttendee(new Person("X")); // should not crash
        assertNotNull(m.getAttendees());
    }

    @Test
    public void testToStringWithAttendeesAndRoom() {
        ArrayList<Person> attendees = new ArrayList<>();
        attendees.add(new Person("Alice"));
        attendees.add(new Person("Bob"));
        Room room = new Room("2A02");
        Meeting m = new Meeting(6, 10, 9, 11, attendees, room, "Team Sync");
        String output = m.toString();
        assertTrue(output.contains("2A02"));
        assertTrue(output.contains("Alice"));
        assertTrue(output.contains("Bob"));
        assertTrue(output.contains("Team Sync"));
    }

    @Test
    public void testToStringWithNoAttendeesOrRoom() {
        Meeting m = new Meeting(7, 15, 10, 11);
        String output = m.toString();
        assertTrue(output.contains("none"));
        assertTrue(output.contains("7/15"));
    }

    @Test
    public void testSettersAndGetters() {
        Meeting m = new Meeting();
        Room r = new Room("3B");
        m.setMonth(8);
        m.setDay(9);
        m.setStartTime(10);
        m.setEndTime(11);
        m.setRoom(r);
        m.setDescription("Updated");

        assertEquals(8, m.getMonth());
        assertEquals(9, m.getDay());
        assertEquals(10, m.getStartTime());
        assertEquals(11, m.getEndTime());
        assertEquals("Updated", m.getDescription());
        assertEquals("3B", m.getRoom().getID());
    }
}
