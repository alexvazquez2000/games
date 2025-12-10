package org.alex.games.bj.handler;

import java.util.Hashtable;

import javax.swing.table.AbstractTableModel;

public  class MyStatsHashTModel extends Hashtable<String, CellStatus> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7329948556403560278L;

	private AbstractTableModel model;

	private final MyStatsHashTModel instance = this;

	private String name = "";
	
	public MyStatsHashTModel(String name, final String[] dealercardsColumns, final String[] userRows) {
		this.name = name;
		for (String head : dealercardsColumns){
			for (String  row : userRows){
				this.put(row  + "_" + head, new CellStatus("<html>a<br>b</html>"));
			}
		}
	
		model = new AbstractTableModel(){

			/**
			 * 
			 */
			private static final long serialVersionUID = -7472952727976081002L;

			@Override
			public int getColumnCount() {
				return dealercardsColumns.length+1; //plus one for the first column with the user's card
			}

			@Override
			public int getRowCount() {
				return userRows.length;   //header row doesn't count
			}

			
			@Override
			public Object getValueAt(int row, int col) {
				if (col==0)
					return "" + userRows[row] ;
				instance.get(userRows[row] + "_" + dealercardsColumns[col-1]).setAsHtml();
				return instance.get(userRows[row] + "_" + dealercardsColumns[col-1]);
			}
			
			@Override
			public String getColumnName(int column) {
				if (column==0)
					return "";
		        return dealercardsColumns[column-1];
		      }

		};
	}

	/**
	 * @return the model
	 */
	public AbstractTableModel getModel() {
		return model;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	
}
