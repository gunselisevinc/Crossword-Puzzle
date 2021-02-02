package com.company;

import static com.company.Game.BLANK;
import static com.company.Game.BLOCKED;

public class Main {

    public static void main(String[] args) {

        int[][] board = { { 1,     BLANK, -2,    BLANK, -3    },
                { BLOCKED, BLOCKED, BLANK, BLOCKED, BLANK },
                { BLOCKED, 4,     BLANK, -5,    BLANK },
                { -6,    BLOCKED, 7,     BLANK, BLANK },
                { 8,     BLANK, BLANK, BLANK, BLANK },
                {BLANK, BLOCKED, BLOCKED, BLANK, BLOCKED } };

        //finding the possible number of words to be placed on board
        int max = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == BLANK || board[i][j] == BLOCKED)
                {
                    continue;
                }
                if (Math.abs(board[i][j]) > max)
                {
                    max = Math.abs(board[i][j]);
                }
            }
        }

        int possibleWordSequences = max;

        String[] wordList = {"aft", "ale", "eel", "heel", "hike", "hoses", "keel", "knot", "laser", "lee", "line", "sails", "sheet", "steer", "tie"};

        Game crossword = new Game(board, wordList, possibleWordSequences);

        crossword.showResult();
    }
}
