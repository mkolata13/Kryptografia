package com.example.algorytm3des;

public class DES {

    byte[][] subKeys1;
    byte[][] subKeys2;
    byte[][] subKeys3;

    private int[][][] sBoxes = {
            { 		{ 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7 },
                    { 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8 },
                    { 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0 },
                    { 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13 }
            },
            { 		{ 15, 1, 8, 14, 6, 11, 3, 2, 9, 7, 2, 13, 12, 0, 5, 10 },
                    { 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5 },
                    { 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15 },
                    { 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9 }
            },
            { 		{ 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8 },
                    { 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1 },
                    { 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7 },
                    { 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12 }
            },
            { 		{ 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15 },
                    { 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9 },
                    { 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4 },
                    { 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14 }
            },
            { 		{ 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9 },
                    { 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6 },
                    { 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14 },
                    { 11, 8, 12, 7, 1, 14, 2, 12, 6, 15, 0, 9, 10, 4, 5, 3 }
            },
            { 		{ 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11 },
                    { 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8 },
                    { 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6 },
                    { 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13 }

            },
            { 		{ 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1 },
                    { 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6 },
                    { 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2 },
                    { 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12 }

            },
            { 		{ 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7 },
                    { 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2 },
                    { 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8 },
                    { 2, 1, 14, 7, 4, 10, 18, 13, 15, 12, 9, 0, 3, 5, 6, 11 }

            }
    };

    // permutacja P w funkcji feistela
    private int[] P = { 16,  7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26,  5, 18, 31, 10,
                         2,  8, 24, 14, 32, 27,  3,  9, 19, 13, 30,  6, 22, 11,  4, 25
    };

    // permutacja klucza poczatkowa 64bit -> 56bit
    private int[] PC1 = { 57, 49, 41, 33, 25, 17, 9,
                            1, 58, 50, 42, 34, 26, 18,
                            10,  2, 59, 51, 43, 35, 27,
                            19, 11,  3, 60, 52, 44, 36,
                            63, 55, 47, 39, 31, 23, 15,
                            7, 62, 54, 46, 38, 30, 22,
                            14,  6, 61, 53, 45, 37, 29,
                            21, 13,  5, 28, 20, 12, 4
    };

    // permutacja klucza dla i rundy 56bit -> 48bit
    private int[] PC2 = { 14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10,
                        23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2,
                        41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48,
                        44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32
    };

    // liczba przesuniec klucza dla i rundy
    private int[] keyShift = { 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1 };

    // permutacja rozszerzenia dla funkcji feinstela
    private int[] expansionTable = { 32, 1, 2, 3, 4, 5,
                                    4, 5, 6, 7, 8, 9,
                                    8, 9, 10, 11, 12, 13,
                                    12, 13, 14, 15, 16, 17,
                                    16, 17, 18, 19, 20, 21,
                                    20, 21, 22, 23, 24, 25,
                                    24, 25, 26, 27, 28, 29,
                                    28, 29, 30, 31, 32, 1
    };

    int countBytesFromBits(int numOfBits) {
        return ((numOfBits - 1) / 8 + 1);
    }

    byte[] xor(byte[] x, byte[] y) {
        byte[] result = new byte[x.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) (x[i] ^ y[i]);
        }

