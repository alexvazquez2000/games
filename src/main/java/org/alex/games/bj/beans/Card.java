package org.alex.games.bj.beans;

public class Card {

	private SUITS suit;
	private CARDLETTER cardLetter;
	private String shortName;
	private String longName;
	private int numValue = 0;
	private boolean open= false;
	
	public Card(SUITS suit, CARDLETTER cardLetter) {
		this.suit = suit;
		this.cardLetter = cardLetter;
		shortName = cardLetter.name().replaceAll("v", "");		
		//longName = suit.name().substring(0,1) + cardLetter.name().replaceAll("v", "");
		longName = suit.getValue() + cardLetter.name().replaceAll("v", "");
		numValue =  cardLetter.ordinal() + 1;
		//FIXME: This is needed for BJ
		//But not for spider
		//if (numValue > 10) numValue=10;
		
	}
	
	/**
	 * 2,3,4,5,6,7,8,9, 10, J, Q, K, A
	 * @return the shortName  
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * 2,3,4,5,6,7,8,9, 10 (J, Q and K return 10) , A
	 * @return the shortName  
	 */
	public String getValueShortName() {
		if (numValue==10)
			return "" + numValue;
		return shortName;
	}

	/**
	 * The Suit followed by 2,3,4,5,6,7,8,9, 10, J, Q, K, A
	 * @return the longName
	 */
	public String getLongName() {
		return longName;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return shortName;
	}


	/**
	 * @return true is card is Ace
	 */
	public boolean isA(){
		if (cardLetter==CARDLETTER.A) return true;
		return false;		
	}
	
	public int getNumValue() {		
		return numValue;
	}

	/**
	 * @return the open - defaults to false
	 */
	public boolean isOpen() {
		return open;
	}

	/**
	 * @param open the open to set
	 */
	public void setOpen(boolean open) {
		this.open = open;
	}

	/**
	 * @return the suit
	 */
	public SUITS getSuit() {
		return suit;
	}	
}
