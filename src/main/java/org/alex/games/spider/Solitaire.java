package org.alex.games.spider;

import java.util.ArrayList;
import java.util.List;

import org.alex.games.bj.beans.Card;
import org.alex.games.bj.beans.Shoe;

public class Solitaire {

	private Board board;
	private List<Move> history = new ArrayList<>();
	
	public static void main(String[] args) {
		Solitaire spider = new Solitaire();
		spider.setupBoard();
		
		spider.printBoard();
		List<Move> moves = null;
		do{
			moves = spider.findMovesAllowed();
			spider.printBoard();
		} while (spider.makeMoves(moves));
		
		if (true) {
			while (!Shoe.isEmpty()){
				spider.dealNextRow();
				spider.printBoard();
				do{
					moves = spider.findMovesAllowed();
					spider.printBoard();
				} while (spider.makeMoves(moves));
				System.out.println("-----------------------");
			}
		}
	}

	private boolean makeMoves(List<Move> moves) {
		boolean madeAMove = false;
		for (Move move : moves) {
			Column fromCol = board.getColumn(move.getFromCol());
			Column toCol = board.getColumn(move.getToCol());
			Card card = move.getCard();
			int fromIndex = fromCol.getLastIndexOfOpenCard(card);
			if (fromIndex >=0) {
				//the card was found in the source column
				if (toCol.isValidMove(card) > 0) {
					//make sure the move is still valid
					for(int x=fromCol.getCards().size()-1; x>=fromIndex; x--){
						toCol.getCards().add(fromCol.getCards().get(x));
						fromCol.getCards().remove(x);
					}
					fromCol.openBottomCard();
					madeAMove = true;
					System.out.println("Move col[" + move.getFromCol() +"]" + fromIndex + " -> to col[" + move.getToCol() +"]=" 
							+ move.getCard().getLongName() + "("+ move.getWeight() + ")");
					//TODO: After a move we need to check if the new cards in toCol make a fullChain
				}
			}
		}
		return madeAMove;
	}

	private List<Move> findMovesAllowed() {
		List<Move> movesFound = new ArrayList<Move>();
		for(int i=0; i<10; i++){
			List<Cut> allowedCuts = board.getColumn(i).getAllowedCuts();
			//TODO: The allowedCut should have the index of the cell.  If index is 0 then it allows to have an empty column
			//allowed Cuts should have a value/weight
			for(Cut cut : allowedCuts) {
				Card card = cut.getCard();
				//System.out.print("col[" + i +"]=cut at " + card.getLongName());
				for(int j=0; j<10; j++){
					if (i == j) {
						//can't move into itself
						continue;
					}
					int weight = board.getColumn(j).isValidMove(card);
					if (weight > 0) {
						if (weight==2 && cut.getParent()!=null ) {
							
						} else {
							//System.out.print(" to col[" + j +"]=" + weight);
							movesFound.add(new Move(i,j,card,weight));
						}
					}
				}
			}
			//System.out.println();
		}
		return movesFound;
	}

	private boolean dealNextRow() {
		//check that there is at least one card on each column
		for(int col=0; col<10; col++){
			if (board.getColumn(col).getCards().isEmpty() )
				return false;
		}
		//none of the columns are empty
		for(int col=0; col<10; col++){
			Card card = Shoe.getCard();
			card.setOpen(true);
			board.getColumn(col).getCards().add(card);
		}
		history.add(new Move(-1,-1,null,-1));
		//TODO: After dealing check if any of the new cards in the columns make a fullChain
		return true;
	}

	private void printBoard() {
		board.print();
		System.out.println("-----------------------");
	}

	private void setupBoard() {
		int rowMax = 7;
		for (int row=0; row< 7; row++){
			for(int col=0; col<10; col++){
				if (row==rowMax-1 && col>3) 
					continue;
				Card card = Shoe.getCard();
				board.getColumn(col).getCards().add(card);
			}
		}
		//flip the bottom card open
		for(int col=0; col<10; col++){
			board.getColumn(col).openBottomCard();
		}
	}

	private Solitaire(){
		Shoe.Initialize(2);  //number of decks
		board = new Board();
	}
}
