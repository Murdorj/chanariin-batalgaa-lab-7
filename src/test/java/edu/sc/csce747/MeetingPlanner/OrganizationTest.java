package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;

public class OrganizationTest {

    @Test
    public void testGetExistingRoomAndEmployee() throws Exception {
        Organization org = new Organization();
        assertEquals("2A01", org.getRoom("2A01").getID());
        assertEquals("Greg Gay", org.getEmployee("Greg Gay").getName());
    }

    @Test
    public void testUnknownRoom_employee_throw() {
        Organization org = new Organization();
        try {
            org.getRoom("NOPE");
            fail("Expected exception for unknown room");
        } catch (Exception expected) {}
        try {
            org.getEmployee("Nobody");
            fail("Expected exception for unknown employee");
        } catch (Exception expected) {}
    }
}
