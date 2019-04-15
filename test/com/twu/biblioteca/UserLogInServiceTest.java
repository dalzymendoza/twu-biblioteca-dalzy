package com.twu.biblioteca;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class UserLogInServiceTest {

    @Test
    public void shouldReturnInputProcessResponseSuccessIfInputMatchesUserAndPasswordInRepo() {
        ServiceHandler serviceHandler = mock(ServiceHandler.class);
        when(serviceHandler.requestInput(anyString())).thenReturn("gwen", "gwen");
        UserLogInService userLogInService = new UserLogInService(serviceHandler);
        ServiceHandler.InputProcessResponse response = userLogInService.processInput("L");
        assertThat(response, is(ServiceHandler.InputProcessResponse.SUCCESS));
    }

    @Test
    public void shouldReturnInputProcessResponseFailIfInputUserInRepoButPasswordDoesntMatch() {
        ServiceHandler serviceHandler = mock(ServiceHandler.class);
        when(serviceHandler.requestInput(anyString())).thenReturn("gwen", "stacy");
        UserLogInService userLogInService = new UserLogInService(serviceHandler);
        ServiceHandler.InputProcessResponse response = userLogInService.processInput("L");
        assertThat(response, is(ServiceHandler.InputProcessResponse.FAIL));
    }

    @Test
    public void shouldReturnInputProcessResponseFailIfInputUserNotInRepo() {
        ServiceHandler serviceHandler = mock(ServiceHandler.class);
        when(serviceHandler.requestInput(anyString())).thenReturn("stacy", "stacy");
        UserLogInService userLogInService = new UserLogInService(serviceHandler);
        ServiceHandler.InputProcessResponse response = userLogInService.processInput("L");
        assertThat(response, is(ServiceHandler.InputProcessResponse.FAIL));
    }

}
