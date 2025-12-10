package org.alex.games.bj.beans;

import java.util.ArrayList;
import java.util.List;

public class UsedStack {

	private static UsedStack instance;

	private List<Card> stack = new ArrayList<Card>();

	/**
	 * Singelton like the shoe.   This is used to keep all the cards that already have played.  This queue is recycled into the shoe when 
	 * there are no more cards in the shoe to play.
	 * 
	 */
	private UsedStack() {		
	}

	public static UsedStack getInstance() {
		if (instance == null){
			instance = new UsedStack();
		}
		return instance;
	}

	public static void put(Card card) {
		getInstance().stack.add(card);
	}

	public static List<Card> getFullStack() {
		return getInstance().stack;
	}


	public static void emptyStack() {
		getInstance().stack.clear();		
	}

}
