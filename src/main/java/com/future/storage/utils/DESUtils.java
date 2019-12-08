package com.future.storage.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * DES加密工具类
 *
 * @author shiyong
 * 2019-10-09 11:35
 */
public class DESUtils {

    private final static String DES = "DES";

    private final static Base64.Decoder decoder = Base64.getDecoder();
    private final static Base64.Encoder encoder = Base64.getEncoder();

    /**
     * 根据键值进行加密
     * @param data 数据
     * @param key  秘钥
     * @return String
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
        byte[] bt = encrypt(data.getBytes("utf-8"), key.getBytes("utf-8"));

        return encoder.encodeToString(bt);
    }

    /**
     * 根据键值进行解密
     * @param data 数据
     * @param key  秘钥
     * @return String
     * @throws Exception
     */
    public static String decrypt(String data, String key) throws Exception {
        if (data == null){
            return null;
        }

        byte[] buf = decoder.decode(data);
        byte[] bt = decrypt(buf,key.getBytes());

        return new String(bt, "utf-8");
    }

    /**
     * 根据键值进行加密
     * @param data 数据
     * @param key  加密键byte数组
     * @return byte[]
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey secretKey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);

        return cipher.doFinal(data);
    }


    /**
     * 根据键值进行解密
     * @param data 数据
     * @param key  加密键byte数组
     * @return byte[]
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey secretKey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);

        return cipher.doFinal(data);
    }
}
