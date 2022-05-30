package com.miaomi.fenbei.base.util;

import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Cipher;

public class EncryptHelp {

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

//    private static final String SERVER_KEY_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDBnUPvc11VWcoyJyFJGDiyWuof4LnS2vgEChj+iVaAz+/eGbqJkeQp9ynCQs2zJFP7sONKwKFXKMa4D5hK6jIPZPh2aRzm3jhDzSvgHAx/3NkUt7+2HJjYeIXbswCCdXnoA8MQc8u/Wptm/GNqfXOUjUtplsVzlpHW7zts5OQu1QIDAQAB";

//    private static final String SERVER_KEY_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCmXSQQKdZnIpeBeythL8Vclijz1J8DvAOAC1GDohZtEpXKdrwMMw+R/JMieZja4KvvCXBz42JaP0H4zERWaFb7i3b3GjiGHzftsQlD3dTCc/s8V0djxC3cb3GnG3yGVanAVgFaq0f1ybOPIcUBo1ZH5RO9zwlCfpfLNdTo6HSiuwIDAQAB";

    private static final String SERVER_KEY_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC8UK5kW0PqIcZgmH3fhRvba1FTbDQ+BU4X4GVS9onPtSLywIIuIbkF7cqCincuvUMQOx9G1P3DUsrheXLiiMj5OhPKdgU6l1TxWLcp0ZkNiPaDC6hX2nTS3Gj+T/jBYgLRomijanVCZ41D1C//xWEDwDA5FonRy1OS+G9R7CQh8QIDAQAB";

//    private static final String CLIENT_KEY_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALyyi6PfuTdZ+9mc"
//            + "RMu88UfE0tea20+KUFj+yHnkmDflp7ucw+losv23Gm6of0OwTh2PNYcHeDLpk6xq"
//            + "lkfECAELny1n6PdPIMrAV1VYMOD+oLsL1+cskOjouCQJtrXR4hmWWX6qC2nJWvbe"
//            + "RZaASQ5jHUAg2HLX4mxtPsWr4ju3AgMBAAECgYB0ofdl5xbYe6oLq2dqdvK75ZBc"
//            + "6766v0dCetj3XrAnfK/cat09HBXmdJLF6ygeco8V/jqbp6ZH8c/xNkCFQ0meKf1z"
//            + "v9P07qrGuWNhOalAU/I+ITJcEtcl15AzN+3U+paPd7s6caLnrvNV91dNaRAg2jgj"
//            + "9sIKy8lwqaXhiLL2oQJBAPjrSQwfWxD9EPMLxvtKFv5Ny0Nwagz/bCj55of1qeGN"
//            + "iDzdaHiUWY53KfbB999GIn6DaSqOFcHKO/rLdEaNPtMCQQDCELSSvvATklZ0YDFH"
//            + "eX7dWNBDdYksfaOV6zgfIl1pA9bNOFNJeLk1SLvGCWqaQJCSDxHX/l2BJj3TTahQ"
//            + "WOkNAkEAxIMj8SEUCO5xIh/LIHnWez+5V+14m/hOUG8x02Zbjojo5Hw7TO55YWKs"
//            + "S3XIlYlOFCj0rrbrcEmTXqSekFBUJwJAGCwCgeC8gIOSty4gFToB3koorq5eJqeD"
//            + "j7HbrK0YG3N59tfUL+uUjhmAIfucRphSKY8s9s1dEjAUNVSP6WoZpQJARSwak50+"
//            + "OCdncbUM/gwfnDHvr8AhV5G0gQovi70OqVPh6K8bNpmv+rzbDiUpHH695FyG0BM1"
//            + "FlDvnfRZGRI82Q==";

