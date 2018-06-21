package hr.fer.zemris.java.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The Crypto class is used to calculate the SHA-1 digest of the provided string. Useful for calculating hashes for
 * passwords to be stored in the database.
 *
 * @author matej
 */
public class Crypto {
    /**
     * Returns the 40 character long SHA-1 digest of given string.
     *
     * @param in string to hash
     *
     * @return SHA-1 digest of given string
     */
    public static String getDigest(String in) {
        MessageDigest md;

        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Could not instantiate Message Digest.");
            return "";
        }

        byte[] hash = md.digest(in.getBytes());

        return bytetohex(hash);
    }

    /**
     * Converts given array of bytes into a hex string.
     *
     * @param byteArray array of bytes to convert
     *
     * @return converted hex string
     */
    private static String bytetohex(byte[] byteArray) {
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
