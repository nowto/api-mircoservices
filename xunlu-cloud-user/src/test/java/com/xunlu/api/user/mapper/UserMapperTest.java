package com.xunlu.api.user.mapper;

import com.xunlu.api.user.domain.ThirdUser;
import com.xunlu.api.user.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;
import java.util.Map;
import java.util.Objects;

@RunWith(SpringRunner.class)
@MybatisTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserMapperTest {
    private static final String USER_NAME = "UserMapperTest";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserMapper userMapper;


    @Test
    public void testAddUserShoudSuccessWhenUserNoId() {
        User userNoId = new User();

        User.Prefer prefer = new User.Prefer();
        prefer.setPreferFlight(User.Prefer.Flight.CHEAP_TRANSFER);
        userNoId.setPrefer(prefer);
        userNoId.setEmail("liweibor@163.com");
        userNoId.setNickName("liweibor");
        userNoId.setUserName("liweibor");

        boolean ret = userMapper.addUser(userNoId);
        Assert.assertTrue(ret);
        Assert.assertNotNull(userNoId.getId());
        Assert.assertEquals(User.Prefer.Flight.CHEAP_TRANSFER, userNoId.getPrefer().getPreferFlight());
    }

    @Test
    @Sql(statements = "INSERT INTO xunlu.tb_user\n" +
            "(user_name, nick_name, person_sign, photo, email, phone, password, prefer_natural, prefer_human, prefer_running, prefer_play_time, prefer_night_play, prefer_pub_trans_first, prefer_hotel_level, create_time, prefer_trip_number, prefer_flight, area_code, tim_sync, tim_identifier, is_spider)\n" +
            "VALUES('"+ USER_NAME +"', '小丸子', '', 'http://wx.qlogo.cn/mmopen/nibxxlib1VaPfA0MxaGnppIyX5D30vgaUykqe1sJ8icWcFvUO376eDUIbqWcM72tHwdPBwygJm69BcL1VOQSt1BtA/0', NULL, NULL, NULL, 20, 20, 20, 20, 10, 20, 20, '2016-09-26 20:17:15.000', 10, 10, NULL, 1, 'fc17719aa31c31875461eeb9cbea6777', 1);\n")
    public void testFindOneById() {
        Integer id = getIdUserNameEqual(USER_NAME);

        User user = new User();
        user.setId(id);
        User findUser = userMapper.findOne(user);

        Assert.assertTrue(findUser instanceof User);
        Assert.assertNotNull(findUser);
        Assert.assertEquals(findUser.getId(), id);
        Assert.assertEquals(findUser.getPrefer().getUserId(), id);
    }

    @Test
    @Sql(statements = {"INSERT INTO xunlu.tb_user\n" +
            "(id, user_name, nick_name, person_sign, photo, email, phone, password, prefer_natural, prefer_human, prefer_running, prefer_play_time, prefer_night_play, prefer_pub_trans_first, prefer_hotel_level, create_time, prefer_trip_number, prefer_flight, area_code, tim_sync, tim_identifier, is_spider)\n" +
            "VALUES("+Integer.MAX_VALUE+", '"+ USER_NAME +"', '小丸子', '', NULL, NULL, NULL, NULL, 20, 20, 20, 20, 10, 20, 20, '2016-09-26 20:17:15.000', 10, 10, NULL, 1, 'fc17719aa31c31875461eeb9cbea6777', 1);\n",
    "INSERT INTO xunlu.tb_third_user\n" +
            "(user_id, `type`, nick_name, img, openid, create_time)\n" +
            "VALUES("+Integer.MAX_VALUE+", 3, '石沉海', 'http://test.com/test.jpg', 'D644049F4091B90CA941C5CAF3290C14', '2015-08-31 23:17:25.000');\n"})
    public void testFindOneThirdUser() {
        Integer id = getIdUserNameEqual(USER_NAME);

        User user = new User();
        user.setId(id);
        User findUser = userMapper.findOne(user);

        Assert.assertNotNull(findUser);
        Assert.assertEquals(id, findUser.getId());
        Assert.assertEquals(id, findUser.getPrefer().getUserId());

        Assert.assertTrue(findUser instanceof ThirdUser);
        Assert.assertEquals("D644049F4091B90CA941C5CAF3290C14", ((ThirdUser) findUser).getOpenid());
        Assert.assertEquals(ThirdUser.Type.WEIBO, ((ThirdUser) findUser).getType());
        Assert.assertEquals("http://test.com/test.jpg", findUser.getPhoto());
    }

    @Test
    @Sql(statements = "INSERT INTO xunlu.tb_user\n" +
            "(user_name, nick_name, person_sign, photo, email, phone, password, \n" +
            //region enum clomn
            "prefer_natural, " +
            "prefer_human, " +
            "prefer_running, " +
            "prefer_play_time, " +
            "prefer_night_play, " +
            "prefer_pub_trans_first, " +
            "prefer_hotel_level, " +
            //endregion
            "create_time, prefer_trip_number, prefer_flight, area_code, tim_sync, tim_identifier, is_spider)\n" +
            "VALUES('"+ USER_NAME +"', '小丸子', '', 'http://wx.qlogo.cn/mmopen/nibxxlib1VaPfA0MxaGnppIyX5D30vgaUykqe1sJ8icWcFvUO376eDUIbqWcM72tHwdPBwygJm69BcL1VOQSt1BtA/0', NULL, NULL, NULL, " +
            //region enum value
            "20, " +
            "20, " +
            "20, " +
            "20, " +
            "10, " +
            "20, " +
            "20, " +
            //endregion
            "'2016-09-26 20:17:15.000', 10, 10, NULL, 1, 'fc17719aa31c31875461eeb9cbea6777', 1);\n")
    public void testFindOneByAllValue() {
        Integer id = getIdUserNameEqual(USER_NAME);

        User user = new User();
        user.setId(id);
        user.setUserName(USER_NAME);
        user.setNickName("小丸子");
        user.setPersonSign("");
        user.setPhoto("http://wx.qlogo.cn/mmopen/nibxxlib1VaPfA0MxaGnppIyX5D30vgaUykqe1sJ8icWcFvUO376eDUIbqWcM72tHwdPBwygJm69BcL1VOQSt1BtA/0");


        user.setTimIdentifier("fc17719aa31c31875461eeb9cbea6777");

        User.Prefer prefer = new User.Prefer();
        prefer.setPreferTripNumber(User.Prefer.TripNumber.LONE_RANGER);
        prefer.setPreferFlight(User.Prefer.Flight.EFFICIENT_DIRECT);
        prefer.setPreferNatural(User.Prefer.Natural.CLOSE);
        prefer.setPreferHuman(User.Prefer.Natural.CLOSE);
        prefer.setPreferRunning(User.Prefer.Running.COVER_SUBURBS);
        prefer.setPreferPlayTime(User.Prefer.PlayTime.BIG_HALF_DAY);
        prefer.setPreferNightPlay(User.Prefer.NightPlay.REST_IN_HOTEL);
        prefer.setPreferPubTransFirst(User.Prefer.PubTransFirst.SYNTHETICAL);
        prefer.setPreferHotelLevel(User.Prefer.HotelLevel.TWO_THREE_STAR);
        User findUser = userMapper.findOne(user);

        Assert.assertNotNull(findUser);
        Assert.assertEquals(findUser.getId(), id);
    }

    @Test
    @Sql(statements = "insert into xunlu.tb_user (user_name, password, create_time) values ('"+USER_NAME+"', 'testtest', now())")
    public void testFindPassword() {
        String findPassword = userMapper.findPassword(null);
        Assert.assertNull(findPassword);

        Integer id = getIdUserNameEqual(USER_NAME);
        findPassword = userMapper.findPassword(id);
        Assert.assertEquals("testtest", findPassword);

        findPassword = userMapper.findPassword(-22);
        Assert.assertNull(findPassword);
    }

    @Test
    @Sql(statements = "INSERT INTO xunlu.tb_user\n" +
            "(user_name, nick_name, person_sign, photo, email, phone, password, \n" +
            //region enum clomn
            "prefer_natural, " +
            "prefer_human, " +
            "prefer_running, " +
            "prefer_play_time, " +
            "prefer_night_play, " +
            "prefer_pub_trans_first, " +
            "prefer_hotel_level, " +
            //endregion
            "create_time, prefer_trip_number, prefer_flight, area_code, tim_sync, tim_identifier, is_spider)\n" +
            "VALUES('"+ USER_NAME +"', '小丸子', '', 'http://wx.qlogo.cn/mmopen/nibxxlib1VaPfA0MxaGnppIyX5D30vgaUykqe1sJ8icWcFvUO376eDUIbqWcM72tHwdPBwygJm69BcL1VOQSt1BtA/0', NULL, NULL, NULL, " +
            //region enum value
            "20, " +
            "20, " +
            "20, " +
            "20, " +
            "10, " +
            "20, " +
            "20, " +
            //endregion
            "'2016-09-26 20:17:15.000', 10, 10, NULL, 1, 'fc17719aa31c31875461eeb9cbea6777', 1);\n")
    public void testGetUserPrefer() {
        Assert.assertNull(userMapper.getUserPrefer(null));

        Integer id = getIdUserNameEqual(USER_NAME);

        User.Prefer getUserPreferRet = userMapper.getUserPrefer(id);
        Assert.assertEquals(getUserPreferRet.getPreferTripNumber(),  User.Prefer.TripNumber.LONE_RANGER);
        Assert.assertEquals(getUserPreferRet.getPreferFlight(),  User.Prefer.Flight.EFFICIENT_DIRECT);
        Assert.assertEquals(getUserPreferRet.getPreferNatural(),  User.Prefer.Natural.CLOSE);
        Assert.assertEquals(getUserPreferRet.getPreferHuman(),  User.Prefer.Natural.CLOSE);
        Assert.assertEquals(getUserPreferRet.getPreferRunning(),  User.Prefer.Running.COVER_SUBURBS);
        Assert.assertEquals(getUserPreferRet.getPreferPlayTime(),  User.Prefer.PlayTime.BIG_HALF_DAY);
        Assert.assertEquals(getUserPreferRet.getPreferNightPlay(),  User.Prefer.NightPlay.REST_IN_HOTEL);
        Assert.assertEquals(getUserPreferRet.getPreferPubTransFirst(),  User.Prefer.PubTransFirst.SYNTHETICAL);
        Assert.assertEquals(getUserPreferRet.getPreferHotelLevel(),  User.Prefer.HotelLevel.TWO_THREE_STAR);
    }

    @Test
    @Sql(statements = "INSERT INTO xunlu.tb_user\n" +
            "(user_name, nick_name, person_sign, photo, email, phone, password, prefer_natural, prefer_human, prefer_running, prefer_play_time, prefer_night_play, prefer_pub_trans_first, prefer_hotel_level, create_time, prefer_trip_number, prefer_flight, area_code, tim_sync, tim_identifier, is_spider)\n" +
            "VALUES('"+ USER_NAME +"', '小丸子', '', 'http://wx.qlogo.cn/mmopen/nibxxlib1VaPfA0MxaGnppIyX5D30vgaUykqe1sJ8icWcFvUO376eDUIbqWcM72tHwdPBwygJm69BcL1VOQSt1BtA/0', NULL, NULL, NULL, 20, 20, 20, 20, 10, 20, 20, '2016-09-26 20:17:15.000', 10, 10, NULL, 1, 'fc17719aa31c31875461eeb9cbea6777', 1);\n")
    public void testUpdateTIMIdentifier() {
        // identifier  is not null
        Integer id = getIdUserNameEqual(USER_NAME);

        userMapper.updateTIMIdentifier(id, "helloworld");

        Map<String, Object> map = jdbcTemplate.queryForMap("select * from tb_user where id = " + id);
        Assert.assertEquals("helloworld", map.get("tim_identifier"));
        Assert.assertEquals(1, map.get("tim_sync"));


        // identifier is null
        userMapper.updateTIMIdentifier(id, null);

        map = jdbcTemplate.queryForMap("select * from tb_user where id = " + id);
        Assert.assertEquals(null, map.get("tim_identifier"));
        Assert.assertEquals(0, map.get("tim_sync"));
    }

    @Test
    @Sql(statements = "insert into tb_user (user_name, nick_name, create_time) values ('"+USER_NAME+"', 'testnickname', now())")
    public void testUpdatePrefer() {
        Integer id = getIdUserNameEqual(USER_NAME);

        User.Prefer prefer = new User.Prefer();
        prefer.setPreferFlight(User.Prefer.Flight.CHEAP_TRANSFER);

        boolean testUpdatePreferRet = userMapper.updatePrefer(null, prefer);
        Assert.assertFalse(testUpdatePreferRet);

        testUpdatePreferRet = userMapper.updatePrefer(-11, prefer);
        Assert.assertFalse(testUpdatePreferRet);

        testUpdatePreferRet = userMapper.updatePrefer(id, prefer);
        Assert.assertTrue(testUpdatePreferRet);
    }

    @Test
    @Sql(statements = "insert into tb_user (user_name, nick_name, create_time) values ('"+USER_NAME+"', 'testnickname', now())")
    public void testUpdatePassword() {
        Integer id = getIdUserNameEqual(USER_NAME);

        userMapper.updatePassword(id, "password");

        String exceptedPassword = getColumnValueById(id, "password", String.class);
        Assert.assertEquals("password", exceptedPassword);

        boolean ret = userMapper.updatePassword(null, "password");
        Assert.assertFalse(ret);

        ret = userMapper.updatePassword(null, null);
        Assert.assertFalse(ret);

        ret = userMapper.updatePassword(id, null);
        exceptedPassword = getColumnValueById(id, "password", String.class);
        Assert.assertTrue(ret);
        Assert.assertNull(exceptedPassword);
    }

    @Test
    @Sql(statements = "insert into tb_user (user_name, nick_name, create_time) values ('"+USER_NAME+"', 'nnnnnn', now())")
    public void testUpdateNickName() {
        Integer id = getIdUserNameEqual(USER_NAME);

        userMapper.updateNickName(id, "testnickname");

        String exceptedNickName = getColumnValueById(id, "nick_name", String.class);
        Assert.assertEquals("testnickname", exceptedNickName);

        boolean ret = userMapper.updateNickName(null, "testnickname");
        Assert.assertFalse(ret);

        ret = userMapper.updateNickName(null, null);
        Assert.assertFalse(ret);

        ret = userMapper.updateNickName(id, null);
        exceptedNickName = getColumnValueById(id, "nick_name", String.class);
        Assert.assertTrue(ret);
        Assert.assertNull(exceptedNickName);
    }

    private Integer getIdUserNameEqual(String userName) {
        return jdbcTemplate.queryForObject("select id from tb_user where user_name='" + userName + "'", Integer.class);
    }

    private  <T> T getColumnValueById(Integer id, String column, Class<T> columnClass) {
        return jdbcTemplate.queryForObject("select "+column+" from tb_user where id = " + id, columnClass);
    }
}