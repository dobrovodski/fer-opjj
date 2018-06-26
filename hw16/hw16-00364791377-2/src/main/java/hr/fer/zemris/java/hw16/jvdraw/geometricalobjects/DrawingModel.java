package hr.fer.zemris.java.hw16.jvdraw.geometricalobjects;

interface DrawingModel {
    public int getSize();

    public GeometricalObject getObject(int index);

    public void add(GeometricalObject object);

    public void addDrawingModelListener(DrawingModelListener l);

    public void removeDrawingModelListener(DrawingModelListener l);

    void remove(GeometricalObject object);
    
    void changeOrder(GeometricalObject object, int offset);
}
