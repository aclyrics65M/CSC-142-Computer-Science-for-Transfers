package model;

/*
 * File: Player.java
 * @author: Faraz Mamaghani
 * @course: CS for Transfers
 * @date: 3/28/2018
 */

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class Player implements Actor{

    // Data members
    Label javaLabel;
    private int xCoordinate;
    private int yCoordinate;

    /**
     *  Constructor to create a Player
     */
    public Player(int yPosition, int xPosition) {
        this.xCoordinate = xPosition;
        this.yCoordinate = yPosition;

    }

    /** returnPlayerLabel() method
     */
    public Label getPlayerLabel() {
        return javaLabel;
    }

    public int getRow() {
        return yCoordinate;
    }

    public int getColumn() {
        return xCoordinate;
    }

    public void setRow(int rowValue) {
        this.yCoordinate = rowValue;
    }

    public void setColumn(int columnValue) {
        this.xCoordinate = columnValue;
    }

}
