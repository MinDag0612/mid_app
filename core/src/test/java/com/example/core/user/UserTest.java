package com.example.core.user;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Before;
import org.junit.Test;

public class UserTest {
    private User user;

    @Before
    public void setup() {
        user = new User("Nguyen Van A", "test@example.com");
    }

    @Test
    public void testUserCreation() {
        assertThat(user.getFullname()).isEqualTo("Nguyen Van A");
        assertThat(user.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    public void testUserDefaultConstructor() {
        User emptyUser = new User();
        assertThat(emptyUser.getFullname()).isNull();
        assertThat(emptyUser.getEmail()).isNull();
    }

    @Test
    public void testUserToString() {
        String result = user.toString();
        assertThat(result).contains("Nguyen Van A");
        assertThat(result).contains("test@example.com");
    }

    @Test
    public void testUserWithEmptyData() {
        User emptyUser = new User("", "");
        assertThat(emptyUser.getFullname()).isEmpty();
        assertThat(emptyUser.getEmail()).isEmpty();
    }
}
