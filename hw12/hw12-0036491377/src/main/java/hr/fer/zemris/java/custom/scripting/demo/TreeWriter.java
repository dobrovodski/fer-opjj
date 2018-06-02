package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Demo program for {@link INodeVisitor} which creates a {@link WriterVisitor} that simply writes the provided script to
 * the standard output.
 *
 * @author matej
 */
public class TreeWriter {
    /**
     * Entry point.
     *
     * @param args requires one argument - the path to the script file
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Program accepts one argument");
        }

        String docBody;
        try {
            docBody = new String(Files.readAllBytes(Paths.get(args[0])));
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not read file");
        }

        SmartScriptParser p = new SmartScriptParser(docBody);
        WriterVisitor visitor = new WriterVisitor();
        p.getDocumentNode().accept(visitor);
    }

    /**
     * This visitor goes through all the nodes of the script file and writes them to the standard output.
     *
     * @author matej
     */
    private static class WriterVisitor implements INodeVisitor {

        @Override
        public void visitTextNode(TextNode node) {
            visitChildNodes(node);
        }

        @Override
        public void visitForLoopNode(ForLoopNode node) {
            visitChildNodes(node);
            System.out.print("{$END$}");
        }

        @Override
        public void visitEchoNode(EchoNode node) {
            visitChildNodes(node);
        }

        @Override
        public void visitDocumentNode(DocumentNode node) {
            visitChildNodes(node);
        }

        private void visitChildNodes(Node parent) {
            System.out.print(parent.asText());
            for (int i = 0, n = parent.numberOfChildren(); i < n; i++) {
                parent.getChild(i).accept(this);
            }
        }
    }
}
