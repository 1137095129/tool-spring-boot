package com.github.wang.utils.encrypt;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;

public class EncryptKeyObj implements Serializable {
    private static final long serialVersionUID = 7890349981274940779L;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    private String publicKeyStr;
    private String privateKeyStr;

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKeyStr() {
        return publicKeyStr;
    }

    public void setPublicKeyStr(String publicKeyStr) {
        this.publicKeyStr = publicKeyStr;
    }

    public String getPrivateKeyStr() {
        return privateKeyStr;
    }

    public void setPrivateKeyStr(String privateKeyStr) {
        this.privateKeyStr = privateKeyStr;
    }
}
