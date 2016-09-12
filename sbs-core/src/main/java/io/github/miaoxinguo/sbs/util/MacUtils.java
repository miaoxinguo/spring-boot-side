package io.github.miaoxinguo.sbs.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Mac运算工具类
 */
public final class MacUtils {

    private static final String HASH_ALG_HMACSHA1 = "HmacSHA1";

    private MacUtils() {
    }

    /**
     * HmacSHA1 Base64格式
     *
     * @param text 明文
     * @param key 对称密钥
     * @return 散列值的十六进制小写格式
     */
    public static String hmacSha1Base64(String text, String key) throws InvalidKeyException {
        return Base64.encodeBase64String(hmac(text, key, HASH_ALG_HMACSHA1));
    }

    /**
     * mac运算
     *
     * @param text 明文
     * @param key 对称密钥
     * @param alg 算法
     * @return 散列字符串
     */
    private static byte[] hmac(String text, String key, String alg) throws InvalidKeyException {
        Mac mac = null;
        try {
            mac = Mac.getInstance(alg);
            mac.init(new SecretKeySpec(key.getBytes(), mac.getAlgorithm()));
        } catch (NoSuchAlgorithmException e) {
            // ingore
        }
        return mac != null ? mac.doFinal(text.getBytes()) : new byte[0];
    }

}
