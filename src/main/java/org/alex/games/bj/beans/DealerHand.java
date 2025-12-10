package org.alex.games.bj.beans;

import org.alex.games.bj.handler.StatsTable;

public class DealerHand extends Hand{
	
	public DealerHand(Card openCard, Card closeCard) {
		super(openCard,closeCard);
	}

	/**
	 * @return the closeCard
	 */
	public Card getCloseCard() {
		return super.getCard2();
	}

	/**
	 * @param closeCard the closeCard to set
	 */
	public void setCloseCard(Card closeCard) {
		super.setCard2(closeCard);
	}

	/**
	 * @return the open card
	 */
	public Card getOpenCard() {
		return super.getCard1();
	}

	/**
	 * @param openCard the openCard to set
	 */
	public void setOpenCard(Card openCard) {
		super.setCard1(openCard);
	}

	@Override
	/**
	 * https://www.blackjackinfo.com/blackjack-rules/rule-variations/
	 * Dealer Hits Soft 17
	 * Generally, the dealer in blackjack must hit if he has a total of 16 or less, and stand if he has 17 or more.
	 * 
	 * But at some games there is an exception when the dealer has a hand of "soft" 17.
	 * 
	 * If you look at the table, you will see one of two phrases on the felt:
	 * -    Dealer Stands on All 17s: This is the simple version. The dealer will stand with any total of 17 or more,
	 *      whether that total is soft or not. (This rule is abbreviated S17. The S is for Stand, not Soft!)
	 *      
	 * -    Dealer Hits Soft 17: In casinos with this rule, the dealer will stand with any hard 17, 
	 *      but draw another card if he has a soft 17, such as (Ace, 2, 4). (This rule is abbreviated H17.)
	 *      
	 * Seventeen is a weak hand, so if the dealer is allowed to try to improve the soft 17 hands, it makes the game tougher.
	 *  When a dealer is allowed to hit soft 17, it adds about 0.2% to the house advantage. Years ago, the only "Hit Soft 17"
	 *   games in the US were in and around Reno, Nevada. Almost all other areas used the better rule of standing on all 17s.	 *   
	 *   Over the years, more and more casinos have switched to hitting soft 17, and there are now far more H17 games than S17 games.
	 *   
	 *   You can still find some games where the dealer stands on all 17s, even in casinos where some of the tables use the H17 rule. Look around!
	 */
	public void play() {
		int points =this.pointsCount();
		boolean hitSoft17 = true;
		boolean dealerHit = points <= 16 || (hitSoft17 && this.isSoftHand() && points ==17);
		while ( dealerHit==true ){
			this.Hit(Shoe.getCard());
			points =this.pointsCount();
			//check if we need to keep hitting
			dealerHit = points <= 16 || (hitSoft17 && this.isSoftHand() && points ==17);
		}		
	}

	/**
	 * @param gameStatus
	 * @return positive number if user won, negative if lost money.   0 if pushed.  Returns the sum of all the games in the hand, including splits
	 */
	public long finalStatus(UserHand user, GAMESTATUS gameStatus) {
		long winnings = 0l;
		//let the stats handler decide who won
		if (user.isSplit()){
			gameStatus = StatsTable.getInstance().finalHandInfo(gameStatus, this, user.getSplitHand1());
			winnings += handleMoney(gameStatus,user.getSplitHand1().getBet());
			
			gameStatus = StatsTable.getInstance().finalHandInfo(gameStatus, this, user.getSplitHand2());
			winnings += handleMoney(gameStatus,user.getSplitHand2().getBet());
		} else {
			gameStatus = StatsTable.getInstance().finalHandInfo(gameStatus, this, user);
			winnings += handleMoney(gameStatus,user.getBet());
		}
		return winnings; 

	}

	private long handleMoney(GAMESTATUS gameStatus, long userBet) {
		long winnings = 0l;
		if (gameStatus == GAMESTATUS.USER_BJ_WON){
			winnings = userBet* 3/2;
		} else if (gameStatus == GAMESTATUS.USER_WON){
			winnings = userBet;
		} else if (gameStatus == GAMESTATUS.SURRENDER){
			winnings = - ((userBet+1) / 2); //plus 1 to round off
		} else if (gameStatus == GAMESTATUS.BJ_PUSHED || gameStatus == GAMESTATUS.PUSHED){
			//no change to money
			winnings = 0l;
		} else {
			winnings = -userBet;
		}
		return winnings;
	}

}
