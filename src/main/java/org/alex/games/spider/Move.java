package org.alex.games.spider;

import org.alex.games.bj.beans.Card;

public class Move {

	private int fromCol;
	private int toCol;
	private Card card;
	private int weight;
	
	/**
	 * @param fromCol
	 * @param toCol
	 * @param card
	 * @param weight
	 */
	public Move(int fromCol, int toCol, Card card, int weight) {
		this.fromCol = fromCol;
		this.toCol = toCol;
		this.card = card;
		this.weight = weight;
	}

	/**
	 * @return the fromCol
	 */
	public int getFromCol() {
		return fromCol;
	}

	/**
	 * @param fromCol the fromCol to set
	 */
	public void setFromCol(int fromCol) {
		this.fromCol = fromCol;
	}

	/**
	 * @return the toCol
	 */
	public int getToCol() {
		return toCol;
	}

	/**
	 * @param toCol the toCol to set
	 */
	public void setToCol(int toCol) {
		this.toCol = toCol;
	}

	/**
	 * @return the card
	 */
	public Card getCard() {
		return card;
	}

	/**
	 * @param card the card to set
	 */
	public void setCard(Card card) {
		this.card = card;
	}

	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
}
