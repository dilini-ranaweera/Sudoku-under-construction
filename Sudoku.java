package org.cis120.Sudoku;

/**
 * CIS 120 HW09 - Game From Scratch: Sudoku
 * (c) University of Pennsylvania
 * Created by Dilini Ranaweera in Fall 2021.
 */

import java.io.*;
import java.util.*;

/**
 * This class is a model for Sudoku.
 * 
 * This game adheres to a Model-View-Controller design framework.
 * This framework is very effective for turn-based games. We
 * STRONGLY recommend you review these lecture slides, starting at
 * slide 8, for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec36.pdf
 * 
 * This model is completely independent of the view and controller.
 * This is in keeping with the concept of modularity! We can play
 * the whole game from start to finish without ever drawing anything
 * on a screen or instantiating a Java Swing object.
 *
 */
public class Sudoku {

    /**
     * Creating instance fields that represent the internal state of the game. I'm
     * using a 9x9 array
     * to model the internal state of the game (Design concept 1). There are methods
     * to check whether
     * the numbers 1-9 are contained within each row, column, and 3x3 grid. These
     * methods are used
     * represent whether the user has won the game or not, and that method is
     * checked after every
     * user input to the board.
     * 
     * To represent each 3x3 box, I use a linkedlist. This is easier than trying to
     * use a
     * separate 2D array in terms of searching.
     */

    private int[][] board;
    private int numMovesRemaining;
    private boolean wonGame; // something with updating the status with this - look at TTT for
                             // details

    // getting the comparison array - use this to determine if something won
    private int[][] comparison = new int[9][9];

    /**
     * Constructor for a {@code Sudoku}
     */

    public Sudoku() {
        reset();
    }

    /**
     * Reset method used in the constructor and to create a blank game board.
     */

    public void reset() {
        board = new int[9][9];
        numMovesRemaining = 200;
        wonGame = false;
    }

    /**
     * get comparison Matrix
     */

    int[][] easySolution = { { 4, 3, 5, 2, 6, 9, 7, 8, 1 },
        { 6, 8, 2, 5, 7, 1, 4, 9, 3 },
        { 1, 9, 7, 8, 3, 4, 5, 6, 2 },
        { 8, 2, 6, 1, 9, 5, 3, 4, 7 },
        { 3, 7, 4, 6, 8, 2, 9, 1, 5 },
        { 9, 5, 1, 7, 4, 3, 6, 2, 8 },
        { 5, 1, 9, 3, 2, 6, 8, 7, 4 },
        { 2, 4, 8, 9, 5, 7, 1, 3, 6 },
        { 7, 6, 3, 4, 1, 8, 2, 5, 9 } };

    int[][] mediumSolution = { { 1, 2, 3, 6, 7, 8, 9, 4, 5 },
        { 5, 8, 4, 2, 3, 9, 7, 6, 1 },
        { 9, 6, 7, 1, 4, 5, 3, 2, 8 },
        { 3, 7, 2, 4, 6, 1, 5, 8, 9 },
        { 6, 9, 1, 5, 8, 3, 2, 7, 4 },
        { 4, 5, 8, 7, 9, 2, 6, 1, 3 },
        { 8, 3, 6, 9, 2, 4, 1, 5, 7 },
        { 2, 1, 9, 8, 5, 7, 4, 3, 6 },
        { 7, 4, 5, 3, 1, 6, 8, 9, 2 } };

    int[][] hardSolution = { { 5, 8, 1, 6, 7, 2, 4, 3, 9 },
        { 7, 9, 2, 8, 4, 3, 6, 5, 1 },
        { 3, 6, 4, 5, 9, 1, 7, 8, 2 },
        { 4, 3, 8, 9, 5, 7, 2, 1, 6 },
        { 2, 5, 6, 1, 8, 4, 9, 7, 3 },
        { 1, 7, 9, 3, 2, 6, 8, 4, 5 },
        { 8, 4, 5, 2, 1, 9, 3, 6, 7 },
        { 9, 1, 3, 7, 6, 8, 5, 2, 4 },
        { 6, 2, 7, 4, 3, 5, 1, 9, 8 } };

    public void getComparisonArray(int version) {
        switch (version) {
            case 1:
                arrayAssign(easySolution);
                break;
            case 2:
                arrayAssign(mediumSolution);
                break;
            case 3:
                arrayAssign(hardSolution);
                break;
            default:
                break;
        }
    }

    /**
     * private helper method to assign every element in the
     * hard coded arrays to the comparison array
     */
    private void arrayAssign(int[][] array) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                comparison[r][c] = array[r][c];
            }
        }
    }

    /**
     * method to play a turn and update the state
     */
    public void playTurn(int row, int column, int number) {
        setCell(row, column, number);
        numMovesRemaining--;

    }

    /**
     * method to check whether the game is over or not. The game is over if
     * the arrays are equal or the number of moves is 0.
     */

    public boolean gameOver() {

        if (board.equals(comparison)) {
            wonGame = true;
        }
        return wonGame || numMovesRemaining == 0;
    }

    /**
     * get methods and set methods for the instance variables - creating out of
     * abundance
     */

    public int[][] getBoard() {
        return board;
    }

    public int getNumMovesRemaining() {
        return numMovesRemaining;
    }

    public boolean getWonGame() {
        return wonGame;
    }

    public void setCell(int row, int column, int number) {

        try {
            board[row][column] = number;

            if (number > 9 || number < 1) {
                board[row][column] = 0;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Can't give that input");
        }
    }

    public void setNumMovesRemaining(int remain) {

        if (remain < 0) {
            remain = 0;
        }

        if (remain > 200) {
            remain = 200;
        }

        numMovesRemaining = remain;
    }

    public void setWonGame(boolean win) {
        wonGame = win;
    }

    public int getCell(int row, int column) {
        return board[row][column];
    }

}
