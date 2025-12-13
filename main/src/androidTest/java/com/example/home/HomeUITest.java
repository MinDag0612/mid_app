package com.example.home;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class HomeUITest {
    private Intent intent;

    @Before
    public void setup() {
        intent = new Intent(ApplicationProvider.getApplicationContext(), Home.class);
        intent.putExtra("email", "test@test.com");
        intent.putExtra("userId", "123");
        intent.putExtra("fullname", "Test User");
    }

    @Test
    public void testHomeActivityLaunches() {
        ActivityScenario<Home> scenario = ActivityScenario.launch(intent);
        onView(withId(R.id.txtName)).check(matches(isDisplayed()));
        onView(withId(R.id.rvNotes)).check(matches(isDisplayed()));
    }
}
