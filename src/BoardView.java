import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

/**
 * BoardView
 * 
 * @author Jim Kuglics
 *
 */

public class BoardView extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JLabel instruction;
	private JPanel rows;
	private JPanel guessPanel;
	private JPanel feedbackPanel;
	private JPanel buttons;
	private JPanel pegsButtonsPanel;
	
	private JButton undo = new JButton("Undo");
	private JButton done = new JButton("Done");
	private JButton clear = new JButton("Clear");
	
	private JMenuItem newGame = new JMenuItem("New Game");
	private JMenuItem endGame = new JMenuItem("End Game");
	private JMenuItem setTimer = new JMenuItem("Set Timer");
	private JMenuItem exit = new JMenuItem("Exit");
	private JRadioButtonMenuItem[] codebreaker = new JRadioButtonMenuItem[4];
	private JRadioButtonMenuItem[] codemaker = new JRadioButtonMenuItem[3];
	private JCheckBoxMenuItem log = new JCheckBoxMenuItem("Logging");
	
	private RowsView rowsview = new RowsView();
	
	public BoardController controller;
	public MenuListener menuListener;
	
	private int currentGuessRow = -1;
	private int currentFeedbackRow = -1;
	
	/**
	 * Constructor
	 * @param _game instance of MastermindGame
	 */
	public BoardView(){
		instruction = new JLabel("Mastermind");
	}
	
	/**
	 * Creates the main GUI window
	 */
	public void create(){
		setSize(250,575);	
		setTitle("MASTERMIND");
		setLayout(new BorderLayout());
		
		add(instruction, BorderLayout.NORTH);
		instruction.setHorizontalAlignment(SwingConstants.CENTER);
		
		rowsview.create();
		rows = rowsview.getBoard();
		rows.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
		
		PegsView pegsview = new PegsView();
		pegsview.createGuessPanel();
		pegsview.createFeedbackPanel();
		
		pegsButtonsPanel = new JPanel(new BorderLayout());
		
		guessPanel = pegsview.getGuessPanel();
		feedbackPanel = pegsview.getFeedbackPanel();
		buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttons.add(undo);
		buttons.add(done);
		buttons.add(clear);
		
		pegsButtonsPanel.add(feedbackPanel, BorderLayout.CENTER);
		feedbackPanel.setVisible(false);
		
		pegsButtonsPanel.add(guessPanel, BorderLayout.NORTH);
		
		pegsButtonsPanel.add(buttons, BorderLayout.SOUTH);
		
		add(rows, BorderLayout.CENTER);
		add(pegsButtonsPanel, BorderLayout.SOUTH);
		
		createMenus();
		
		//create a BoardController and MenuListener
		controller = new BoardController(this, rowsview.getGuessRows(), rowsview.getFeedbackRows(),
				pegsview.getPegs(), rowsview.getSolution(), rowsview.getEye(), undo, done, clear,
				guessPanel, feedbackPanel, pegsButtonsPanel, instruction, currentGuessRow, currentFeedbackRow);
		menuListener = new MenuListener(newGame, exit, codebreaker, log, controller, setTimer, codemaker, endGame);
		
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Create a menu bar and add all the items to it
	 */
	public void createMenus(){
		JMenuBar bar = new JMenuBar();
		
		JMenu file = new JMenu("File");
		bar.add(file);
		file.add(newGame);
		file.add(endGame);
		file.add(exit);
			
		JMenu options = new JMenu("Options");
		JMenu codebreakerMenu = new JMenu("Codebreaker");
		JMenu codemakerMenu = new JMenu("Codemaker");	
		
		codebreaker[0] = new JRadioButtonMenuItem("Human", true);
		codebreaker[1] = new JRadioButtonMenuItem("Random");
		codebreaker[2] = new JRadioButtonMenuItem("Smart");
		codebreaker[3] = new JRadioButtonMenuItem("Networked");
		
		codemaker[0] = new JRadioButtonMenuItem("Human", true);
		codemaker[1] = new JRadioButtonMenuItem("Computer");
		codemaker[2] = new JRadioButtonMenuItem("Networked");
		
		codebreakerMenu.add(codebreaker[0]);
		codebreakerMenu.add(codebreaker[1]);
		codebreakerMenu.add(codebreaker[2]);
		codebreakerMenu.add(codebreaker[3]);
		codemakerMenu.add(codemaker[0]);
		codemakerMenu.add(codemaker[1]);
		codemakerMenu.add(codemaker[2]);
		
		options.add(codebreakerMenu);
		options.add(codemakerMenu);
		options.add(setTimer);
		options.add(log);
		
		bar.add(options);
		
		setJMenuBar(bar);
	}
	
	/**
	 * Places the computers guess of the board.
	 * @param guess
	 */
	public void placeComputerGuess(ArrayList<PegColor> guess){
		for(int i = 0; i < 4; i++){
			rowsview.getGuessRows()[currentGuessRow][i].setIcon(new javax.swing.ImageIcon("icons/"+
					guess.get(i).ordinal()+".png"));	
		}

		feedbackPanel.setVisible(true);
		guessPanel.setVisible(false);
		instruction.setText("Codemaker's Turn");
	}
	
	public void placeComputerFeedback(ArrayList<PegColor> feedback){
		for(int i = 0; i < 4; i++){
			rowsview.getFeedbackRows()[currentFeedbackRow][i].setIcon(new javax.swing.ImageIcon("icons/"+
					feedback.get(i).ordinal()+".png"));	
		}

		feedbackPanel.setVisible(false);
		guessPanel.setVisible(true);
		instruction.setText("Codebreaker's Turn");
	}
	
	
	/**
	 * @return controller - instance of BoardController
	 */
	public BoardController getController(){
		return controller;
	}
	
	public void setCurrentGuessRow(int num){
		currentGuessRow = num;
	}
	
	public void setCurrentFeedbackRow(int num){
		currentFeedbackRow = num;
	}
	
	public int getCurrentGuessRow(){
		return currentGuessRow;
	}
	
	public int getCurrentFeedbackRow(){
		return currentFeedbackRow;
	}
}
