package model;

import controller.GameController;
import javafx.application.Platform;

import java.util.ArrayList;


public class EnemyThread extends Thread {
    // Class to give enemy a reference to

    // Data Fields
    private GameController javaControl;

    // public timeInterval
    private int timeInterval = 1000;

    private Board sampleBoard;

    // Constructor
    public EnemyThread(GameController controller, Board javaBoard) {
        this.javaControl = controller;
        this.sampleBoard = javaBoard;
    }

    /** Create run method */
    public void run() {
        boolean loopStatus = true;
        int initialEnemyRow;
        int initialEnemyColumn;

        while (loopStatus) {

            // Sleep for a thousand milliseconds
            try {
                Thread.sleep(timeInterval);

                ArrayList<Enemy> enemyArrayList = sampleBoard.getEnemies();

                for (Enemy enemyObject : enemyArrayList) {

                    enemyObject.move();

                    Platform.runLater(() -> javaControl.update());


                }

                } catch(InterruptedException e){
                    e.printStackTrace();

                } catch(IllegalStateException oe){
                    oe.printStackTrace();
                }


            }
        }

    /**method to increase speed */
    public void increaseEnemySpeed() {
        timeInterval -= 100;
    }

    /** method to decrease speed */
    public void decreaseEnemySpeed() {
        timeInterval += 100;
    }

}
