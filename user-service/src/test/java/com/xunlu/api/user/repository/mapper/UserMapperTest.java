package com.xunlu.api.user.repository.mapper;

import com.xunlu.api.common.CommonAutoConfiguration;
import com.xunlu.api.user.domain.ThirdUser;
import com.xunlu.api.user.domain.User;
import com.xunlu.api.user.domain.WeixinThirdUser;
import com.xunlu.api.user.security.ThirdUserPrincipal;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ImportAutoConfiguration(CommonAutoConfiguration.class)
public class UserMapperTest {
    private static final String USER_NAME = "UserMapperTest";

    private static final Integer insertId = -1;

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
    public void testAddThirdUser() {
        ThirdUserPrincipal principal = new ThirdUserPrincipal(ThirdUser.Type.WEIBO, "openid", "liweibo", "http://test.com/test.jpg");

        ThirdUser userHaveId = User.newThirdRegisterUser(principal);
        userHaveId.setId(3);
        boolean ret = userMapper.addThirdUser(userHaveId);
        Assert.assertTrue(ret);
    }

    @Test
    public void testAddWeixinThirdUser() {
        ThirdUserPrincipal principal = new ThirdUserPrincipal(ThirdUser.Type.WEIXIN, "openid", "liweibo", "http://test.com/test.jpg");
        WeixinThirdUser userHaveId = (WeixinThirdUser) User.newThirdRegisterUser(principal);
        userHaveId.setId(3);
        boolean ret = userMapper.addWeixinThirdUser(userHaveId);
        Assert.assertTrue(ret);
    }

    @Test
    @Sql(statements = "INSERT INTO xunlu.tb_user\n" +
            "(id, user_name, nick_name, person_sign, photo, email, phone, password, prefer_natural, prefer_human, prefer_running, prefer_play_time, prefer_night_play, prefer_pub_trans_first, prefer_hotel_level, create_time, prefer_trip_number, prefer_flight, area_code, tim_sync, tim_identifier, is_spider)\n" +
            "VALUES(-1, '"+ USER_NAME +"', '小丸子', '', 'http://wx.qlogo.cn/mmopen/nibxxlib1VaPfA0MxaGnppIyX5D30vgaUykqe1sJ8icWcFvUO376eDUIbqWcM72tHwdPBwygJm69BcL1VOQSt1BtA/0', NULL, NULL, NULL, 20, 20, 20, 20, 10, 20, 20, '2016-09-26 20:17:15.000', 10, 10, NULL, 1, 'fc17719aa31c31875461eeb9cbea6777', 1);\n")
    public void testGetById() {
        Integer id = insertId;
        User findUser = userMapper.getById(id);
        Assert.assertTrue(findUser instanceof User);
        Assert.assertNotNull(findUser);
        Assert.assertEquals(findUser.getId(), id);
        Assert.assertEquals(findUser.getPrefer().getUserId(), id);
    }

    @Test
    @Sql(statements = {"INSERT INTO xunlu.tb_user\n" +
            "(id, user_name, phone, create_time)\n" +
            "VALUES(-1, '"+ USER_NAME +"', 'phone', '2016-09-26 20:17:15.000');\n"})
    public void testFindByPhone() {
        Integer id = insertId;

        User findUser = userMapper.findByPhone("phone");

        Assert.assertNotNull(findUser);
        Assert.assertEquals(id, findUser.getId());
        Assert.assertEquals(id, findUser.getPrefer().getUserId());
    }

    @Test
    @Sql(statements = {"INSERT INTO xunlu.tb_user\n" +
            "(id, user_name, nick_name, person_sign, photo, email, phone, password, prefer_natural, prefer_human, prefer_running, prefer_play_time, prefer_night_play, prefer_pub_trans_first, prefer_hotel_level, create_time, prefer_trip_number, prefer_flight, area_code, tim_sync, tim_identifier, is_spider)\n" +
            "VALUES(-1, '"+ USER_NAME +"', '小丸子', '', NULL, NULL, 'phone', NULL, 20, 20, 20, 20, 10, 20, 20, '2016-09-26 20:17:15.000', 10, 10, NULL, 1, 'fc17719aa31c31875461eeb9cbea6777', 1);\n",
            "INSERT INTO xunlu.tb_third_user\n" +
                    "(user_id, `type`, nick_name, img, openid, create_time)\n" +
                    "VALUES(-1, 3, '石沉海', 'http://test.com/test.jpg', 'testFindThirdUser', '2015-08-31 23:17:25.000');\n"})
    public void testFindThirdUser() {
        Integer id = insertId;

        ThirdUser findUser = userMapper.findThirdUser(ThirdUser.Type.WEIBO, "testFindThirdUser");

        Assert.assertNotNull(findUser);
        Assert.assertEquals(id, findUser.getId());

        Assert.assertEquals("testFindThirdUser", findUser.getOpenid());
        Assert.assertEquals(ThirdUser.Type.WEIBO, findUser.getType());
    }

