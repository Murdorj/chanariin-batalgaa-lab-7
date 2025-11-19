package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;

public class RoomTest {

    @Test
    public void testDefaultConstructorAndGetID() {
        Room r = new Room();
        assertEquals("", r.getID());
        assertNotNull(r);
    }

    @Test
    public void testConstructorWithID() {
        Room r = new Room("2A01");
        assertEquals("2A01", r.getID());
    }

    @Test
    public void testAddMeetingAndPrintAgenda() throws Exception {
        Room r = new Room("2A02");
        Meeting a = new Meeting(5, 15, 9, 10, new ArrayList<>(), r, "Team Sync");
        r.addMeeting(a);
        String agenda = r.printAgenda(5);
        assertTrue(agenda.contains("Team Sync"));
        assertTrue(agenda.contains("5/15"));
    }

    @Test
    public void testPrintAgendaDay() throws Exception {
        Room r = new Room("2A03");
        Meeting a = new Meeting(6, 20, 10, 11, new ArrayList<>(), r, "Client Call");
        r.addMeeting(a);
        String agenda = r.printAgenda(6, 20);
        assertTrue(agenda.contains("Client Call"));
    }

    @Test
    public void testAddOverlap_shouldThrowConflictMessage() throws Exception {
        Room r = new Room("2A04");
        Meeting m1 = new Meeting(5, 21, 9, 12, new ArrayList<>(), r, "Morning");
        r.addMeeting(m1);

        Meeting m2 = new Meeting(5, 21, 11, 13, new ArrayList<>(), r, "Overlap");
        try {
            r.addMeeting(m2);
            fail("Should have thrown TimeConflictException");
        } catch (TimeConflictException e) {
            assertTrue(e.getMessage().startsWith("Conflict for room 2A04"));
        }
    }

    @Test
    public void testIsBusyAndGetMeeting() throws Exception {
        Room r = new Room("2A05");
        Meeting m = new Meeting(7, 10, 8, 9, new ArrayList<>(), r, "Check Busy");
        r.addMeeting(m);

        boolean busy = r.isBusy(7, 10, 8, 9);
        assertTrue(busy);

        Meeting fetched = r.getMeeting(7, 10, 0);
        assertNotNull(fetched);
        assertEquals("Check Busy", fetched.getDescription());
    }

    @Test
    public void testRemoveMeeting() throws Exception {
        Room r = new Room("2A06");
        Meeting m = new Meeting(8, 5, 14, 15, new ArrayList<>(), r, "Temp Meeting");
        r.addMeeting(m);

        // эхлээд add хийгдсэн эсэхийг шалгах
        Meeting fetched = r.getMeeting(8, 5, 0);
        assertEquals("Temp Meeting", fetched.getDescription());

        // дараа нь устгах
        r.removeMeeting(8, 5, 0);

        // дахин шалгахад meeting байхгүй байх ёстой (өөрөөр хэлбэл IndexOutOfBounds үүсэх ёсгүй)
        try {
            r.getMeeting(8, 5, 0);
            fail("Expected IndexOutOfBoundsException after removal");
        } catch (IndexOutOfBoundsException e) {
            assertTrue(e.getMessage().contains("Index"));
        }
    }

}
