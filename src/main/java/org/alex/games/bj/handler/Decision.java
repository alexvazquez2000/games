package org.alex.games.bj.handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.table.AbstractTableModel;

import org.alex.games.bj.beans.Card;
import org.alex.games.bj.beans.UserAction;
import org.alex.games.bj.beans.UserHand;

public class Decision {
	 
	private static DecisionTableModel hard = new  DecisionTableModel(StatsTable.dealerCards, StatsTable.hardRows);
	private static DecisionTableModel soft = new DecisionTableModel(StatsTable.dealerCards, StatsTable.softRows); 
	private static DecisionTableModel pairs = new DecisionTableModel(StatsTable.dealerCards, StatsTable.pairRows);

	private static volatile boolean initialized = false;

	private static boolean readTables() {
		boolean readFileOK = false;
		File file = new File("bjdecisions.txt");
		if (file.exists() && file.canWrite()){
			BufferedReader in = null;
			try {
				 in = new BufferedReader(new FileReader(file));
				 String line;
				while ( (line = in.readLine())!=null ){
					StringTokenizer st = new StringTokenizer(line,"\t");
					String tableName = st.nextToken();
					String cellName = st.nextToken();
					String action = st.nextToken();
					if (tableName.equals("soft")) 
						soft.put(cellName, UserAction.find(action));
					else if (tableName.equals("hard")) 
						hard.put(cellName, UserAction.find(action));
					else if (tableName.equals("pair")) 
						pairs.put(cellName, UserAction.find(action));
				}
				readFileOK = true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (in!=null) 
						in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}		
		return readFileOK;
	}
	
	public static void saveTables() {
		File file = new File("bjdecisions.txt");
		
		OutputStreamWriter out = null;
		try {
			out = new OutputStreamWriter(new FileOutputStream(file));
			hard.save("hard", out);
			soft.save("soft", out);
			pairs.save("pair", out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out!=null){
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static AbstractTableModel getHardDT(){
		if (!initialized) initValues();
		return hard.getModel();
	}
	public static AbstractTableModel getSoftDT(){
		if (!initialized) initValues();
		return soft.getModel();
	}
	public static AbstractTableModel getPairsDT(){
		if (!initialized) initValues();
		return pairs.getModel();
	}
	
	
	public static synchronized void initValues() {
		if (initialized) return;
		
		if (! readTables()) {
			//use some default values only if the file is not found
			for (String dealerCard : StatsTable.dealerCards){
				for (String  row : StatsTable.hardRows){
				//2 = A+A but never used
				//3 = A+2 but never used because it is treated as 13 and handled in the Ace table
				UserAction action = UserAction.STAY;
				if ((row.equals("10") && dealerCard.equals("10"))
						|| row.equals("11")) { 
					action = UserAction.DOUBLE;
				} else if ((dealerCard.equals("A") && row.equals("20")) ||
						(dealerCard.equals("7") && row.equals("16")) ||  
						(dealerCard.equals("8") && row.equals("17"))
						) {						
					action = UserAction.SURRENDER;
				}
				hard.put(row + "_" + dealerCard, action);
				
			}

			for (String  row : StatsTable.pairRows){
				UserAction action = UserAction.STAY;
				if (row.equals("2") || row.equals("3")){
					action = UserAction.SPLIT;
				} else if (row.equals("10")) {
						action = UserAction.STAY;
				}
				pairs.put(row + "_" + dealerCard, action);				
			}

			for (String  row : StatsTable.softRows){
				UserAction action = UserAction.STAY;
				soft.put(row + "_" + dealerCard, action);				
			}
		}
		}
		initialized = true;
	}

	public static UserAction whatToDO(Card dealerOpenCard, UserHand hand ) {		
		if (hand.isBusted())
			return UserAction.STAY;  //TODO:  This should be a different action
		
		int points = hand.pointsCount();
				
		boolean initialDecision = hand.getCards().isEmpty();
		
		UserAction action ; 
		if (initialDecision) {
			if ( hand.isSoftHand()) {
				action = soft.get(points, dealerOpenCard.getValueShortName());									
			} else if ( hand.getCard1().getNumValue()==hand.getCard2().getNumValue()){
				action = pairs.get(hand.getCard1().getValueShortName(), dealerOpenCard.getValueShortName());				
			} else {
				action = hard.get( points, dealerOpenCard.getValueShortName());
			}
		} else {
			if ( hand.isSoftHand()) {
				action = soft.get(points, dealerOpenCard.getValueShortName());									
			} else {
				//pairs are not longer to be split, so they don't make sense
				action = hard.get( points, dealerOpenCard.getValueShortName());
			}
			action = checkIfAllowed( action);
		}
		return action; 
	}

	private static UserAction checkIfAllowed( UserAction action) {
		if (action==UserAction.DOUBLE ) {
			action = UserAction.HIT;
		} else if (action==UserAction.SURRENDER ) {
			action = UserAction.STAY;  // TODO: is there a scenario when this should be HIT ?
		} else if (action==UserAction.SPLIT ) {
			action = UserAction.STAY;   //TODO: or hit ?
			throw new RuntimeException("SPLIT not allowed after the intial decision was made");
		}
		return action;
	}

	
//	/**
//	 *	//TODO:  Document this function 
//	 * @param stats
//	 */
//	public static void adjustDecisions(StatsTable stats) {
//		for (String dealerCard : StatsTable.dealerCards){
//			for (String  row : StatsTable.hardRows){
//				String key = row + "_" + dealerCard;
//				
//				CellStatus cell = stats.getHard().get(key);
//				UserAction action = null;
//				action=adjust(hard.get(row,dealerCard), cell.isWinning());									
//				if (action!= null){
//					hard.put(key,action);
//					stats.getHard().get(key).resetCounters();
//				}
//			}
//
//			for (String  row : StatsTable.pairRows){
//				String key = row + "_" + dealerCard;
//				CellStatus cell = stats.getPairs().get(key);
//				UserAction action=null;
//				action=adjust(pairs.get(row,dealerCard), cell.isWinning());									
//				if (action!= null){
//					pairs.put(key,action);
//					stats.getPairs().get(key).resetCounters();
//				}
//			}
//
//			for (String  row : StatsTable.softRows){
//				String key = row + "_" + dealerCard;
//				CellStatus cell = stats.getSoft().get(key);
//				UserAction action=null;
//				action=adjust(soft.get(row,dealerCard), cell.isWinning());									
//				if (action!= null){
//					soft.put(key,action);
//					stats.getSoft().get(key).resetCounters();
//				}				
//			}
//		}
//		
//	}
//
//	/**
//	 * TODO:  Document this function
//	 * @param userAction
//	 * @param wlstatus
//	 * @return
//	 */
//	private static UserAction adjust(UserAction userAction, WInvsLOOSE_STATUS wlstatus) {
//		if (userAction==null || wlstatus==WInvsLOOSE_STATUS.PUSH || wlstatus==WInvsLOOSE_STATUS.NA) 
//			return null;
//		UserAction newUserAction = null;
//		if (wlstatus==WInvsLOOSE_STATUS.LOOSING){
//			switch (userAction) {
//			case STAY:
//				newUserAction = UserAction.HIT;
//				break;
//			case HIT:
//				newUserAction = UserAction.STAY;  //split ?
//				break;
//			case DOUBLE:
//				newUserAction = UserAction.HIT;
//				break;
//			case SPLIT:
//				newUserAction = UserAction.STAY;
//				break;
//			case SURRENDER:  // give another chance
//				newUserAction = UserAction.STAY;
//				break;			
//			}
//		} else if (wlstatus==WInvsLOOSE_STATUS.WINNING){
//			switch (userAction) {
//			case HIT:
//				newUserAction = UserAction.DOUBLE;
//				break;
//			case SPLIT:  //TODO:not implemented
//				newUserAction = UserAction.STAY;
//				break;			
//			}			
//		}
//		return newUserAction;
//	}
}
