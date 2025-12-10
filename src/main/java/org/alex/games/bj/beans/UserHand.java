package org.alex.games.bj.beans;

import org.alex.games.bj.handler.Decision;
import org.alex.games.bj.handler.StatsTable;

public class UserHand extends Hand {

	
	private long bet = 25;
	
	private boolean doubleBet = false;
	private boolean surrendered = false;
	private boolean split = false;  // the main hand was split
	private boolean allowSplit = true;
	private UserHand splitHand1 = null;
	private UserHand splitHand2 = null;
	
	public UserHand(Card card1, Card card2) {
		super(card1, card2);
	}

	@Override
	public void returnCards() {
		if (isSplit()){
			splitHand1.returnCards();
			splitHand2.returnCards();
		} else {
			super.returnCards();
		}
	}
	
	/**
	 * @return true if user no longer has a valid hand.   If true then dealer doesn't need to play.
	 */
	public boolean isAllBustedOrSurrendered() {
		if (isSplit())
			return (splitHand1.isBusted() || splitHand1.isSurrendered()) && (splitHand2.isBusted() || splitHand2.isSurrendered()) ;
		try {
			return super.isBusted() || isSurrendered();
		} catch (Exception e){
			this.printHand();
			throw new RuntimeException("What happned?");
		}
		
	}
	
	public void play(Card dealerOpenCard) {
		
		UserAction action = Decision.whatToDO(dealerOpenCard, this);
		if (action==null){
			System.out.println(dealerOpenCard.getValueShortName() + " vs "+ this.getCard1().getValueShortName() + "/" + this.getCard2().getValueShortName());
			throw new RuntimeException("No Action defined Dealer open is " + dealerOpenCard + " and we have" + this.printHand() + " ");			 
			//action = Decision.whatToDO(dealerOpenCard, this.getCard1(), this.getCard2(), this);
		} else { 
			if (action==UserAction.SPLIT && this.allowSplit==false)
				action=UserAction.STAY;
			//
			switch (action){
			case STAY:
				break;
			case HIT:
				this.Hit(Shoe.getCard());
				this.play(dealerOpenCard);  //see if we need to keep going
				break;
			case DOUBLE:
				doubleBet();
				break;
			case SPLIT:
				//System.err.println("SPLIT not implemented"); //TODO:
				this.setSplit(true);
				splitHand1 = new UserHand(this.getCard1(),Shoe.getCard());
				splitHand1.setAllowSplit(false);
				splitHand2 = new UserHand(this.getCard2(),Shoe.getCard());
				splitHand2.setAllowSplit(false);
				setCard1(null);
				setCard2(null);
				splitHand1.play(dealerOpenCard);
				splitHand2.play(dealerOpenCard);
				break;
			case SURRENDER:
				surrendered=true;
				break;
			default:
				System.err.println("No Action defined Dealer open is " + dealerOpenCard + " and we have" + this.printHand() + " --  STAY ?");
				throw new RuntimeException("No Action defined Dealer open is " + dealerOpenCard + " and we have" + this.printHand() + " --  STAY ?");
			}
		}
	}

	/**
	 * @return the bet
	 */
	public long getBet() {
		return bet;
	}

	/**
	 * @param bet the bet to set
	 */
	public void setBet(long bet) {
		this.bet = bet;
	}

	private void doubleBet() {
		bet = bet*2;
		this.Hit(Shoe.getCard());
		setDoubleBet(true);
	}

	/**
	 * @return the doubleBet
	 */
	public boolean isDoubleBet() {
		return doubleBet;
	}

	/**
	 * @param doubleBet the doubleBet to set
	 */
	private void setDoubleBet(boolean doubleBet) {
		this.doubleBet = doubleBet;
	}


	public String printHand() {
		if (this.isSplit()){
			return "Hand 1: " + this.getSplitHand1().printHand() + (isDoubleBet()? " DOUBLED":" ") + "\n" +
				   "Hand 2: " + this.getSplitHand2().printHand() + (isDoubleBet()? " DOUBLED":" ") + "\n";
		}
		return super.printHand() + (isDoubleBet()? " DOUBLED":" "); 
	}

	/**
	 * @return the surrendered
	 */
	public boolean isSurrendered() {
		return surrendered;
	}

	/**
	 * @param surrendered the surrendered to set
	 */
	public void setSurrendered(boolean surrendered) {
		this.surrendered = surrendered;
	}

	/**
	 * @return the split
	 */
	public boolean isSplit() {
		return split;
	}


	/**
	 * @param split the split to set
	 */
	private void setSplit(boolean split) {
		this.split = split;
	}

	/**
	 * @return the allowSplit
	 */
	public boolean isAllowSplit() {
		return allowSplit;
	}

	/**
	 * @param allowSplit the allowSplit to set
	 */
	public void setAllowSplit(boolean allowSplit) {
		this.allowSplit = allowSplit;
	}



	/**
	 * @return the splitHand1
	 */
	public UserHand getSplitHand1() {
		return splitHand1;
	}


	/**
	 * @return the splitHand2
	 */
	public UserHand getSplitHand2() {
		return splitHand2;
	}

}
