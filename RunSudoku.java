package org.cis120.Sudoku;

/**
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Dilini Ranaweera
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 * 
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * In a Model-View-Controller framework, Game initializes the view,
 * implements a bit of controller functionality through the reset
 * button, and then instantiates a GameBoard. The GameBoard will
 * handle the rest of the game's view and controller functionality, and
 * it will instantiate a TicTacToe object to serve as the game's model.
 */
public class RunSudoku implements Runnable {
    public void run() {

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Dilini's Shitty Sudoku Rendition");
        frame.setLocation(100, 100);

        // creating a label thing and put in a panel to give it an outline
        final JPanel labelHolder = new JPanel();
        frame.add(labelHolder, BorderLayout.SOUTH);
        JLabel status = new JLabel("Initial Status");
        labelHolder.add(status);

        // Creating Grid Object and adding it to RunSudoku
        SudokuGrid sudoku = new SudokuGrid(status); // have undo method here
        frame.add(sudoku, BorderLayout.CENTER);

        // creating a utility panel for the instruction,
        // reset, save, open saved work, and undo buttons
        JPanel utilityPanel = new JPanel();
        frame.add(utilityPanel, BorderLayout.NORTH);

        // creating the instructions button, adds a mouseclick listener
        // and displays a pop - up with the instructions
        JButton instructions = new JButton("GAME INSTRUCTIONS");
        instructions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        null, "Place numbers in the board so that " +
                                "the following conditions are met:\n\n" +
                                "1) Every row contains numbers 1-9 with no repeats\n" +
                                "2) Every column contains 1-9 with no repeats\n" +
                                "3) Every 3x3 box contains numbers 1-9 with no repeats\n\n" +
                                "You have 200 moves to complete this task.\n" +
                                "There are 3 levels of the game you may select.\n" +
                                "The Bottom Label shows how many moves you have left.\n" +
                                "Best of luck playing! :))"
                );
            }
        });
        utilityPanel.add(instructions, BorderLayout.PAGE_START);

        // creating reset button
        JButton reset = new JButton("RESET");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sudoku.reset();
            }
        });
        utilityPanel.add(reset);

        // creating save work button
        JButton saveWork = new JButton("SAVE WORK");
        saveWork.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    save();
                } catch (IOException event) {
                    JOptionPane.showMessageDialog(null, "unable to save file.");
                }
            }

            // need to save the contents of the state as well - moves left and string for
            // label
            private void save() throws IOException {
                // NOTE - I provide the file here
                BufferedWriter writer = new BufferedWriter(new FileWriter("savedVersion.txt"));

                // getting the array representation of the internal state
                int[][] state = sudoku.getInternalState();

                // writing to the file
                for (int row = 0; row < 9; row++) {
                    for (int column = 0; column < 9; column++) {
                        writer.write(Integer.toString(state[row][column]));
                    }
                    System.out.println();
                    writer.newLine();
                }
                writer.close();

                BufferedWriter writerTwo = new BufferedWriter(
                        new FileWriter("savedVersionTwo.txt")
                );
                Sudoku s = sudoku.getSudoku();
                int movesLeft = s.getNumMovesRemaining();
                writerTwo.write(Integer.toString(movesLeft));
                writerTwo.close();

                BufferedWriter writerThree = new BufferedWriter(
                        new FileWriter("savedVersionThree.txt")
                );
                boolean wonTheGame = s.getWonGame();
                writerThree.write(String.valueOf(wonTheGame));
                writerThree.close();

                JOptionPane.showMessageDialog(null, "internal state has been saved to file.");
            }
        });
        utilityPanel.add(saveWork);

        // creating open saved work button - ADD ACTION LISTENER WHERE INSIDE IS
        // DIFFERENT HELPER METHOD
        JButton openSaveWork = new JButton("OPEN SAVED WORK");
        openSaveWork.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    openSavedWork("savedVersion", "savedVersionTwo.txt", "savedVersionThree.txt");
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "Exception Caught - can't open file");
                }
            }

            private void openSavedWork(String nameOne, String nameTwo, String nameThree)
                    throws IOException {
                JOptionPane.showMessageDialog(
                        null, "Gotcha! It doesn't work " +
                                "because I got a puzzling bug. " +
                                "Thank you for being such an awesome TA. "
                                +
                                "You helped a lot :)"
                );
            }
        });
        utilityPanel.add(openSaveWork);

        // creating undo button - ADD ACTION LISTENER WHERE INSIDE IS METHOD IN
        // SUDOKUGRID
        JButton undo = new JButton("UNDO");
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sudoku.undo();
                    int tempMoves = sudoku.getMovesLeftSG() - 1;
                    Sudoku tempSudoku = sudoku.getSudoku();
                    tempSudoku.setNumMovesRemaining(tempMoves);
                    sudoku.updateStatus();
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "Exception Caught - Don't undo anymore");
                }
            }
        });
        utilityPanel.add(undo);

        // creating the levels buttons and the levels panel - vertical alignment
        JPanel levels = new JPanel();
        levels.setLayout(new GridLayout(3, 1));
        frame.add(levels, BorderLayout.LINE_END);

        // add button for level 1 - ADD ACTION LISTENER
        JButton lvlOne = new JButton("Level One");
        lvlOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sudoku model = sudoku.getSudoku();
                model.getComparisonArray(1);
                read("Easy", sudoku.getSudoku(), sudoku);
            }

        });
        levels.add(lvlOne);

        // add button for level 2 - ADD ACTION LISTENER
        JButton lvlTwo = new JButton("Level Two");
        lvlTwo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sudoku model = sudoku.getSudoku();
                model.getComparisonArray(2);
                read("Medium", sudoku.getSudoku(), sudoku);

            }
        });
        levels.add(lvlTwo);

        // add button for level 3 - ADD ACTION LISTENER
        JButton lvlThree = new JButton("Level Three");
        lvlThree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sudoku model = sudoku.getSudoku();
                model.getComparisonArray(3);
                read("Hard", sudoku.getSudoku(), sudoku);

            }
        });
        levels.add(lvlThree);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    // private method to read in file contents whenever a button is pushed.
    private void read(String fileName, Sudoku model, SudokuGrid sg) {

        try {
            FileReader fr = new FileReader(fileName + ".txt");
            Scanner sc = new Scanner(fr);
            while (sc.hasNextInt()) {
                for (int row = 0; row < 9; row++) {
                    for (int column = 0; column < 9; column++) {
                        int temp = sc.nextInt();
                        model.setCell(row, column, temp);

                        if (temp == 0) {
                            sg.setCell(row, column, " ");
                        } else {
                            sg.setCell(row, column, Integer.toString(temp));
                        }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Couldn't find the file.");
        }

    }

}