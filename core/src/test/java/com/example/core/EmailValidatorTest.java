package com.example.core;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EmailValidatorTest {

    @Test
    public void testValidEmail() {
        assertTrue(EmailValidator.isValid("test@example.com"));
    }

    @Test
    public void testInvalidEmail_NoAt() {
        assertFalse(EmailValidator.isValid("testexample.com"));
    }

    @Test
    public void testInvalidEmail_NoDot() {
        assertFalse(EmailValidator.isValid("test@examplecom"));
    }

    @Test
    public void testInvalidEmail_Empty() {
        assertFalse(EmailValidator.isValid(""));
    }

    @Test
    public void testInvalidEmail_Null() {
        assertFalse(EmailValidator.isValid(null));
    }

    @Test
    public void testIsGmail_Valid() {
        assertTrue(EmailValidator.isGmail("user@gmail.com"));
    }

    @Test
    public void testIsGmail_Invalid() {
        assertFalse(EmailValidator.isGmail("user@yahoo.com"));
    }

    @Test
    public void testIsGmail_CaseInsensitive() {
        assertTrue(EmailValidator.isGmail("USER@GMAIL.COM"));
    }
}
