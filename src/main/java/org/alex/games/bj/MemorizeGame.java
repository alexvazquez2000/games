package org.alex.games.bj;



import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;

import org.alex.games.bj.beans.Card;
import org.alex.games.bj.beans.DealerHand;
import org.alex.games.bj.beans.GAMESTATUS;
import org.alex.games.bj.beans.Shoe;
import org.alex.games.bj.beans.UserAction;
import org.alex.games.bj.beans.UserHand;
import org.alex.games.bj.handler.DashBoardPanel;
import org.alex.games.bj.handler.Decision;
import org.alex.games.bj.handler.DecisionTableCellEditor;
import org.alex.games.bj.handler.DecisionTableCellRenderer;
import org.alex.games.bj.handler.ResultsTableCellRenderer;
import org.alex.games.bj.handler.StatsTable;

public class MemorizeGame {
	
	//http://www.java2s.com/Code/Java/2D-Graphics-GUI/Imagecrop.htm

	public static long wallet = 0l;
	public static long maxLoss = 0l;
	public static long maxWin = 0l;
	
	private final static FeltTop feltTop = new FeltTop();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Shoe.Initialize(6);  //number of decks
				
		JFrame jfrm = new JFrame("Mem Card");
		jfrm.setSize(1200, 800);
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//jfrm.add(feltTop);
		//jfrm.add( DashBoardPanel.getInstance());
		jfrm.pack();
		jfrm.setVisible(true);
		
//		//int maxNumberOfAdjustments = 1;
//		//int numberOfGames = 100000;
//		int numberOfGames = 3;
//		//for (int j=0; j<maxNumberOfAdjustments; j++){
//			for (int x=0; x<numberOfGames; x++){
//				MemorizeGame game = new MemorizeGame();
//				game.startDealing();
//				if ((x % 100)==0) {
//					//repaint					
//					DashBoardPanel.getInstance().SetMoney("$" + wallet 
//							//+ " adjustment " + j
//							+ "   MaxWin=" + maxWin + " / MaxLoss=" + maxLoss);
//				}
//			}
//			DashBoardPanel.getInstance().SetMoney("$" + wallet 
//					//+ "  adjustment "	+ j
//					+ "   MaxWin=" + maxWin + " / MaxLoss=" + maxLoss);
//			//stats.printStats();
//			System.out.println("Money = $" +  wallet);
//		StatsTable.getInstance().printStats();
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
