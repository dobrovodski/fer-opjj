package hr.fer.zemris.java.hw07.crypto;

/**
 * Utility class that provides methods for converting hex strings to byte arrays and the other way around.
 *
 * @author matej
 */
public class Util {
    /**
     * Converts given string of hex values to an array of bytes.
     *
     * @param keyText string to convert
     *
     * @return converted byte array
     *
     * @throws IllegalArgumentException if the hex string isn't convertible to bytes
     */
    public static byte[] hextobyte(String keyText) {
        int length = keyText.length();
        keyText = keyText.toLowerCase();

        if (length % 2 != 0) {
            throw new IllegalArgumentException("Hex string cannot have an odd length.");
        }

        byte[] byteArray = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            char c1 = keyText.charAt(i);
            char c2 = keyText.charAt(i + 1);

            int b1 = hexDigitToNumber(c1) * 16;
            int b2 = hexDigitToNumber(c2);

            if (b1 == -1 || b2 == -1) {
                throw new IllegalArgumentException("Could not convert to byte: " + c1 + c2);
            }

            byteArray[i / 2] = (byte) (b1 + b2);
        }

        return byteArray;
    }

    /**
     * Converts given hexadecimal digit (0-9 and A-F) to the appropriate numerical value. If the digit is invalid,
     * returns -1.
     *
     * @param c digit to convert
     *
     * @return converted value or -1 if it is an invalid character
     */
    private static int hexDigitToNumber(char c) {
        if (c >= 48 && c <= 57) {
            return c - 48;
        }

        if (c >= 97 && c <= 102) {
            return c - 87;
        }

        if (c >= 65 && c <= 70) {
            return c - 55;
        }

        return -1;
    }

    /**
     * Converts given array of bytes into a hex string.
     *
     * @param byteArray array of bytes to convert
     *
     * @return converted hex string
     */
    public static String bytetohex(byte[] byteArray) {
        StringBuilder sb = new StringBuilder();
        char[] charMap = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        for (byte byteNum : byteArray) {
            char c1 = (char) ((byteNum >> 4) & 0xF);
            char c2 = (char) ((byteNum) & 0xF);

            sb.append(charMap[c1]).append(charMap[c2]);
        }

        return sb.toString();
    }
}
