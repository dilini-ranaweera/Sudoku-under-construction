package org.cis120.Sudoku;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import java.util.*;

public class SudokuCellListener implements KeyListener {

    // private fields that represent the JTextField and it's position in the array
    private JTextField cell;
    private int row;
    private int column;
    private Sudoku model;
    private SudokuGrid sg;

    // put in array format so undo button has functionality
    private int[] characteristics = new int[3];

    // linked list for moves
    private LinkedList<int[]> temp = new LinkedList();

    public SudokuCellListener(
            JTextField cell, int row, int column, Sudoku model, SudokuGrid sg,
            LinkedList<int[]> temp
    ) {
        this.cell = cell;
        this.row = row;
        this.column = column;
        this.model = model;
        this.sg = sg;
        this.temp = temp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char num = e.getKeyChar();
        int num2 = (int) num; // askii

        int finalNum = num2 - 48; // askii conversion
        characteristics[0] = row;
        characteristics[1] = column;
        characteristics[2] = finalNum;

        switch (finalNum) {
            case 1:
                model.playTurn(row, column, finalNum);
                sg.updateStatus();
                temp.add(characteristics);
                break;
            case 2:
                model.playTurn(row, column, finalNum);
                sg.updateStatus();
                temp.add(characteristics);
                break;
            case 3:
                model.playTurn(row, column, finalNum);
                sg.updateStatus();
                temp.add(characteristics);
                break;
            case 4:
                model.playTurn(row, column, finalNum);
                sg.updateStatus();
                temp.add(characteristics);
                break;
            case 5:
                model.playTurn(row, column, finalNum);
                sg.updateStatus();
                temp.add(characteristics);
                break;
            case 6:
                model.playTurn(row, column, finalNum);
                sg.updateStatus();
                temp.add(characteristics);
                break;
            case 7:
                model.playTurn(row, column, finalNum);
                sg.updateStatus();
                temp.add(characteristics);
                break;
            case 8:
                model.playTurn(row, column, finalNum);
                sg.updateStatus();
                temp.add(characteristics);
                break;
            case 9:
                model.playTurn(row, column, finalNum);
                sg.updateStatus();
                temp.add(characteristics);
                break;
            case -40:
                JOptionPane.showMessageDialog(null, "Not allowed to click backspace");
                break;
            default:
                JOptionPane.showMessageDialog(null, "Enter a number between 1 and 9");
                cell.setText(" ");

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    // method to return the characteristics of the key typed
    public int[] getCharacteristics() {
        return characteristics;
    }
}
