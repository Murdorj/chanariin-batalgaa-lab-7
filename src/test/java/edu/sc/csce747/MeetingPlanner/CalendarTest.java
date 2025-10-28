package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;

public class CalendarTest {

    @Test
    public void testAddMeeting_holiday() {
        Calendar calendar = new Calendar();
        try {
            Meeting midsommar = new Meeting(6, 26, "Midsommar");
            calendar.addMeeting(midsommar);
            assertTrue("Midsommar should be marked as busy on the calendar",
                    calendar.isBusy(6, 26, 0, 23));
        } catch (TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    @Test
    public void testCheckTimes_validDecember_shouldNotThrow() {
        try {
            // Одоогийн кодонд bug байгаа (>=12) тул энэ тест FAIL болно — алдааг барина.
            Calendar.checkTimes(12, 10, 9, 10);
        } catch (TimeConflictException e) {
            fail("December (12) must be valid month, but threw: " + e.getMessage());
        }
    }

    @Test
    public void testCheckTimes_invalidMonth_low_high() {
        try {
            Calendar.checkTimes(0, 10, 9, 10);
            fail("Expected TimeConflictException for month=0");
        } catch (TimeConflictException expected) {}
        try {
            Calendar.checkTimes(13, 10, 9, 10);
            fail("Expected TimeConflictException for month=13");
        } catch (TimeConflictException expected) {}
    }

    @Test
    public void testCheckTimes_invalidDay_low_high() {
        try {
            Calendar.checkTimes(1, 0, 9, 10);
            fail("Expected TimeConflictException for day=0");
        } catch (TimeConflictException expected) {}
        try {
            Calendar.checkTimes(1, 32, 9, 10);
            fail("Expected TimeConflictException for day=32");
        } catch (TimeConflictException expected) {}
    }

    @Test
    public void testCheckTimes_invalidTime_bounds_and_range() {
        try {
            Calendar.checkTimes(1, 10, -1, 10);
            fail("start=-1 must throw");
        } catch (TimeConflictException expected) {}
        try {
            Calendar.checkTimes(1, 10, 0, 24);
            fail("end=24 must throw");
        } catch (TimeConflictException expected) {}
        try {
            Calendar.checkTimes(1, 10, 10, 10);
            fail("start>=end must throw");
        } catch (TimeConflictException expected) {}
    }

    @Test
    public void testImpossibleDays_markedBusy_viaSentinel() throws Exception {
        // Calendar ctor нь боломжгүй өдрүүдийг all-day "Day does not exist" уулзалтаар бөглөсөн байдаг.
        Calendar c = new Calendar();
        assertTrue(c.isBusy(2, 30, 0, 23));
        assertTrue(c.isBusy(2, 31, 0, 23));
        assertTrue(c.isBusy(4, 31, 0, 23));
        assertTrue(c.isBusy(6, 31, 0, 23));
        assertTrue(c.isBusy(9, 31, 0, 23));
        assertTrue(c.isBusy(11, 31, 0, 23));
    }

    @Test
    public void testAddMeeting_overlap_inclusive_end_conflict() {
        // Танай одоогийн давхцах шалгалт: start/end нь "хоёр талдаа inclusive".
        Calendar c = new Calendar();
        try {
            Meeting a = new Meeting(6, 1, 10, 12, new ArrayList<Person>(), new Room("X"), "A");
            c.addMeeting(a);
            // Хил дээр тэнцүү (12–14) ч танай кодонд OVERLAP гэж тооцогдоно (mStart<=e нөхцөлөөс).
            Meeting b = new Meeting(6, 1, 12, 14, new ArrayList<Person>(), new Room("X"), "B");
            c.addMeeting(b);
            fail("Expected overlap conflict because of inclusive end logic");
        } catch (TimeConflictException expected) {}
    }

    @Test
    public void testAddMeeting_strictOverlap_conflict() {
        Calendar c = new Calendar();
        try {
            Meeting a = new Meeting(5, 20, 10, 12, new ArrayList<Person>(), new Room("R"), "A");
            c.addMeeting(a);
            Meeting b = new Meeting(5, 20, 11, 13, new ArrayList<Person>(), new Room("R"), "B");
            c.addMeeting(b);
            fail("Should have thrown conflict for overlapping meetings");
        } catch (TimeConflictException expected) {}
    }
}
