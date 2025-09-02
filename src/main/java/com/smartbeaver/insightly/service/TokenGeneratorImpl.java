package com.smartbeaver.insightly.service;


import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;


@Component
public class TokenGeneratorImpl implements TokenGeneratorService {
    private final SecureRandom random = new SecureRandom();


    public String generateUrlSafeToken(int bytes) {
        byte[] buf = new byte[bytes];
        random.nextBytes(buf);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(buf);
    }
}

