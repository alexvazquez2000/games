package org.alex.games.bj.handler;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Hashtable;

import javax.swing.table.AbstractTableModel;

import org.alex.games.bj.beans.UserAction;

public class DecisionTableModel extends Hashtable<String, UserAction>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8356939167154626247L;

	private AbstractTableModel model;
	
	private final DecisionTableModel instance = this;

	private String[]  userRows;

	private String[]  dealercardsColumns;

	public Object get(String x){
		return null;
	}
	
	/**
	 * @param user
	 * @param dealer
	 * @return
	 */
	public UserAction get(String user, String dealer){
		return super.get(user + "_" + dealer);
	}

	/**
	 * @param user
	 * @param dealer
	 * @return
	 */
	public UserAction get(int points, String valueShortName) {
		return get(points+"", valueShortName);
	}

	public UserAction put(String string, UserAction action) {
		
		UserAction ua = super.put(string, action);
		model.fireTableDataChanged();
		return ua;
	}

	
	public DecisionTableModel(final String[] dealercardsColumns, final String[] userRows) {
		this.dealercardsColumns = dealercardsColumns;
		this.userRows = userRows;
//		for (String  row : userRows){				
//			for (String dealerCard : dealercardsColumns){
				;//this.put(row + "_" + dealerCard, UserAction.STAY);
//			}
//		}
		model = new AbstractTableModel(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public int getColumnCount() {
				return dealercardsColumns.length + 1;
			}

			@Override
			public int getRowCount() {
				return userRows.length;
			}

			@Override
			public Object getValueAt(int row, int col) {
				if (col==0)
					return "" + userRows[row] ;
				//UserAction action = instance.get(dealercardsColumns[col-1] + "_" + userRows[row]);
				UserAction action = instance.get( userRows[row] , dealercardsColumns[col-1]);
				if (action==null) return null;
				return action;
			}

			@Override
			public boolean isCellEditable(int row, int col) {
				//Note that the data/cell address is constant,
				//no matter where the cell appears onscreen.
				if (col ==0 )
					return false;
				return true;
				//new DecisionTableCellEditor();
			}

			/*
			 * Don't need to implement this method unless your table's
			 * data can change.
			 */
			@Override
			public void setValueAt(Object value, int row, int col) {
				
				try {
					UserAction action = instance.put(userRows[row] + "_" + dealercardsColumns[col-1], (UserAction) value);
					//UserAction action = instance.get( userRows[row] + "_" + dealercardsColumns[col-1] );
					System.out.println("SetValueAt(" + row + "," + col + ")=" +action + "-->" +(UserAction) value);
					action =  (UserAction) value;
					fireTableCellUpdated(row, col);
				} catch (Exception e){
					e.printStackTrace();
				}
			}

			@Override
			public String getColumnName(int column) {
				if (column==0)
					return "";
				return dealercardsColumns[column-1];
			}
			
			 /*
	         * JTable uses this method to determine the default renderer/
	         * editor for each cell.  If we didn't implement this method,
	         * then the last column would contain text ("true"/"false"),
	         * rather than a check box.
	         */
	        public Class<?> getColumnClass(int col) {
	            //return getValueAt(0, c).getClass();
	        	if (col==0)
					return String.class;
				return UserAction.class;
	        }
		};
	}
	
	/**
	 * @return the model
	 */
	public AbstractTableModel getModel() {
		return model;
	}

	
	public void save(String label, OutputStreamWriter out) throws IOException {
		if (out==null) return;
		for (String  row : userRows){				
			for (String dealerCard : dealercardsColumns){
				String key = row + "_" + dealerCard;
				UserAction action = this.get(row, dealerCard);
				out.write(label + "\t" + key + "\t" + action.name()+"\n");
			}
		}
		
	}



}
