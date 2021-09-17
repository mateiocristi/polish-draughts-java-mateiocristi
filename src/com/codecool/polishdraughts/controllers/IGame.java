package com.codecool.polishdraughts.controllers;

import com.codecool.polishdraughts.utils.Color;
import com.codecool.polishdraughts.utils.Coordinates;

public interface IGame {
    void start();
    void playRound();
    Coordinates getPlayerStartPosition(Color player);
    Coordinates getPlayerMove(Color player);
    boolean quitGame(String userInput);
    boolean isMoveLegal(Coordinates startPos, Coordinates endPos, Color player);
    boolean isWin(Color player);
}
