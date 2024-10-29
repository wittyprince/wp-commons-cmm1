package io.github.wittyprince.commons.cmm1.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.Base64;

public class DecryptUtil {

    private DecryptUtil() {
    }

    /**
     * 密钥长度为32位
     */
    private static final String SECRET_KEY = "e75e2d3126357d031ddaf412f9110f66";
    //偏移量
    private static final String IV = "1234567812345678";

    private static final String ALGORITHM = "AES";

    // 加密模式CBC
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    // 加密模式: ECB
    private static final String TRANSFORMATION_ECB = "AES/ECB/PKCS5Padding";

    /**
     * 前端回传后端解密接口
     *
     * @param content 待解密串
     * @return String
     */
    public static String decode(String content) throws Exception {
        return decode(content, SECRET_KEY, IV);
    }

    public static String decode(String decodedStr, String secretKeyStr, String ivStr) throws Exception {
        SecretKey secretKey = new SecretKeySpec(secretKeyStr.getBytes(), ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivStr.getBytes());
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        byte[] contentBytes = Base64.getDecoder().decode(decodedStr);
        return new String(cipher.doFinal(contentBytes), StandardCharsets.UTF_8);
    }

    /**
     * 加密
     *
     * @param data  待加密串
     * @return String
     */
    public static String encode(String data) throws GeneralSecurityException, IOException {
        return encode(data, SECRET_KEY, IV);
    }

    public static String encode(String toBeEncodedStr, String secretKeyStr, String ivStr) throws GeneralSecurityException {
        if (toBeEncodedStr == null || secretKeyStr == null || ivStr == null) {
            return null;
        }
        // 实例化Cipher对象，它用于完成实际的加密操作
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        IvParameterSpec ips = new IvParameterSpec(ivStr.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(secretKeyStr.getBytes(), ALGORITHM), ips);
        byte[] bytes = cipher.doFinal(toBeEncodedStr.getBytes(StandardCharsets.UTF_8));
        return new String(Base64.getEncoder().encode(bytes), StandardCharsets.UTF_8);
    }

    public static void main(String[] args) throws Exception {
        String data = "admin";
        String encode = encode(data);
        System.out.println(encode);
        String decode = decode(encode);
        System.out.println(decode);
    }
}
