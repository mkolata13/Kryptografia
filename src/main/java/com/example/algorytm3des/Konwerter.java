package com.example.algorytm3des;

import java.math.BigInteger;

public class Konwerter {
    public byte[] hexStringToByteArray(String s) {
        byte[] bytes = new BigInteger(s, 16).toByteArray();
        if (bytes[0] == 0) {
            byte[] trimmedBytes = new byte[bytes.length - 1];
            System.arraycopy(bytes, 1, trimmedBytes, 0, trimmedBytes.length);
            return trimmedBytes;
        }
        return bytes;
    }

    public String bytesToHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
