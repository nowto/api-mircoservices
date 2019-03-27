package com.xunlu.api.user.service;

import com.xunlu.api.user.domain.User;
import com.xunlu.api.user.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

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
}