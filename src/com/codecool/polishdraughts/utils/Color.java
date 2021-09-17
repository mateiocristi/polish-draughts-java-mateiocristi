package com.codecool.polishdraughts.utils;

public enum Color {
    WHITE("white", 0),
    BLACK("black", 1);

    private String name;
    private int number;

    private Color(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

}
