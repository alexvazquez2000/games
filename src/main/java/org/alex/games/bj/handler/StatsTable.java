package org.alex.games.bj.handler;

import javax.swing.table.AbstractTableModel;

import org.alex.games.bj.beans.Card;
import org.alex.games.bj.beans.DealerHand;
import org.alex.games.bj.beans.GAMESTATUS;
import org.alex.games.bj.beans.UserHand;

public class StatsTable {
	private static StatsTable instance = null;
	
	public static int dealerBkJkCount  =0;
	public static int userBkJkCount  =0;

	public static int gamesPlayed = 0;
	public static int gamesLostCount = 0;
	public static int gamesTiedCount = 0;
	public static int gamesWonCount = 0;
	public static int gamesSurrenderCount = 0;
	public static int gamesDoubledCount = 0;
	
	public static int offerInsurance = 0;
	public static int offerInsuranceDealerBJ = 0;
	public static int offerInsurancePushedBJ = 0;
	
	
	public static final String dealerCards[] = {"2","3","4","5","6","7","8","9","10","A"};
	//public static final String hardRows[] = {"4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21"};
	//public static final String hardRows[] = {"5","6","7","8","9","10","11","12","13","14","15","16","17","18","19"};  //4 & 20 are pairs and 21=BJ/Ace table
	public static final String hardRows[] = {"5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21"};  //20=not a pair or  21 not BJ, use this if multiple cards
	public static final String pairRows[] = {"2","3","4","5","6","7","8","9","10"};  //A+A is not a pair, is soft 12
	public static final String softRows[] = {"12","13","14","15","16","17","18","19","20","21"};

	private MyStatsHashTModel[] statsTable;
	
	private MyStatsHashTModel hard = new  MyStatsHashTModel("hard", dealerCards, hardRows);
	private MyStatsHashTModel soft = new MyStatsHashTModel("soft", dealerCards, softRows); 
	private MyStatsHashTModel pairs = new MyStatsHashTModel("pair", dealerCards, pairRows);

	
	private StatsTable() {	
		int i= 0;
		statsTable = new MyStatsHashTModel[5];
		statsTable[i++] = new  MyStatsHashTModel("hard initial", dealerCards, hardRows);
		statsTable[i++] = new  MyStatsHashTModel("hard", dealerCards, hardRows);  //after initial
		statsTable[i++] = new  MyStatsHashTModel("soft initial", dealerCards, softRows);
		statsTable[i++] = new  MyStatsHashTModel("soft", dealerCards, softRows);  //after initial
		statsTable[i++] = new  MyStatsHashTModel("pairs", dealerCards, pairRows);
	}
	
	public MyStatsHashTModel getTable(int i){
		return statsTable[i];
	}
	
	
	public MyStatsHashTModel getHard(){
		return hard;
	}
	public MyStatsHashTModel getSoft(){
		return soft;
	}
	public MyStatsHashTModel getPairs(){
		return pairs;
	}
	
	
	public AbstractTableModel getHardModel(){
		return hard.getModel();
	}
	public AbstractTableModel getSoftModel(){
		return soft.getModel();
	}
	public AbstractTableModel getPairsModel(){
		return pairs.getModel();
	}
	
	public GAMESTATUS intialHandInfo(DealerHand dealer, UserHand user){
		
		GAMESTATUS initalStatus;
		
		gamesPlayed++;
		
		Card d1 = dealer.getOpenCard();
		Card c3 = user.getCard1();
		Card c4 = user.getCard2();

		if (c3.isA() || c4.isA()){
			String key =  user.pointsCount() + "_" + d1.getValueShortName();
			//System.out.println("key=" + key);
			//ace.put(key , (Integer)( ace.get(key)+1) );
			soft.get(key).increaseCounter();
		} else if (!c3.getValueShortName().equals("A") && 
				c3.getNumValue() == c4.getNumValue()){ //TODO: this allows a split on different cards that have a value of 10
			String key = c3.getValueShortName() + "_" + d1.getValueShortName();
			//System.out.println("key=" + key);
			//pairs.put(key , (Integer)( pairs.get(key).increaseCounter()) );
			pairs.get(key).increaseCounter();
		} else  {
			String key = user.pointsCount() + "_" + d1.getValueShortName();
			//System.out.println("key=" + key);
			//single.put(key , (Integer)( single.get(key)+1) );
			hard.get(key).increaseCounter();
		}
		
		if (dealer.getOpenCard().isA() ) {
			offerInsurance++;
			if (dealer.isBlackJack()) {
				offerInsuranceDealerBJ++;
				if (user.isBlackJack()){ 
					offerInsurancePushedBJ++;
				}
			}
		}
		
		if (dealer.isBlackJack()) {
			dealerBkJkCount++;
			if (user.isBlackJack()) {
				userBkJkCount++;  //TODO:  Do we really want this counted? 
				initalStatus = GAMESTATUS.BJ_PUSHED;
			} else {
				//TODO:  might want to count the BJ here so we don't count the push
				initalStatus = GAMESTATUS.DEALER_BJ_WON;
			}
		} else if (user.isBlackJack()){
			userBkJkCount++;
			initalStatus = GAMESTATUS.USER_BJ_WON;
		} else {
			initalStatus = GAMESTATUS.OPEN;
		}
		return initalStatus;
	}

	