    @Test
    @Sql(statements = "insert into xunlu.tb_user (id, user_name, password, create_time) values (-1, '"+USER_NAME+"', 'testtest', now())")
    public void testFindPassword() {
        String findPassword = userMapper.findPassword(null);
        Assert.assertNull(findPassword);

        findPassword = userMapper.findPassword(insertId);
        Assert.assertEquals("testtest", findPassword);

        findPassword = userMapper.findPassword(-22);
        Assert.assertNull(findPassword);
    }

    @Test
    @Sql(statements = "INSERT INTO xunlu.tb_user\n" +
            "(id, user_name, nick_name, person_sign, photo, email, phone, password, \n" +
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
            "VALUES(-1, '"+ USER_NAME +"', '小丸子', '', 'http://wx.qlogo.cn/mmopen/nibxxlib1VaPfA0MxaGnppIyX5D30vgaUykqe1sJ8icWcFvUO376eDUIbqWcM72tHwdPBwygJm69BcL1VOQSt1BtA/0', NULL, NULL, NULL, " +
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

        User.Prefer getUserPreferRet = userMapper.getUserPrefer(insertId);
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
            "(id, user_name, nick_name, person_sign, photo, email, phone, password, prefer_natural, prefer_human, prefer_running, prefer_play_time, prefer_night_play, prefer_pub_trans_first, prefer_hotel_level, create_time, prefer_trip_number, prefer_flight, area_code, tim_sync, tim_identifier, is_spider)\n" +
            "VALUES(-1, '"+ USER_NAME +"', '小丸子', '', 'http://wx.qlogo.cn/mmopen/nibxxlib1VaPfA0MxaGnppIyX5D30vgaUykqe1sJ8icWcFvUO376eDUIbqWcM72tHwdPBwygJm69BcL1VOQSt1BtA/0', NULL, NULL, NULL, 20, 20, 20, 20, 10, 20, 20, '2016-09-26 20:17:15.000', 10, 10, NULL, 1, 'fc17719aa31c31875461eeb9cbea6777', 1);\n")
    public void testUpdateTIMIdentifier() {
        // identifier  is not null

        userMapper.updateTIMIdentifier(insertId, "helloworld");

        Map<String, Object> map = jdbcTemplate.queryForMap("select * from tb_user where id = " + insertId);
        Assert.assertEquals("helloworld", map.get("tim_identifier"));
        Assert.assertEquals(1, map.get("tim_sync"));


        // identifier is null
        userMapper.updateTIMIdentifier(insertId, null);

        map = jdbcTemplate.queryForMap("select * from tb_user where id = " + insertId);
        Assert.assertEquals(null, map.get("tim_identifier"));
        Assert.assertEquals(0, map.get("tim_sync"));
    }

    @Test
    @Sql(statements = "insert into tb_user (id, user_name, nick_name, create_time) values (-1, '"+USER_NAME+"', 'testnickname', now())")
    public void testUpdatePrefer() {

        User.Prefer prefer = new User.Prefer();
        prefer.setPreferFlight(User.Prefer.Flight.CHEAP_TRANSFER);

        boolean testUpdatePreferRet = userMapper.updatePrefer(null, prefer);
        Assert.assertFalse(testUpdatePreferRet);

        testUpdatePreferRet = userMapper.updatePrefer(-11, prefer);
        Assert.assertFalse(testUpdatePreferRet);

        testUpdatePreferRet = userMapper.updatePrefer(insertId, prefer);
        Assert.assertTrue(testUpdatePreferRet);
    }

    @Test
    @Sql(statements = "insert into tb_user (id, user_name, nick_name, create_time) values (-1, '"+USER_NAME+"', 'testnickname', now())")
    public void testUpdatePassword() {

        userMapper.updatePassword(insertId, "password");

        String exceptedPassword = getColumnValueById(insertId, "password", String.class);
        Assert.assertEquals("password", exceptedPassword);

        boolean ret = userMapper.updatePassword(null, "password");
        Assert.assertFalse(ret);

        ret = userMapper.updatePassword(null, null);
        Assert.assertFalse(ret);

        ret = userMapper.updatePassword(insertId, null);
        exceptedPassword = getColumnValueById(insertId, "password", String.class);
        Assert.assertTrue(ret);
        Assert.assertNull(exceptedPassword);
    }

