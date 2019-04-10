package com.xunlu.api.user.service;

import com.xunlu.api.user.domain.ThirdUser;
import com.xunlu.api.user.domain.User;
import com.xunlu.api.user.repository.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void addUser(User user) {
        if (!userMapper.addUser(user)) {
            return;
        }
        if (user instanceof ThirdUser) {
            userMapper.addThirdUser((ThirdUser) user);
        }
        String identifier = tencentIMService.makeIdentifier(timPrefix, user.getId());
        if (tencentIMService.accountImport(identifier, null, null)) {
            userMapper.updateTIMIdentifier(user.getId(), identifier);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Integer id) {
        if (id == null) {
            return null;
        }
        return userMapper.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByPhone(@NonNull String phone) {
        if (phone == null) {
            throw new IllegalArgumentException("手机号不能是null");
        }
        return userMapper.findByPhone(phone);
    }

    @Override
    public ThirdUser findThirdUserByTypeAndOpenid(ThirdUser.Type type, String openid) {
        if (type == null) {
            throw new IllegalArgumentException("登录类型ThirdUser.type不能为null");
        }
        if (openid == null) {
            throw new IllegalArgumentException("openid不能为null");
        }

        ThirdUser condition = new ThirdUser();
        condition.setType(type);
        condition.setOpenid(openid);
        User user = userMapper.findOne(condition);
        if (user instanceof ThirdUser) {
            return (ThirdUser) user;
        } else {
            throw new ServiceException("");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public String findPassword(Integer id) {
        if (id == null) {
            return null;
        }
        return userMapper.findPassword(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User.Prefer getUserPrefer(Integer id) {
        if (id == null) {
            return null;
        }
        return userMapper.getUserPrefer(id);
    }

    @Override
    @Transactional
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
    @Transactional
    public boolean updatePassword(Integer id, String password) {
        return userMapper.updatePassword(id, password);
    }

    @Override
    @Transactional
    public boolean updateNickName(Integer id, String nickName) {
        return userMapper.updateNickName(id, nickName);
    }

    @Override
    @Transactional
    public boolean updatePersonSign(Integer id, String personSign) {
        return userMapper.updatePersonSign(id, personSign);
    }

    @Override
    @Transactional
    public boolean updatePhoto(Integer id, String photo) {
        return userMapper.updatePhoto(id, photo);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer checkLiked(Integer userId, Integer uid) {
        return null;
    }
}