	public void printStats() {
		
		System.out.println("\n\n\n  HARD HAND");
		for (String dealerCard : dealerCards)
			System.out.print("\t" + dealerCard);
		System.out.print("\n");
		for (String  row : hardRows){			
			System.out.print("\n" + row);
			for (String dealerCard : dealerCards){
				System.out.print("\t" + hard.get(row + "_" + dealerCard));
			}
		}
		
		System.out.println("\n\n\n  PAIRS");
		for (String dealerCard : dealerCards) System.out.print("\t" + dealerCard);
		System.out.print("\n");
		for (String  row : pairRows){
			System.out.print("\n" + row);
			for (String dealerCard : dealerCards){
				System.out.print("\t" + pairs.get(row + "_" + dealerCard));
				
			}
		}
		
		System.out.println("\n\n\n  ACE/SOFT HAND");
		for (String head : dealerCards) System.out.print("\t" + head);
		System.out.print("\n");
		for (String  row : softRows){
			System.out.print("\n" + row);
			for (String dealerCard : dealerCards){
				System.out.print("\t" + soft.get(row + "_" + dealerCard));
			}
		}
		
		System.out.println("\n\n\n  Summary \n");
		
		System.out.println("dealer BJ=" + dealerBkJkCount);
		System.out.println("user BJ=" + userBkJkCount);
		System.out.println("user doubled=" + gamesDoubledCount);
		System.out.println("user surrender=" + gamesSurrenderCount);		
		System.out.println("games lost/pushed/won=" +gamesLostCount +"/" +  gamesTiedCount + "/" + gamesWonCount + "   Total games played=" + gamesPlayed);
		
		System.out.println("\n\nInsurance offered=" + offerInsurance
				+ " Dealer BJ=" + offerInsuranceDealerBJ 
				+ " PushedBJ=" + offerInsurancePushedBJ 
				+ "  - " + ((100*offerInsuranceDealerBJ)/(offerInsurance+1)) + "% " );
	}


	public GAMESTATUS finalHandInfo(GAMESTATUS initialStatus, DealerHand dealer, UserHand user) {
		GAMESTATUS gameStatus;
		//who won?
		int dealerPoints = dealer.pointsCount();
		int userPoints = user.pointsCount();
		
		if (dealer.isBusted() &&  !user.isBusted()){
			gamesWonCount++;  //dealer busted,  count it as a win
			gameStatus = GAMESTATUS.USER_WON;
		} else if (user.isSurrendered() ) {
			gamesLostCount++;
			gamesSurrenderCount++;
			gameStatus = GAMESTATUS.SURRENDER;
		} else if (dealerPoints > userPoints || userPoints>21){
			gamesLostCount++;
			gameStatus = GAMESTATUS.DEALER_WON; 
		} else if (dealerPoints == userPoints ){
			gamesTiedCount++;
			gameStatus = GAMESTATUS.PUSHED;
		} else if (dealerPoints < userPoints || dealerPoints>21) {
			gamesWonCount++;
			gameStatus = GAMESTATUS.USER_WON;
		} else {
			gameStatus = GAMESTATUS.WHO_WON;  //error 
		}
		
		//
		if (user.isDoubleBet() ) {
			gamesDoubledCount++;
		}
		
		statsUpdate(dealer, user, gameStatus);
		
		if (initialStatus == GAMESTATUS.OPEN)
			return gameStatus;
		
		return initialStatus;
		
	}


	private void statsUpdate(DealerHand dealer, UserHand user, GAMESTATUS gameStatus) {
		Card d1 = dealer.getOpenCard();
		Card c1 = user.getCard1();
		Card c2 = user.getCard2();

		int initialPoints = user.getIntialPoints();
		
		if (c1.isA() || c2.isA() && user.getCards().size()<2 ){  //count as ace only if a hit or it it stayed
			String key = initialPoints + "_" + d1.getValueShortName();
			//System.out.println("key=" + key);
			//ace.put(key , (Integer)( ace.get(key)+1) );
			if (gameStatus==GAMESTATUS.USER_BJ_WON || gameStatus==GAMESTATUS.USER_WON) 
				soft.get(key).increaseWon();
			else if (gameStatus==GAMESTATUS.DEALER_BJ_WON || gameStatus==GAMESTATUS.DEALER_WON) 
				soft.get(key).increaseLost();
			else 
				soft.get(key).increasePush();
			
		} else if (c1.getValueShortName().equals(c2.getValueShortName()) && !c1.getValueShortName().equals("A")) {
			//pairs
			String key = c1.getValueShortName() + "_" + d1.getValueShortName();
			if (gameStatus==GAMESTATUS.USER_BJ_WON || gameStatus==GAMESTATUS.USER_WON) 
				pairs.get(key).increaseWon();
			else if (gameStatus==GAMESTATUS.DEALER_BJ_WON || gameStatus==GAMESTATUS.DEALER_WON) 
				pairs.get(key).increaseLost();
			else 
				pairs.get(key).increasePush();
		} else {
			//keys
			String key = initialPoints + "_" + d1.getValueShortName();
//			//System.out.println("key=" + key);
//			//ace.put(key , (Integer)( ace.get(key)+1) );
			if (gameStatus==GAMESTATUS.USER_BJ_WON || gameStatus==GAMESTATUS.USER_WON) 
				hard.get(key).increaseWon();
			else if (gameStatus==GAMESTATUS.DEALER_BJ_WON || gameStatus==GAMESTATUS.DEALER_WON) 
				hard.get(key).increaseLost();
			else 
				hard.get(key).increasePush();
		} 
		
	}

	public static StatsTable getInstance() {
		if (instance==null){
			synchronized (StatsTable.class) {
				instance = new StatsTable();				
			}		
		}
		return instance;
	}
}
