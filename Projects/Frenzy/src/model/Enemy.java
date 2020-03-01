package model;

/*
 * File: Enemy.java
 * @author: Faraz Mamaghani
 * @course: CS for Transfers
 * @date: 3/28/2018
 */

import controller.GameController;
import javafx.scene.control.Label;

import java.util.Random;


public class Enemy implements Actor {

    // Data members
    Label enemyLabel[] = new Label[3];

    private String enemyName;
    private int enemyRowPosition;
    private int enemyColumnPosition;
    private int randomXMovement;
    private int randomYMovement;
    private boolean movementVertical;

    // Create an enemy thread
    EnemyThread enemyThread;

    // Create local game controller variable
    GameController javaController;

    Random random = new Random();
    /**
     * Blank Default Constructor
     * To create an Enemy
     */
    public Enemy(GameController javaController, int row, int column, String name) {
        this.enemyName = name;
        this.javaController = javaController;

        this.enemyRowPosition = row;
        this.enemyColumnPosition = column;

        int randomX = random.nextInt(2);
        randomXMovement = (randomX == 0 ? -1 : 1);

        int randomY = random.nextInt(2);
        randomYMovement = (randomY == 0 ? -1: 1);

        // Initialize movement
        movementVertical = true;
    }

    /** returnEnemyLabel() method
     */
    public Label[] returnEnemyLabel() {
        return enemyLabel;
    }

    /** Move new enemy to new position */
    public void move() {

        int r = enemyRowPosition;
        int c = enemyColumnPosition;

        if(movementVertical) {
            enemyRowPosition += randomYMovement;
        } else {
            enemyColumnPosition += randomXMovement;
        }

        movementVertical = !movementVertical;

        if (enemyRowPosition < 0) {
            enemyRowPosition  = 8;
        } else if (enemyRowPosition > 8) {
            enemyRowPosition = 0;
        }

        if (enemyColumnPosition < 0) {
            enemyColumnPosition = 8;
        } else if (enemyColumnPosition > 8) {
            enemyColumnPosition = 0;
        }

        javaController.handleEnemyMovement(r, c, enemyRowPosition, enemyColumnPosition);
    }

    public String getName() {
        return enemyName;
    }

    /** Setters and Getters */
    /** Get row method */
    public int getRow() {
        return enemyRowPosition;
    }

    /** Get column method */
    public int getColumn() {
        return enemyColumnPosition;
    }

    /** Set enemy row */
    public void setRow(int rowNumber) {
        this.enemyRowPosition = rowNumber;
    }

    /** Set column row */
    public void setColumn(int columnNumber) {
        this.enemyColumnPosition = columnNumber;
    }

}