    public static Map<String, String> encrypt(Map<String, String> list) {
        Map<String, String> result = new TreeMap<String, String>();
        try {
            String strBusiness = generateBusinessString(list);
            String strReqData = encryptTempKey(strBusiness);

            result.put("reqdata", strReqData);
//            result.put("token", SharePrefUtil.getInstance(context).getToken());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

//    public static String encrypt( String str, String publicKey ) throws Exception{
//        //base64编码的公钥
//        byte[] decoded = MrBase64Utils.decode(publicKey);
//        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
//        //RSA加密
//        Cipher cipher = Cipher.getInstance("RSA");
//        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
//        String outStr = MrBase64Utils.encode(cipher.doFinal(str.getBytes("UTF-8")));
//        return outStr;
//    }

//    public static Map<String, String> decrypt(String reqkey, String reqdata) {
//        Map<String, String> result = new HashMap<>();
//
//        String tempStr = decryptResponse(reqdata);
//        tempStr = tempStr.replaceAll("&sign=[\\S]*$", "");
//        String[] arrayStr = tempStr.split("&");
//        for (int i = 0; i < arrayStr.length && null != arrayStr[i]; i++) {
//            String s = arrayStr[i];
//            String[] mapStr = s.split("=");
//            result.put(mapStr[0], mapStr[1]);
//        }
//
//        return result;
//    }

    private static String generateBusinessString(Map<String, String> dataMaps) {
        String strResult = "";

        try {
            // step1. 将键值对按key排序并组装
            StringBuilder strBusiness = new StringBuilder();

            for (String key : dataMaps.keySet()) {
                if (strBusiness.toString() != "") {
                    strBusiness.append("&");
                }
                strBusiness.append(key);
                strBusiness.append("=");
                strBusiness.append(java.net.URLEncoder.encode(dataMaps.get(key), "UTF-8"));
            }

//            // get private key
//            PrivateKey clientPKey = getPrivateKey();
//
//            // step2. 生成sign. MD5withRSA
//            java.security.Signature signet = java.security.Signature
//                    .getInstance("MD5withRSA");
//            signet.initSign(clientPKey);
//            signet.update(strBusiness.getBytes("UTF-8"));
//            byte[] digest = signet.sign();
//            String sign = MrBase64Utils.encode(digest);
//
//            // step3. 连接sign的业务参数串
//            strBusiness += "&sign=" + java.net.URLEncoder.encode(sign, "UTF-8");
            strResult = strBusiness.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return strResult;
    }

//    private static PrivateKey getPrivateKey() {
//        PrivateKey privateKey = null;
//
//        try {
//            byte[] keys = MrBase64Utils.decode(CLIENT_KEY_PRIVATE);
//            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(keys);
//            KeyFactory factory = KeyFactory.getInstance("RSA");
//            privateKey = factory.generatePrivate(priPKCS8);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return privateKey;
//    }

    private static PublicKey getPublicKey() {
        PublicKey publicKey = null;

        try {
            byte[] keys = KMBase64Utils.decode(SERVER_KEY_PUBLIC);
            KeySpec keySpec = new X509EncodedKeySpec(keys);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            publicKey = factory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return publicKey;
    }

    private static String generateTempKey() {
        String strResult = "9841a727a5287609";
//        String strResult = "6461z728a5150852";
        return strResult;

    }

//    private static String generateReqData(String strBusiness, String sKey) {
//        String strResult = "";
//        try {
//            byte[] raw = sKey.getBytes("utf-8");
//            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
//            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// "算法/模式/补码方式"
//            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
//            byte[] encrypted = cipher.doFinal(strBusiness.getBytes("utf-8"));
//
//            strResult = MrBase64Utils.encode(encrypted);
//
//            strResult = java.net.URLEncoder.encode(strResult, "UTF-8");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return strResult;
//    }
    private static String encryptTempKey(String strKey) {
        String strResult = "";

        try {
            PublicKey publicKey = getPublicKey();
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encrypted = strKey.getBytes();
            int inputLen = encrypted.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(encrypted, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(encrypted, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();
            strResult = new String(Base64.encode(encryptedData, Base64.NO_WRAP));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return strResult;
    }

//    /**
//     * 获取返回码
//     *
//     * @param json
//     * @return
//     */
//    public static String getResultCode(String json) {
//        String resultCode = getResultParams(json).get("result");
//        return resultCode;
//    }

//    /**
//     * 获取解密后的所有参数
//     *
//     * @param json
//     * @return
//     */
//    public static Map<String, String> getResultParams(String json) {
//        String responseStr = json.substring("respdata=".length());
//        String decryptStr = decryptResponse(responseStr);
//        TreeMap<String, String> responseParams = new TreeMap<>();
//        for (String paramStr : decryptStr.split("&")) {
//            String[] param = paramStr.split("=");
//            responseParams.put(param[0], param[1]);
//        }
//        return responseParams;
//    }

    /**
//     * 解密
//     *
//     * @param strResponse
//     * @return
//     */
//    public static String decryptResponse(String strResponse) {
//        String strResult = "";
//
//        try {
//            // urldecode
//            String strUrlDecode = java.net.URLDecoder.decode(strResponse,
//                    "UTF-8");
//
//            // base64 decode
//            byte[] decodeBytes = MrBase64Utils.decode(strUrlDecode);
//
//            // AES解密
//            String tempKey = generateTempKey();
//            String decAES = decryptResponseWithAES(decodeBytes, tempKey);
//
//            strResult = decAES;
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.i("lzq","解密出错 ： "+e.toString());
//        }
//
//        return strResult;
//    }
//
//    private static String decryptResponseWithAES(byte[] strBusiness, String sKey) {
//        String strResult = "";
//
//        try {
//            byte[] raw = sKey.getBytes("utf-8");
//            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
//            // "算法/模式/补码方式"
//            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
//            byte[] decrypted = cipher.doFinal(strBusiness);
//
//            strResult = new String(decrypted);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.i("lzq","解密出错 ： "+e.toString());
//
//        }
//
//        return strResult;
//    }

}
