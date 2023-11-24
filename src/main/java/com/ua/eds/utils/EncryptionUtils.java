package com.ua.eds.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@UtilityClass
public class EncryptionUtils {

    private final static String ALGORITHM_AES = "AES";

    @SneakyThrows
    public String generateSecretKey() {
        var keyGenerator = KeyGenerator.getInstance(ALGORITHM_AES);
        keyGenerator.init(256);
        return Base64Utils.encodeKey(keyGenerator.generateKey().getEncoded());
    }

    @SneakyThrows
    public String encrypt(String msg, String privateKey) {
        var secretKey = new SecretKeySpec(Base64Utils.decodeKey(privateKey), ALGORITHM_AES);

        var cipher = Cipher.getInstance(ALGORITHM_AES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encryptedBytes = cipher.doFinal(msg.getBytes(StandardCharsets.UTF_8));
        return Base64Utils.encodeKey(encryptedBytes);
    }

    @SneakyThrows
    public String decrypt(String msg, String privateKey) {
        var secretKey = new SecretKeySpec(Base64Utils.decodeKey(privateKey), ALGORITHM_AES);

        var cipher = Cipher.getInstance(ALGORITHM_AES);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] decryptedBytes = cipher.doFinal(Base64Utils.decodeKey(msg));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

}
