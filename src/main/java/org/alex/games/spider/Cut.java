package org.alex.games.spider;

import org.alex.games.bj.beans.Card;

public class Cut {

	private Card card;
	private Card parent;
	private int index;
	private int weight;
	
	public Cut(Card card, Card parent, int index, int weight) {
		super();
		this.card = card;
		this.parent = parent;
		this.index = index;
		this.weight = weight;
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
	 * @return the parent
	 */
	public Card getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Card parent) {
		this.parent = parent;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
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
