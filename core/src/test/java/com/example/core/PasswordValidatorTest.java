package com.example.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PasswordValidatorTest {

    @Test
    public void testValidPassword() {
        assertTrue(PasswordValidator.isValid("123456"));
    }

    @Test
    public void testInvalidPassword_TooShort() {
        assertFalse(PasswordValidator.isValid("12345"));
    }

    @Test
    public void testInvalidPassword_Empty() {
        assertFalse(PasswordValidator.isValid(""));
    }

    @Test
    public void testInvalidPassword_Null() {
        assertFalse(PasswordValidator.isValid(null));
    }

    @Test
    public void testStrongPassword() {
        assertTrue(PasswordValidator.isStrong("Pass123word"));
    }

    @Test
    public void testWeakPassword_NoUpper() {
        assertFalse(PasswordValidator.isStrong("pass123word"));
    }

    @Test
    public void testWeakPassword_NoLower() {
        assertFalse(PasswordValidator.isStrong("PASS123WORD"));
    }

    @Test
    public void testWeakPassword_NoDigit() {
        assertFalse(PasswordValidator.isStrong("Password"));
    }

    @Test
    public void testWeakPassword_TooShort() {
        assertFalse(PasswordValidator.isStrong("Pass1"));
    }
}
