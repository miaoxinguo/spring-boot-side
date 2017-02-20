package io.github.miaoxinguo.sbs.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 散列工具类
 */
public final class HashUtils {

    private static final String HASH_ALG_SHA1 = "SHA1";
    private static final String HASH_ALG_MD5 = "MD5";
    
    private HashUtils() {
    }

    /**
     * MD5 Base64格式
     * 
     * @param text 明文
     * @return Base64散列字符串
     */
    public static String md5Base64(String text) {
        return new String(Base64.encodeBase64(hash(text, HASH_ALG_MD5)));
    }

    /**
     * MD5 十六进制格式
     *
     * @param text 明文
     * @return 散列值的十六进制小写格式
     */
    public static String md5Hex(String text) {
        return Hex.encodeHexString(hash(text, HASH_ALG_MD5));
    }

    /**
     * SHA1 Base64格式
     *
     * @param text 明文
     * @return Base64散列字符串
     */
    public static String sha1Base64(String text) {
        return new String(Base64.encodeBase64(hash(text, HASH_ALG_SHA1)));
    }

    /**
     * SHA1 十六进制格式
     * 
     * @param text 明文
     * @return 散列值的十六进制小写格式
     */
    public static String sha1Hex(String text) {
        return Hex.encodeHexString(hash(text, HASH_ALG_SHA1));
    }

    /**
     * 散列运算
     * 
     * @param text 明文
     * @param alg 算法 
     * @return 散列字符串
     */
    private static byte[] hash(String text, String alg) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(alg);
        } catch (NoSuchAlgorithmException e) {
            // ingore
        }
        return md == null ? new byte[0] : md.digest(text.getBytes());
    }

}
