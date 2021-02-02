# Crossword-Puzzle

## Constraint Satisfaction Problem

### New Crossword Board

Board and Word List is created in main class. An example of board and word list is already implemented but in case of changing them, creating board have some specifications

* board is a two dimensional array
* blocked cells should be indicated as "BLOCKED" and available cells as "BLANK"
* word indexes on the board should be given in their first cells
* word indexes should be positive if across and negative if downward.

After creating the board and word list, program will automatically calculate the number of word spaces on the board and a new crossword puzzle should be created and sent to the solution method. Output will be shown directly on the command line.

## Demo

An example crossword puzzle is already implemented in the main regarding the example given in assignment manuel. If not changed, it should work properly.

Compiling and running the code with these criteria will give the required result.

## Implementation Detail

Project consists of three classes for the implementation, one for letter, one for variables and one for crossword puzzle. Letter class is to take a word and its index and return an array of letters of that word. And the main program is held on the game class where variables class is for the indexes.

The two dimensional board is built in main by the user with some specific representations. Across word indexes are positive and downward word indexes are negative and blocked cells are written as BLOCKED and the available cells are written as BLANK. These two keywords are originally integers and eases the job for comparison and they are more user-friendly. 

The game class has a board,  number of available word sequences on the board, a two dimensional array of words with their each letters, list of words, a list of variables and a boolean list of words to check if they are used or not. 

Once a crossword puzzle is created, it is sent to the backtracking algorithm. And the backtracking algorithm simply works as keeping track of the starting index, length of it and direction. If it fits with the constraints then the backtracking search algorithm makes one more decision with the new variable until it gets stuck with no solution. In that case the search algorithm turns back to the last point it made a decision and changes it and goes on like this. 

At last, if the backtracking search algorithm has ended up with a successful solution, the program displays the arrangement of the words on command line.

Minimum Remaining Values (MRV) is used as a heuristic which chooses the variable with the fewest legal values. In variables class, all the indexes are stored with their domains according to their lengths.
