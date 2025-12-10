package org.alex.games.bj.handler;

import javax.swing.JLabel;

public class CellStatus extends JLabel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8789392816718756183L;
	
	private int counter = 0;
	private int lost = 0;
	private int won = 0;
	private int push = 0;
	private int hit = 0; //increase when not doubled but we hit
	private int doubled = 0;
	private int surrendered = 0;
	private int split =0;
	
	// max number of cards that we had with a win
	// max number of cards that we had with a loss

	
	
	public CellStatus(String string) {
		super(string);
	}

	public void resetCounters() {
		counter = 0;
		lost = 0;
		won = 0;
		push = 0;
		hit = 0; //increase when not doubled but we hit
		doubled = 0;
		surrendered = 0;
		split =0;
	}

	public void increaseCounter() {
		counter++;		
	}
	
	public void increaseWon() {
		won++;		
	}
	public void increasePush() {
		push++;		
	}
	public void increaseLost() {
		lost++;		
	}

	
	/**
	 * @return the counter
	 */
	public int getCounter() {
		return counter;
	}

	/**
	 * @return the lost
	 */
	public int getLost() {
		return lost;
	}

	/**
	 * @return the won
	 */
	public int getWon() {
		return won;
	}

	/**
	 * @return the push
	 */
	public int getPush() {
		return push;
	}

	/**
	 * @return the surrendered
	 */
	public int getSurrendered() {
		return surrendered;
	}

	public void setAsHtml() {
		//return new JLabel("<HTML>aa<br>ZZna</HTML>", SwingConstants.CENTER);
		String text = "aa \nZZna";
		setText(text);
		setToolTipText("hi\nline2");
	}

	public String toString(){
		setToolTipText("hi\nline2");
		if (counter==0)return "n/a";
		
		if ((counter - push)==0)return counter+"=push";
		int percentWon = (won *100) / (counter - push);
		//return "" + percentWon + "%";
		return percentWon + "%" + counter + "=\ni" + lost + "/" + won + "/" + push; 
	}

	/**
	 * @return  0 if not played
	 * 	1- if all plays are push
	 *  2 - if winning
	 *  -1 if loosing
	 */
	public WInvsLOOSE_STATUS isWinning(){		
		if (counter==0) return WInvsLOOSE_STATUS.NA;
		if ((counter - push)==0) return WInvsLOOSE_STATUS.PUSH;
		int percentWon = (won *100) / (counter - push);
		if (percentWon>=50)
			return WInvsLOOSE_STATUS.WINNING;
		return WInvsLOOSE_STATUS.LOOSING;
	}

}
