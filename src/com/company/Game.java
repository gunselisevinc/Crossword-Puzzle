package com.company;

public class Game {

    //Attributes
    private int[][] board; //2D array for board
    private int totalIndex; //number of available sequences on the board
    private Letter[][] currentWords; //current words placed on the board
    private String[] wordList; //words to be placed on board
    private boolean[] wordsUsed; //keeps track of the word is on the board or not
    private Variable[] variableList;//all variables on the board(indexes)

    //Constructor
    public Game(int[][] boardInput, String[] wordListInput, int totalIndexInput)
    {
        //Error Detection
        if(boardInput == null)
        {
            System.out.println("Invalid Board");
        }
        if(wordListInput == null)
        {
            System.out.println("Invalid List of Words");
        }

        int wListLen = wordListInput.length;
        this.wordList = new String[wListLen];
        System.arraycopy(wordListInput,0,this.wordList,0,wListLen);

        this.wordsUsed = new boolean[wListLen];

        int numOfColumns = boardInput[0].length;
        int numOfRows = boardInput.length;
        this.board = new int[numOfRows][numOfColumns];
        for (int i = 0; i < numOfRows ; i++) {
            for (int j = 0; j < boardInput[i].length ; j++) {
                this.board[i][j] = boardInput[i][j];
            }
        }

        this.currentWords = new Letter[numOfRows][numOfColumns];

        this.totalIndex = totalIndexInput;

        int currentIndex = 0;
        this.variableList = new Variable[totalIndexInput];

        int boundary = totalIndexInput * (-1);

        for (int i = 0; i < boardInput.length; i++) {
            for (int j = 0; j < boardInput[i].length; j++) {
                if (boardInput[i][j] > boundary && boardInput[i][j] < totalIndexInput+1)
                {
                    int indexInput = boardInput[i][j];
                    int xCoor = i;
                    int yCoor = j;
                    int length = getIndexLength(Math.abs(boardInput[i][j]));

                    variableList[currentIndex] = new Variable(indexInput, xCoor, yCoor, length, wordListInput);
                    currentIndex++;
                }
            }
        }
    }

    //keywords to define cell availability on the board
    public static final int BLOCKED = -500;
    public static final int BLANK = -600;

    //MRV heuristic that returns an index
    public int mrv (int indexInput)
    {
        int minimum = variableList[indexInput-1].domain.length;
        Variable minVar = variableList[indexInput-1];
        for (int i = 0; i < variableList.length; i++) {
            if(variableList[i].domain.length < minimum)
            {
                minimum = variableList[i].domain.length;
                minVar = variableList[i];
            }
        }
        return minVar.indexValue;
    }

    //calls the backtracking algorithm with the first word
    public void showResult()
    {
        backtrack(mrv(1));
    }

    //Backtrack is to find proper solution to he current game
    public void backtrack(int indexNum)
    {
        if(indexNum > totalIndex) //no more blanks on the board
        {
            printResult();
            return;
        }
        for (int wordIndex = 0; wordIndex < wordList.length; wordIndex++)
        {
            if(wordsUsed[wordIndex]) //don't check used words again
            {
                continue;
            }
            if (nodeConsistent(wordIndex, indexNum))
            {
                insertWord(wordIndex, indexNum);
                backtrack(indexNum + 1);
                deleteWord(wordIndex, indexNum);
            }
        }
    }

    //displays the result
    private void printResult()
    {
        System.out.print(this);
    }

    //checks node consistency
    private boolean nodeConsistent(int wordIndexInput, int indexInput)
    {
        int indexLen = getIndexLength(indexInput);

        return wordList[wordIndexInput].length() == indexLen && overlapIsValid(wordIndexInput, indexInput) ;
    }

