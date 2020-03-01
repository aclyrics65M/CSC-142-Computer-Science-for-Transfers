package controller;

/*
 * File: GameController.java
 * @author: Faraz Mamaghani
 * @course: CS for Transfers
 * @date: 3/28/2018
 */

import frenzyui.Frenzy;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Board;
import model.Enemy;
import model.Player;
import java.time.Duration;
import java.time.Instant;

import java.awt.*;

public class GameController {

    // Data members
    Board javaBoard;
    int boardSize;
    Frenzy javaFrenzyObject;

    /** Constructor for GameController */
    public GameController(Frenzy objectFrenzy, Board modelBoard, int maxSize) {
        this.javaFrenzyObject = objectFrenzy;
        this.javaBoard = modelBoard;
        this.boardSize = maxSize;
        this.update();
    }


    /** Every second, this method is called */
    public void handleEnemyMovement(int oldRow, int oldCol, int newRow, int newCol) {
        //System.out.println("Handle Enemy Movement");

        // Update enemy position
        javaBoard.setAtPosition(newRow, newCol, javaBoard.getAtPosition(oldRow, oldCol));
        javaBoard.setAtPosition(oldRow, oldCol, null);


    }

    public void handle(KeyEvent event) {

        // Start a timer for movement
        Instant start = Instant.now();

        // Initialize
        int initialRowPosition = javaBoard.getPlayerRowPosition();
        int initialColumnPosition = javaBoard.getPlayerColumnPosition();

        // Updated Column position
        int updatedXPosition = 0;

        // Updated Row Position
        int updatedYPosition = 0;

        javaFrenzyObject.setBlockNull(initialRowPosition, initialColumnPosition);


        // Create a nested if-else statement
        if(event.getCode().equals(KeyCode.UP)) {

            // Check if reached upper boundary of board
            if(initialRowPosition - 1 >= 0) {

                updatedXPosition = initialColumnPosition;
                updatedYPosition = initialRowPosition - 1;


            } else {

                updatedXPosition = initialColumnPosition;
                updatedYPosition = initialRowPosition + 8;

            }

            // Set new player position
            javaBoard.updatePlayerPosition(initialRowPosition, initialColumnPosition,
                    updatedYPosition, updatedXPosition);


        } else if (event.getCode().equals(KeyCode.DOWN)) {


            // Check if reached lower boundary of board
            if(initialRowPosition + 1 <= 8) {

                updatedXPosition = initialColumnPosition;
                updatedYPosition = initialRowPosition + 1;

            } else {

                updatedXPosition = initialColumnPosition;
                updatedYPosition = initialRowPosition - 8;

            }

            // Set new player position
            javaBoard.updatePlayerPosition(initialRowPosition, initialColumnPosition,
                    updatedYPosition, updatedXPosition);

        } else if (event.getCode().equals(KeyCode.LEFT)) {

            // Check if reached left boundary of board
            if(initialColumnPosition - 1 >= 0) {
                updatedXPosition = initialColumnPosition - 1;
                updatedYPosition = initialRowPosition;


            } else {
                updatedXPosition = initialColumnPosition + 8;
                updatedYPosition = initialRowPosition;

            }

            // Set new player position
            javaBoard.updatePlayerPosition(initialRowPosition, initialColumnPosition,
                    updatedYPosition, updatedXPosition);

        } else if (event.getCode().equals(KeyCode.RIGHT)) {

            // Check if reached right boundary of board
            if(initialColumnPosition + 1 <= 8) {
                updatedXPosition = initialColumnPosition + 1;
                updatedYPosition = initialRowPosition;


            } else {
                updatedXPosition = initialColumnPosition - 8;
                updatedYPosition = initialRowPosition;

            }

            // Set new player position
            javaBoard.updatePlayerPosition(initialRowPosition, initialColumnPosition,
                    updatedYPosition, updatedXPosition);
        }

        // Stop timer
        Instant end = Instant.now();

        // Calculate time duration
        Duration timeElapsed = Duration.between(start, end);

        this.update();

    }

    /** Update notification method */
    public void update() {
        //System.out.println("");
        // go through each cell in the model
        for(int row = 0; row < javaBoard.getNumberOfRows(); row++) {
            for(int column = 0; column < javaBoard.getNumberOfColumns(); column++) {

                if(javaBoard.getAtPosition(row, column) == null) {
                    javaFrenzyObject.setBlockNull(row, column);

                } else if(javaBoard.getAtPosition(row, column) instanceof Player) {
                    javaFrenzyObject.setPlayerPosition(row, column);

                } else if (javaBoard.getAtPosition(row, column) instanceof Enemy) {
                    Enemy enemy = (Enemy) javaBoard.getAtPosition(row, column);
                    javaFrenzyObject.setEnemyPosition(enemy, row, column);

                }

            }
        }

    }

    /** Get score from board */
    public int getScoreFromBoard() {
        return javaBoard.getScoreVariable();
    }




}
