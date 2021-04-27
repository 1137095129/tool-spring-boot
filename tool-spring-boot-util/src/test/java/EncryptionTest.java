import com.github.wang.utils.encrypt.BaseEncryptionUtil;
import com.github.wang.utils.encrypt.RSAEncryptionHandler;
import com.github.wang.utils.regex.RegexUtil;
import org.junit.Test;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class EncryptionTest {
    @Test
    public void RSATest() throws Exception {
        RSAEncryptionHandler handler = new RSAEncryptionHandler();
        KeyPair keyPair = handler.createKeyPair(2048);
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        String privateKeyStr = BaseEncryptionUtil.base64EncodeToString(privateKey.getEncoded());
        String publicKeyStr = BaseEncryptionUtil.base64EncodeToString(publicKey.getEncoded());

        String targetStr = "hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!hello,world!";

        byte[] privateEncryptBytes = handler.encrypt(targetStr, privateKeyStr, 2048, true);
        String privateEncryptStr = BaseEncryptionUtil.base64EncodeToString(privateEncryptBytes);
        System.out.println("private encrypt string:" + privateEncryptStr);

        byte[] publicDecryptBytes = handler.decrypt(privateEncryptStr, publicKeyStr, 2048, false);
        String publicDecryptStr = new String(publicDecryptBytes);
        System.out.println("public decrypt string:" + publicDecryptStr);

        byte[] publicEncryptBytes = handler.encrypt(targetStr, publicKeyStr, 2048, false);
        String publicEncryptStr = BaseEncryptionUtil.base64EncodeToString(publicEncryptBytes);
        System.out.println("public encrypt string:" + publicEncryptStr);

        byte[] privateDecryptBytes = handler.decrypt(publicEncryptStr, privateKeyStr, 2048, true);
        String privateDecryptStr = new String(privateDecryptBytes);
        System.out.println("private decrypt string:" + privateDecryptStr);

    }
}
