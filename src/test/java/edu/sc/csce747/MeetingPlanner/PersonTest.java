package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;

public class PersonTest {

    @Test
    public void testDefaultConstructor_shouldInitializeCalendar() {
        Person p = new Person();
        assertNotNull(p);
        assertEquals("", p.getName());
        // calendar null биш — printAgenda ажиллах ёстой
        String agenda = p.printAgenda(1);
        assertTrue(agenda.contains("Agenda for 1"));
    }

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

    @Test
    public void testGetMeetingAndRemoveMeeting() throws Exception {
        Person p = new Person("Dave");
        Room r = new Room("R03");
        Meeting m = new Meeting(8, 5, 9, 10, new ArrayList<Person>(), r, "Morning");
        p.addMeeting(m);

        Meeting fetched = p.getMeeting(8, 5, 0);
        assertEquals("Morning", fetched.getDescription());

        // remove хийх
        p.removeMeeting(8, 5, 0);

        // remove хийсний дараа get хийхэд IndexOutOfBounds гарах ёстой
        try {
            p.getMeeting(8, 5, 0);
            fail("Expected IndexOutOfBoundsException after remove");
        } catch (IndexOutOfBoundsException e) {
            assertTrue(e.getMessage().contains("Index"));
        }
    }

    @Test
    public void testPrintAgendaMonthAndDay_shouldIncludeMeeting() throws Exception {
        Person p = new Person("Eve");
        Room r = new Room("2A04");
        Meeting m = new Meeting(9, 1, 14, 15, new ArrayList<Person>(), r, "Sync");
        p.addMeeting(m);

        String monthAgenda = p.printAgenda(9);
        String dayAgenda = p.printAgenda(9, 1);

        assertTrue(monthAgenda.contains("Agenda for 9"));
        assertTrue(dayAgenda.contains("Sync"));
    }

    @Test
    public void testGetName_shouldReturnCorrectName() {
        Person p = new Person("Frank");
        assertEquals("Frank", p.getName());
    }
}
