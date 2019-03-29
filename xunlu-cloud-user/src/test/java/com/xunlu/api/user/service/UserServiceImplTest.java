package com.xunlu.api.user.service;

import com.xunlu.api.user.domain.User;
import com.xunlu.api.user.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

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

    @Test(expected = IllegalArgumentException.class)
    public void findByPhoneShoudThrowExceptionWhenParamNull() {
        UserServiceImpl userService = new UserServiceImpl(null, null);
        userService.findByPhone(null);
    }

    @Test
    public void findByPhonNonException() {
        mockUserMapper = mock(UserMapper.class);
        doAnswer(invocation -> {
            User u = invocation.getArgument(0);
            if (Objects.equals("110", u.getPhone())) {
                return u;
            }
            return null;
        }).when(mockUserMapper).findOne(any());

        UserServiceImpl userService = new UserServiceImpl(mockUserMapper, null);
        User exceptNonNull = userService.findByPhone("110");

        Assert.assertNotNull(exceptNonNull);
        Assert.assertEquals("110", exceptNonNull.getPhone());

        User exceptNull = userService.findByPhone("notFound");
        Assert.assertNull(exceptNull);
    }

    @Test
    public void getUserPrefer() {
        mockUserMapper = mock(UserMapper.class);
        User.Prefer prefer = new User.Prefer();
        prefer.setUserId(1111);
        prefer.setPreferFlight(User.Prefer.Flight.CHEAP_TRANSFER);
        when(mockUserMapper.getUserPrefer(1111)).thenReturn(prefer);


        UserServiceImpl userService = new UserServiceImpl(mockUserMapper, null);
        User.Prefer exceptNonNull = userService.getUserPrefer(1111);

        Assert.assertNotNull(exceptNonNull);
        Assert.assertEquals(1111, exceptNonNull.getUserId().intValue());
        Assert.assertEquals(User.Prefer.Flight.CHEAP_TRANSFER, exceptNonNull.getPreferFlight());

        //-------
        when(mockUserMapper.getUserPrefer(2222)).thenReturn(null);
        User exceptNull = userService.getUser(2222);
        Assert.assertNull(exceptNull);

        exceptNull = userService.getUser(null);
        Assert.assertNull(exceptNull);
    }

    @Test
    public void findPassword() {
        mockUserMapper = mock(UserMapper.class);
        when(mockUserMapper.findPassword(11)).thenReturn("testtest");
        when(mockUserMapper.findPassword(22)).thenReturn(null);
        UserServiceImpl userService = new UserServiceImpl(mockUserMapper, null);

        String findPassword = userService.findPassword(null);
        Assert.assertNull(findPassword);

        findPassword = userService.findPassword(11);
        Assert.assertEquals("testtest", findPassword);

        findPassword = userService.findPassword(22);
        Assert.assertNull(findPassword);
    }

    @Test
    public void updatePrefer() {
        User.Prefer prefer = new User.Prefer();
        prefer.setPreferFlight(User.Prefer.Flight.CHEAP_TRANSFER);

        mockUserMapper = mock(UserMapper.class);
        when(mockUserMapper.updatePrefer(1, prefer)).thenReturn(true);
        when(mockUserMapper.updatePrefer(2, prefer)).thenReturn(false);
        when(mockUserMapper.updatePrefer(null, prefer)).thenReturn(false);
        when(mockUserMapper.updatePrefer(1, null)).thenReturn(false);

    }
}