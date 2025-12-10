package org.alex.games.spider;

import java.util.ArrayList;
import java.util.List;

import org.alex.games.bj.beans.Card;

public class Column {
	private int columnNumber = -1;
	private List<Card> cards;
	
	public Column(int columnNumber){
		this.columnNumber = columnNumber;
		cards = new ArrayList<Card>();
	}

	/**
	 * @return the cards
	 */
	public List<Card> getCards() {
		return cards;
	}

	/**
	 * @param cards the cards to set
	 */
	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public void print() {
		System.out.print(columnNumber + "  " );
		for(Card card : cards){
			System.out.print( (card.isOpen()? card.getLongName() : "[" + card.getLongName() + "]" ) + "-");
		}
		System.out.println();
	}
	
	public List<Cut> getAllowedCuts(){
		List<Cut> allowedCut = new ArrayList<Cut>();
	
		if (cards.size()>0 ) {
			int weight =0;
			//only if we have at least a card
			int index = cards.size()-1;
			Card lastCard = cards.get(index);
			Card parentCard = null;
			boolean check = true;
			while (index > 0 && check) {
				index--;
				parentCard = cards.get(index);
				if (parentCard.isOpen() && parentCard.getSuit() == lastCard.getSuit() 
						&& parentCard.getNumValue()-1 == lastCard.getNumValue()){
					//allowedCut.add(parentCard);
					weight++;//add 1 for each card that is added to the chain
					lastCard = parentCard;
				} else {
					check=false;
				}
			}
			
			
			
			if (lastCard.isOpen()  && allowedCut.size() == 0) {
				Card parent = (parentCard != null && parentCard.isOpen() ? parentCard : null);
				allowedCut.add(new Cut(lastCard,parent,index, weight ));
			}
		}
		return allowedCut;
	}

	/**
	 * @param card
	 * @return  if card can not be moved here then return 0.
	 * OK to move into this empty column = 1
	 *  OK to move = 2
	 *  OK and extends chain return 3   
	 * 	
	 */
	public int isValidMove(Card card) {
		if (cards.size()==0) return 1;
		
		int index = cards.size()-1;
		Card lastCard = cards.get(index);
		if (lastCard.isOpen() && lastCard.getNumValue()-1 == card.getNumValue()){
			//if they are also the same suit then weight should be higher
			if (lastCard.getSuit() == card.getSuit()) {
				//FIXME: The length of the chain should be added to the weight
				return 3;  //this move extends the chain
			}
			return 2;
		}
		return 0;
	}

	public int getLastIndexOfOpenCard(Card card) {
		int index = cards.size()-1;
		if (index >=0) {
			Card lastCard = cards.get(index);
			while (index >= 0 && lastCard.isOpen()) {
				lastCard = cards.get(index);
				if (card.getNumValue()==lastCard.getNumValue() && card.getSuit()==lastCard.getSuit())
					return index;
				index--;
			}
		}
		return -1;
	}

	public void openBottomCard() {
		List<Card> cards = getCards();
		if (cards.size()>0)
			cards.get(cards.size()-1).setOpen(true);
	}
}
