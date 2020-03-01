package model;

import controller.GameController;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.TimerTask;

public class EnemySpawner {

    // Data Fields
    Board sampleBoard;

    // Constructor
    public EnemySpawner(Board javaBoard) {
        this.sampleBoard = javaBoard;
    }

    // Run method
    public void run() {
        sampleBoard.createEnemy();

    }
}
