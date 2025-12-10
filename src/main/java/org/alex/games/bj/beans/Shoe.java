package org.alex.games.bj.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Shoe {

	private static Shoe instance;
	
	private List<Card> stack = new ArrayList<Card>();
	
	/**
	 * private to implement a Singleton 
	 * @param i  is the number of decks that will be used. 
	 */
	private Shoe(int i) {
		Deck []deck = new Deck[i];
		for(int x=0; x<i; x++){
			//add decks
			 deck[x] = new Deck();
			 //get the cards from the new deck and add them to the shoe
			 Card []cards = deck[x].getCards();
			 for(int j=0; j<cards.length;j++){
				 stack.add(cards[j]);
			 }
		}
		ShuffleList();
	}

	
	private void ShuffleList() {
		long seed = System.nanoTime();
		Collections.shuffle(stack,new Random(seed));
	}

	/**
	 * @param i number of decks to add
	 */
	public static void Initialize(int i) {
		if (instance == null){ 
			instance = new Shoe(i);
			//Shoe.print();
		}		
	}

	public static void print(){
		int count = 0;
		int sumCheck = 0;
		List<Card> stack = getInstance().stack;
		for (int i = 0; i< stack.size(); i++) {
			Card card = stack.get(i);
			count++;
			sumCheck += card.getNumValue();
			System.out.print( card.toString() + "(" + card.getNumValue() + ") ");
			if ((i%55)==0) System.out.print("\n");
		}
		System.out.println("\nprinted " + count + " cards and sumCheck=" + sumCheck);
	}

	
	private void validateStack() {
		int count = 0;
		int sumCheck = 0;
		for (int i = 0; i< stack.size(); i++) {
			Card card = stack.get(i);
			count++;
			sumCheck += card.getNumValue();			
		}
		//System.out.println("\nprinted " + count + " cards and sumCheck=" + sumCheck);
		//312 cards and sumCheck=2808
	}

	
	
	public static Card getCard(){
		if (getInstance().stack.size()==0){
			//System.out.println("We are out of cards,  get them back from the used Stack and reshuffle");
			//System.out.print("UsedStack.size =" + UsedStack.getFullStack().size());
			//getInstance().stack = 
			List <Card>usedCards = UsedStack.getFullStack();
			if (usedCards != null){
				for(int x=0;x< usedCards.size();x++){
					getInstance().stack.add(usedCards.get(x));
				}
				UsedStack.emptyStack();
				getInstance().ShuffleList();
			}
			getInstance().validateStack();
		} 
		
		if (getInstance().stack.size()==0) return null;
		
		Card card = getInstance().stack.get(0);
		getInstance().stack.remove(0);
		return card;
	}

	//public static card peakAtStack(){	}

	public static Shoe getInstance() {
		if (instance == null){
			instance = new Shoe(6);
		}
		return instance;
	}


	public static boolean isEmpty() {
		return getInstance().stack.isEmpty();
	}

}
