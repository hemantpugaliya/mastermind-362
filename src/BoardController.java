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
	private int currentGuessRow = -1;
	private int currentFeedbackRow = -1;
	
	private JButton undo;
	private JButton done;
	private JButton clear;
	
	private boolean settingSolution;
	private boolean guessState;
	private boolean computer;
	private boolean logging;
	
	private boolean buttonsOn = true;
	
	private MastermindGame game;
	
	private int selectedPeg = 8;
	
	private Timer timer = new Timer();
	private int time = 0;
	
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
	public BoardController(MastermindGame _game, JButton[][] _guess, JButton[][] _feed, JToggleButton[] _pegs,
			JButton[] _solution, JButton _eye, JButton _undo, JButton _done, JButton _clear,
			JPanel _guessPanel, JPanel _feedbackPanel, JPanel _pegButtons, JLabel _instruction){
		
		guessRows = _guess;
		feedbackRows = _feed;
		guessPegs = _pegs;
		solutionSet = _solution;
		eye = _eye;
		game = _game;
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
		
		if(row == currentGuessRow && selectedPeg < 6){
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
		
		if(row == currentFeedbackRow){
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
			
			//only react if row is full
			if(full){
				for(int i = 0; i < 4; i++){
					solutionSet[i].setIcon(new javax.swing.ImageIcon("icons/gray3.png"));
				}
				settingSolution = false;
				guessState = true;
				currentGuessRow = 9;
				currentFeedbackRow = 9;
				
				
				if(computer){
					guessState = false;
					askForComputerGuess();
				}
				else
					instruction.setText("Codebreaker's Turn");
			}
		}
		else if(guessState){	
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
				game.makeGuess(guess);
				currentGuessRow -= 1;
				resetCurrentGuess();
				guessPanel.setVisible(false);
				feedbackPanel.setVisible(true);
				guessState = false;
				instruction.setText("Codemaker's Turn");
			}
		}
		else{
			ArrayList<PegColor> feedback = new ArrayList<PegColor>();
			
			for(int i = 0; i < 4; i++){
				feedback.add(PegColor.values()[currentFeedback[i]]);
			}
			
			int gameState = game.giveFeedback(feedback);
			
			currentFeedbackRow -= 1;
			resetCurrentFeedback();
			feedbackPanel.setVisible(false);
			guessPanel.setVisible(true);
			guessState = true;

			
			if(looking){
				closeEye();
			}
			
			//checks for winning conditions
			if(gameState == 1){
				instruction.setText("Codemaker Wins!");
				openEye();
				turnButtonsOff();
				guessState = false;
				if(logging)
					game.stopLogging();
			}
			else if(gameState == 2){
				instruction.setText("Codebreaker Wins!");
				openEye();
				turnButtonsOff();
				guessState = false;
				if(logging)
					game.stopLogging();
			}	
			else if(computer){
				askForComputerGuess();
			}
			else{
				instruction.setText("Codebreaker's Turn");
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
		else if(guessState){
			for(int i = 0; i < 4; i++){
				guessRows[currentGuessRow][i].setIcon(new javax.swing.ImageIcon("icons/gray.png"));
				resetCurrentGuess();
			}
		}
		else{
			for(int i = 0; i < 4; i++){
				feedbackRows[currentFeedbackRow][i].setIcon(new javax.swing.ImageIcon("icons/gray2.png"));
				resetCurrentFeedback();
			}
		}
	}
	
	/**
	 * Removes current pegs from the board and returns to the last finished guessing turn.
	 */
	public void undo(){
		if(guessState && currentGuessRow <= 8){
			clear();
			currentGuessRow += 1;			
			guessState = false;
			currentFeedbackRow += 1;
			clear();
			guessState = true;
			clear();
			game.undo();
		}
		else if(currentGuessRow <= 8){
			clear();
			currentGuessRow += 1;
			guessState = true;
			clear();
			feedbackPanel.setVisible(false);
			guessPanel.setVisible(true);
			instruction.setText("Codebreaker's Turn");
			game.undo();
		}
	}
	
	/**
	 * checks whether to make solution visible or not
	 */
	public void eyeball(){
		if(!looking && !guessState)
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
		settingSolution = true;
		guessState = false;
		closeEye();
		looking = false;
		
		feedbackPanel.setVisible(false);
		guessPanel.setVisible(true);
		
		if(!buttonsOn){
			turnButtonsOn();
		}
		
		if(computer){
			undo.removeActionListener(this);
		}
	}
	
	/**
	 * Places the computers guess of the board.
	 * @param guess
	 */
	public void placeComputerGuess(ArrayList<PegColor> guess){
		for(int i = 0; i < 4; i++){
			currentGuess[i] = guess.get(i).ordinal();
			guessRows[currentGuessRow][i].setIcon(new javax.swing.ImageIcon("icons/"+currentGuess[i]+".png"));	
		}
		
		currentGuessRow -= 1;
		
		guessState = false;
		feedbackPanel.setVisible(true);
		guessPanel.setVisible(false);
		done.addActionListener(this);
		clear.addActionListener(this);
		instruction.setText("Codemaker's Turn");
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
	 * Sets computer boolean to true so the UI responds correctly
	 * to moves made by the computer.
	 */
	public void setCodebreakerComputer(){
		computer = true;
		if(guessState){
			askForComputerGuess();
		}
		guessState = false;
		undo.removeActionListener(this);
	}
	
	/**
	 * Sets computer boolean to false.
	 */
	public void setCodebreakerHuman(){
		computer = false;
		undo.addActionListener(this);
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
		
		if(!computer){
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
	    timer.schedule(new ComputerTimer(), seconds * 1000);
	  }
	
	/**
	 * Class that does the actual Timer threading magic.
	 * 
	 * @author kuglics
	 *
	 */
	class ComputerTimer extends TimerTask {
	    public void run() {
	      game.makeGuess(null); //notifies computer to make a guess
	    }
	  }

}
