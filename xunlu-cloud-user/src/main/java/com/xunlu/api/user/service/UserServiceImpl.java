package com.xunlu.api.user.service;

import com.xunlu.api.user.domain.User;
import com.xunlu.api.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author liweibo
 */
@Service
public class UserServiceImpl implements UserService {
    /**
     * 将自有帐号导入云通信ＩＭ时生成Identifier时的前缀
     */
    @Value("${tencent-im-service.make-identifier-prefix}")
    private String timPrefix;

    private TencentIMService tencentIMService;
    private UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper, TencentIMService tencentIMService) {
        this.userMapper = userMapper;
        this.tencentIMService = tencentIMService;
    }

    @Override
    public void addUser(User user) {
        if (!userMapper.addUser(user)) {
            return;
        }
        String identifier = tencentIMService.makeIdentifier(timPrefix, user.getId());
        if (tencentIMService.accountImport(identifier, null, null)) {
            userMapper.updateTIMIdentifier(user.getId(), identifier);
        }
    }

    /**
     * protected method for test
     * @param timPrefix
     */
    protected void setTimPrefix(String timPrefix) {
        this.timPrefix = timPrefix;
    }

    @Override
    public User getUser(Integer id) {
        return null;
    }

    @Override
    public User findByPhone(String phone) {
        return null;
    }

    @Override
    public User findUserPrefer(Integer userId) {
        return null;
    }

    @Override
    public int updateUser(User user) {
        return 0;
    }

    @Override
    public String findPassword(Integer userId) {
        return null;
    }

    @Override
    public void updatePassword(Integer userId, String password) {

    }

    @Override
    public void updateNickName(Integer userId, String nickName) {

    }

    @Override
    public void updatePersonSign(Integer userId, String personSign) {

    }

    @Override
    public void updatePhoto(Integer userId, String photo) {

    }

    @Override
    public User findById(Integer userId) {
        return null;
    }

    @Override
    public Integer checkLiked(Integer userId, Integer uid) {
        return null;
    }
}
