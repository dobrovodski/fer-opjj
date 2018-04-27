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
     */
    public static byte[] hextobyte(String keyText) {
        int length = keyText.length();
        if (length % 2 != 0) {
            throw new IllegalArgumentException("Hex cannot have an odd length.");
        }

        byte[] byteArray = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            char c1 = keyText.charAt(i);
            char c2 = keyText.charAt(i + 1);
            int b1 = Character.digit(c1, 16) * 16;
            int b2 = Character.digit(c2, 16);

            if (b1 == -1 || b2 == -1) {
                throw new IllegalArgumentException("Could not convert to byte: " + c1 + c2);
            }

            byteArray[i / 2] = (byte) (b1 + b2);
        }

        return byteArray;
    }

    /**
     * Converts given array of bytes into a hex string.
     *
     * @param bytearray array of bytes to convert
     *
     * @return converted hex string
     */
    public static String bytetohex(byte[] bytearray) {
        StringBuilder sb = new StringBuilder();
        for (byte byteNum : bytearray) {
            //TODO: write own tohexstring?
            String converted = Integer.toHexString(byteNum);
            if (converted.length() < 2) {
                converted = '0' + converted;
            }

            sb.append(converted);
        }

        return sb.toString();
    }
}
