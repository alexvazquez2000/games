package org.alex.games.bj.handler;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellRenderer;

import org.alex.games.bj.beans.UserAction;


public class DashBoardPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8060946866572157115L;
	
	private static DashBoardPanel instance = null; 
	
	//private static DecisionTableCellRenderer cellRender = new DecisionTableCellRenderer();
	
	//http://exampledepot.8waytrips.com/egs/javax.swing.table/ComboBox.html

	private JTabbedPane decisionTableTabPanel = null;
	private JTabbedPane resultsTableTabPanel = null;

	
	private static final JTable hard = new JTable(Decision.getHardDT());
	
	private static final JTable soft = new JTable(Decision.getSoftDT());
	
	//private static final JTable pair = new JTable(Decision.getPairsDT());
	
	private JLabel money;
	
	private static ResultsTableCellRenderer resultsCellRender = new ResultsTableCellRenderer();
	
	private static final JTable hardResults = new JTable(StatsTable.getInstance().getHardModel()){			
		private static final long serialVersionUID = 1449249866538571339L;
		public TableCellRenderer getCellRenderer(int row, int col){
				return resultsCellRender;
		}
	};

	private static final JTable softResults = new JTable(StatsTable.getInstance().getSoftModel()){			
		private static final long serialVersionUID = 5146521650291032111L;
		public TableCellRenderer getCellRenderer(int row, int col){
				return resultsCellRender;
		}
	};

