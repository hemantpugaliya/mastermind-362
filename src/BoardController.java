import java.awt.event.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

import client.MMGameClient;

import server.MMServerObservable;
import transferObjects.gameplay.MMFeedback;
import transferObjects.gameplay.MMGuess;
import transferObjects.networking.MMConnectAcknowledgedNotification;
import transferObjects.networking.MMConnectNotification;
import transferObjects.networking.MMDisconnectNotification;
import transferObjects.networking.exceptions.MMNetworkingException;

/**
 * BoardController
 * 
 * This class works as the controller for the view.
 * All logic for updating the UI is done here.
 * 
 * @author Jim Kuglics
 *
 */

public class BoardController implements ActionListener, MMServerObservable {
	
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
	private boolean gameOver = true;
	
	private boolean buttonsOn = true;
	
	private int selectedPeg = 8;
	
	private Timer timer = new Timer();
	private int time = 0;
	
	private GameState currState;
	private GameState nextState;
	
	private CodeBreakerFactory cbFactory;
	private CodeMakerFactory cmFactory;
	private LoggingState loggingState;
	private String getIP = null;
	private MMGameClient client;
	
	private boolean requestSent = false;
	private boolean requestReceived = false;
	private boolean networkedCM = false;
	private boolean networkedCB = false;
		
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
			solutionHider();
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
				
