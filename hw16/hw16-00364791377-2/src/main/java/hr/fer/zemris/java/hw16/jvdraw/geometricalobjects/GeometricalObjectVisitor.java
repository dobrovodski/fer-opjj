package hr.fer.zemris.java.hw16.jvdraw.geometricalobjects;

interface GeometricalObjectVisitor {
    public abstract void visit(Line line);

    public abstract void visit(Circle circle);

    public abstract void visit(FilledCircle filledCircle);
}