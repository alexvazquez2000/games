package org.alex.games.bj.handler;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import org.alex.games.bj.beans.UserAction;

public class DecisionTableCellEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {

	private UserAction userAction;
	private List<UserAction> listOfActions = new ArrayList<UserAction>();
	
	public DecisionTableCellEditor(){
			for(UserAction a: UserAction.values()){
				listOfActions.add(a);
			}
	}
	
	@Override
	public Object getCellEditorValue() {		
		return this.userAction;
	}
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        if (value instanceof UserAction) {
            this.userAction = (UserAction) value;
        }
         
        JComboBox<UserAction> comboUserAction = new JComboBox<UserAction>();
         
        for (UserAction ua : UserAction.values()) {
        	comboUserAction.addItem(ua);
        }
         
        comboUserAction.setSelectedItem(userAction);
        comboUserAction.addActionListener(this);
         
//        if (isSelected) {
//        	comboUserAction.setBackground(table.getSelectionBackground());
//        } else {
//        	comboUserAction.setBackground(table.getSelectionForeground());
//        }
         
        return comboUserAction;
    }
 
	@Override
	public void actionPerformed(ActionEvent event) {
		Object eventSource = event.getSource();
		
		//if (eventSource instanceof JComboBox){
			JComboBox<UserAction> comboCountry =  (JComboBox<UserAction>) eventSource;
			this.userAction = (UserAction) comboCountry.getSelectedItem();
			System.out.println("action=" + event.toString() + "   '" + this.userAction.name());
		//}
	}
}
