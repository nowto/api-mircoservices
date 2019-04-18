package com.xunlu.api.gateway.security.service;

import com.xunlu.api.user.domain.User;
import com.xunlu.api.gateway.security.repository.TokenRepository;
import org.springframework.stereotype.Service;

/**
 * TokenService实现
 * @author liweibo
 */
@Service
public class TokenServiceImpl implements TokenService {
    private TokenRepository tokenRepository;

    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public User getUser(String token) {
        if (token == null) {
            return null;
        }
        return tokenRepository.getUserByToken(token);
    }

    @Override
    public void invalidateTokenForUser(Integer userId) {
        tokenRepository.deleteToken(userId);
    }
}
