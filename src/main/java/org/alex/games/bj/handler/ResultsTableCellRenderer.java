package org.alex.games.bj.handler;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ResultsTableCellRenderer extends DefaultTableCellRenderer{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1877692911453084514L;

	public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int col) {

      Component c = super.getTableCellRendererComponent(table, value,
               isSelected, hasFocus, row, col);
      Object valueAt = table.getModel().getValueAt(row, col);
      WInvsLOOSE_STATUS winning = WInvsLOOSE_STATUS.NA;
      //String s = "";
      if (valueAt instanceof CellStatus) {
    	  winning = ((CellStatus) valueAt).isWinning();
      //} else {
          //if (valueAt != null) {
           //   s = valueAt.toString();
           //}    	  
      }
      
      
      if (col == 0) {
          c.setForeground(Color.lightGray);
          c.setBackground(Color.black);    	  
      } else if (winning==WInvsLOOSE_STATUS.NA) {  //have not played
         c.setForeground(Color.black);
         c.setBackground(Color.gray);
      } else if (winning==WInvsLOOSE_STATUS.PUSH) {  //only pushes
          c.setForeground(Color.gray);
          c.setBackground(Color.cyan);
      } else if (winning==WInvsLOOSE_STATUS.WINNING) {  //winning
          c.setForeground(Color.blue);
          c.setBackground(Color.white);
      } else if (winning==WInvsLOOSE_STATUS.LOOSING) {  //loosing
           c.setForeground(Color.yellow);
           c.setBackground(Color.RED);
      } else {
         c.setForeground(Color.black);
         c.setBackground(Color.WHITE);
      }

      return c;
   }
}
