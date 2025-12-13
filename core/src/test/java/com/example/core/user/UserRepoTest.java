package com.example.core.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

public class UserRepoTest {
    @Test
    public void getUserById_success_invokesCallback() {
        UserRepo.UserSource source = mock(UserRepo.UserSource.class);
        UserRepo userRepo = new UserRepo(source);
        User expected = new User("User name", "user@example.com");
        UserRepo.Callback<User> callback = mock(UserRepo.Callback.class);

        doAnswer(invocation -> {
            UserRepo.Callback<User> cb = invocation.getArgument(1);
            cb.onResult(expected);
            return null;
        }).when(source).getUserById(eq("uid123"), any());

        userRepo.getUserById("uid123", callback);

        verify(callback).onResult(expected);
    }

    @Test
    public void getUserById_failure_invokesCallbackWithNull() {
        UserRepo.UserSource source = mock(UserRepo.UserSource.class);
        UserRepo userRepo = new UserRepo(source);
        UserRepo.Callback<User> callback = mock(UserRepo.Callback.class);

        doAnswer(invocation -> {
            UserRepo.Callback<User> cb = invocation.getArgument(1);
            cb.onResult(null);
            return null;
        }).when(source).getUserById(eq("uid123"), any());

        userRepo.getUserById("uid123", callback);

        verify(callback).onResult(null);
    }
}
