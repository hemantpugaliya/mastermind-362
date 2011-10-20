import java.awt.event.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

/**
 * BoardController
 * 
 * This class works as the controller for the view.
 * All logic for updating the UI is done here.
 * 
 * @author Jim Kuglics
 *
 */

public class BoardController implements ActionListener{
	
	private BoardView view;
	
	private JButton[][] guessRows;
	private JButton[][] feedbackRows;
	private JToggleButton[] guessPegs;
	private JButton[] solutionSet;
	private JButton eye;
	
	private boolean looking;
	
	private JPanel guessPanel;
	private JPanel feedbackPanel;
	
	private JLabel instruction;
	
	private int[] currentGuess = new int[4];
	private int[] currentFeedback = new int [4];
	private int[] solution = new int[4];
	
	private JButton undo;
	private JButton done;
	private JButton clear;
	
	private boolean settingSolution;
	private boolean guessing;
	private boolean computerCB;
	private boolean computerCM;
	private boolean logging;
	private boolean gameOver;
	
	private boolean buttonsOn = true;
	
	private int selectedPeg = 8;
	
	private Timer timer = new Timer();
	private int time = 0;
	
	private GameState currState;
	private GameState nextState;
	
	private CodeBreakerFactory cbFactory;
	private CodeMakerFactory cmFactory;
	
