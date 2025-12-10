package org.alex.games.bj;



import javax.swing.JFrame;

import org.alex.games.bj.beans.Card;
import org.alex.games.bj.beans.DealerHand;
import org.alex.games.bj.beans.GAMESTATUS;
import org.alex.games.bj.beans.Shoe;
import org.alex.games.bj.beans.UserHand;
import org.alex.games.bj.handler.DashBoardPanel;
import org.alex.games.bj.handler.StatsTable;

public class Game {
	//private StatsTable stats = StatsTable.getInstance();

	public static long wallet = 0l;
	public static long maxLoss = 0l;
	public static long maxWin = 0l;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Shoe.Initialize(6);  //number of decks
				
		JFrame jfrm = new JFrame("Black");
		jfrm.setSize(1200, 800);
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jfrm.add( DashBoardPanel.getInstance());
		jfrm.pack();
		jfrm.setVisible(true);
		
		//int maxNumberOfAdjustments = 1;
		//int numberOfGames = 100000;
		int numberOfGames = 3;
		//for (int j=0; j<maxNumberOfAdjustments; j++){
			for (int x=0; x<numberOfGames; x++){
				Game game = new Game();
				game.startDealing();
				if ((x % 100)==0) {
					//repaint					
					DashBoardPanel.getInstance().SetMoney("$" + wallet 
							//+ " adjustment " + j
							+ "   MaxWin=" + maxWin + " / MaxLoss=" + maxLoss);
				}
			}
			DashBoardPanel.getInstance().SetMoney("$" + wallet 
					//+ "  adjustment "	+ j
					+ "   MaxWin=" + maxWin + " / MaxLoss=" + maxLoss);
			//stats.printStats();
			System.out.println("Money = $" +  wallet);
			//if (j<maxNumberOfAdjustments-1)  // don't adjust on the last iteration or it shows the adjusted cells as N/A
				//Decision.adjustDecisions(StatsTable.getInstance());
		//}
		StatsTable.getInstance().printStats();
		
	}

	private void startDealing() {
//		Card card = Shoe.getCard();
//		while (card != null ){
//			UsedStack.put(card);
//			card = Shoe.getCard();
//		}

		Card card1 = Shoe.getCard();
		Card card2 = Shoe.getCard();
		Card card3 = Shoe.getCard();
		Card card4 = Shoe.getCard();
		
		DealerHand dealer= new DealerHand(card1, card3);
		UserHand user = new UserHand(card2, card4);
		
		GAMESTATUS gameStatus = StatsTable.getInstance().intialHandInfo(dealer, user);
		
		// See if we have a BJ in the initial hand or it it is open to play
		if (gameStatus == GAMESTATUS.OPEN){				
			user.play(dealer.getOpenCard());
			
			// now the dealer plays
			//if adding more users then dealer plays if there is at least one user to play
			if (! user.isAllBustedOrSurrendered())
				dealer.play();
		}

		long winnings = dealer.finalStatus(user,gameStatus);
		
		
		wallet += winnings;
		
		if (wallet > maxWin) maxWin = wallet;
		else if (wallet < maxLoss) maxLoss = wallet;
		
		printGame(dealer, user, gameStatus, winnings);
		
		dealer.returnCards();
		user.returnCards();
	}


	private void printGame(DealerHand dealer, UserHand user, GAMESTATUS gameStatus, long winnings) {
		System.out.println (" User Hand " + user.printHand() );
		System.out.println ("House Hand (o/c)" + dealer.printHand() );
		System.out.println (gameStatus.name() +  "  winnings $" + winnings);
		System.out.println ();
		
	}

}
