package com.finchuk.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Created by olexandr on 30.03.17.
 */
public class PasswordEncoder {
    private static final Logger LOGGER = LogManager.getLogger(PasswordEncoder.class);

    public static String encode(String pass) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("can't find such algorithm", e);
            return null;
        }
        byte[] hash = messageDigest.digest(pass.getBytes());
        return Base64.getEncoder().encodeToString(hash);

    }
}
