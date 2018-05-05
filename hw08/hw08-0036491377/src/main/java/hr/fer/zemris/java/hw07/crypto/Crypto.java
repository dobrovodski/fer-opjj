package hr.fer.zemris.java.hw07.crypto;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

/**
 * This class provides the ability to check a file's sha-256 digest and to encrypt/decrypt a file using the AES
 * crypto-algorithm. The first command line argument determines what action to perform (checksha, encrypt, decrypt)
 * while the second and third argument determine the input / output file.
 *
 * @author matej
 */
public class Crypto {
    /**
     * Entry point.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("This program requires two parameters.");
            return;
        }

        String type = args[0].toLowerCase();
        Path path = Paths.get(args[1]);
        Scanner sc = new Scanner(System.in);

        if (type.equals("checksha")) {
            System.out.format("Please provide expected sha-256 digest for %s: %n> ", path);
            String provided = sc.nextLine();
            checkSHA(path, provided);

            sc.close();
            return;
        }

        if (type.equals("encrypt") || type.equals("decrypt")) {
            if (args.length < 3) {
                System.out.println("Encryption and decryption require an input and an output file.");
                sc.close();
                return;
            }

            boolean encrypt = false;
            if (type.equals("encrypt")) {
                encrypt = true;
            }

            Path out = Paths.get(args[2]);
            System.out.format("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits): %n> ");
            String password = sc.nextLine();
            System.out.format("Please provide initialization vector as hex-encoded text (32 hex-digits): %n> ");
            String initVector = sc.nextLine();

            SecretKeySpec keySpec;
            AlgorithmParameterSpec paramSpec;
            try {
                keySpec = new SecretKeySpec(Util.hextobyte(password), "AES");
                paramSpec = new IvParameterSpec(Util.hextobyte(initVector));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                sc.close();
                return;
            }

            Cipher cipher;
            try {
                cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
                System.out.println("Could not instantiate cipher.");
                sc.close();
                return;
            }

            try {
                cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
            } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
                System.out.println("Could not initialize cipher.");
                sc.close();
                return;
            }

            encipherDecipher(path, out, cipher);
            type = type.substring(0, 1).toUpperCase() + type.substring(1);
            System.out.format("%s completed. Generated file %s based on file %s", type, out, path);

            sc.close();
        } else {
            System.out.println("This function only supports the operations checksha, encrypt and decrypt");
            sc.close();
        }
    }

    /**
     * Returns the SHA-256 digest from provided InputStream.
     *
     * @param is input stream
     *
     * @return SHA-256 digest of stream
     *
     * @throws IOException if an error occurs while reading from the input stream
     */
    private static String getDigest(InputStream is) throws IOException {
        MessageDigest md;
        byte[] bytes = new byte[4096];

        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Could not instantiate Message Digest.");
            return "";
        }

        int count;
        while ((count = is.read(bytes)) != -1) {
            md.update(bytes, 0, count);
        }
        byte[] hash = md.digest();

        return Util.bytetohex(hash);
    }

    /**
     * Checks the provided SHA-256 digest with the calculated one. Returns {@code true} if they are the same, {@code
     * false} otherwise.
     *
     * @param path path to file to check
     * @param provided provided digest
     */
    private static void checkSHA(Path path, String provided) {
        String digest;

        try {
            InputStream is = new BufferedInputStream(Files.newInputStream(path, StandardOpenOption.READ));
            digest = getDigest(is);
            is.close();
        } catch (IOException ignored) {
            System.out.println("Could not open file: " + path.toString());
            return;
        }

        System.out.format("Digesting completed. Digest of %s ", path.toString());
        boolean same = digest.equals(provided);
        if (same) {
            System.out.println("matches expected digest.");
        } else {
            System.out.format("does not match the expected digest. Digest was: %s%n", digest);
        }
    }

    /**
     * Uses the provided cipher to either encrypt or decrypt the input file and stores the result to the output path.
     *
     * @param in input path of file to encrypt or decrypt
     * @param out output path of file after the original has been encrypted or decrypted
     * @param cipher cipher to use while encrypting or decrypting
     */
    private static void encipherDecipher(Path in, Path out, Cipher cipher) {
        byte[] bytes = new byte[4096];
        byte[] output;

        try (InputStream is = new BufferedInputStream(Files.newInputStream(in, StandardOpenOption.READ));
             OutputStream os = new BufferedOutputStream(Files.newOutputStream(out, StandardOpenOption.CREATE))
        ) {
            int count;
            while ((count = is.read(bytes)) != -1) {
                output = cipher.update(bytes, 0, count);
                os.write(output);
            }
        } catch (IOException ignored) {
            System.err.println("Error while writing to output file.");
        }
    }
}
