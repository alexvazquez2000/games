package org.alex.games.bj.beans;

public enum SUITS {
	//they must be in this order to match the image on games/src/main/resources/English_pattern_playing_cards_deck.png
	SPADES("♠"), HEARTS("♥"), DIAMONDS("♦"), CLUBS("♣");

	private String value;

	private SUITS(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
