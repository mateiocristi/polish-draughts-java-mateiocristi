package com.codecool.polishdraughts.views;

public class Display implements IDisplay {


    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void printData(String data) {
        System.out.println(data);
    }

    @Override
    public void printSameLineMsg(String msg) {
        System.out.print(msg);
    }

    @Override
    public void printMessWithVar(String msg, String data) {
        System.out.printf(msg, data);
    }
}
