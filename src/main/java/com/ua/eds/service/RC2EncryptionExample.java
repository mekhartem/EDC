package com.ua.eds.service;

import lombok.SneakyThrows;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.*;

public class RC2EncryptionExample {

    @SneakyThrows
    public static void main(String[] args) {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("RC2");
        keyGenerator.init(40);
        SecretKey secretKey = keyGenerator.generateKey();

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        byte[] data = "Hello, World!".getBytes();
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initSign(privateKey);
        signature.update(data);
        byte[] digitalSignature = signature.sign();

        Cipher cipher = Cipher.getInstance("RC2/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] iv = new byte[cipher.getBlockSize()];
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        byte[] encryptedData = cipher.doFinal(data);

        signature.initVerify(publicKey);
        signature.update(data);
        boolean verified = signature.verify(digitalSignature);

        if (verified) {
            System.out.println("Підпис перевірено успішно.");
        } else {
            System.out.println("Підпис не перевірено.");
        }
    }
}
