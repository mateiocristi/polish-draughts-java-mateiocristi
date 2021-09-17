package com.codecool.polishdraughts.models;

import com.codecool.polishdraughts.utils.Color;
import com.codecool.polishdraughts.utils.Terminal;

public class Board implements IBoard {

    private Pawn[][] fields;
    private Terminal term;
    private int boardSize;
    private final String ANSI_YELLOW = "\u001B[33m";
    private final String ANSI_BLUE = "\u001B[34m";
    private final String ANSI_RESET = "\u001B[0m";

    public Board() {
        term = new Terminal();
    }

    public Pawn[][] getFields() {
        return fields;
    }

    public int getBoardSize() {
        return boardSize;
    }

    @Override
    public void createBoard(int boardSize) {
        this.boardSize = boardSize;
        fields = new Pawn[boardSize][boardSize];
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (row == 0 || row == 2) {   // white pawns
                    if (col % 2 != 0) {
                        fields[row][col] = new Pawn(row, col, Color.WHITE);
                    } else {
                        fields[row][col] = null;
                    }
//                } else if (row == 1 || row == 3) {
//                    if (col % 2 == 0) {
//                        fields[row][col] = new Pawn(row, col, Color.WHITE);
//                    } else {
//                        fields[row][col] = null;
//                    }
//                } else if (row == boardSize - 1 || row == boardSize - 3) {   // black pawns
//                    if (col % 2 == 0) {
//                        fields[row][col] = new Pawn(row, col, Color.BLACK);
//                    } else {
//                        fields[row][col] = null;
//                    }
                } else if (row == boardSize - 2 || row == boardSize - 4) {
                    if (col % 2 != 0) {
                        fields[row][col] = new Pawn(row, col, Color.BLACK);
                    } else {
                        fields[row][col] = null;
                    }
                } else {
                    fields[row][col] = null;
                }
            }
        }
    }

    @Override
    public void removePawn(int row, int col) {
        fields[row][col] = null;
    }

    @Override
    public void placePawn(int newRow, int newCol, Color color) {
        fields[newRow][newCol] = new Pawn(newRow, newCol, color);
    }

    @Override
    public String toString() {
        term.clearScreen();
        StringBuilder sb = new StringBuilder();
        for (int col = 0; col < boardSize; col++) {
            String numCoords = String.format("%3d", col + 1);
            sb.append(numCoords);
        }
        for (int row = 0; row < boardSize; row++) {
            String letterCoords = String.format("\n%c", 'A' + row);
            sb.append(letterCoords);
            for (int col = 0; col < boardSize; col++) {
                if (fields[row][col] != null) {
                    if (fields[row][col].isWhite()) {
                        sb.append(ANSI_YELLOW + " @ " + ANSI_RESET);
                    } else {
                        sb.append(ANSI_BLUE + " @ " + ANSI_RESET);
                    }
                } else {
                    sb.append(" . ");
                }
            }
        }

        return sb.toString();
    }
}
