package com.xiao.project.utils;

import org.apache.commons.codec.digest.DigestUtils;


import javax.crypto.Cipher;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

public class RSAPasswordEncoder {
	
    public static final String CHARSET = "UTF-8";
    public static final String RSA_ALGORITHM = "RSA";

    
    public static RSAPublicKey getPublicKey(String modulus, String publicExponent) {
	
		BigInteger bigIntModulus = new BigInteger(modulus,16);
		
		BigInteger bigIntPrivateExponent = new BigInteger(publicExponent,16);
		
		RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);
		
		KeyFactory keyFactory;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publicKey = keyFactory.generatePublic(keySpec);
			return (RSAPublicKey)publicKey;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}
    
    public static String encryptByPublicKey(String data, RSAPublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
        cipher.init(1, publicKey);
        int key_len = publicKey.getModulus().bitLength() / 8;
        String[] datas = splitString(data, key_len - 11);
        String mi = "";
        String[] var9 = datas;
        int var8 = datas.length;

        for(int var7 = 0; var7 < var8; ++var7) {
            String s = var9[var7];
            mi = mi + bcd2Str(cipher.doFinal(s.getBytes()));
        }

        return mi;
    }




    public static String bcd2Str(byte[] bytes) {
        char[] temp = new char[bytes.length * 2];

        for(int i = 0; i < bytes.length; ++i) {
            char val = (char)((bytes[i] & 240) >> 4 & 15);
            temp[i * 2] = (char)(val > '\t' ? val + 65 - 10 : val + 48);
            val = (char)(bytes[i] & 15);
            temp[i * 2 + 1] = (char)(val > '\t' ? val + 65 - 10 : val + 48);
        }

        return new String(temp);
    }

    public static String[] splitString(String string, int len) {
        int x = string.length() / len;
        int y = string.length() % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }

        String[] strings = new String[x + z];
        String str = "";

        for(int i = 0; i < x + z; ++i) {
            if (i == x + z - 1 && y != 0) {
                str = string.substring(i * len, i * len + y);
            } else {
                str = string.substring(i * len, i * len + len);
            }

            strings[i] = str;
        }

        return strings;
    }
    
    public static void main(String[] args) {
		try {
			String keyStr = "90c09717a00c11b755227fa1f2b8c0f9d0fab10aff0a4e77b5c8e4ccfdbe5284b89fac71e58a111d5fc1afb052d8a41cbef71c58d86b4059cc9dde68d3b2d6837dfcc798c179772fcb10b6410cab8da9101d953568980d4301b8a23135cfc395f5fa75c2223186d512be5b0370c2e06f951a25842d8629851610581f9369b903";
			String keyExponent = "10001";
			RSAPublicKey publicKey = RSAPasswordEncoder.getPublicKey(keyStr,keyExponent);
			
			String passWord = "000000";
			String md5 = DigestUtils.md5Hex(passWord);
			String hashAndPsw = md5 + passWord;
			
			System.out.println("密码：" + passWord);
			System.out.println("密文：" + RSAPasswordEncoder.encryptByPublicKey(hashAndPsw, publicKey).toLowerCase());
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
	}
    
}
