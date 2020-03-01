package frenzyui;

/*
 * File: Frenzy.java
 * @author: Faraz Mamaghani
 * @course: CS for Transfers
 * @date: 3/28/2018
 */

import controller.GameController;
import javafx.application.Application;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.*;


import java.util.Observable;
import java.util.Observer;
import java.util.Random;


public class Frenzy extends Application implements Observer {

    // Create new updated label
    Label[][] labelBlocks = new Label[9][9];
    Label emptyLabel;
    Label javaLabel;
    Label enemyLabel;

    // Declare board
    Board javaFrenzyBoard;

    // Declare GridPane object
    GridPane javaGridPane = new GridPane();

    // Label output text
    Label outputText;

    // Create controller object and gridPane
    GameController gameControllerObject;
    public GridPane pane;

    // Create a scoreCount variable
    private int scoreCount = 0;

    private TextArea messageText;
    private TextField textLine;
    private GridPane textPane;

    @Override
    public void start(Stage primaryStage) {

        // Put the label in corresponding part
        GridPane javaPane = makeGridPane(9);

        // Create a board
        javaFrenzyBoard = new Board(this);
        javaFrenzyBoard.addObserver(this);

        // Implement a game controller
        gameControllerObject = new GameController(this,javaFrenzyBoard, 9);

        // Create a Pane
        BorderPane mainPane = new BorderPane();
        //mainPane.setOnKeyPressed(this::handle);

        mainPane.setPrefHeight( 550 );
        mainPane.setPrefWidth( 650 );

        // right side holds the display and control buttons.
        BorderPane pane = new BorderPane();
        pane.setCenter( makeFrenzyControlPane());
        pane.setMaxSize( Double.MAX_VALUE, Double.MAX_VALUE );

        // Create Grid Pane and set it to the center of the main pane
        //GridPane p = makeGridPane(9);
        mainPane.setCenter(javaPane);
        mainPane.setRight( pane );

        // Create a scene
        Scene scene = new Scene( mainPane );

        System.out.println( "start called." );
        System.out.println( "start may process the command line." );
        System.out.println( "start builds and shows the GUI." );
        System.out.println("");
        System.out.println("Game is Running!");

        primaryStage.setTitle( "Frenzy" );
        primaryStage.setScene(scene);
        primaryStage.show();



    }


    @Override
    public void stop() throws Exception {

        super.stop();
        System.out.println( "stop called. Do termination cleanup." );
    }

    /** Main method*/
    public static void main(String[] args) {

        // Declare the value of the board size
        int sizeBoard = Integer.parseInt(args[0]);

        if (sizeBoard < 7) {
            System.out.println("Error! The size of the board cannot be less than 7!");
            System.exit(0);
        }

        System.out.println( "main launching application..." );
        Application.launch( args );
    }

    /**
     * makeGridPane creates and returns a grid layout of buttons.
     * The grid does its own layout and the size is fixed by default.
     * Changing the window size will not cause the grid to get larger.
     * @param: size of the grid
     * @return grid pane that can be added to a region
     */
    public GridPane makeGridPane(int gridSize) {

        //javaGridPane = new GridPane();
        // Double for loop
        for(int a = 0; a < gridSize; a++) {
            for(int b = 0; b < gridSize; b++) {
                Label labelObject = new Label();

                labelObject.setAlignment(Pos.CENTER);
                labelObject.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                // Put label object into 2D array
                labelBlocks[a][b] = labelObject;

                // Put label inside grid pane
                javaGridPane.add(labelObject, b, a);
            }
        }


        // Set each grid cell to white
        javaGridPane.setBackground(new Background(new BackgroundFill(Color.WHITE,
                CornerRadii.EMPTY, Insets.EMPTY)));

        // Set the dimensions of the gridSize
        javaGridPane.setMinSize(gridSize, gridSize);

        // Set padding with a value of 10
        javaGridPane.setPadding(new Insets(10));

        int row = gridSize;
        int col = gridSize;

        ColumnConstraints[] columnConstrains = new ColumnConstraints[col];

        for (int i = 0; i < col; i++) {
            columnConstrains[i] = new ColumnConstraints();
            columnConstrains[i].setPercentWidth(100.0 / col);
        }

        javaGridPane.getColumnConstraints().addAll(columnConstrains);

        RowConstraints[] rowConstrains = new RowConstraints[row];

        for (int j = 0; j < row; j++) {
            rowConstrains[j] = new RowConstraints();
            rowConstrains[j].setPercentHeight(100.0 / row);
        }

        javaGridPane.getRowConstraints().addAll(rowConstrains);

        // Set the visible lines true
        javaGridPane.setGridLinesVisible( true ); // makes it hard to see active item

        return javaGridPane;
    }


    /**
     * makeFrenzyControlPane creates and returns the frenzy control pane.
     * @return pane for inclusion in a display pane
     */
    public Pane makeFrenzyControlPane() {
        // Create a VBox pane
        VBox frenzyControlPane = new VBox();

        // Create a Tile Pane
        TilePane javaTile = new TilePane();
        javaTile.setPadding(new Insets(10, 5, 5, 5));

        // Create a Label for the score
        javaLabel = new Label("Score: " + scoreCount);

        messageText = new TextArea();
        messageText.setWrapText(true);
        messageText.setEditable(false);
        messageText.appendText("Game is running!\n");

        frenzyControlPane.getChildren().addAll(javaLabel, this.makeButtonPane(), messageText);
        frenzyControlPane.setMaxSize(150, 200);

        javaTile.setOrientation(Orientation.VERTICAL);
        javaTile.getChildren().add(frenzyControlPane);

        //.setCenter(this.makeButtonPane());

        // return statement
        return frenzyControlPane;
    }


