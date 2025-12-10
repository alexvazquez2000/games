package org.alex.games.bj.beans;




public class Deck {

	private Card []cards;
	
	public Deck(){
		cards = new Card[52];
		int i = 0;
		for (SUITS suit :SUITS.values()){
			for(CARDLETTER cardValue : CARDLETTER.values()){
				cards[i++] = new Card(suit, cardValue);
			}
		}		
	}

	
	/**
	 * @return all the cards in the deck
	 */
	public Card[] getCards() {
		return cards;
	}
}
