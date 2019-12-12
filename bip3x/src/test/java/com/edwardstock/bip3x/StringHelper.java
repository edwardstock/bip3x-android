/*
 * Copyright (C) by MinterTeam. 2019
 * @link <a href="https://github.com/MinterTeam">Org Github</a>
 * @link <a href="https://github.com/edwardstock">Maintainer Github</a>
 *
 * The MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.edwardstock.bip3x;

/**
 * minter-android-core. 2018
 * @author Eduard Maximovich <edward.vstock@gmail.com>
 */
public class StringHelper {
    public static final String HEX_NUM_PATTERN = "^(0x)?([a-fA-F0-9]{2,})$";
    private final static char[] hexArray = "0123456789ABCDEF".toLowerCase().toCharArray();

    public static String bytesToHexString(final byte[] data) {
        return bytesToHexString(data, false);
    }

    public static String bytesToHexString(final byte[] data, boolean uppercase) {
        if (data == null || data.length == 0) {
            return "";
        }

        int size = data.length;
        char[] hexChars = new char[data.length * 2];
        for (int j = 0; j < size; j++) {
            int v = data[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }

        if (uppercase) {
            return new String(hexChars).toUpperCase();
        }

        return new String(hexChars);
    }

    public static byte[] hexStringToBytes(final String s) {
        if (s == null || s.length() == 0) {
            return new byte[0];
        }

        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16)
            );
        }
        return data;
    }

    public static String bytesToString(String hexString) {
        return bytesToString(hexStringToBytes(hexString));
    }

    public static String bytesToString(byte[] data) {
        return new String(data);
    }

    public static String bytesToString(byte[] data, int readLength) {
        if (data.length < readLength) {
            throw new ArrayIndexOutOfBoundsException(
                    "Read length less than array size: " + readLength + " of " + data.length);
        }
        final char[] out = new char[readLength];
        for (int i = 0; i < readLength; i++) {
            out[i] = (char) data[i];
        }

        return new String(out);
    }

    public static boolean testHex(String s) {
        return s.matches(HEX_NUM_PATTERN);
    }
}
