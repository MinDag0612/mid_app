package com.example.core.user;

import org.junit.Test;
import static org.junit.Assert.*;

public class SimpleUserTest {

    @Test
    public void testUserGetters() {
        User user = new User("Name", "email@test.com");
        assertEquals("Name", user.getFullname());
        assertEquals("email@test.com", user.getEmail());
    }

    @Test
    public void testEmptyUser() {
        User user = new User();
        assertNull(user.getFullname());
    }
}
