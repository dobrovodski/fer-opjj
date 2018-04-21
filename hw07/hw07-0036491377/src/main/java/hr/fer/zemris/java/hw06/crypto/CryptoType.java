package hr.fer.zemris.java.hw06.crypto;

/**
 * Enum for representing the different {@link}.
 *
 * @author matej
 */
public enum CryptoType {
    CHECKSHA("checksha"),
    ENCRYPT("encrypt"),
    DECRYPT("decrypt");

    private String name;

    CryptoType(String name) {
        this.name = name;
    }

    public static CryptoType getType(String name) {
        for (CryptoType t : CryptoType.values()) {
            if (t.name.equals(name)) {
                return t;
            }
        }

        throw new IllegalArgumentException("Could not interpret as crypto action: " + name);
    }
}

