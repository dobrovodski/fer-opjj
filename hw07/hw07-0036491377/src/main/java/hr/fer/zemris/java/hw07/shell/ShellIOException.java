package hr.fer.zemris.java.hw07.shell;

public class ShellIOException extends RuntimeException {
    public ShellIOException(String message) {
        super(message);
    }

    public ShellIOException() {
        super();
    }
}
