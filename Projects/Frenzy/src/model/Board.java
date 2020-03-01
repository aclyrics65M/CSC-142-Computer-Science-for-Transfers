package model;

/*
 * File: Board.java
 * @author: Faraz Mamaghani
 * @course: CS for Transfers
 * @date: 3/28/2018
 */

import controller.GameController;
import frenzyui.Frenzy;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Observable;

import java.util.Random;
import java.util.Timer;


public class Board extends Observable  {


    // Label output text
    Label outputText;
    EnemyThread enemyThread;

    // Declare cell matrix
    Cell[][] completeStoreMatrix = new Cell[9][9];

    // Declare Player object
    public Player playerObject;

    // Declare Enemy object
    public Enemy enemyObject;

    // Controller object for reference
    public GameController objectController;

    // Create Frenzy object for reference
    private Frenzy frenzyObject;
    public int gridSize = 9;

    // Create new score variable
    private int scoreVariable = 0;

    private Timer javaTimer;

    EnemySpawner enemySpawn;

    private String text;

    // Array list of enemies
    ArrayList<Enemy>  enemyArrayList = new ArrayList<>();

    /**
     * Blank constructor for Board
     */
    public Board(Frenzy frenzy) {

        // Two for loops to create actor objects in each block
        for(int i = 0; i < getNumberOfRows(); i++) {
            for(int j = 0; j < getNumberOfColumns(); j++) {
                // For each block in the matrix add an actor object
                completeStoreMatrix[i][j] = new Cell(i, j);
            }
        }


        objectController = new GameController(frenzy, this, gridSize);

        // Add player to the grid at a random location
        frenzyObject = frenzy;


        Random random = new Random();

        int randomXPosition = random.nextInt(gridSize - 1);
        int randomYPosition = random.nextInt(gridSize - 1);


        // Create player
        playerObject = new Player(randomXPosition, randomYPosition);
        // Set the player
        completeStoreMatrix[randomXPosition][randomYPosition].setActorObject(playerObject);
        frenzyObject.setPlayerPosition(randomXPosition, randomYPosition);


        // Add three initial enemies
        for(int i = 0; i < 3; i++) {
           createEnemy();
        }

        // Create enemy thread
        enemyThread = new EnemyThread(objectController, this);

        // Start enemy thread
        enemyThread.start();


    }

    /** Get at specific position */
    public Actor getAtPosition(int row, int column) {
        return completeStoreMatrix[row][column].getActorObject();
    }

    public void setAtPosition(int row, int column, Actor actorObject) {
        completeStoreMatrix[row][column].setActorObject(actorObject);
    }


    /** Method to get Player row Position */
    public int getPlayerRowPosition() {
        return playerObject.getRow();
    }

    /** Method to get Player column position */
    public int getPlayerColumnPosition() {
        return playerObject.getColumn();
    }

    /** Method to get Enemy row position */
    public int getEnemyRowPosition() {
        return enemyObject.getRow();
    }

    public int getEnemyColumnPosition() {
        return enemyObject.getColumn();
    }


    /** Method to update Player Position in Board */
    /** This method is Synchronized */
    public synchronized void updatePlayerPosition(int oldRowPosition, int oldColumnPosition, int newRowPosition, int newColumnPosition) {
        // Remove the current node

        if(newRowPosition < 0 || newRowPosition > 9 || newColumnPosition < 0 || newColumnPosition > 9) {
            playerObject.setRow(oldRowPosition);
            playerObject.setColumn(oldColumnPosition);

        }else {
            playerObject.setRow(newRowPosition);
            playerObject.setColumn(newColumnPosition);

            completeStoreMatrix[oldRowPosition][oldColumnPosition].setActorObject(null);

            // check for enemy at position
            if(enemyArrayList.contains(completeStoreMatrix[newRowPosition][newColumnPosition].getActorObject())) {
                // Declare enemy object
                enemyObject = (Enemy) completeStoreMatrix[newRowPosition][newColumnPosition].getActorObject();
                enemyArrayList.remove(completeStoreMatrix[newRowPosition][newColumnPosition].getActorObject());

                // Update the text screen
                text = "P ate " + enemyObject.getName();
                frenzyObject.getTextArea().appendText(text + "\n");

                // Update frenzy
                frenzyObject.update(this, this);
                updateScoreCount(scoreVariable);
                notifyObservers();


            }

            // Notify observers method
//            notifyObservers();

            completeStoreMatrix[newRowPosition][newColumnPosition].setActorObject(playerObject);
        }

    }

    /** Method to update Enemy position */
    public void updateEnemyPosition(int oldRowPosition, int oldColumnPosition, int newRowPosition, int newColumnPosition) {

        if(newRowPosition < 0 || newRowPosition > 9 || newColumnPosition < 0 || newColumnPosition > 9) {
            enemyObject.setRow(oldRowPosition);
            enemyObject.setColumn(oldColumnPosition);
            //completeStoreMatrix[oldRowPosition][oldColumnPosition].setActorObject(playerObject);

        }else {
            enemyObject.setRow(newRowPosition);
            enemyObject.setColumn(newColumnPosition);

            completeStoreMatrix[oldRowPosition][oldColumnPosition].setActorObject(null);
            completeStoreMatrix[newRowPosition][newColumnPosition].setActorObject(enemyObject);
        }

    }



    public int getNumberOfRows() {
        return 9;
    }

    public int getNumberOfColumns() {
        return 9;
    }


    public void setController(GameController controller) {
        this.objectController = controller;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemyArrayList;
    }



    /** Create enemy method */
    public void createEnemy() {

        Random random = new Random();

        // Generate a random x and y position
        int randomXPosition = random.nextInt(gridSize - 1);
        int randomYPosition = random.nextInt(gridSize - 1);

        // Check to make sure the positions do not overlap
        if (completeStoreMatrix[randomXPosition][randomYPosition].getActorObject() != null) {
            // Regenerate the random positions
            randomXPosition = random.nextInt(gridSize - 1);
            randomYPosition = random.nextInt(gridSize - 1);
        }


        enemyObject = new Enemy(objectController, randomXPosition, randomYPosition, this.randomName());

        completeStoreMatrix[randomXPosition][randomYPosition].setActorObject(enemyObject);
        frenzyObject.setEnemyPosition(enemyObject, randomXPosition, randomYPosition);

        enemyArrayList.add(enemyObject);
    }


    /** Add notifier observer */
    private void announceChange() {
        super.setChanged();
        super.notifyObservers();
    }

    /** Method to create random name */
    public String randomName() {
        // Generate a random name code snippet
        Random random = new Random();
        String alphabet = "ABCDEFGHIJKLMNO%QRSTUVWXYZ#!@%";
        int randomNumber = random.nextInt(30);
        char randomLetter = alphabet.charAt(randomNumber);
        String randomCharacter = Character.toString(randomLetter);

        return randomCharacter;
    }

    /** Return score method */
    public int getScoreVariable() {
        return scoreVariable;
    }

    public void updateScoreCount(int updatedScore) {
        // Increase score count by 1
        scoreVariable = updatedScore + 1;
    }

    /** Return string text */
    public String obtainStringMessage() {
        return text;
    }

    /** Add observable*/

}

