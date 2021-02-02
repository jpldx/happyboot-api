package org.happykit.happyboot.util;


import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA公钥/私钥/签名工具包
 *
 * @author shaoqiang
 * @version 1.0
 * @date 2020/7/23 17:39
 */
public class RSAUtils {
    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";

    /**
     * 密钥长度 于原文长度对应 以及越长速度越慢
     */
    private final static int KEY_SIZE = 1024;
    /**
     * 用于封装随机产生的公钥与私钥
     */
    private static Map<Integer, String> keyMap = new HashMap<Integer, String>(2);

    /**
     * 随机生成密钥对
     */
    public static void genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        // 初始化密钥对生成器
        keyPairGen.initialize(KEY_SIZE, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 得到公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        // 得到私钥字符串
        String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        // 将公钥和私钥保存到Map
        //0表示公钥
        keyMap.put(0, publicKeyString);
        //1表示私钥
        keyMap.put(1, privateKeyString);
    }

    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str, String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.getDecoder().decode(publicKey);
        Key pubKey = KeyFactory.getInstance(KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance(pubKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = Base64.getDecoder().decode(str);
        //base64编码的私钥
        byte[] decoded = Base64.getDecoder().decode(privateKey);
        Key priKey = KeyFactory.getInstance(KEY_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance(priKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(System.currentTimeMillis());
        long temp = System.currentTimeMillis();
        //生成公钥和私钥
        genKeyPair();


        //加密字符串
        System.out.println("公钥:" + keyMap.get(0));
        System.out.println("私钥:" + keyMap.get(1));
        System.out.println("生成密钥消耗时间:" + (System.currentTimeMillis() - temp) / 1000.0 + "秒");
        //客户id + 授权时间 + 所用模块
        String message = "4028138151b3cf300151b419df090007" + "2015-12-17 11:30:22" + "A01,A02";
        message = "admin";
        System.out.println("原文:" + message);
        temp = System.currentTimeMillis();
        //通过原文，和公钥加密。

//		String messageEn1 = encrypt(message, keyMap.get(0));
        String messageEn = encrypt(message, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC/zPfsnwaSjJ3oqcIg9vSic5uCQK1ep0MwyeY9w3PRDk7dgSMJY2b/f7D+1fQsK0FDU3wTwJUeOOfOcfjbQyet4BtiwNo58w2kyA+vCozhu4BUSIAWdpm37F4AOaRadN8P2v/cwCnIlEnuaSG/VtCj2wBlPMMAXcxazj9PWIUfzQIDAQAB");
        messageEn = "uMi6pI3bD9RaQcoEjJvOHha6P3zKVM8dQJizN63Mkm2uQlEngqvzAH0z/XtC4f5KSyKqsVOLufQxHsf0Kq1VcnAP2pTfJGjLG6LvvUSXSPHYUANiQfmw6CHRlZ8dkgIFulghtUypqRnqBoydcjswuVJ4T9ZPkeDryTJmIvFD/08=";
        System.out.println("密文:" + messageEn);
        System.out.println("加密消耗时间:" + (System.currentTimeMillis() - temp) / 1000.0 + "秒");
        temp = System.currentTimeMillis();
        //通过密文，和私钥解密。
//		String messageDe1 = decrypt(messageEn1, keyMap.get(1));
        String messageDe = decrypt(messageEn, "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAL/M9+yfBpKMneipwiD29KJzm4JArV6nQzDJ5j3Dc9EOTt2BIwljZv9/sP7V9CwrQUNTfBPAlR44585x+NtDJ63gG2LA2jnzDaTID68KjOG7gFRIgBZ2mbfsXgA5pFp03w/a/9zAKciUSe5pIb9W0KPbAGU8wwBdzFrOP09YhR/NAgMBAAECgYBlv8e6eHaVIhHXTs9Ui44l7CyQQd13PEZxyHnjRB/ZxLxj19ENdvU6D7SGzFv3Xo8Ft3E4TU8ONGQM6ft53jtanvj5IZPabk/lkpge8L6WqSxnYcWJNWxcJJBSuDk5Z4U0nW0FZNM0tlBGPppC+rNSDanO3tgrZ3lpIn7FIrBL5QJBAPaVXGbW+HiqrUZCapf+aw6/FfG3RrK26jmMWRv28QhmTUE7y9oay2FQIN/uFJ/Tp7gm+dt6Mc1w0h6+BVuKcMMCQQDHIArTDuWoDSfdmFQh33T5xGX1q5+jkHDXJ0dQuGDmHcr7mPDCTEUXMbNy25rVGhgHSDfxScqvoYC/pWbA1SQvAkAm1itxfxYvWyJjWH6VZdrSvcHlCiq2ZxzI55P5VZFs8z/jsFlRBrtVnlsvKb5R1fIqjOj5amuBoe1WLjOF0W4lAkBH7VobnQD18DKbR9/0EFyXsArIcAMNOSqZfTW0gbV2ygI9WaR1+sjmNOzGK29FVNSjJMIYZXhScrsn/t4b/6G/AkBsM9ZTRT0T+O38g2OALwlrYY+tE3Cx3tv7SkSUSMKr9Vyry8aDR9wjHY73MfIjP93Mu3TNJZm/NV6vwOYgzEan");
        System.out.println("解密:" + messageDe);
        System.out.println("解密消耗时间:" + (System.currentTimeMillis() - temp) / 1000.0 + "秒");
    }
}