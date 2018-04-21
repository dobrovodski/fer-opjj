package hr.fer.zemris.java.hw06.crypto;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import static hr.fer.zemris.java.hw06.crypto.Util.bytetohex;

public class Crypto {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("NO :<");
        }

        CryptoType type = CryptoType.getType(args[0]);
        Path path = Paths.get(args[1]);
        Scanner sc = new Scanner(System.in);

        switch (type) {
            case CHECKSHA:
                System.out.print("Please provide expected sha-256 digest for hw06part2.pdf: \n> ");
                String provided = sc.nextLine();
                checkSHA(path, provided);
            case ENCRYPT:
                break;
            case DECRYPT:
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

        return bytetohex(hash);
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
}
