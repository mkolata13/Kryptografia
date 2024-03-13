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

    byte[] stringToBytesUTF8(String string)
    {
        byte[] bytes = new byte[string.length() << 1];
        int position = 0;

        for(char character : string.toCharArray())
        {
            bytes[position++] = (byte) ((character & 0xFF00) >> 8);
            bytes[position++] = (byte) (character & 0x00FF);
        }

        return bytes;
    }

    String bytesToStringUTF8(byte[] bytes)
    {
        char[] buffer = new char[bytes.length >> 1];

        for(int i = 0; i < buffer.length; i++)
        {
            int bpos = i << 1;
            char c = (char)(((bytes[bpos]&0x00FF)<<8) + (bytes[bpos+1]&0x00FF));
            buffer[i] = c;
        }
        return new String(buffer);
    }
}
