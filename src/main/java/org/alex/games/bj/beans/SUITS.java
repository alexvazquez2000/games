package org.alex.games.bj.beans;

public enum SUITS {
	CLUBS("♣"), DIAMONDS("♦"), HEARTS("♥"), SPADES("♠");

    private String value;

    private SUITS(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SUITS[] suits = new SUITS[]{
        CLUBS, DIAMONDS, HEARTS, SPADES
    };
}
