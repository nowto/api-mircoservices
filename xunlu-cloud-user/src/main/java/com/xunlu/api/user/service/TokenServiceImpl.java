package com.xunlu.api.user.service;

import com.xunlu.api.user.domain.AccessToken;
import com.xunlu.api.user.domain.User;
import com.xunlu.api.user.repository.TokenRepository;
import org.springframework.stereotype.Service;

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

        AccessToken token = tokenRepository.findOne(user);

        if (token == null) {
            token = generateToken(user);
            tokenRepository.addToken(user, token.getToken());
        }
        return token;
    }
}
