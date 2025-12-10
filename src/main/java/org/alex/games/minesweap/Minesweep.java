package org.alex.games.minesweap;

import javax.swing.JFrame;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Minesweep implements java.awt.event.MouseListener {
	enum CellState {
		EMPTY, MINE, MINE_FLAGGED, NUMBER, FLAGGED, REVEALED
	};

	private static final int ROWS = 10;
	private static final int COLS = 10;
	Random random = new Random();

	int mines = 10;
	CellState[][] board = new CellState[ROWS][COLS];
	int[][] minesNextNum = new int[ROWS][COLS];
	JPanel[][] cells = new JPanel[ROWS][COLS];
	boolean gameOver = false;
	int numberOfFlags = 0;

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		frame.setTitle("Minesweeper");
		frame.setSize(400, 400);
		Minesweep minesweep = new Minesweep();
		JPanel panel = minesweep.createMainPanel();
		frame.add(panel);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setFocusable(true);
		frame.setFocusableWindowState(true);
		frame.requestFocus();
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					System.exit(0);
				}
			}
		});

	}

	private JPanel createMainPanel() {
		JPanel mainPanel = new JPanel();
		initializeBoard();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(Color.DARK_GRAY);
		mainPanel.setPreferredSize(new Dimension(400, 400));
		mainPanel.requestFocus();
		mainPanel.add(createCellsPanel(), BorderLayout.CENTER);
		return mainPanel;
	}

	private Component createCellsPanel() {
		JPanel cellsPanel = new JPanel();
		cellsPanel.setLayout(new GridLayout(ROWS, COLS));
		cellsPanel.setBackground(Color.LIGHT_GRAY);
		cellsPanel.setPreferredSize(new Dimension(400, 400));
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				cells[i][j] = new JPanel();
				JPanel cell = cells[i][j];
				cell.setName(i + "\t" + j);
				cell.setBackground(Color.GRAY);
				cell.setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLACK));
				cell.setPreferredSize(new Dimension(40, 40));
				boolean isDebug = isDebuggingEnabled();
				if (isDebug) {
					// Reveal values - for debugging only
					String cellText = " ";
					if (board[i][j] == CellState.MINE) {
						cellText = "M"; // Display 'M' for mines
					} else if (minesNextNum[i][j] > 0) {
						cellText = String.valueOf(minesNextNum[i][j]);
					}
					cell.add(new JLabel(cellText));
				}
				cell.addMouseListener(this);
				cellsPanel.add(cell);
			}
		}
		return cellsPanel;
	}

	protected void revealSurroundingCells(int i, int j) {
		// This method should reveal surrounding cells if the clicked cell has no
		// adjacent mines
		for (int row = i - 1; row <= i + 1; row++) {
			for (int col = j - 1; col <= j + 1; col++) {
				if (isValidCell(row, col) && board[row][col] != CellState.MINE
						&& board[row][col] != CellState.MINE_FLAGGED && board[row][col] != CellState.REVEALED) {
					// Reveal the cell
					board[row][col] = CellState.REVEALED;
					// Update the cell's visual representation
					JPanel cell = cells[row][col];
					cell.setBackground(Color.GREEN);
					int adjacentMines = minesNextNum[row][col];
					if (adjacentMines > 0) {
						cell.removeAll(); // Remove the previous label
						JLabel label = new JLabel(String.valueOf(adjacentMines));
						cell.add(label);
						cell.revalidate(); // Refresh the cell to show the label
					} else {
						// If no adjacent mines, recursively reveal surrounding cells
						revealSurroundingCells(row, col);
					}
				}
			}
		}
	}

	private void initializeBoard() {
		numberOfFlags = 0;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				board[i][j] = CellState.EMPTY;
				// Initialize the numbers around the mines
				minesNextNum[i][j] = 0;
			}
		}
		// Initialize the board with mines and numbers
		int i = 0;
		while (i < mines) {
			int row = random.nextInt(ROWS);
			int col = random.nextInt(COLS);
			if (board[row][col] == CellState.EMPTY) {
				board[row][col] = CellState.MINE;
				i++;
				updateNumbers(row, col);
			}

		}
	}

	/**
	 * @return true if running in debug mode
	 */
	public static boolean isDebuggingEnabled() {
		List<String> arguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
		for (String arg : arguments) {
			if (arg.contains("jdwp")) {
				return true;
			}
		}
		return false;
	}

	private void updateNumbers(int row, int col) {
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (isValidCell(row + i, col + j) && board[row + i][col + j] != CellState.MINE) {
					minesNextNum[row + i][col + j]++;
				}
			}
		}
	}

	private boolean isValidCell(int row, int col) {
		return row >= 0 && row < ROWS && col >= 0 && col < COLS;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent evt) {
		if (gameOver) {
			// Ignore clicks if the game is over
			return;
		}
		// Handle cell click
		JPanel cell = (JPanel) evt.getSource();
		int rowIndex = -1, colIndex = -1;
		String[] indices = cell.getName().split("\t");
		if (indices.length == 2) {
			try {
				rowIndex = Integer.parseInt(indices[0]);
				colIndex = Integer.parseInt(indices[1]);
			} catch (NumberFormatException ex) {
				System.err.println("Invalid cell indices: " + cell.getName());
				return;
			}
		} else {
			System.err.println("Cell name does not contain valid indices: '" + cell.getName() + "'");
			return;
		}
		System.out.println("Cell clicked: [" + rowIndex + "][" + colIndex + "] button = " + evt.getButton());
		if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
			// Check game state and reveal cell
			if (board[rowIndex][colIndex] == CellState.MINE || board[rowIndex][colIndex] == CellState.MINE_FLAGGED) {
				// If the cell is a mine, end the game
				gameOver = true;
				System.out.println("Game Over! You hit a mine.");
				// Show game over message and reveal all mines
				for (int r = 0; r < ROWS; r++) {
					for (int c = 0; c < COLS; c++) {
						if (board[r][c] == CellState.MINE) {
							cells[r][c].setBackground(Color.ORANGE);
						} else if (board[r][c] == CellState.EMPTY) {
							// cells[r][c].getComponent(r * COLS + c).setBackground(Color.GRAY);
						}
					}
				}
				// Highlight the clicked mine cell
				cell.setBackground(Color.RED);

			} else if (board[rowIndex][colIndex] == CellState.EMPTY || board[rowIndex][colIndex] == CellState.FLAGGED) {
				board[rowIndex][colIndex] = CellState.REVEALED;
				cell.setBackground(Color.GREEN);
				// Optionally, display the number of adjacent mines
				int adjacentMines = minesNextNum[rowIndex][colIndex];
				if (adjacentMines > 0) {
					cell.removeAll(); // Remove the previous label
					JLabel label = new JLabel(String.valueOf(adjacentMines));
					cell.add(label);
					cell.revalidate(); // Refresh the cell to show the label
				} else {
					// If no adjacent mines, reveal surrounding cells (not implemented here)
					System.out.println("No adjacent mines, consider revealing surrounding cells.");
					revealSurroundingCells(rowIndex, colIndex);
				}
			}
		} else if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
			// Right click
			System.out
					.println("Right click on cell: [" + rowIndex + "][" + colIndex + "]= " + board[rowIndex][colIndex]);
			// cell.setBackground(Color.BLUE);
			// Handle flagging/unflagging the cell
			if (board[rowIndex][colIndex] == CellState.REVEALED) {
				// Ignore right-click on revealed cells
				return;
			}
			if (board[rowIndex][colIndex] == CellState.EMPTY) {
				// Flag the cell
				board[rowIndex][colIndex] = CellState.FLAGGED;
				numberOfFlags++;
				JLabel flagLabel = new JLabel("F"); // Display 'F' for flagged cells
				cell.add(flagLabel);
				cell.setBackground(Color.BLUE);
				cell.revalidate();
			} else if (board[rowIndex][colIndex] == CellState.MINE) {
				// Flag the cell
				board[rowIndex][colIndex] = CellState.MINE_FLAGGED;
				numberOfFlags++;
				JLabel flagLabel = new JLabel("F"); // Display 'F' for flagged cells
				cell.add(flagLabel);
				cell.setBackground(Color.BLUE);
				cell.revalidate();
			} else if (board[rowIndex][colIndex] == CellState.FLAGGED) {
				// Unflag the cell
				board[rowIndex][colIndex] = CellState.EMPTY;
				numberOfFlags--;
				cell.removeAll(); // Remove the flag label
				cell.setBackground(Color.GRAY); // Reset background color
				cell.revalidate();

			} else if (board[rowIndex][colIndex] == CellState.MINE_FLAGGED) {
				// Unflag the cell
				board[rowIndex][colIndex] = CellState.MINE;
				numberOfFlags--;
				cell.removeAll(); // Remove the flag label
				cell.setBackground(Color.GRAY); // Reset background color
				cell.revalidate();

			}

			if (numberOfFlags == mines && checkAllFlags()) {
				System.out.println("Congratulations! You flagged all mines.");
				// Optionally, you can add logic to end the game or show a message
				gameOver = true;
				// TODO: Show a message or highlight the flagged cells
			}

		}
		evt.consume(); // Consume the event to prevent further processing

	}

	private boolean checkAllFlags() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if (board[i][j] == CellState.MINE || board[i][j] == CellState.FLAGGED
						|| board[i][j] == CellState.EMPTY) {
					// Found a mine that is not flagged
					// or a flagged cell that is not a mine
					System.out.println("Found a mine that is not flagged or a flagged cell that is not a mine at [" + i
							+ "][" + j + "]= " + board[i][j]);
					return false;
				}
			}
		}
		System.out.println("All mines are flagged correctly if " + numberOfFlags + " == " + mines);
		return numberOfFlags == mines; // All mines are flagged correctly
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