//	private static final JTable pairsResults = new JTable(StatsTable.getInstance().getPairsModel()){			
//		private static final long serialVersionUID = -4356374910383315645L;
//		public TableCellRenderer getCellRenderer(int row, int col){
//				return resultsCellRender;
//		}
//	};


	private DashBoardPanel(){
		int widthDecisionPanels = 550;
		int widthMaxDecisionPanels = 600;
		
		
		hard.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		hard.setDefaultRenderer(UserAction.class, new DecisionTableCellRenderer());
		hard.setDefaultEditor(UserAction.class, new DecisionTableCellEditor());
		JScrollPane hardScrollPane = new JScrollPane(hard);
		hardScrollPane.setPreferredSize(new Dimension(widthDecisionPanels, 325));
		hardScrollPane.setMinimumSize(new Dimension(200, 50));
		hardScrollPane.setMaximumSize(new Dimension(widthMaxDecisionPanels, 325));
		//hard.setFillsViewportHeight(true);
		
		soft.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		soft.setDefaultRenderer(UserAction.class, new DecisionTableCellRenderer());
		soft.setDefaultEditor(UserAction.class, new DecisionTableCellEditor());
		JScrollPane softScrollPane = new JScrollPane(soft);
		softScrollPane.setPreferredSize(new Dimension(widthDecisionPanels, 200));
		softScrollPane.setMinimumSize(new Dimension(200, 50));
		softScrollPane.setMaximumSize(new Dimension(widthMaxDecisionPanels, 200));
		//soft.setFillsViewportHeight(true);
		
//		pair.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		pair.setDefaultRenderer(UserAction.class, new DecisionTableCellRenderer());
//		pair.setDefaultEditor(UserAction.class, new DecisionTableCellEditor());
//		JScrollPane pairScrollPane = new JScrollPane(pair);
//		pairScrollPane.setPreferredSize(new Dimension(widthDecisionPanels, 300));
//		pairScrollPane.setMinimumSize(new Dimension(200, 50));
//		pairScrollPane.setMaximumSize(new Dimension(widthMaxDecisionPanels, 300));
		//pair.setFillsViewportHeight(true);
		
		int widthResultsPanels = 850;
		hardResults.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane hardResultScrollPane = new JScrollPane(hardResults);
		hardResultScrollPane.setPreferredSize(new Dimension(widthResultsPanels, 325));
		hardResultScrollPane.setMinimumSize(new Dimension(200, 50));
		
		softResults.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane softResultScrollPane = new JScrollPane(softResults);
		softResultScrollPane.setPreferredSize(new Dimension(widthResultsPanels, 200));
		softResultScrollPane.setMinimumSize(new Dimension(200, 50));

//		pairsResults.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		JScrollPane pairsResultScrollPane = new JScrollPane(pairsResults);
//		pairsResultScrollPane.setPreferredSize(new Dimension(widthResultsPanels, 300));
//		pairsResultScrollPane.setMinimumSize(new Dimension(200, 50));

		JLabel moneyLabel = new JLabel("Wallet");
		money = new JLabel("$" + 0);
		
		
		JPanel mainPanel = new JPanel( );
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		JPanel moneyPanel = new JPanel( );
		moneyPanel.setLayout(new BoxLayout(moneyPanel, BoxLayout.X_AXIS));
		moneyPanel.add(moneyLabel);
		moneyPanel.add(money);
		
		final JTabbedPane decisionTableTabPanel = new JTabbedPane( );
		decisionTableTabPanel.addTab("Hard", hardScrollPane);
		decisionTableTabPanel.addTab("Soft", softScrollPane);
//		decisionTableTabPanel.addTab("Pairs", pairScrollPane);
		
		final JTabbedPane resultsTableTabPanel = new JTabbedPane( );
		resultsTableTabPanel.addTab("Hard", hardResultScrollPane);
		resultsTableTabPanel.addTab("Soft", softResultScrollPane);
//		resultsTableTabPanel.addTab("Pairs", pairsResultScrollPane);
		
		for (int i=0;i<1; i++){
			JTable pairsResults = new JTable(StatsTable.getInstance().getPairsModel()){			
				private static final long serialVersionUID = -4356374910383315645L;
				public TableCellRenderer getCellRenderer(int row, int col){
						return resultsCellRender;
				}
			};

			pairsResults.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			JScrollPane pairsResultScrollPane = new JScrollPane(pairsResults);
			pairsResultScrollPane.setPreferredSize(new Dimension(widthResultsPanels, 300));
			pairsResultScrollPane.setMinimumSize(new Dimension(200, 50));
			
			JTable pair = new JTable(Decision.getPairsDT());
			pair.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			pair.setDefaultRenderer(UserAction.class, new DecisionTableCellRenderer());
			pair.setDefaultEditor(UserAction.class, new DecisionTableCellEditor());
			JScrollPane pairScrollPane = new JScrollPane(pair);
			pairScrollPane.setPreferredSize(new Dimension(widthDecisionPanels, 300));
			pairScrollPane.setMinimumSize(new Dimension(200, 50));
			pairScrollPane.setMaximumSize(new Dimension(widthMaxDecisionPanels, 300));
			
			decisionTableTabPanel.addTab("Pairs", pairScrollPane);
			resultsTableTabPanel.addTab("Pairs", pairsResultScrollPane);
		}

		decisionTableTabPanel.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent changeEvent) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
				int index = sourceTabbedPane.getSelectedIndex();
				System.out.println("Tab changed to: " + sourceTabbedPane.getTitleAt(index));
				resultsTableTabPanel.setSelectedIndex(index);
			}
		});

		resultsTableTabPanel.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent changeEvent) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
				int index = sourceTabbedPane.getSelectedIndex();
				System.out.println("Tab changed to: " + sourceTabbedPane.getTitleAt(index));
				decisionTableTabPanel.setSelectedIndex(index);
			}
		});
		
		JButton saveDecisionButton = new JButton("Save");
		saveDecisionButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//save
				Decision.saveTables();
			}
		});
		
		
		JPanel chartPanel = new JPanel( );
		chartPanel.setLayout(new BoxLayout(chartPanel, BoxLayout.X_AXIS));
		chartPanel.add(decisionTableTabPanel);
		chartPanel.add(resultsTableTabPanel);
		
		mainPanel.add(moneyPanel);
		mainPanel.add(chartPanel);
		mainPanel.add(saveDecisionButton);
		
		add(mainPanel);
	}


	public static DashBoardPanel getInstance() {
		if (instance==null){
			synchronized (DashBoardPanel.class) {
				instance = new DashBoardPanel();				
			}		
		}
		return instance;
	}


	public void SetMoney(String mmString) {
		money.setText(mmString);
		
	}
}
