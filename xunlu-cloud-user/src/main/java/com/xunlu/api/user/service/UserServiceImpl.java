package com.xunlu.api.user.service;

import com.xunlu.api.user.domain.User;
import com.xunlu.api.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
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

    /**
     * protected method for test
     * @param timPrefix
     */
    protected void setTimPrefix(String timPrefix) {
        this.timPrefix = timPrefix;
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

    @Override
    public User getUser(Integer id) {
        if (id == null) {
            return null;
        }

        User user = new User();
        user.setId(id);
        return userMapper.findOne(user);
    }

    @Override
    public User findByPhone(@NonNull String phone) {
        if (phone == null) {
            throw new IllegalArgumentException("手机号不能是null");
        }
        User user = new User();
        user.setPhone(phone);
        return userMapper.findOne(user);
    }

    @Override
    public String findPassword(Integer id) {
        if (id == null) {
            return null;
        }
        return userMapper.findPassword(id);
    }

    @Override
    public User.Prefer getUserPrefer(Integer id) {
        if (id == null) {
            return null;
        }
        return userMapper.getUserPrefer(id);
    }

    @Override
    public boolean updatePrefer(Integer id, User.Prefer prefer) {
        if (id == null) {
            return false;
        }
        if (prefer == null) {
            return false;
        }
        if (!prefer.hasPrefer()) {
            return false;
        }

        return userMapper.updatePrefer(id, prefer);
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
    public Integer checkLiked(Integer userId, Integer uid) {
        return null;
    }
}
