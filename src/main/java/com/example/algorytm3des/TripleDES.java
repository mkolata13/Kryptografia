package com.example.algorytm3des;

import java.util.Arrays;

public class TripleDES extends DES {
    public byte[] TripleDES_Encrypt(byte[] data, byte[][] keys) {
        byte[] paddedData = addPadding(data);
        byte[] encryptedData = new byte[paddedData.length];

        subKeys1 = generateSubKeys(keys[0]);
        subKeys2 = generateSubKeys(keys[1]);
        subKeys3 = generateSubKeys(keys[2]);

        for (int i = 0; i < data.length; i += 8) {
            byte[] block = Arrays.copyOfRange(paddedData, i, i + 8);
            byte[] encryptedBlock = encryptBlock(block);
            System.arraycopy(encryptedBlock, 0, encryptedData, i, 8);
        }

        return encryptedData;
    }

    public byte[] TripleDES_Decrypt(byte[] data, byte[][] keys) {
        byte[] decryptedData = new byte[data.length];

        subKeys1 = generateSubKeys(keys[0]);
        subKeys2 = generateSubKeys(keys[1]);
        subKeys3 = generateSubKeys(keys[2]);

        for (int i = 0; i < data.length; i += 8) {
            byte[] block = Arrays.copyOfRange(data, i, i + 8);
            byte[] decryptedBlock = decryptBlock(block);
            System.arraycopy(decryptedBlock, 0, decryptedData, i, 8);
        }

        return removePadding(decryptedData);
    }
    byte[] encryptBlock(byte[] block) {
        byte[] result = Arrays.copyOf(block, block.length);
        result = encrypt64Bloc(result, subKeys1, true);
        result = encrypt64Bloc(result, subKeys2, false);
        result = encrypt64Bloc(result, subKeys3, true);
        return result;
    }

    byte[] decryptBlock(byte[] block) {
        byte[] result = Arrays.copyOf(block, block.length);
        result = encrypt64Bloc(result, subKeys3, false);
        result = encrypt64Bloc(result, subKeys2, true);
        result = encrypt64Bloc(result, subKeys1, false);
        return result;
    }

    byte[] addPadding(byte[] data) {
        int paddingLength = 8 - (data.length % 8);
        byte[] paddedData = Arrays.copyOf(data, data.length + paddingLength);
        Arrays.fill(paddedData, data.length, paddedData.length, (byte) paddingLength);
        return paddedData;
    }

    byte[] removePadding(byte[] data) {
        int paddingLength = data[data.length - 1];
        return Arrays.copyOf(data, data.length - paddingLength);
    }
}
