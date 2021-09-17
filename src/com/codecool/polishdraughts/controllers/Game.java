package com.codecool.polishdraughts.controllers;

import com.codecool.polishdraughts.models.Board;
import com.codecool.polishdraughts.models.Pawn;
import com.codecool.polishdraughts.utils.Color;
import com.codecool.polishdraughts.utils.Coordinates;
import com.codecool.polishdraughts.views.Display;

import java.util.Scanner;

public class Game implements IGame {

    private Scanner scanner;
    private Display display;
    private Board board;
    private boolean gameOver;
    private Color player;

    public Game() {
        this.display = new Display();
        this.player = Color.WHITE;
        scanner = new Scanner(System.in);
        board = new Board();
        gameOver = false;
    }

    public void start() {
        display.printMessage("Welcome!");
        boolean wrongUserInput = true;
        int roundCounter = 0;
        while (wrongUserInput) {
            display.printMessage("Choose board size (10 - 20) ");
            display.printSameLineMsg("Player choice: ");
            String userInput = scanner.nextLine();
            if (quitGame(userInput)) {
                break;
            }
            int userInt = Integer.parseInt(userInput);
            if (userInt >= 5 && userInt <= 20) {////////////////////////////////
                wrongUserInput = false;
                board.createBoard(userInt);
            } else {
                display.printMessage("Wrong input!");
            }
        }
        while (!gameOver) {
            // winning condition
            if (isWin(player)) {
                if(player.equals(Color.WHITE)) {
                    player = Color.BLACK;
                } else {
                    player = Color.WHITE;
                }
                display.printMessWithVar("Player %s won!", player.getName());
                break;
            }
            if (roundCounter % 2 == 0) {
                player = Color.WHITE;
            } else {
                player = Color.BLACK;
            }
            display.printData(board.toString());
            playRound();
            roundCounter++;
        }

    }

    @Override
    public void playRound() {
        boolean isMove = false;
        while (!isMove) {
            Coordinates startPos = getPlayerStartPosition(player);
            Coordinates endPos = getPlayerMove(player);
            isMove = isMoveLegal(startPos, endPos, player);
            if (!isMove) {
                display.printMessage("Please enter a valid move");
            }
        }



    }

    @Override
    public Coordinates getPlayerStartPosition(Color player) {
        int rowPos = -1;
        int colPos = -1;
        int boardSize = board.getBoardSize();
        Pawn[][] fields = board.getFields();
        boolean wrongInput = true;
        while (wrongInput) {
            display.printMessWithVar("player %s start position: ", player.getName());
            String userInput = scanner.nextLine().toUpperCase();
            if (quitGame(userInput)) {
                break;
            }
            wrongInput = false;
            // get valid row
            try {
                char rowChar = userInput.charAt(0);
                if (rowChar >= 'A' && rowChar <= 'A' + (boardSize - 1)) {
                    rowPos = rowChar - 'A';
                } else {
                    wrongInput = true;
                }
            } catch (Exception e) {
                wrongInput = true;
            }
            // get valid col
            if (!wrongInput) {
                try {
                    colPos = Integer.parseInt(userInput.substring(1)) - 1;
                } catch (Exception e) {
                    wrongInput = true;
                }
            }

            // check if move on board
            if (!wrongInput) {
                Pawn pawn = new Pawn(rowPos, colPos, player);
                if (pawn.isMoveOnBoard(boardSize)) {
                    wrongInput = true;
                }
                // check if player pawn is selected
                if (fields[rowPos][colPos] == null) {
                    wrongInput = true;
                } else {
                    Pawn currentPawn = fields[rowPos][colPos];
                    if (player.equals(Color.WHITE) && !currentPawn.isWhite()) {
                        wrongInput = true;
                    }
                    if (player.equals(Color.BLACK) && currentPawn.isWhite()) {
                        wrongInput = true;
                    }
                }
            }

            if (wrongInput) {
                display.printMessage("Wrong coordinates! Try again!");
            }
        }
        return new Coordinates(rowPos, colPos);
    }

    @Override
    public Coordinates getPlayerMove(Color player) {
        int rowPos = -1;
        int colPos = -1;
        int boardSize = board.getBoardSize();
        Pawn[][] fields = board.getFields();
        boolean wrongInput = true;
        while (wrongInput) {
            display.printMessWithVar("player %s end position: ", player.getName());
            String userInput = scanner.nextLine().toUpperCase();
            if (quitGame(userInput)) {
                break;
            }
            wrongInput = false;
            // get valid row
            try {
                char rowChar = userInput.charAt(0);
                if (rowChar >= 'A' && rowChar <= 'A' + (boardSize - 1)) {
                    rowPos = rowChar - 'A';
                } else {
                    wrongInput = true;
                }
            } catch (Exception e) {
                wrongInput = true;
            }
            // get valid col
            if (!wrongInput) {
                try {
                    colPos = Integer.parseInt(userInput.substring(1)) - 1;
                } catch (Exception e) {
                    wrongInput = true;
                }
            }

            // check if move on board
            if (!wrongInput) {
                Pawn pawn = new Pawn(rowPos, colPos, player);
                if (pawn.isMoveOnBoard(boardSize)) {
                    wrongInput = true;
                }
                // check is pos is free
                if (!wrongInput && fields[rowPos][colPos] != null) {
                    wrongInput = true;
                }
            }
            if (wrongInput) {
                display.printMessage("Wrong coordinates! Try again!");
            }

        }
        return new Coordinates(rowPos, colPos);
    }