    //finds the length of the current index on the board
    public int getIndexLength(int indexInput)
    {
        int[] indexCoordinates = getIndexCoordinates(indexInput);

        int row = indexCoordinates[0];
        int column = indexCoordinates[1];

        boolean isAcross = board[row][column] > 0;
        boolean isDownwards = board[row][column] < 0;

        int len = -1;

        if(isAcross)
        {
            for (len = 0; len < board[0].length - column; len++)
            {
                if (board[row][column + len] == BLOCKED)
                {
                    break;
                }
            }
        }
        else if(isDownwards)
        {
            for (len = 0; len < board.length - row; len++)
            {
                if (board[row + len][column] == BLOCKED)
                {
                    break;
                }
            }
        }

        return len;
    }

    //returns the coordinate of the current index
    public int[] getIndexCoordinates(int indexInput)
    {
        for (int i = 0; i < board.length ; i++) {
            for (int j = 0; j < board[i].length; j++) {
               if (board[i][j] != BLANK && board[i][j] != BLOCKED && Math.abs(board[i][j]) == indexInput)
               {
                   int[] coord = new int[2];
                   coord[0] = i;
                   coord[1] = j;
                   return coord;
               }
            }
        }
        return null;
    }

    //checks if the current word can be placed to current index with a valid overlap
    private boolean overlapIsValid(int wordIndexInput, int indexInput)
    {
        int[] indexCoordinates = getIndexCoordinates(indexInput);

        int row = indexCoordinates[0];
        int column = indexCoordinates[1];

        int indexLen = getIndexLength(indexInput);

        boolean isAcross = board[row][column] > 0;
        boolean isDownwards = board[row][column] < 0;

        String currentWord = wordList[wordIndexInput];

        for (int i = 0; i < indexLen; i++) {
            if (currentWords[row][column] != null && currentWords[row][column].character != currentWord.charAt(i))
            {
                return false;
            }
            if(isAcross)
            {
                column++;
            }
            else if (isDownwards)
            {
                row++;
            }
        }
        return true;
    }

    // insert the word given as word index to the board index given
    private void insertWord(int wordIndexInput, int indexInput)
    {
        int[] indexCoordinates = getIndexCoordinates(indexInput);

        int row = indexCoordinates[0];
        int column = indexCoordinates[1];

        int indexLen = getIndexLength(indexInput);

        boolean isAcross = board[row][column] > 0;
        boolean isDownwards = board[row][column] < 0;

        Letter[] letterList = Letter.wordToLetter(wordList[wordIndexInput], wordIndexInput);

        for (int i = 0; i < indexLen; i++) {
            if (currentWords[row][column] == null)
            {
                currentWords[row][column] = letterList[i];
            }
            if (isAcross)
            {
                column++;
            }
            else if (isDownwards)
            {
                row++;
            }
        }
        wordsUsed[wordIndexInput] = true;
    }

    //delete the word from the board with word index and board index
    private void deleteWord(int wordIndexInput, int indexInput)
    {
        int[] indexCoordinates = getIndexCoordinates(indexInput);

        int row = indexCoordinates[0];
        int column = indexCoordinates[1];

        int indexLen = getIndexLength(indexInput);

        boolean isAcross = board[row][column] > 0;
        boolean isDownwards = board[row][column] < 0;

        for (int i = 0; i < indexLen; i++) {
            if (currentWords[row][column] != null && currentWords[row][column].wordIndex == wordIndexInput)
            {
                currentWords[row][column] = null;
            }
            if (isAcross)
            {
                column++;
            }
            else if (isDownwards)
            {
                row++;
            }
            wordsUsed[wordIndexInput] = false;
        }
    }

    public String toString() {
        String output = "";

        for (int i = 0; i < board.length; i++) {

            //divide lines
            output += "\n";
            //show blocked cells
            for (int j = 0; j < board[i].length; j++) {
                char ch = ' ';
                if (board[i][j] == BLOCKED) {
                    ch = '•';
                } else {
                    if (currentWords[i][j] == null) //blank
                    {
                        ch = ' ';
                    }
                    else //letter
                    {
                        ch = currentWords[i][j].character;
                        ch = Character.toLowerCase(ch);
                    }
                }
                if (j == board[i].length - 1) {
                    output += " " + ch;
                } else {
                    output += " " + ch + " |";
                }
            }
        }
        output += "\n";
        return output;
    }
}
