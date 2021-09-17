package com.codecool.polishdraughts.models;

import com.codecool.polishdraughts.utils.Color;
import com.codecool.polishdraughts.utils.Coordinates;

public class Pawn implements IPawn {

    private Coordinates position;
    private Color color;

    public Pawn (int rowPos, int colPos, Color color) {
        this.position = new Coordinates(rowPos, colPos);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean isWhite() {
        return color.equals(Color.WHITE);
    }

    @Override
    public boolean isBlack() {
        return color.equals(Color.BLACK);
    }

    @Override
    public boolean isMoveOnBoard(int boardSize) {
        int rowPos = position.getRow();
        int colPos = position.getCol();
        return rowPos < 0 || rowPos >= boardSize || colPos < 0 || colPos >= boardSize;
    }

}
