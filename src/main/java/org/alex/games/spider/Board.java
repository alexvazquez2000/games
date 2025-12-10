package org.alex.games.spider;

import org.alex.games.bj.beans.Shoe;

public class Board {
	private Column[] columns;
	
	public Board(){
		columns = new Column[10];
		for (int x=0;x<10;x++){
			columns[x] = new Column(x);
		}
	}

	/**
	 * @return the columns
	 */
	public Column getColumn(int c) {
		return columns[c];
	}

	/**
	 * @return the columns
	 */
	public Column[] getColumns() {
		return columns;
	}

	/**
	 * @param columns the columns to set
	 */
	public void setColumns(Column[] columns) {
		this.columns = columns;
	}

	public void print() {
		for (int x=0;x<10;x++){
			columns[x].print();
		}
		Shoe.print();
	}
	
	
}
