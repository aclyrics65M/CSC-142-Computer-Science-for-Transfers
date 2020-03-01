package model;

/** New Class called cell */
public class Cell {

    Actor actorObject;

    // Data Fields
    private int numberOfRows;
    private int numberOfColumns;

    /** Constructor for Cell */
    public Cell(int rowNumber, int columnNumber) {
        this.numberOfRows = rowNumber;
        this.numberOfColumns = columnNumber;
    }

    /** Method called contains */
    public void setActorObject(Actor sampleActor) {
        this.actorObject = sampleActor;
    }

    public Actor getActorObject(){
        return actorObject;
    }
}
