package com.xunlu.api.user.service;

import com.xunlu.api.user.domain.AccessToken;
import com.xunlu.api.user.domain.User;
import com.xunlu.api.user.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * {@link TokenService}的实现
 * @author liweibo
 */
@Service
public class TokenServiceImpl extends AbstractTokenService implements TokenService{
    private TokenRepository tokenRepository;

    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public AccessToken getExistingOrGenerateNew(User user) {
        if (user == null) {
            throw new UserNotExistException("用户不存在");
        }

        String tokenStr = tokenRepository.findOne(user);

        AccessToken token = tokenStr == null ? generateToken(user) : new AccessToken(tokenStr);

        if (!Objects.equals(tokenStr, token.getToken())) {
            tokenRepository.addToken(user, token.getToken());
        }
        return token;
    }
}
