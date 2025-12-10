package org.alex.games.bj.handler;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.alex.games.bj.beans.UserAction;

public class DecisionTableCellRenderer extends DefaultTableCellRenderer {

	/** */
	private static final long serialVersionUID = 1877692911453084514L;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
		String s = "";
		if (value instanceof UserAction) {
			s = ((UserAction) value).name();
			setText(s);
		}

		if (col == 0) {
			setForeground(Color.gray);
			setBackground(Color.blue);
		} else if (s.equalsIgnoreCase("STAY")) {
			setForeground(Color.gray);
			setBackground(Color.YELLOW);
		} else if (s.equalsIgnoreCase("SURRENDER")) {
			setForeground(Color.gray);
			setBackground(Color.RED);
		} else if (s.equalsIgnoreCase("DOUBLE")) {
			setForeground(Color.gray);
			setBackground(Color.BLUE);
		} else if (s.equalsIgnoreCase("HIT")) {
			setForeground(Color.gray);
			setBackground(Color.GREEN);
		} else if (s.equalsIgnoreCase("SPLIT")) {
			setForeground(Color.gray);
			setBackground(Color.CYAN);
		} else {
			if (isSelected) {
				setBackground(table.getSelectionBackground());
			} else {
				setBackground(table.getSelectionForeground());
			}
		}

		return this;
	}
}
