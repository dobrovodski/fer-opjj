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

public class Crypto {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("NO :<");
        }

        CryptoType type = CryptoType.getType(args[0]);
        Path path = Paths.get(args[1]);
        Scanner sc = new Scanner(System.in);

        switch (type) {
            case CHECKSHA: {
                System.out.format("Please provide expected sha-256 digest for %s: %n> ", path);
                String provided = sc.nextLine();
                checkSHA(path, provided);
            }
            case ENCRYPT:
                if (args.length < 3) {
                    System.out.println("Encryption requires an input and an output file.");
                    return;
                }

            case DECRYPT:
                if (args.length < 3) {
                    System.out.println("Decryption requires an input and an output file.");
                    return;
                }

                Path out = Paths.get(args[2]);
                System.out.format("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits): %n> ");
                String password = sc.nextLine();
                System.out.format("Please provide initialization vector as hex-encoded text (32 hex-digits): %n> ");
                String initVector = sc.nextLine();

                SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(password), "AES");
                AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(initVector));
                Cipher cipher;
                try {
                    cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
                    System.out.println("Could not instantiate cipher.");
                    return;
                }

                try {
                    cipher.init(false ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
                } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
                    System.out.println("Could not initialize cipher.");
                    return;
                }

                encryptDecrypt(path, out, cipher);
                break;
        }

        sc.close();
    }

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

    private static void checkSHA(Path path, String provided) {
        String digest;

        try {
            InputStream is = new BufferedInputStream(Files.newInputStream(path, StandardOpenOption.READ));
            digest = getDigest(is);
            is.close();
        } catch (IOException ignored) {
            //TODO
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

    private static void encryptDecrypt(Path in, Path out, Cipher cipher) {
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
            //TODO
            return;
        }
    }
}