        return result;
    }

    void setBit(byte[] data, int index, int value) {
        int posByte = index / 8;
        int offset = index % 8;

        data[posByte] = (byte) ((data[posByte] & ~(1 << (7 - offset))) | ((value & 0x01) << (7 - offset)));
    }

    int getBit(byte[] data, int index) {
        if (data == null || index < 0 || index >= data.length * 8) {
            return 0;
        }

        int byteIndex = index / 8;
        int bitOffset = index % 8;
        byte bit = data[byteIndex];

        return (bit >> 7 - bitOffset) & 1;
    }

    byte[] getBits(byte[] data, int startIndex, int numOfBits) {
        byte[] result = new byte[countBytesFromBits(numOfBits)];

        for (int i = 0; i < numOfBits; i++) {
            int value = getBit(data, startIndex + i);
            setBit(result, i, value);
        }

        return result;
    }

    byte[] rotateLeft(byte[] data, int numOfBits, int shifts) {
        byte[] result = new byte[countBytesFromBits(numOfBits)];

        for (int i = 0; i < numOfBits; i++) {
            int value = getBit(data, (i + shifts) % numOfBits);
            setBit(result, i, value);
        }

        return result;
    }

    byte[] permutate(byte[] data, int[] table) {
        byte[] result = new byte[countBytesFromBits(table.length)];

        for (int i = 0; i < table.length; i++) {
            int value = getBit(data, table[i] - 1);
            setBit(result, i, value);
        }

        return result;
    }

    byte[][] generateSubKeys(byte[] key) {
        byte[][] subKeys = new byte[16][];
        byte[] _56bitKey = permutate(key, PC1);

        byte[] leftKey = getBits(_56bitKey, 0,  28);
        byte[] rightKey = getBits(_56bitKey, 28, 56);

        for (int i = 0; i < 16; i++) {
            leftKey = rotateLeft(leftKey, 28, keyShift[i]);
            rightKey = rotateLeft(rightKey, 28, keyShift[i]);

            byte[] _K = mergeTwoBitTables(leftKey, 28, rightKey, 28);
            subKeys[i] = permutate(_K, PC2);
        }

        return subKeys;
    }

    byte[] mergeTwoBitTables(byte[] a, int aLen, byte[] b, int bLen) {
        int numOfBytes = (aLen + bLen - 1) / 8 + 1;
        byte[] out = new byte[numOfBytes];
        int j = 0;
        for (int i = 0; i < aLen; i++) {
            int val = getBit(a, i);
            setBit(out, j, val);
            j++;
        }
        for (int i = 0; i < bLen; i++) {
            int val = getBit(b, i);
            setBit(out, j, val);
            j++;
        }
        return out;
    }

    byte[] feistelFunction(byte[] halfData, byte[] key) {
        byte[] result;
        result = permutate(halfData, expansionTable);
        result = xor(result, key);
        result = sBoxFunction(result);
        result = permutate(result, P);

        return result;
    }

    byte[] sBoxFunction(byte[] data) {
        // Podział danych na grupy po 6 bitów
        data = divide48BitsTo6BitGroups(data);

        // Inicjalizacja tablicy wynikowej
        byte[] result = new byte[data.length / 2];

        // Przetwarzanie każdej grupy 6 bitów
        int halfByte = 0;
        for (int b = 0; b < data.length; b++) {
            byte valByte = data[b];

            // Obliczanie indeksu w tablicy sBox
            int row = 2 * (valByte >> 7 & 0x0001) + (valByte >> 2 & 0x0001);
            int col = valByte >> 3 & 0x000F;

            // Pobieranie wartości z odpowiedniego sBox
            int val = sBoxes[b][row][col];

            // Składanie wyniku z dwóch części 4-bitowych
            if (b % 2 == 0) {
                halfByte = val;
            }
            else {
                result[b / 2] = (byte) (16 * halfByte + val);
            }
        }

        return result;
    }

    byte[] divide48BitsTo6BitGroups(byte[] data) {
        int numOfBytes = (8 * data.length - 1) / 6 + 1;
        byte[] result = new byte[numOfBytes];

        for (int i = 0; i < numOfBytes; i++)
        {
            for (int j = 0; j < 6; j++)
            {
                int value = getBit(data, 6 * i + j);
                setBit(result, 8 * i + j, value);
            }
        }
        return result;
    }

    public byte[] encrypt64BitBlock(byte[] block, byte[][] subkeys, boolean encrypt) {
        byte[] result = block;
        byte[] R;
        byte[] L;

        L = getBits(result, 0, 32);
        R = getBits(result, 32, 32);

        for (int i = 0; i < 16; i++) {
            byte[] _R = R;
            if(encrypt)
                R = feistelFunction(R,subkeys[i]);
            else
                R = feistelFunction(R, subkeys[15-i]);
            R = xor(L, R);
            L = _R;
        }

        result = mergeTwoBitTables(R, 32, L, 32);

        return result;
    }
}
