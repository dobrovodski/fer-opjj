package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Interface which provides methods that are called when visiting different kinds of nodes in a script.
 *
 * @author matej
 */
public interface INodeVisitor {
    /**
     * Called when visiting a text node.
     *
     * @param node node being visited.
     */
    public void visitTextNode(TextNode node);

    /**
     * Called when visiting a for loop node.
     *
     * @param node node being visited
     */
    public void visitForLoopNode(ForLoopNode node);

    /**
     * Called when visiting an echo node.
     *
     * @param node node being visited
     */
    public void visitEchoNode(EchoNode node);

    /**
     * Called when visiting a document node.
     *
     * @param node node being visited
     */
    public void visitDocumentNode(DocumentNode node);
}
