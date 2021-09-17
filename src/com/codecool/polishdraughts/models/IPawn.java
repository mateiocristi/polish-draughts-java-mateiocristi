package com.codecool.polishdraughts.models;

import com.codecool.polishdraughts.utils.Color;
import com.codecool.polishdraughts.utils.Coordinates;

public interface IPawn {
    boolean isWhite();
    boolean isBlack();
    boolean isMoveOnBoard(int boardSize);
}
