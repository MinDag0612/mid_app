package com.example.core;

public class EmailValidator {
    
    public static boolean isValid(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return email.contains("@") && email.contains(".");
    }
    
    public static boolean isGmail(String email) {
        if (!isValid(email)) {
            return false;
        }
        return email.toLowerCase().endsWith("@gmail.com");
    }
}
