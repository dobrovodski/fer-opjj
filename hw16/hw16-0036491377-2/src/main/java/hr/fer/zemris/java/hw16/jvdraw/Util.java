package hr.fer.zemris.java.hw16.jvdraw;

public class Util {
    public static int[] checkParsable(String[] toParse) {
        int[] parsed = new int[toParse.length];
        for (int i = 0; i < toParse.length; i++) {
            try {
                parsed[i] = Integer.parseInt(toParse[i]);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Not an integer: " + toParse[i]);
            }
        }
        return parsed;
    }
}
