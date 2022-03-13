package org.cis120.Sudoku;

import javax.swing.*;
import javax.swing.border.Border; // idk why this is here
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SudokuGrid extends JPanel {

    /**
     * This class represents a sudoku grid. It's a nine-by-nine grid that represents
     * a sudoku game board. It's represented through JPanel GridLayouts and each
     * cell is JTextField where the user can type in a number.
     *
     * This class has all the necessary methods to update the internal state of the
     * game
     * and display correct methods as well. Analogous to TicTacToe's GameBoard
     * class.
     */

    // fields for creating the grid
    private SudokuCellListener[][] listeners;
    private JTextField[][] cells;
    private JLabel label;
    private Border cellBorder;

    // field for modelling the internal state
    private Sudoku sudokuModel;

    // collection for keeping track of the moves made by the user - linked list of
    // int arrays
    private LinkedList<int[]> moves = new LinkedList<>();

    public SudokuGrid(JLabel initial) {

        // creates border around the court area, JComponent method
        cellBorder = BorderFactory.createLineBorder(Color.BLACK);
        setBorder(cellBorder);
        this.setLayout(new GridLayout(9, 9, -1, -1));
        this.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // sets the thing as focusable to play the game
        this.setFocusable(true);

        // Initializes a model for the internal state
        sudokuModel = new Sudoku();

        // initializes the label
        label = initial;

        // initializes the JTextField Array to blanks, adds listeners to each cell
        cells = new JTextField[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j] = new JTextField(" ");
                cells[i][j].setBorder(cellBorder);
                cells[i][j].setHorizontalAlignment(JTextField.CENTER);
                cells[i][j].addKeyListener(
                        new SudokuCellListener(cells[i][j], i, j, sudokuModel, this, moves)
                );
                this.add(cells[i][j]);

            }
        }

        requestFocusInWindow();

    }

    // add invalid part for this - FIX IN OH TODAY
    public void updateStatus() {
        if (sudokuModel.getWonGame()) {
            label.setText("Congratulations! You Won the Game :)))");
        }

        if (sudokuModel.getNumMovesRemaining() == 0) {
            label.setText("I'm sorry you ran out of moves. You lost!!");
        } else {
            label.setText("Number of Moves Remaining: " + sudokuModel.getNumMovesRemaining());
        }
    }

    // method for undo - tied to weird KL and update - FIX IN OH TODAY
    public void undo() {

        // access the last element in the linked list
        int[] last = moves.getLast();

        // update the internal contents of the game state using the set method and
        // number 0
        sudokuModel.setCell(last[0], last[1], 0);
        cells[last[0]][last[1]].setText(" ");

        // remove the last element in the linked list
        moves.removeLast();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 600);
    }

    // method for save ability in program
    public int[][] getInternalState() {
        return sudokuModel.getBoard();
    }

    // method for getting the number of moves left
    public int getMovesLeftSG() {
        return sudokuModel.getNumMovesRemaining();
    }

    // method for getting the sudoku
    public Sudoku getSudoku() {
        return sudokuModel;
    }

    // method for setting the cells in the JTextField Array
    public void setCell(int row, int column, String text) {
        cells[row][column].setText(text);
    }

    public void reset() {
        sudokuModel.reset();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                setCell(i, j, " ");
            }
        }

        // add piece for status label change
    }

    // method for getting the linked list that represents the moves
    public LinkedList<int[]> getMoves() {
        return moves;
    }

    // method for setting the linked list that represents the move
    public void setMoves(LinkedList<int[]> given) {
        moves.clear();

        for (int[] entry : given) {
            moves.add(entry);
        }
    }
}