    @Override
    public boolean quitGame(String userInput) {
        if (userInput.equals("Q")) {
            gameOver = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean isMoveLegal(Coordinates startPos, Coordinates endPos, Color player) {
        int direction;
        if (player.getName().equals("white")) {
            direction = 1;
        } else {
            direction = -1;
        }
        int oldRow = startPos.getRow();
        int oldCol = startPos.getCol();
        int newRow = endPos.getRow();
        int newCol = endPos.getCol();
        System.out.println(oldRow + " " + oldCol);
        System.out.println(newRow + " " + newCol);
        Pawn[][] fields = board.getFields();
        // normal move
        if (newRow == oldRow + direction && newCol == oldCol + 1 || newRow == oldRow + direction && newCol == oldCol - 1) {
            board.removePawn(oldRow, oldCol);
            board.placePawn(newRow, newCol, player);
            return true;
        }
        // take enemy pawn SW
        if (newRow == oldRow + 2 && newCol == oldCol - 2 && fields[oldRow + 1][oldCol - 1] != null && !fields[oldRow + 1][oldCol - 1].getColor().getName().equals(player.getName())) {
            board.removePawn(oldRow, oldCol);
            board.removePawn(oldRow + 1, oldCol - 1);
            board.placePawn(newRow, newCol, player);
            return true;
        }
        // take enemy pawn SE
        if (newRow == oldRow + 2 && newCol == oldCol + 2 && fields[oldRow + 1][oldCol + 1] != null && !fields[oldRow + 1][oldCol + 1].getColor().getName().equals(player.getName())) {
            board.removePawn(oldRow, oldCol);
            board.removePawn(oldRow + 1, oldCol + 1);
            board.placePawn(newRow, newCol, player);
            return true;
        }
        // take enemy pawn NW
        if (newRow == oldRow - 2 && newCol == oldCol - 2 && fields[oldRow - 1][oldCol - 1] != null && !fields[oldRow - 1][oldCol - 1].getColor().getName().equals(player.getName())) {
            board.removePawn(oldRow, oldCol);
            board.removePawn(oldRow - 1, oldCol - 1);
            board.placePawn(newRow, newCol, player);
            return true;
        }
        // take enemy pawn NE
        if (newRow == oldRow - 2 && newCol == oldCol + 2 && fields[oldRow - 1][oldCol + 1] != null && !fields[oldRow - 1][oldCol + 1].getColor().getName().equals(player.getName())) {
            board.removePawn(oldRow, oldCol);
            board.removePawn(oldRow - 1, oldCol + 1);
            board.placePawn(newRow, newCol, player);
            return true;
        }
        return false;
    }

    @Override
    public boolean isWin(Color player) {
        int direction;
        if (player.getName().equals("white")) {
            direction = 1;
        } else {
            direction = -1;
        }
        System.out.println("board size " + board.getBoardSize());
        Pawn[][] fields = board.getFields();
        for (int i = 0; i < board.getBoardSize(); i++) {
            for (int j = 0; j < board.getBoardSize(); j++) {
                if (fields[i][j] != null && fields[i][j].getColor().getName().equals(player.getName())) {
                    if (fields[i][j].getColor().getName().equals(player.getName())) {
                        // check simple moves
                        try {
                            if (fields[i + direction][j + 1] == null || fields[i + direction][j - 1] == null) {
                                return false;
                            }
                        } catch (Exception ignored) {

                        }
                        // check if can take NW pawn
                        try {
                            if (fields[i - 2][j - 2] == null && fields[i - 1][j - 1] != null && !fields[i - 1][j - 1].getColor().getName().equals(player.getName())) {
                                return false;
                            }
                        } catch (Exception ignored) {

                        }
                        // check if can take NE pawn
                        try {
                            if (fields[i - 2][j + 2] == null && fields[i - 1][j + 1] != null && !fields[i - 1][j + 1].getColor().getName().equals(player.getName())) {
                                return false;
                            }
                        } catch (Exception ignored) {

                        }
                        // check if can take SE pawn
                        try {
                            if (fields[i + 2][j - 2] == null && fields[i + 1][j - 1] != null && !fields[i + 1][j - 1].getColor().getName().equals(player.getName())) {
                                return false;
                            }
                        } catch (Exception ignored) {

                        }
                        // check if can take SW pawn
                        try {
                            if (fields[i + 2][j + 2] == null && fields[i + 1][j + 1] != null && !fields[i + 1][j + 1].getColor().getName().equals(player.getName())) {
                                return false;
                            }
                        } catch (Exception ignored) {

                        }

                    }
                }
            }

        }
        return true;
    }


}
