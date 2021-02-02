package com.company;

public class Letter {

    //Attributes
    public int wordIndex;
    public char character;

    //Constructor
    public Letter(int wordIndexInput, char characterInput)
    {
        this.wordIndex = wordIndexInput;
        this.character = characterInput;
    }

    //Method to return a list of letters from a word
    public static Letter[] wordToLetter(String wordInput, int wordIndexInput)
    {
        int len = wordInput.length();
        Letter[] letters = new Letter[len];
        for (int i = 0; i < len ; i++) {
            letters[i] = new Letter(wordIndexInput, wordInput.charAt(i));
        }

        return letters;
    }
}
