package com.bharatarmy.Appguide;

public enum ShowCaseContentPosition {
    UNDEFINED (0),
    TOP (1),
    RIGHT (2),
    BOTTOM (3),
    LEFT (4);

    private final int position;

    ShowCaseContentPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
