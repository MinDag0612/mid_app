package com.example.auth;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginUITest {

    @Rule
    public ActivityScenarioRule<Login> activityRule = 
        new ActivityScenarioRule<>(Login.class);

    @Test
    public void testLoginFormExists() {
        onView(withId(R.id.edtEmailLogin));
        onView(withId(R.id.edtPassLogin));
        onView(withId(R.id.btnLogin));
    }

    @Test
    public void testGoToRegisterClick() {
        onView(withId(R.id.txtGoRegister)).perform(click());
    }
}
