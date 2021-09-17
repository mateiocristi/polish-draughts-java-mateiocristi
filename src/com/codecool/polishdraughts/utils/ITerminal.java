package com.codecool.polishdraughts.utils;

import com.codecool.polishdraughts.views.Display;

public interface ITerminal {
    void clearScreen();
    void placeCommand(String command, Display display);
}
