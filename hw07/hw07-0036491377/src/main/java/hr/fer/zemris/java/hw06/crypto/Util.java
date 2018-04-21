package hr.fer.zemris.java.hw06.crypto;

public class Util {
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
            int b2 =  Character.digit(c2, 16);

            if (b1 == -1 || b2 == -1) {
                throw new IllegalArgumentException("Could not convert to byte: " + c1 + c2);
            }

            byteArray[i / 2] = (byte) (b1 + b2);
        }

        return byteArray;
    }

    public static String bytetohex(byte[] bytearray) {
        StringBuilder sb = new StringBuilder();
        for (byte byteNum : bytearray) {
            String converted = Integer.toHexString(byteNum & 0xFF);
            if (converted.length() < 2) {
                converted = '0' + converted;
            }

            sb.append(converted);
        }

        return sb.toString();
    }
}