	/**
	 * Creates a new BoardController and adds ActionListener's to appropriate items
	 * @param _game - MastermindGame
	 * @param _guess - 2d array of buttons used to place guess pegs
	 * @param _feed - 2d array of buttons used to place feedback pegs
	 * @param _pegs - used to select pegs
	 * @param _solution - buttons used to add solution
	 * @param _eye - shows solution
	 * @param _undo - used to undo move
	 * @param _done - used to submit a move
	 * @param _clear - clears a row
	 * @param _guessPanel - contains guess pegs
	 * @param _feedbackPanel - contains feedback pegs
	 * @param _pegButtons - contains guessPanel and feedbackPanel
	 * @param _instruction - displays game information
	 */
	public BoardController(BoardView _view, JButton[][] _guess, JButton[][] _feed, JToggleButton[] _pegs,
			JButton[] _solution, JButton _eye, JButton _undo, JButton _done, JButton _clear,
			JPanel _guessPanel, JPanel _feedbackPanel, JPanel _pegButtons, JLabel _instruction,
			int _curGuessRow, int _curFeedbackRow){
		
		view = _view;
		
		guessRows = _guess;
		feedbackRows = _feed;
		guessPegs = _pegs;
		solutionSet = _solution;
		eye = _eye;
		instruction = _instruction;
		
		eye.addActionListener(this);
		eye.setActionCommand("e");
		
		undo = _undo;
		undo.addActionListener(this);
		undo.setActionCommand("u");
		
		done = _done;	
		done.addActionListener(this);
		done.setActionCommand("d");
		
		clear = _clear;
		clear.addActionListener(this);
		clear.setActionCommand("c");
		
		guessPanel = _guessPanel;
		feedbackPanel = _feedbackPanel;
		
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 4; j++){
				guessRows[i][j].addActionListener(this);
				String command = Integer.toString(i) + Integer.toString(j);
				guessRows[i][j].setActionCommand("g"+command);
				feedbackRows[i][j].addActionListener(this);
				feedbackRows[i][j].setActionCommand("f"+command);
			}
		}		
		
		for(int i = 0; i < 8; i++){
			guessPegs[i].removeActionListener(this);
			guessPegs[i].addActionListener(this);
			String command = Integer.toString(i);
			guessPegs[i].setActionCommand("p"+command);
		}
		
		for(int i = 0; i < 4; i++){
			solutionSet[i].addActionListener(this);
			String command = Integer.toString(i);
			solutionSet[i].setActionCommand("s"+command);
		}
		
		resetCurrentGuess();
		resetCurrentFeedback();
	}
	
	/**
	 * Responds to all input from user accordingly
	 */
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		char type = action.charAt(0);

		if(type == 'p'){
			selectPeg(action);
		}
		
		if(type == 'g' && selectedPeg != 8){
			placeGuessPeg(action);
		}
		
		if(type == 'f' && selectedPeg != 8){
			placeFeedbackPeg(action);
		}
		
		if(type == 's' && selectedPeg != 8){
			placeSolutionPeg(action);
		}
		
		if(type == 'd'){
			done();
		}
		
		if(type == 'c'){
			clear();
		}
		
		if(type == 'e'){
			eyeball();
		}
		
		if(type == 'u'){
			undo();
		}
	}
	
	/**
	 * Makes sure only one peg is selected at a time.
	 * @param p
	 */
	public void selectPeg(String p){
		char peg = p.charAt(1);
		int pegNum = Character.getNumericValue(peg);
		
		for(int i = 0; i < 8; i++){
			if(guessPegs[i].getModel().isSelected() && 
					i != pegNum){
				guessPegs[i].getModel().setSelected(false);
			}
		}
		
		if(selectedPeg != pegNum)
			selectedPeg = pegNum;
		else
			selectedPeg = 8;
	}
	
	/**
	 * Allows a guess peg to be placed on the current row of the board.
	 * @param p
	 */
	public void placeGuessPeg(String p){
		char r = p.charAt(1);
		int row = Character.getNumericValue(r);
		
		char g = p.charAt(2);
		int guess = Character.getNumericValue(g);
		
		if(row == view.getCurrentGuessRow() && selectedPeg < 6){
			guessRows[row][guess].setIcon(new javax.swing.ImageIcon("icons/"+selectedPeg+".png"));
			currentGuess[guess] = selectedPeg;
		}
	}
	
	/**
	 * Allows a feedback peg to be placed on the current row of the board.
	 * @param p
	 */
	public void placeFeedbackPeg(String p){
		char r = p.charAt(1);
		int row = Character.getNumericValue(r);
		
		char g = p.charAt(2);
		int guess = Character.getNumericValue(g);
		
		System.out.println(row+ " "+guess+" "+view.getCurrentFeedbackRow());
		
		if(row == view.getCurrentFeedbackRow()){
			System.out.println("?");
			int smallPeg = selectedPeg;
			if(smallPeg > 5){
				feedbackRows[row][guess].setIcon(new javax.swing.ImageIcon("icons/"+smallPeg+".png"));
				currentFeedback[guess] = smallPeg;
			}	
		}
	}
	
	/**
	 * Allows a solution peg to be placed in the solution row.
	 * @param p
	 */
	public void placeSolutionPeg(String p){
		char s = p.charAt(1);
		int guess = Character.getNumericValue(s);
		
		if(settingSolution){
			solutionSet[guess].setIcon(new javax.swing.ImageIcon("icons/"+selectedPeg+".png"));		
			solution[guess] = selectedPeg;
		}
	}
	
	/**
	 * Used whenever a move is finished or solution is set.
	 */
	public void done(){
		if(settingSolution){
			ArrayList<PegColor> solutionArray = new ArrayList<PegColor>();
			boolean full = false;	
			
			for(int i = 0; i < 4; i++){
				if(solution[i] != 8){
					solutionArray.add(PegColor.values()[solution[i]]);
					if(i == 3)
						full = true;
				}
				else{
					break;
				}
			}
			
			//only react if solution is full
			if(full){
				for(int i = 0; i < 4; i++){
					solutionSet[i].setIcon(new javax.swing.ImageIcon("icons/gray3.png"));
				}
				settingSolution = false;
				view.setCurrentGuessRow(9);
				view.setCurrentFeedbackRow(9);
				
				
				if(computerCB){
					guessing = true;
					askForComputerGuess();
				}
				else{
					guessing = true;
					instruction.setText("Codebreaker's Turn");
				}
			}
		}
		else if(guessing){	
			ArrayList<PegColor> guess = new ArrayList<PegColor>();
			boolean full = false;	
			
			for(int i = 0; i < 4; i++){
				if(currentGuess[i] != 8){
					guess.add(PegColor.values()[currentGuess[i]]);
					if(i == 3)
						full = true;
				}
				else{
					break;
				}
			}
			
			//only react if row is full
			if(full){
				currState.makeMove(guess);
				toggleGameState();
				guessing = false;
				view.setCurrentGuessRow(view.getCurrentGuessRow() - 1);
				resetCurrentGuess();
				guessPanel.setVisible(false);
				feedbackPanel.setVisible(true);
				instruction.setText("Codemaker's Turn");
			}
			
			if(computerCM){
				askForComputerGuess();
			}
		}
		else{
			ArrayList<PegColor> feedback = new ArrayList<PegColor>();
			
			for(int i = 0; i < 4; i++){
				feedback.add(PegColor.values()[currentFeedback[i]]);
			}
			
			currState.makeMove(feedback);
			toggleGameState();
			
			if(!gameOver){
				guessing = true;
				
				view.setCurrentFeedbackRow(view.getCurrentFeedbackRow() - 1);
				resetCurrentFeedback();
				feedbackPanel.setVisible(false);
				guessPanel.setVisible(true);
			
				if(looking){
					closeEye();
				}
				
				if(computerCB){
					askForComputerGuess();
				}
				else{
					instruction.setText("Codebreaker's Turn");
				}
			}
		}
		
		
		//unselects current peg
		for(int i = 0; i < 8; i++){
			if(guessPegs[i].getModel().isSelected()){
				guessPegs[i].getModel().setSelected(false);
			}
		}
		
		selectedPeg = 8;
	}
	
	/**
	 * Clears the current row the user is adding pegs to.
	 */
	public void clear(){
		if(settingSolution){
			for(int i = 0; i < 4; i++){
				solutionSet[i].setIcon(new javax.swing.ImageIcon("icons/gray3.png"));
				resetSolution();
			}
		}
		else if(guessing){
			for(int i = 0; i < 4; i++){
				guessRows[view.getCurrentGuessRow()][i].setIcon(new javax.swing.ImageIcon("icons/gray.png"));
				resetCurrentGuess();
			}
		}
		else{
			for(int i = 0; i < 4; i++){
				feedbackRows[view.getCurrentFeedbackRow()][i].setIcon(new javax.swing.ImageIcon("icons/gray2.png"));
				resetCurrentFeedback();
			}
		}
	}
	
	/**
	 * Removes current pegs from the board and returns to the last finished guessing turn.
	 */
	public void undo(){
		if(guessing && view.getCurrentGuessRow() <= 8){
			clear();
			view.setCurrentGuessRow(view.getCurrentGuessRow() + 1);			
			guessing = false;
			view.setCurrentFeedbackRow(view.getCurrentFeedbackRow() + 1);
			clear();
			guessing = true;
			clear();
			currState.undoTurn();
			toggleGameState();
		}
		else if(view.getCurrentGuessRow() <= 8){
			clear();
			view.setCurrentGuessRow(view.getCurrentGuessRow() + 1);
			guessing = true;
			clear();
			feedbackPanel.setVisible(false);
			guessPanel.setVisible(true);
			instruction.setText("Codebreaker's Turn");
			currState.undoTurn();
			toggleGameState();
		}
	}
	
	/**
	 * checks whether to make solution visible or not
	 */
	public void eyeball(){
		if(!looking && !guessing)
			openEye();
		else
			closeEye();
	}
	
	/**
	 * makes solution visible
	 */
	public void openEye(){
		for(int i = 0; i < 4; i++){
			solutionSet[i].setIcon(new javax.swing.ImageIcon("icons/"+solution[i]+".png"));
		}
		looking = true;	
	}
	
	/**
	 * makes solution invisible
	 */
	public void closeEye(){
		for(int i = 0; i < 4; i++){
			solutionSet[i].setIcon(new javax.swing.ImageIcon("icons/gray3.png"));
		}
		looking = false;
	}
	
	/**
	 * used when clearing a row, undoing a move, or resetting the game
	 */
	public void resetCurrentGuess(){
		for(int i = 0; i < 4; i++){
			currentGuess[i] = 8;
		}
	}
	
	/**
	 * used when clearing a row, undoing a move, or resetting the game
	 */
	public void resetCurrentFeedback(){
		for(int i = 0; i < 4; i++){
			currentFeedback[i] = 8;
		}
	}
	
	/**
	 * used when clearing solution or resetting the game
	 */
	public void resetSolution(){
		for(int i = 0; i < 4; i++){
			solution[i] = 8;
		}
	}

	/**
	 * Used to retrieve the computers guess.  Uses a Timer to wait
	 * a specified time.
	 */
	public void askForComputerGuess(){
		turnButtonsOff();
		instruction.setText("Computer is thinking...");
		setTimer(time);
		turnButtonsOn();
		done.removeActionListener(this);
		clear.removeActionListener(this);
	}
	
	/**
	 * Turns off all the buttons on the bottom panel.
	 */
	public void turnButtonsOff(){
		for(int i = 0; i < 8; i++){
			guessPegs[i].removeActionListener(this);
		}
		
		done.removeActionListener(this);
		clear.removeActionListener(this);
		undo.removeActionListener(this);
		eye.removeActionListener(this);
		
		buttonsOn = false;
	}
	
	/**
	 * Turns the buttons back on accordingly.
	 */
	public void turnButtonsOn(){
		for(int i = 0; i < 8; i++){
			guessPegs[i].addActionListener(this);
		}
		
		done.addActionListener(this);
		clear.addActionListener(this);
		
		if(!computerCB){
			undo.addActionListener(this);
		}
		
		eye.addActionListener(this);
		
		buttonsOn = true;
	}
	
	/**
	 * Sets logging boolean
	 * @param log
	 */
	public void setLogging(boolean log){
		logging = log;
	}
	
	/**
	 * Sets time to wait for computer move.
	 * @param seconds
	 */
	public void setTime(int seconds){
		time = seconds;
	}
	
	/**
	 * Waits to retrieve the computer's move for a specified time.
	 * @param seconds
	 */
	public void setTimer(int seconds) {
	    timer = new Timer();
	    timer.schedule(new ComputerTimer(this), seconds * 1000);
	  }
	
	public void toggleGameState(){
		System.out.println("meow");
		GameState temp;
		
		temp = currState;
		currState = nextState;
		nextState = temp;
	}
	
	public void endGame(int winner){
		if(winner == 1){
			instruction.setText("Codemaker Wins!");
		}
		else if(winner == 2){
			instruction.setText("Codebreaker Wins!");
		}
		
		openEye();
		turnButtonsOff();
		guessing = false;
		gameOver = true;
	}
	
	
	private int codeBreaker = 0;
	private int codeMaker = 0;
	
	public void setCodeBreaker(int p){
		codeBreaker = p;
		if(p != 0){
			computerCB = true;
			undo.removeActionListener(this);
		}
		else{
			computerCB = false;
		}
	}
	
	public void setCodeMaker(int p){
		codeMaker = p;
		if(p != 0){
			computerCM = true;
		}
		else{
			computerCM = false;
		}
	}
	
	/**
	 * Sets computer boolean to true so the UI responds correctly
	 * to moves made by the computer.
	 */
	public void setCodebreakerComputer(){
		computerCB = true;
		if(guessing){
			askForComputerGuess();
		}
	}
	
	/**
	 * Sets computer boolean to false.
	 */
	public void setCodebreakerHuman(){
		computerCB = false;
		if(!gameOver)
			undo.addActionListener(this);
	}
	
	/**
	 * Called when New Game is selected from menu.
	 * Resets the UI board back to original state.
	 */
	public void resetGame(){
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 4; j++){
				guessRows[i][j].setIcon(new javax.swing.ImageIcon("icons/gray.png"));
				feedbackRows[i][j].setIcon(new javax.swing.ImageIcon("icons/gray2.png"));
			}
		}
		resetCurrentGuess();
		resetCurrentFeedback();
		resetSolution();
		instruction.setText("Set The Code:");
		guessing = false;
		gameOver = false;
		closeEye();
		looking = false;
		
		feedbackPanel.setVisible(false);
		guessPanel.setVisible(true);
		
		MastermindBoard board = new MastermindBoard();
		board.registerObserver(view);
		cbFactory = new CodeBreakerFactory(board);
		cmFactory = new CodeMakerFactory(board);
		
		CodeMaker cm = cmFactory.setCodeMaker(codeMaker);
		CodeBreaker cb = cbFactory.setCodeBreaker(codeBreaker);
		
		ArrayList<MastermindCommand> history = new ArrayList<MastermindCommand>();
		LoggingState logging = new NoLogState();
		
		currState = new GuessState(this, board, logging, history, cb);
		nextState = new FeedbackState(this, board, logging, history, cm);
		
		if(!buttonsOn){
			turnButtonsOn();
		}
		
		if(computerCB){
			undo.removeActionListener(this);
		}
		
		if(computerCM){
			setComputerSolution(cm.getCode());
		}
		else{
			settingSolution = true;
		}

	}
	
	public void setComputerSolution(PegColor[] sol){
		for(int i = 0; i < 4; i++){
			solution[i] = sol[i].ordinal();
		}
		
		for(int i = 0; i < 4; i++){
			solutionSet[i].setIcon(new javax.swing.ImageIcon("icons/gray3.png"));
		}
		settingSolution = false;
		view.setCurrentGuessRow(9);
		view.setCurrentFeedbackRow(9);
			
		if(computerCB){
			guessing = true;
			askForComputerGuess();
		}
		else{
			guessing = true;
			instruction.setText("Codebreaker's Turn");
		}
	}
	
	/**
	 * Class that does the actual Timer threading magic.
	 * 
	 * @author kuglics
	 *
	 */
	class ComputerTimer extends TimerTask {
	    BoardController bc;
		
		public ComputerTimer(BoardController controller){
	    	bc = controller;
	    }
		
		public void run() {      
	    	currState.makeMove(null);
	    	if(guessing){
	    		view.setCurrentGuessRow(view.getCurrentGuessRow() - 1);
	    		toggleGameState();
	    		guessing = false;
	    	}
	    	else{
	    		toggleGameState();
	    		view.setCurrentFeedbackRow(view.getCurrentGuessRow() - 1);
	    	}
	    	
	    	done.addActionListener(bc);
			clear.addActionListener(bc);
	      
	    }
	  }

}
