package org.alex.games.bj.beans;

import java.util.ArrayList;
import java.util.List;

public class Hand {
	private Card card1;  //for dealer this is closed

	private Card card2;

	private List<Card> cards = new ArrayList<Card>();

	/**
	 * Has an Ace, and total sum allows at least one of the ace to be counted as 11.
	 */
	private boolean softHand;
		
	private boolean busted=false; //true when count >21 
	
	/**
	 * @return true if busted.  False if it is still a valid hand
	 */
	public boolean isBusted() {
		if (pointsCount()>21 )  //TODO: Do we need check for softHand ?
 			busted=true;
		return busted;
	}

	public void Hit(Card card){
		cards.add(card);
		pointsCount();
	}
	
	public Hand(Card card1, Card card2) {
		this.card1 = card1;
		this.card2 = card2;
	}
	
	public boolean isBlackJack(){
		if ((card1.getNumValue()==10 && card2.isA()  || card2.getNumValue()==10 && card1.isA() ) && (cards.isEmpty()) )
			return true;
		return false;
	}
	
	public void returnCards() {
		UsedStack.put(card1);
		UsedStack.put(card2);
		for (Card card: cards){
			UsedStack.put(card);
		}
	}

	public int getIntialPoints() {
		boolean hasAce= false;
		
		int sum = card1.getNumValue() + card2.getNumValue();
		if (card1.isA() || card2.isA())
			hasAce=true;
		
		if (hasAce && sum<=11) 
			 sum += 10;		
		return sum;
	}

	public int pointsCount(){
		boolean hasAce= false;
		
		int sum = card1.getNumValue() + card2.getNumValue();
		if (card1.isA() || card2.isA())
			hasAce=true;
		
		for (Card card: cards){
			sum += card.getNumValue();
			if (card.isA()) hasAce=true;
		}
		if (hasAce && sum<=11) {
			setSoftHand(true);//  softHand = true;
			
			sum += 10;
		} else {
			setSoftHand(false);//  softHand = false;
		}
		return sum;
	}
	
	/**
	 * @return the card1, for dealer this is closed
	 */
	public Card getCard1() {
		return card1;
	}

	/**
	 * @param card1 the card1 to set, for dealer this is closed
	 */
	public void setCard1(Card card1) {
		this.card1 = card1;
	}

	/**
	 * @return the card2, for dealer this is open
	 */
	public Card getCard2() {
		return card2;
	}

	/**
	 * @param card2 the card2 to set, for dealer this is open
	 */
	public void setCard2(Card card2) {
		this.card2 = card2;
	}

	
	/**
	 * @return the cards
	 */
	public List<Card> getCards() {
		return cards;
	}
	
	public void play() {
		throw new RuntimeException("Hand must override the Hand.play() function.");
	}

	/**
	 * @return the softHand
	 */
	public boolean isSoftHand() {
		return softHand;
	}

	/**
	 * @param softHand the softHand to set
	 */
	private void setSoftHand(boolean softHand) {
		this.softHand = softHand;
	}

	public String printHand() {
		return " " + card1.toString() + "/" + card2.toString() +
			" " + printStack() + 
			(isBusted()?" BUSTED":" " ) +
			(isSoftHand()?" SOFT":" " ) +
			"(" + pointsCount() + ")";
	}
	
	/**
	 * @return a string with all additional cards after the inital hand 
	 */
	private String printStack() {		
		String stack = "";
		for (Card card: cards){
			stack += (stack.length()>0?"/":"");
			stack += card.toString(); 
		}
		return stack;
	}

}
