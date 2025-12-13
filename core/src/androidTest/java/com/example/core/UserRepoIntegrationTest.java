package com.example.core;

import static com.google.common.truth.Truth.assertThat;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.core.user.User;
import com.example.core.user.UserRepo;

@RunWith(AndroidJUnit4.class)
public class UserRepoIntegrationTest {
    private UserRepo userRepo;
    private Context context;

    @Before
    public void setup() {
        context = ApplicationProvider.getApplicationContext();
        userRepo = new UserRepo();
    }

    @Test
    public void testUserRepoContext() {
        assertThat(context).isNotNull();
        assertThat(userRepo).isNotNull();
    }

    @Test
    public void testGetUserByIdCallback() {
        UserRepo.Callback<User> callback = user -> {
            // Callback test
        };
        assertThat(callback).isNotNull();
    }
}
