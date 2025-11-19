package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;

public class CalendarTest {

    @Test
    public void testAddMeeting_holiday() throws Exception {
        Calendar calendar = new Calendar();
        Meeting midsommar = new Meeting(6, 26, "Midsommar");
        calendar.addMeeting(midsommar);
        assertTrue(calendar.isBusy(6, 26, 0, 23));
    }

    @Test
    public void testCheckTimes_validDecember_shouldNotThrow() throws Exception {
        Calendar.checkTimes(12, 10, 9, 10);
    }

    @Test(expected = TimeConflictException.class)
    public void testInvalidMonthLow() throws Exception {
        Calendar.checkTimes(0, 10, 9, 10);
    }

    @Test(expected = TimeConflictException.class)
    public void testInvalidMonthHigh() throws Exception {
        Calendar.checkTimes(13, 10, 9, 10);
    }

    @Test(expected = TimeConflictException.class)
    public void testInvalidDayLow() throws Exception {
        Calendar.checkTimes(1, 0, 9, 10);
    }

    @Test(expected = TimeConflictException.class)
    public void testInvalidDayHigh() throws Exception {
        Calendar.checkTimes(1, 32, 9, 10);
    }

    @Test(expected = TimeConflictException.class)
    public void testInvalidTimeStartNegative() throws Exception {
        Calendar.checkTimes(1, 10, -1, 10);
    }

    @Test(expected = TimeConflictException.class)
    public void testInvalidTimeEndOver() throws Exception {
        Calendar.checkTimes(1, 10, 0, 24);
    }

    @Test(expected = TimeConflictException.class)
    public void testInvalidTimeStartAfterEnd() throws Exception {
        Calendar.checkTimes(1, 10, 10, 10);
    }

    @Test
    public void testImpossibleDaysAreBusy() throws Exception {
        Calendar c = new Calendar();
        assertTrue(c.isBusy(2, 30, 0, 23));
        assertTrue(c.isBusy(2, 31, 0, 23));
        assertTrue(c.isBusy(4, 31, 0, 23));
        assertTrue(c.isBusy(6, 31, 0, 23));
        assertTrue(c.isBusy(9, 31, 0, 23));
        assertTrue(c.isBusy(11, 31, 0, 23));
    }

    @Test
    public void testAddMeetingOverlapConflict() throws Exception {
        Calendar c = new Calendar();
        Meeting a = new Meeting(6, 1, 10, 12, new ArrayList<Person>(), new Room("X"), "A");
        c.addMeeting(a);
        Meeting b = new Meeting(6, 1, 11, 13, new ArrayList<Person>(), new Room("X"), "B");
        try {
            c.addMeeting(b);
            fail("Expected overlap conflict");
        } catch (TimeConflictException expected) {
            assertTrue(expected.getMessage().contains("Overlap"));
        }
    }

    @Test
    public void testPrintAgendaMonthAndDay() throws Exception {
        Calendar c = new Calendar();
        Meeting m = new Meeting(5, 10, 9, 10, new ArrayList<Person>(), new Room("R"), "Standup");
        c.addMeeting(m);
        String monthAgenda = c.printAgenda(5);
        String dayAgenda = c.printAgenda(5, 10);
        assertTrue(monthAgenda.contains("Standup"));
        assertTrue(dayAgenda.contains("Standup"));
    }

    @Test
    public void testGetAndRemoveMeeting() throws Exception {
        Calendar c = new Calendar();
        Meeting m = new Meeting(3, 15, 8, 9, new ArrayList<Person>(), new Room("R2"), "Review");
        c.addMeeting(m);
        Meeting fetched = c.getMeeting(3, 15, 0);
        assertEquals("Review", fetched.getDescription());
        c.removeMeeting(3, 15, 0);
        try {
            c.getMeeting(3, 15, 0);
            fail("Should throw IndexOutOfBounds after remove");
        } catch (IndexOutOfBoundsException expected) {}
    }

    @Test
    public void testClearScheduleShouldRemoveAll() throws Exception {
        Calendar c = new Calendar();
        Meeting m = new Meeting(9, 5, 10, 11, new ArrayList<Person>(), new Room("Y"), "Meeting");
        c.addMeeting(m);
        assertTrue(c.isBusy(9, 5, 10, 11));
        c.clearSchedule(9, 5);
        try {
            c.getMeeting(9, 5, 0);
            fail("Expected IndexOutOfBounds after clearSchedule");
        } catch (IndexOutOfBoundsException e) {
            assertTrue(e.getMessage().contains("Index"));
        }
    }
}
