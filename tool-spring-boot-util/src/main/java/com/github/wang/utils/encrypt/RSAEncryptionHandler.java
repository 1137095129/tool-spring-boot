package com.github.wang.utils.encrypt;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAEncryptionHandler extends AbstractEncryptionHandler {

    @Override
    protected PrivateKey getPrivateKeyByBaseEncodeKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] bytes = BaseEncryptionUtil.base64Decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    @Override
    protected PublicKey getPublicKeyByBaseEncodeKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] bytes = BaseEncryptionUtil.base64Decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    @Override
    public KeyPair createKeyPair(int keySize) throws NoSuchAlgorithmException {
        return createKeyPair("RSA", keySize);
    }

    @Override
    public EncryptKeyObj createEncryptKeyObj(int keySize) throws Exception {
        KeyPair keyPair = createKeyPair(keySize);
        EncryptKeyObj encryptKeyObj = new EncryptKeyObj();
        encryptKeyObj.setPrivateKey(keyPair.getPrivate());
        encryptKeyObj.setPublicKey(keyPair.getPublic());
        encryptKeyObj.setPublicKeyStr(BaseEncryptionUtil.base64EncodeToString(keyPair.getPublic().getEncoded()));
        encryptKeyObj.setPrivateKeyStr(BaseEncryptionUtil.base64EncodeToString(keyPair.getPrivate().getEncoded()));
        return encryptKeyObj;
    }

    @Override
    public String sign(String targetValue, String privateKey) throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        PrivateKey rsaPrivateKeyByEncodeKey = getPrivateKeyByBaseEncodeKey(privateKey);
        Signature signature = Signature.getInstance("RSA");
        signature.initSign(rsaPrivateKeyByEncodeKey);
        signature.update(targetValue.getBytes());
        return BaseEncryptionUtil.base64EncodeToString(signature.sign());
    }

    @Override
    public boolean verify(String targetValue, String publicKey, String sign) throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        PublicKey rsaPublicKeyByEncodeKey = getPublicKeyByBaseEncodeKey(publicKey);
        Signature signature = Signature.getInstance("RSA");
        signature.initVerify(rsaPublicKeyByEncodeKey);
        signature.update(targetValue.getBytes());
        return signature.verify(BaseEncryptionUtil.base64Decode(sign));
    }
}
