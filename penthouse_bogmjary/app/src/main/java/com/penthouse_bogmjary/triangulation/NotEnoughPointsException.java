package com.penthouse_bogmjary.triangulation;

public class NotEnoughPointsException extends Exception {
    private static final long serialVersionUID = 7061712854155625067L;

    public NotEnoughPointsException() {
    }

    public NotEnoughPointsException(String str) {
        super(str);
    }
}