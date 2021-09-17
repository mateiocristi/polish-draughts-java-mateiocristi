package com.codecool.polishdraughts.utils;

import com.codecool.polishdraughts.views.Display;

public class Terminal implements ITerminal{

    private static final String CONTROL_CODE = "\u001b[";
    private static final String CLEAR_CODE = "2J";
    private Display display;

    public Terminal() {
        this.display = new Display();
    }

    @Override
    public void clearScreen() {
        this.placeCommand(CLEAR_CODE, display);
    }

    @Override
    public void placeCommand(String command, Display display) {
        display.printData(CONTROL_CODE + command);
    }
}
