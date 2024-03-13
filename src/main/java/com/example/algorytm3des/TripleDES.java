package com.example.algorytm3des;

import java.util.Arrays;

public class TripleDES extends DES {
    public byte[] TripleDES_Encrypt(byte[] data, byte[][] keys)
    {
        int lenght;
        byte[] padding;
        int i;

        lenght = 8 - data.length % 8;
        padding = new byte[lenght];
        padding[0] = (byte) 0x80;

        for (i = 1; i < lenght; i++)
            padding[i] = 0;


        byte[] tmp = new byte[data.length + lenght];
        byte[] bloc = new byte[8];


        subKeys1 = generateSubKeys(keys[0]);
        subKeys2 = generateSubKeys(keys[1]);
        subKeys3 = generateSubKeys(keys[2]);

        int count = 0;

        for (i = 0; i < data.length + lenght; i++) {
            if (i > 0 && i % 8 == 0) {
                bloc = encrypt64Bloc(bloc, subKeys1, true);
                bloc = encrypt64Bloc(bloc, subKeys2, false);
                bloc = encrypt64Bloc(bloc, subKeys3, true);
                System.arraycopy(bloc, 0, tmp, i - 8, bloc.length);
            }
            if (i < data.length)
                bloc[i % 8] = data[i];
            else {
                bloc[i % 8] = padding[count % 8];
                count++;
            }
        }
        if(bloc.length == 8){
            bloc = encrypt64Bloc(bloc, subKeys1, true);
            bloc = encrypt64Bloc(bloc, subKeys2, false);
            bloc = encrypt64Bloc(bloc, subKeys3, true);
            System.arraycopy(bloc, 0, tmp, i - 8, bloc.length);
        }
        return tmp;
    }

    public byte[] TripleDES_Decrypt(byte[] data, byte[][] keys)
    {
        int i;
        byte[] tmp = new byte[data.length];
        byte[] bloc = new byte[8];

        subKeys1 = generateSubKeys(keys[0]);
        subKeys2 = generateSubKeys(keys[1]);
        subKeys3 = generateSubKeys(keys[2]);

        for (i = 0; i < data.length; i++) {
            if (i > 0 && i % 8 == 0) {
                bloc = encrypt64Bloc(bloc, subKeys3, false);
                bloc = encrypt64Bloc(bloc, subKeys2, true);
                bloc = encrypt64Bloc(bloc, subKeys1, false);
                System.arraycopy(bloc, 0, tmp, i - 8, bloc.length);
            }
            bloc[i % 8] = data[i];
        }
        bloc = encrypt64Bloc(bloc, subKeys3, false);
        bloc = encrypt64Bloc(bloc, subKeys2, true);
        bloc = encrypt64Bloc(bloc, subKeys1, false);
        System.arraycopy(bloc, 0, tmp, i - 8, bloc.length);


        tmp = deletePadding(tmp);

        return tmp;
    }

    public static byte[] addPadding(byte[] data) {
        int paddingLength = 8 - (data.length % 8);
        byte[] paddedData = Arrays.copyOf(data, data.length + paddingLength);
        Arrays.fill(paddedData, data.length, paddedData.length, (byte) paddingLength);
        return paddedData;
    }

    // Usuwanie paddingu z tekstu
    public static byte[] removePadding(byte[] data) {
        int paddingLength = data[data.length - 1];
        return Arrays.copyOf(data, data.length - paddingLength);
    }

    byte[] deletePadding(byte[] input) {
        int count = 0;

        int i = input.length - 1;
        while (input[i] == 0) {
            count++;
            i--;
        }

        byte[] tmp = new byte[input.length - count - 1];
        System.arraycopy(input, 0, tmp, 0, tmp.length);
        return tmp;
    }
}
