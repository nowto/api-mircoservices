package com.xunlu.api.user.service;

import com.xunlu.api.user.domain.User;
import com.xunlu.api.user.repository.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Objects;

import static org.mockito.Mockito.*;

public class UserServiceImplTest {
    UserMapper mockUserMapper;
    TencentIMService mockTencentIMService;

    @Test
    public void testAddUserShouldUpdateIdentiferWhenAccountImportSuccess() {
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
    public void testAddUserShouldNoUpdateIdentiferWhenAccountImportFail() {
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
    public void testGetUser() {
        mockUserMapper = mock(UserMapper.class);
        doAnswer(invocation -> {
            Integer id = invocation.getArgument(0);
            if (Objects.equals(1111, id)) {
                User u = new User();
                u.setId(id);
                return u;
            }
            return null;
        }).when(mockUserMapper).getById(anyInt());

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
    public void testFindByPhoneShoudThrowExceptionWhenParamNull() {
        UserServiceImpl userService = new UserServiceImpl(null, null);
        userService.findByPhone(null);
    }

    @Test
    public void testFindByPhoneNonException() {
        mockUserMapper = mock(UserMapper.class);
        doAnswer(invocation -> {
            String phone = invocation.getArgument(0);
            if (Objects.equals("110", phone)) {
                User u = new User();
                u.setPhone(phone);
                return u;
            }
            return null;
        }).when(mockUserMapper).findByPhone(any());

        UserServiceImpl userService = new UserServiceImpl(mockUserMapper, null);
        User exceptNonNull = userService.findByPhone("110");

        Assert.assertNotNull(exceptNonNull);
        Assert.assertEquals("110", exceptNonNull.getPhone());

        User exceptNull = userService.findByPhone("notFound");
        Assert.assertNull(exceptNull);
    }

    @Test
    public void testGetUserPrefer() {
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
    public void testFindPassword() {
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
    public void testUpdatePrefer() {
        User.Prefer prefer = new User.Prefer();
        prefer.setPreferFlight(User.Prefer.Flight.CHEAP_TRANSFER);

        mockUserMapper = mock(UserMapper.class);
        when(mockUserMapper.updatePrefer(1, prefer)).thenReturn(true);
        when(mockUserMapper.updatePrefer(2, prefer)).thenReturn(false);
        when(mockUserMapper.updatePrefer(null, prefer)).thenReturn(false);
        when(mockUserMapper.updatePrefer(1, null)).thenReturn(false);
        when(mockUserMapper.updatePrefer(null, null)).thenReturn(false);

        UserServiceImpl userService = new UserServiceImpl(mockUserMapper, null);

        boolean ret = userService.updatePrefer(1, prefer);
        Assert.assertTrue(ret);

        ret = userService.updatePrefer(2, prefer);
        Assert.assertFalse(ret);

        ret = userService.updatePrefer(null, prefer);
        Assert.assertFalse(ret);

        ret = userService.updatePrefer(1, null);
        Assert.assertFalse(ret);

        ret = userService.updatePrefer(null, null);
        Assert.assertFalse(ret);
    }

    @Test
    public void testUpdatePassword() {
        mockUserMapper = mock(UserMapper.class);
        when(mockUserMapper.updatePassword(1, "password")).thenReturn(true);
        when(mockUserMapper.updatePassword(2, "password")).thenReturn(false);
        when(mockUserMapper.updatePassword(null, "password")).thenReturn(false);
        when(mockUserMapper.updatePassword(1, null)).thenReturn(true);
        when(mockUserMapper.updatePassword(null, null)).thenReturn(false);

        UserServiceImpl userService = new UserServiceImpl(mockUserMapper, null);

        boolean ret = userService.updatePassword(1, "password");
        Assert.assertTrue(ret);

        ret = userService.updatePassword(2, "password");
        Assert.assertFalse(ret);

        ret = userService.updatePassword(null, "password");
        Assert.assertFalse(ret);

        ret = userService.updatePassword(1, null);
        Assert.assertTrue(ret);

        ret = userService.updatePassword(null, null);
        Assert.assertFalse(ret);
    }

    @Test
    public void testUpdateNickName() {
        mockUserMapper = mock(UserMapper.class);
        when(mockUserMapper.updateNickName(1, "nickname")).thenReturn(true);
        when(mockUserMapper.updateNickName(2, "nickname")).thenReturn(false);
        when(mockUserMapper.updateNickName(null, "nickname")).thenReturn(false);
        when(mockUserMapper.updateNickName(1, null)).thenReturn(true);
        when(mockUserMapper.updateNickName(null, null)).thenReturn(false);

        UserServiceImpl userService = new UserServiceImpl(mockUserMapper, null);

        boolean ret = userService.updateNickName(1, "nickname");
        Assert.assertTrue(ret);

        ret = userService.updateNickName(2, "nickname");
        Assert.assertFalse(ret);

        ret = userService.updateNickName(null, "nickname");
        Assert.assertFalse(ret);

        ret = userService.updateNickName(1, null);
        Assert.assertTrue(ret);

        ret = userService.updateNickName(null, null);
        Assert.assertFalse(ret);

    }

    @Test
    public void testUpdatePersonSign() {
        mockUserMapper = mock(UserMapper.class);
        when(mockUserMapper.updatePersonSign(1, "personsign")).thenReturn(true);
        when(mockUserMapper.updatePersonSign(2, "personsign")).thenReturn(false);
        when(mockUserMapper.updatePersonSign(null, "personsign")).thenReturn(false);
        when(mockUserMapper.updatePersonSign(1, null)).thenReturn(true);
        when(mockUserMapper.updatePersonSign(null, null)).thenReturn(false);

        UserServiceImpl userService = new UserServiceImpl(mockUserMapper, null);

        boolean ret = userService.updatePersonSign(1, "personsign");
        Assert.assertTrue(ret);

        ret = userService.updatePersonSign(2, "personsign");
        Assert.assertFalse(ret);

        ret = userService.updatePersonSign(null, "personsign");
        Assert.assertFalse(ret);

        ret = userService.updatePersonSign(1, null);
        Assert.assertTrue(ret);

        ret = userService.updatePersonSign(null, null);
        Assert.assertFalse(ret);
    }

    @Test
    public void testUpdatePhoto() {
        mockUserMapper = mock(UserMapper.class);
        when(mockUserMapper.updatePhoto(1, "http://test.com/test.jpg")).thenReturn(true);
        when(mockUserMapper.updatePhoto(2, "http://test.com/test.jpg")).thenReturn(false);
        when(mockUserMapper.updatePhoto(null, "http://test.com/test.jpg")).thenReturn(false);
        when(mockUserMapper.updatePhoto(1, null)).thenReturn(true);
        when(mockUserMapper.updatePhoto(null, null)).thenReturn(false);

        UserServiceImpl userService = new UserServiceImpl(mockUserMapper, null);

        boolean ret = userService.updatePhoto(1, "http://test.com/test.jpg");
        Assert.assertTrue(ret);

        ret = userService.updatePhoto(2, "http://test.com/test.jpg");
        Assert.assertFalse(ret);

        ret = userService.updatePhoto(null, "http://test.com/test.jpg");
        Assert.assertFalse(ret);

        ret = userService.updatePhoto(1, null);
        Assert.assertTrue(ret);

        ret = userService.updatePhoto(null, null);
        Assert.assertFalse(ret);
    }
}