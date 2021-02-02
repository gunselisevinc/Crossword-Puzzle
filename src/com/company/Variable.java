package com.company;

public class Variable {

    //Attributes
    public int indexValue;
    public int coordinateX;
    public int coordinateY;
    public int direction; //1 if across, -1 if downward
    public int length;
    public String[] domain;

    //Constructor
    public Variable(int indexInput, int xCoor, int yCoor, int lengthInput, String[] wordListInput)
    {
        this.coordinateX = xCoor;
        this.coordinateY = yCoor;
        this.indexValue = indexInput;
        if (indexInput < 0)
        {
            this.direction = -1;
        }
        else if(indexInput > 0)
        {
            this.direction = 1;
        }
        this.length = lengthInput;

        this.domain = new String[wordListInput.length];
        int x = 0;

        for (int i = 0; i < wordListInput.length; i++) {
            if(this.length == wordListInput[i].length())
            {
                this.domain[x] = wordListInput[i];
                x++;
            }
        }
    }
}
