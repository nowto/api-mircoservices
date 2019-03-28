package com.xunlu.api.user.mapper;

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

import java.util.Map;

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
        Integer id = jdbcTemplate.queryForObject("select id from tb_user where user_name='" + USER_NAME + "'", Integer.class);

        User user = new User();
        user.setId(id);
        User findUser = userMapper.findOne(user);

        Assert.assertNotNull(findUser);
        Assert.assertEquals(findUser.getId(), id);
        Assert.assertEquals(findUser.getPrefer().getUserId(), id);
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
        Integer id = jdbcTemplate.queryForObject("select id from tb_user where user_name='" + USER_NAME + "'", Integer.class);

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

        Integer id = jdbcTemplate.queryForObject("select id from tb_user where user_name='" + USER_NAME + "'", Integer.class);

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
        Integer id = jdbcTemplate.queryForObject("select id from tb_user where user_name='" + USER_NAME + "'", Integer.class);

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
}