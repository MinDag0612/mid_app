package com.example.core.auth;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

public class AuthRepositoryTest {
    private AuthService authService;
    private AuthRepository authRepository;

    @Before
    public void setUp() {
        authService = mock(AuthService.class);
        authRepository = new AuthRepository(authService);
    }

    @Test
    public void registerUser_success_invokesCallbackWithTrue() {
        doAnswer(invocation -> {
            AuthService.AuthServiceCallback callback = invocation.getArgument(2);
            callback.onComplete(true, null);
            return null;
        }).when(authService).createUser(eq("user@test.com"), eq("password"), any(AuthService.AuthServiceCallback.class));

        AuthRepository.AuthCallback callback = mock(AuthRepository.AuthCallback.class);

        authRepository.registerUser("user@test.com", "password", callback);

        verify(callback).onComplete(true, null);
    }

    @Test
    public void registerUser_failure_invokesCallbackWithError() {
        doAnswer(invocation -> {
            AuthService.AuthServiceCallback callback = invocation.getArgument(2);
            callback.onComplete(false, new RuntimeException("boom"));
            return null;
        }).when(authService).createUser(eq("user@test.com"), eq("password"), any(AuthService.AuthServiceCallback.class));

        AuthRepository.AuthCallback callback = mock(AuthRepository.AuthCallback.class);

        authRepository.registerUser("user@test.com", "password", callback);

        verify(callback).onComplete(false, "boom");
    }

    @Test
    public void loginUser_success_invokesCallbackWithTrue() {
        doAnswer(invocation -> {
            AuthService.AuthServiceCallback callback = invocation.getArgument(2);
            callback.onComplete(true, null);
            return null;
        }).when(authService).signIn(eq("user@test.com"), eq("password"), any(AuthService.AuthServiceCallback.class));

        AuthRepository.AuthCallback callback = mock(AuthRepository.AuthCallback.class);

        authRepository.loginUser("user@test.com", "password", callback);

        verify(callback).onComplete(true, null);
    }
}