    @Test
    @Sql(statements = "insert into tb_user (id, user_name, nick_name, create_time) values (-1, '"+USER_NAME+"', 'nnnnnn', now())")
    public void testUpdateNickName() {

        userMapper.updateNickName(insertId, "testnickname");

        String exceptedNickName = getColumnValueById(insertId, "nick_name", String.class);
        Assert.assertEquals("testnickname", exceptedNickName);

        boolean ret = userMapper.updateNickName(null, "testnickname");
        Assert.assertFalse(ret);

        ret = userMapper.updateNickName(null, null);
        Assert.assertFalse(ret);

        ret = userMapper.updateNickName(insertId, null);
        exceptedNickName = getColumnValueById(insertId, "nick_name", String.class);
        Assert.assertTrue(ret);
        Assert.assertNull(exceptedNickName);
    }

    @Test
    @Sql(statements = "insert into tb_user (id, user_name, nick_name, create_time) values (-1, '"+USER_NAME+"', 'nnnnnn', now())")
    public void testUpdatePersonSign() {

        userMapper.updatePersonSign(insertId, "testpersonsign");

        String exceptedPersonSign = getColumnValueById(insertId, "person_sign", String.class);
        Assert.assertEquals("testpersonsign", exceptedPersonSign );

        boolean ret = userMapper.updatePersonSign(null, "testpersonsign");
        Assert.assertFalse(ret);

        ret = userMapper.updatePersonSign(null, null);
        Assert.assertFalse(ret);

        ret = userMapper.updatePersonSign(insertId, null);
        exceptedPersonSign  = getColumnValueById(insertId, "person_sign", String.class);
        Assert.assertTrue(ret);
        Assert.assertNull(exceptedPersonSign );
    }

    @Test
    @Sql(statements = "insert into tb_user (id, user_name, nick_name, create_time) values (-1, '"+USER_NAME+"', 'nnnnnn', now())")
    public void testUpdatePhoto() {

        userMapper.updatePhoto(insertId, "http://test.com/test.jpg");

        String exceptedPhoto = getColumnValueById(insertId, "photo", String.class);
        Assert.assertEquals("http://test.com/test.jpg", exceptedPhoto );

        boolean ret = userMapper.updatePhoto(null, "http://test.com/test.jpg");
        Assert.assertFalse(ret);

        ret = userMapper.updatePhoto(null, null);
        Assert.assertFalse(ret);

        ret = userMapper.updatePhoto(insertId, null);
        exceptedPhoto  = getColumnValueById(insertId, "photo", String.class);
        Assert.assertTrue(ret);
        Assert.assertNull(exceptedPhoto );
    }

    @Test
    @Sql(statements = {"INSERT INTO xunlu.tb_user\n" +
            "(id, user_name, nick_name, person_sign, photo, email, phone, password, prefer_natural, prefer_human, prefer_running, prefer_play_time, prefer_night_play, prefer_pub_trans_first, prefer_hotel_level, create_time, prefer_trip_number, prefer_flight, area_code, tim_sync, tim_identifier, is_spider)\n" +
            "VALUES(-1, '"+ USER_NAME +"', '小丸子', '', NULL, NULL, 'phone', NULL, 20, 20, 20, 20, 10, 20, 20, '2016-09-26 20:17:15.000', 10, 10, NULL, 1, 'fc17719aa31c31875461eeb9cbea6777', 1);\n",
            "INSERT INTO xunlu.tb_third_user\n" +
                    "(id, user_id, `type`, nick_name, img, openid, create_time)\n" +
                    "VALUES(-1, -1, 3, '石沉海', 'http://test.com/test.jpg', 'testFindThirdUser', '2015-08-31 23:17:25.000');\n"})
    public void updateThirdUserOpenid() {

        userMapper.updateThirdUserOpenid (insertId, "helloworld");

        String exceptedOpenid = jdbcTemplate.queryForObject("select openid from tb_third_user where id = " + insertId, String.class);
        Assert.assertEquals("helloworld", exceptedOpenid );

        boolean ret = userMapper.updateThirdUserOpenid(null, "helloworld");
        Assert.assertFalse(ret);

        ret = userMapper.updateThirdUserOpenid(null, null);
        Assert.assertFalse(ret);

        ret = userMapper.updateThirdUserOpenid(insertId, null);
        exceptedOpenid  = jdbcTemplate.queryForObject("select openid from tb_third_user where id = " + insertId, String.class);
        Assert.assertTrue(ret);
        Assert.assertNull(exceptedOpenid );
    }


    private  <T> T getColumnValueById(Integer id, String column, Class<T> columnClass) {
        return jdbcTemplate.queryForObject("select "+column+" from tb_user where id = " + id, columnClass);
    }
}