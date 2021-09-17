package com.codecool.polishdraughts.models;

import com.codecool.polishdraughts.utils.Color;

public interface IBoard {
    void createBoard(int boardSize);
    void removePawn(int row, int col);
    void placePawn(int newRow, int newCol, Color color);
}
