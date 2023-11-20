package com.ua.eds.utils;

import com.ua.eds.dto.SignatureKeyDto;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@UtilityClass
public class DigitalSignatureUtils {

    private final static String ALGORITHM_RSA = "RSA";
    private final static String SIGNATURE_ALGORITHM = "MD5withRSA";

    @SneakyThrows
    public SignatureKeyDto generateKeys() {
        var keyPairGen = KeyPairGenerator.getInstance(ALGORITHM_RSA);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();

        String publicKey = encodeKey(keyPair.getPublic().getEncoded());
        String privateKey = encodeKey(keyPair.getPrivate().getEncoded());
        return new SignatureKeyDto(publicKey, privateKey);
    }

    @SneakyThrows
    public String sign(byte[] data, String privateKeyString) {
        byte[] privateKeyBytes = decodeKey(privateKeyString);

        var keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        var keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        ;
        signature.update(data);

        byte[] digitalSignatureBytes = signature.sign();
        return encodeKey(digitalSignatureBytes);
    }

    @SneakyThrows
    public boolean verify(byte[] data, String signature, String publicKeyString) {
        byte[] publicKeyBytes = decodeKey(publicKeyString);

        var keySpec = new X509EncodedKeySpec(publicKeyBytes);
        var keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
        sig.initVerify(publicKey);

        sig.update(data);

        byte[] signatureBytes = decodeKey(signature);
        return sig.verify(signatureBytes);
    }

    private byte[] decodeKey(String keyString) {
        return Base64.getDecoder().decode(keyString);
    }

    private String encodeKey(byte[] keyBytes) {
        return Base64.getEncoder().encodeToString(keyBytes);
    }

}