		if(row == view.getCurrentFeedbackRow()){
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
				
				if(computerCM){
					askForComputerGuess();
				}
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
	public void solutionHider(){
		if(!looking && !guessing)
			openEye();
		else
			closeEye();
	}
	
	/**
	 * makes solution visible
	 */
	public void openEye(){
		if(solution[0] == 8){
			for(int i = 0; i < 4; i++){
				solutionSet[i].setIcon(new javax.swing.ImageIcon("icons/gray.png"));
			}
		}
		else{
			for(int i = 0; i < 4; i++){
				solutionSet[i].setIcon(new javax.swing.ImageIcon("icons/"+solution[i]+".png"));
			}
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
		else if(winner == 0){
			instruction.setText("Game Ended");
		}
		
		openEye();
		turnButtonsOff();
		guessing = false;
		gameOver = true;
		
		requestSent = requestReceived = false;
		try
		{
			client.pushDisconnectNotification();
		}
		catch(MMNetworkingException e)
		{
			// Do nothing
		}
		
		menu.enableCodebreaker();
		menu.enableCodemaker();
	}
	
	
	private int codeBreaker = 0;
	private int codeMaker = 0;
	
	public void setCodeBreaker(int p){ 
		codeBreaker = p;
		if( p!= 0){
			computerCB = true;
			undo.removeActionListener(this);
		}
		else{
			computerCB = false;
		}
	}
	
	public void setCodeMaker(int p){
		codeMaker = p;
		if( p!= 0){
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
		
		startNetworkedGame();
		
	}
	
	public void setupGame()
	{
		instruction.setText("Set The Code:");
		settingSolution = true;
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
		
		if(!logging)
				loggingState = new NoLogState();
				
		currState = new GuessState(this, board, loggingState, history, cb, client, networkedCB );
		nextState = new FeedbackState(this, board, loggingState, history, cm, client, networkedCM );
		
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
	
	public void startLogging(String file){
		if(!gameOver){
			currState.startLogging(file);
		}
		else
			loggingState = new LogState(file);
	}
	
	public void stopLogging(){
		if(!gameOver){
			currState.stopLogging();
		}
		else
			loggingState = new NoLogState();
	}
	
	private MenuListener menu;
	public void setMenu(MenuListener _menu){
		menu = _menu;
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
	    		view.setCurrentFeedbackRow(view.getCurrentFeedbackRow() - 1);
	    		guessing = true;
	    	}
	    	
	    	if(!gameOver && computerCB && computerCM){
	    		askForComputerGuess();
	    	}
	    	else if(!gameOver){
	    		done.addActionListener(bc);
                clear.addActionListener(bc);
	    	}
	    }
	  }


	/**
	 * Deal with the acknowledgment of our previously sent request if appropriate
	 */
	public void receiveConnectAcknowledgedNotifiction(
			MMConnectAcknowledgedNotification arg0) {
		
		int acceptRequest = new JOptionPane().showConfirmDialog(null, 
		"Connection Acknowledged?");
		
		if(gameOver)
		{
			requestSent = true;
		
			if( requestReceived )
			{
				setupGame();
			}
		}
		
	}

	/**
	 * Deal with requests coming in by accepting and launching a networked game, if appropriate
	 */
	public void receiveConnectionRequest(MMConnectNotification arg0) {
		
		new JOptionPane().showConfirmDialog(null, 
		"Do you want to accept the request?");
		
		// Accept the request if a game is not currently happening
		if(gameOver)
		{
			if( requestSent )
			{
				client.acceptConnectionRequest(arg0);
				requestReceived = true;
				
				setupGame();
			}
			else
			{
				// If we haven't sent a request yet, ask the user to start a networked game
				int startChoice = new JOptionPane().showConfirmDialog(null, 
						"Accept request for networked game?");
				
				// If they say yes, then accept request and start networked game
				if(startChoice == 0)
				{
					client.acceptConnectionRequest(arg0);
					requestReceived = true;
			
					try
					{
						if(arg0.getType() == MMConnectNotification.ConnectionRequestType.CODE_BREAKER)
						{
							// If other player requested to be code breaker, request to be code maker
							getIP = new JOptionPane().showInputDialog("Please enter IP address?");
							client.requestToConnectToRemoteGameAsCodemaker(getIP);
						}
						else if(arg0.getType() == MMConnectNotification.ConnectionRequestType.CODE_MAKER)
						{
							// If other player requested to be code maker, request to be code breaker
							client.requestToConnectToRemoteGameAsCodebreaker(getIP);
						}
					}
					catch( MMNetworkingException e)
					{
						endNetworkGame();
					}
				}
			}
		}
	}

	/**
	 * During game play, if the remote client disconnects, notify the user and end the game
	 */
	public void receiveDisconnectNotification(MMDisconnectNotification arg0) {

		System.out.println("C");
		
		if( !gameOver )
		{
			try
			{
				client.pushDisconnectAcknolwegdedNotification();
			}
			catch(MMNetworkingException e)
			{
				// Do nothing
			}
			
			// Notify the user that the remote player disconnected
			JOptionPane gameOver = new JOptionPane();
			gameOver.showMessageDialog(null, "Remote player disconnected. Game over.");
			
			// End the game with no winner
			endGame(0);
		}
		
	}

	/**
	 * Convert the feedback into a form that matches the internal representation and then
	 * apply the move
	 * 
	 * @param arg0   the guess received
	 */
	public void receiveFeedback(MMFeedback arg0) {
		FeedbackAdapter feedback = new FeedbackAdapter();
		feedback.setColors(arg0.getColors());
		currState.makeMove(feedback.getInternalColors());
		
		toggleGameState();
		guessing = true;
		view.setCurrentFeedbackRow(view.getCurrentFeedbackRow() - 1);
		
		
		
	}

	/**
	 * Convert the guess into a form that matches the internal representation and then
	 * apply the move
	 * 
	 * @param arg0   the guess received
	 */
	public void receiveGuess(MMGuess arg0) {
		GuessAdapter guess = new GuessAdapter();
		guess.setColors(arg0.getColors());
		currState.makeMove(guess.getInternalColors());
		
		toggleGameState();
		guessing = false;
		view.setCurrentGuessRow(view.getCurrentGuessRow() - 1);
	}

	/**
	 * Does nothing in this implementation
	 */
	public void receiveRedo() {
		// Do nothing
		
	}

	/**
	 * Apply the undo
	 */
	public void receiveUndo() {
		undo();
	}
	
	/**
	 * Save an instance of the MMGameClient for networked play
	 */
	public void setNetworkClient(MMGameClient _client)
	{
		client = _client;
	}
	
	/**
	 * Initialize any networking connections by sending connection requests
	 */
	private void startNetworkedGame()
	{
		// If there is a networked player, request to connect
		if( codeBreaker == 3 )
		{
			// Request to register as a codemaker
			networkedCM = true;
			
			try
			{
				 getIP = new JOptionPane().showInputDialog("Please enter IP address?");
				client.requestToConnectToRemoteGameAsCodemaker(getIP);
			}
			catch(MMNetworkingException e)
			{
				endNetworkGame();
			}
			
			instruction.setText("Waiting for connection...");
		}
					
		else if( codeMaker == 2)
		{
			// Request to register as a codebreaker
			networkedCB = true;
			
			try
			{
				client.requestToConnectToRemoteGameAsCodebreaker(getIP);
			}
			catch(MMNetworkingException e)
			{
				endNetworkGame();
			}
			
			instruction.setText("Waiting for connection...");
		}
		else
		{
			// No networked players, start regular game
			setupGame();
		}
	}
	
	/**
	 * Unexpectedly end a game due to a network timeout, disconnection, or other error
	 */
	private void endNetworkGame()
	{
		// Notify the user that the remote player died
		JOptionPane gameOver = new JOptionPane();
		gameOver.showMessageDialog(null, "Remote player disconnected. Game over.");
		
		// End the game with no winner
		endGame(0);
	}
}
