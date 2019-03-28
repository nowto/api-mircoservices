package com.xunlu.api.user.service;

import com.xunlu.api.user.domain.User;
import com.xunlu.api.user.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Objects;

import static org.mockito.Mockito.*;

public class UserServiceImplTest {
    UserMapper mockUserMapper;
    TencentIMService mockTencentIMService;

    @Test
    public void addUserShouldUpdateIdentiferWhenAccountImportSuccess() {
        String identifier = "identifier";
        int generatedKey = 1111;

        mockUserMapper = Mockito.mock(UserMapper.class);
        doAnswer(invocationOnMock -> {
            User user = invocationOnMock.getArgument(0);
            user.setId(generatedKey);
            return true;
        }).when(mockUserMapper).addUser(any());
        when(mockUserMapper.updateTIMIdentifier(generatedKey, identifier)).thenReturn(true);


        mockTencentIMService = mock(TencentIMService.class);
        when(mockTencentIMService.makeIdentifier(anyString(), anyInt())).thenReturn(identifier);
        when(mockTencentIMService.accountImport(identifier, null, null)).thenReturn(true);


        UserService userService = new UserServiceImpl(mockUserMapper, mockTencentIMService);
        ((UserServiceImpl) userService).setTimPrefix("testtest");
        User user = new User();
        user.setUserName("hello");
        userService.addUser(user);

        Assert.assertEquals(generatedKey, user.getId().intValue());
        verify(mockUserMapper).updateTIMIdentifier(generatedKey, identifier);
    }

    @Test
    public void addUserShouldNoUpdateIdentiferWhenAccountImportFail() {
        String identifier = "identifier";
        int generatedKey = 1111;

        mockUserMapper = Mockito.mock(UserMapper.class);
        doAnswer(invocationOnMock -> {
            User user = invocationOnMock.getArgument(0);
            user.setId(generatedKey);
            return true;
        }).when(mockUserMapper).addUser(any());
        when(mockUserMapper.updateTIMIdentifier(generatedKey, identifier)).thenReturn(true);


        mockTencentIMService = mock(TencentIMService.class);
        when(mockTencentIMService.makeIdentifier(anyString(), anyInt())).thenReturn(identifier);
        when(mockTencentIMService.accountImport(identifier, null, null)).thenReturn(false);


        UserService userService = new UserServiceImpl(mockUserMapper, mockTencentIMService);
        ((UserServiceImpl) userService).setTimPrefix("testtest");
        User user = new User();
        user.setUserName("hello");
        userService.addUser(user);

        Assert.assertEquals(generatedKey, user.getId().intValue());
        verify(mockUserMapper, times(0)).updateTIMIdentifier(anyInt(), anyString());
    }

    @Test
    public void getUser() {
        User user = new User();
        user.setId(111);
        user.setUserName("testname");

        mockUserMapper = mock(UserMapper.class);
        doAnswer(invocation -> {
            User u = invocation.getArgument(0);
            if (Objects.equals(1111, u.getId())) {
                return u;
            }
            return null;
        }).when(mockUserMapper).findOne(any());

        UserServiceImpl userService = new UserServiceImpl(mockUserMapper, null);
        User exceptNonNull = userService.getUser(1111);

        Assert.assertNotNull(exceptNonNull);
        Assert.assertEquals(1111, exceptNonNull.getId().intValue());

        User exceptNull = userService.getUser(2222);
        Assert.assertNull(exceptNull);

        exceptNull = userService.getUser(null);
        Assert.assertNull(exceptNull);
    }
}