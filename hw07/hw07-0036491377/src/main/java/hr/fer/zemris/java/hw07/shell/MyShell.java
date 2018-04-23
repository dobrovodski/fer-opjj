package hr.fer.zemris.java.hw07.shell;

public class MyShell {
    public static void main(String[] args) {
        Environment env = new EnvironmentImpl();
        env.writeln("Welcome to MyShell v 1.0");

        while (true) {
            String in = env.readLine();
            if (in.equals("exit")) {
                break;
            }

            env.writeln(in);
        }

    }
}
