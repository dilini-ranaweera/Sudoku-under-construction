=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: 19811649
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

*Return copies!!!*

  1. 2D Array. This is the model for my internal game state. I created a class called Sudoku that kept track of the internal game state, and a field of that class was
   a 2D array. There's get and set methods for each cell, as well as a play turn method to keep track of how many moves the user has left before they lose. I was
   originally going to implement some inheritance by having the base of my internal state be an 3x3 array, and my overall class be an array of those 3x3 array
   objects, but my TA said that it was unnecessary to do that, so I didn't.

  2. Collections. I used a LinkedList of int[]s to keep track of the moves the user made on the board. the ints contained the position in the JTextField array
     the user put the number in (row and column), as well as the number. I made an undo button that utilized this collection to continuously delete the last 
     item from the LinkedList. My TA gave me the feedback that I should be more specific.

  3. JUnit Testable Component. I tested my Sudoku Class that modelled the internal state. In keeping with MVC design pattern, this class was kept completely separate
     from my SudokuGrid class, which was the class that displayed the numbers to the user. To keep this class completely separate, I created a lot of getter and setter
     methods for the various pieces of the Sudoku Class, such as the number of each cell, each cell position, the value at a cell position, as well as the number 
     of moves left and whether or not the user had won the game. All of these methods were tested independent from the Sudoku class's instance in the SudokuGrid
     object fields.

  4. File I/O. I originally had a saved file version for this, but I created levels for my game because I was having a weird issue trying to read in from the files. 
     When the user clicks a button that represents level difficulty, the internal state and grid are updated to show hints for those specific levels. This is done
     independent of the number of moves variable, but all parts of the state are updated. I kept the button for saving my files because that works properly, but the 
     button that is supposed to open the saved work displays a message instead.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

1) Sudoku: Internal state of the game through a 2D Array. Keeps track of the numbers in a 2D array, and whether or not a user has won. Uses getter and setter methods 
   for the instance variables

2) SudokuGrid: The JPanel that has the grids that the user types numbers into. Uses an instance of the Sudoku class and updates the state when the user types something
   in through a KeyListener. Updates status, and has a bunch of getter and setter methods for each instance variable in the class. Has a linkedlist that keeps track of 
   all the moves being made. The stuff inside the linkedlist is being added by the KeyListener class. also has a reset method that resets the board and the internal 
   state.

3)SudokuCellKeyListener: This is the keylistener that updates the state. adds the numbers 1-9 to the board, updates the state using Sudoku's playTurn() method, and
  updates the JLabel in the SudokuGrid object as well. Attemps to not allow the user to add letters to the thing, and attempts to not have the user 
  type in characters.

4)RunSudoku: The runnable object that creates the game. Includes panels for buttons, including a utility panel for reset, undo, and the crappy save/open buttons. Also
  includes panel for levels and a SudokuGrid Object.

5)Game: Runs the Game

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

The JTextFields were pretty hard to work with, and
I still haven't figured out how to prevent certain types of input. I added a listener for each key, 
and that made it difficult to update the WHOLE state when the keys were clicked.
Also all the File I/O was pretty difficult. I struggled on Undo for a while as well becasue I wasn't sure how to access each position of the JTextField that had
been clicked because the position was stored in the cell listener, not the SudokuGrid class.



- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

There was good seperation of functionality because of the separation between the internal state and the SudokuGrid JPanel, but I should not have used JTextFields.
They were very difficult to validate and work with. The private state was completely encapsulated and accessed through getter and setter methods. However, if
I had a 2nd chance with this game, I would use Buttons instead of JTextFields.


========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

I didn't follow any tutorials for this game. I didn't use any images either. I only used Javadocs for the JTextFields.


IMPORTANT NOTE - I PROVIDED THE FILES FOR THE USER TO SAVE TO. THEY ARE ATTACHED WITH THIS UPDATED.
