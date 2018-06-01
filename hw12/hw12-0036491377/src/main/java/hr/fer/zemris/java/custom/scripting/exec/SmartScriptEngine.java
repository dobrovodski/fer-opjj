package hr.fer.zemris.java.custom.scripting.exec;

import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class SmartScriptEngine {
    private DocumentNode documentNode;
    private RequestContext requestContext;
    private ObjectMultistack multistack = new ObjectMultistack();

    private INodeVisitor visitor = new INodeVisitor() {
        @Override
        public void visitTextNode(TextNode node) {
            try {
                requestContext.write(node.asText());
            } catch (IOException e) {
                throw new RuntimeException("Could not write to request context's output stream.");
            }
        }

        @Override
        public void visitForLoopNode(ForLoopNode node) {
            String variableName = node.getVariable().asText();
            ValueWrapper start = new ValueWrapper(convertNumberElement(node.getStartExpression()));
            ValueWrapper end = new ValueWrapper(convertNumberElement(node.getEndExpression()));
            ValueWrapper step = new ValueWrapper(convertNumberElement(node.getStepExpression()));

            multistack.push(variableName, start);

            while (multistack.peek(variableName).numCompare(end.getValue()) <= 0) {
                visitChildNodes(node);
                ValueWrapper current = multistack.pop(variableName);
                current.add(step.getValue());
                multistack.push(variableName, current);
            }

            multistack.pop(variableName);
        }

        private Number convertNumberElement(Element e) {
            if (e instanceof ElementConstantInteger) {
                return Integer.parseInt(e.asText());
            } else if (e instanceof ElementConstantDouble) {
                return Double.parseDouble(e.asText());
            }
            throw new IllegalArgumentException("Could not convert element to number: " + e.asText());
        }

        @Override
        public void visitEchoNode(EchoNode node) {
            ObjectStack stack = new ObjectStack();
            Element[] elements = node.getElements();

            for (Element element : elements) {
                if (element instanceof ElementVariable) {
                    stack.push(multistack.peek(element.asText()).getValue());
                } else if (element instanceof ElementOperator) {
                    functions.get(element.asText()).accept(stack);
                } else if (element instanceof ElementFunction) {
                    functions.get(element.asText()).accept(stack);
                } else {
                    if (element instanceof ElementString) {
                        stack.push(element.asText().substring(1, element.asText().length() - 1));
                    } else {
                        stack.push(convertNumberElement(element));
                    }
                }
            }

            ObjectStack reverse = new ObjectStack();
            while (!stack.isEmpty()) {
                reverse.push(stack.pop());
            }

            while (!reverse.isEmpty()) {
                try {
                    requestContext.write(String.valueOf(reverse.pop()));
                } catch (IOException e) {
                    throw new RuntimeException("Could not write to request context's output stream.");
                }
            }
        }

        @Override
        public void visitDocumentNode(DocumentNode node) {
            visitChildNodes(node);
        }

        private void visitChildNodes(Node parent) {
            for (int i = 0, n = parent.numberOfChildren(); i < n; i++) {
                parent.getChild(i).accept(this);
            }
        }
    };

    private Map<String, Consumer<ObjectStack>> functions = new HashMap<>();

    {
        functions.put("+", stack -> {
            ValueWrapper a = new ValueWrapper(stack.pop());
            ValueWrapper b = new ValueWrapper(stack.pop());
            a.add(b.getValue());
            stack.push(a.getValue());
        });

        functions.put("-", stack -> {
            ValueWrapper a = new ValueWrapper(stack.pop());
            ValueWrapper b = new ValueWrapper(stack.pop());
            a.subtract(b.getValue());
            stack.push(a.getValue());
        });

        functions.put("*", stack -> {
            ValueWrapper a = new ValueWrapper(stack.pop());
            ValueWrapper b = new ValueWrapper(stack.pop());
            a.multiply(b.getValue());
            stack.push(a.getValue());
        });

        functions.put("/", stack -> {
            ValueWrapper a = new ValueWrapper(stack.pop());
            ValueWrapper b = new ValueWrapper(stack.pop());
            a.divide(b.getValue());
            stack.push(a.getValue());
        });

        functions.put("@sin", stack -> {
            ValueWrapper x = new ValueWrapper(stack.pop());
            double value = Double.valueOf(x.getValue().toString());
            stack.push(Math.sin(Math.toRadians(value)));
        });

        functions.put("@decfmt", stack -> {
            DecimalFormat fmt = new DecimalFormat(stack.pop().toString());
            ValueWrapper x = new ValueWrapper(stack.pop());
            stack.push(fmt.format(x.getValue()));
        });

        functions.put("@dup", stack -> {
            Object x = stack.pop();
            stack.push(x);
            stack.push(x);
        });

        functions.put("@swap", stack -> {
            Object a = stack.pop();
            Object b = stack.pop();
            stack.push(a);
            stack.push(a);
        });

        functions.put("@setMimeType", stack -> {
            requestContext.setMimeType(stack.pop().toString());
        });

        functions.put("@paramGet", stack -> {
            Object defaultValue = stack.pop();
            String name = stack.pop().toString();
            String value = requestContext.getParameter(name);
            stack.push(value == null ? defaultValue : value);
        });

        functions.put("@pparamGet", stack -> {
            Object defaultValue = stack.pop();
            String name = stack.pop().toString();
            String value = requestContext.getPersistentParameter(name);
            stack.push(value == null ? defaultValue : value);
        });

        functions.put("@pparamSet", stack -> {
            String name = stack.pop().toString();
            String value = stack.pop().toString();
            requestContext.setPersistentParameter(name, value);
        });

        functions.put("@pparamDel", stack -> {
            String name = stack.pop().toString();
            requestContext.removePersistentParameter(name);
        });

        functions.put("@tparamGet", stack -> {
            Object defaultValue = stack.pop();
            String name = stack.pop().toString();
            String value = requestContext.getTemporaryParameter(name);
            stack.push(value == null ? defaultValue : value);
        });

        functions.put("@tparamSet", stack -> {
            String name = stack.pop().toString();
            String value = stack.pop().toString();
            requestContext.setTemporaryParameter(name, value);
        });

        functions.put("@tparamDel", stack -> {
            String name = stack.pop().toString();
            requestContext.removeTemporaryParameter(name);
        });
    }

    public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
        Objects.requireNonNull(documentNode, "Document node cannot be null.");
        Objects.requireNonNull(requestContext, "Request context cannot be null.");
        this.documentNode = documentNode;
        this.requestContext = requestContext;
    }

    public void execute() {
        documentNode.accept(visitor);
    }

}