    /**
     * makeButtonPane creates and returns a layout of buttons.
     * @return pane that can be added
     */
    public Pane makeButtonPane() {

        GridPane javaGrid = new GridPane();


        // Number of rows and columns
        int numberRows = 2;
        int numberColumns = 2;

        ColumnConstraints[] columnConstrains = new ColumnConstraints[numberColumns];

        for (int i = 0; i < numberColumns; i++) {
            columnConstrains[i] = new ColumnConstraints();
            columnConstrains[i].setPercentWidth(100.0 / numberColumns);
        }

        javaGrid.getColumnConstraints().addAll(columnConstrains);

        RowConstraints[] rowConstrains = new RowConstraints[numberRows];

        for (int j = 0; j < numberRows; j++) {
            rowConstrains[j] = new RowConstraints();
            rowConstrains[j].setPercentHeight(100.0 / numberRows);
        }

        javaGrid.getRowConstraints().addAll(rowConstrains);

        // add buttons specifying both column and row.

        for ( int rows = 0; rows < 2; rows++ ) {
            for ( int columns = 0; columns < 2; columns++ ) {

                Button btn = new Button( "" + ( 1 + rows * 3 + columns ) );

                btn.setOnKeyPressed(this::handle);

                // setting max size makes buttons fill the cell.
                btn.setPrefSize( 100, 100 );
                javaGrid.add(btn, columns, rows);
            }
        }

        // Create the buttons
        // ++ Button
        Button plusplusButton = new Button("++");
        plusplusButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        javaGrid.add(plusplusButton,0,0);

        // -- Button
        Button minusminusButton = new Button("--");
        minusminusButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        javaGrid.add(minusminusButton, 1, 0);

        // restart Button
        Button restartButton = new Button("restart");
        restartButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        javaGrid.add(restartButton, 0, 1);

        // quit Button
        Button quitButton = new Button("quit");
        quitButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        javaGrid.add(quitButton, 1, 1);

        // return statement

        return javaGrid;

    }

    public void handle(KeyEvent event) {
        gameControllerObject.handle(event);
    }

    public void makeBlank(int row, int col){
        javaGridPane.add(new Label() ,row, col);
    }

    public void setPlayerPosition(int row, int col) {
        Label javaLabel = labelBlocks[row][col];
        javaLabel.setText("P");

        // Create a font
        Font javaFont = new Font("Arial", 30);

        // Set the font
        javaLabel.setFont(javaFont);

        // Set a color to yellow
        Color playerColor =  Color.rgb(255, 255, 0);

        // Create a background fill
        BackgroundFill playerBackgroundFill = new BackgroundFill(playerColor, CornerRadii.EMPTY, Insets.EMPTY);

        // Create a background
        Background playerBackground = new Background(playerBackgroundFill);

        // Set the label to the background
        javaLabel.setBackground(playerBackground);
        javaLabel.setAlignment(Pos.CENTER);
        javaLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }

    public void setEnemyPosition(Enemy enemy, int row, int col) {

        // Create a font
        Font javaFont = new Font("Arial", 30);

        // For loop to create three initial distinct enemy objects
        enemyLabel = labelBlocks[row][col];
        enemyLabel.setFont(javaFont);
        enemyLabel.setText(enemy.getName());

        // Set a color to dark green
        Color playerColor = Color.rgb(18, 178, 15);

        // Create a background fill
        BackgroundFill playerBackgroundFill = new BackgroundFill(playerColor, CornerRadii.EMPTY, Insets.EMPTY);

        // Create a background
        Background playerBackground = new Background(playerBackgroundFill);

        enemyLabel.setBackground(playerBackground);
        enemyLabel.setAlignment(Pos.CENTER);
        enemyLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

    }
    /** Void method sets block to null */
    public void setBlockNull(int row, int col) {

        // Set a color to white
        Color playerColor =  Color.rgb(255, 255, 255);

        // Create a background fill
        BackgroundFill playerBackgroundFill = new BackgroundFill(playerColor, CornerRadii.EMPTY, Insets.EMPTY);

        // Create a background
        Background playerBackground = new Background(playerBackgroundFill);

        // Set block to empty (null)
        emptyLabel = labelBlocks[row][col];
        //emptyLabel = new Label("");
        String blank = new String(" ");

        // Set empty label to nothing
        emptyLabel.setText(blank);
        emptyLabel.setBackground(playerBackground);



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

    /** Set score*/
    public void setScoreCount(int newScore) {
        this.scoreCount = newScore;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                javaLabel.setText("Score: " + scoreCount);
            }
        });

    }


    @Override
    public void update(Observable o, Object arg) {

        setScoreCount(gameControllerObject.getScoreFromBoard() + 1);
    }

   /** Return label*/
    public TextArea getTextArea() {
        //messageText.setEditable(false);
        return messageText;
    }


    /** Button function */
}
