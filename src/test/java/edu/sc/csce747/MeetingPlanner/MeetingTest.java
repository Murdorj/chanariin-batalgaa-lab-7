package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;

public class MeetingTest {

    @Test
    public void testToString_noRoom_noAttendees_shouldNotNPE() {
        // Одоогийн Meeting.toString() нь room.getID(), attendees-г шууд дуудах тул NPE үүсч магадгүй.
        // Энэ тест FAIL байж болно — алдааг илрүүлэх зорилготой.
        Meeting m = new Meeting(1, 2, 9, 10);
        String s = m.toString(); // NPE үүсэхгүй байх ёстой
        assertNotNull(s);
        assertTrue(s.contains("1/2"));
    }

    @Test
    public void testAddRemoveAttendee() {
        Meeting m = new Meeting(3, 4, 8, 9);
        Person p = new Person("Alice");
        m.addAttendee(p);
        assertEquals(1, m.getAttendees().size());
        m.removeAttendee(p);
        assertEquals(0, m.getAttendees().size());
    }
}
