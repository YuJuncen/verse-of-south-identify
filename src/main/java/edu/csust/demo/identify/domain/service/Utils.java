package edu.csust.demo.identify.domain.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    public static MessageDigest md5Instance() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not supported!");
        }
    }
}
