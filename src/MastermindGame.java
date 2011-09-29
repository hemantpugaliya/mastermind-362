import java.util.ArrayList;


/**
 * MastermindGame
 * 
 * @author Gabbie Burns
 * @contributor Jim Kuglics
 *
 */
public class MastermindGame extends Game {
	
	/**
	 * A set of commands used for logging different moves
	 */
	private ToggleLogCommand tLog = null;
	private GuessCommand gLog = null;
	private FeedbackCommand fLog = null;
	private UndoCommand uLog = null;
	
	/**
	 * The two players responsible for game play
	 */
	private MastermindPlayer maker = null;
	private MastermindPlayer breaker = new HumanCodeBreaker();
	
	/**
	 * A representation of the board
	 */
	protected MastermindBoard board = null;
	
	/**
	 * Indicates whether not logging is currently enabled
	 * TODO: needed?
	 */
	protected boolean logging = false;
	
	/**
	 * The current state of the game, encapsulating the turn information
	 */
	private GameState currState = null;
	
	/**
	 * The next state of the game, encapsulating the information for the next turn
	 */
	private GameState nextState = null;
	
	/**
	 * A reference for the controller that communicates with our UI
	 */
	private BoardController controller = null;
	
		
	/**
	 * Store a copy of the controller to use when informing the UI of computer-generated moves
	 * 
	 * @param cont
	 */
	public void setController( BoardController cont )
	{
		controller = cont;
	}
	
	/**
	 * Reset everything for a fresh game
	 * 
	 * @param _logging   whether or not logging is enabled
	 * @param filename   the name of the file to use for logging, if enabled
	 * @param _playerNum   an integer representing which kind of codebreaker
	 */
	public void newGame(boolean _logging, String filename, int _playerNum)
	{
		// Create the board
		board = new MastermindBoard();
	
		// Create the players
		maker = new CodeMaker( );		
		setCodeBreaker(_playerNum);
		
		// Create the game states to use during play
		currState = new GuessState();
		nextState = new FeedbackState();
		
		logging = _logging;
		
		// Start logging if applicable
		if( _logging )
		{
			startLogging(filename);
		}
			
	}
	
	/**
	 * Start logging the moves for the current game
	 * 
	 * @param filename   the file to be used for logging the current game
	 */
	public void startLogging( String filename )
	{
		logging = true;
		tLog = new ToggleLogCommand(filename);
		tLog.Execute();
		
		gLog = new GuessCommand();
		fLog = new FeedbackCommand();
		uLog = new UndoCommand(); 
		
		// Log the existing game history
		retroLog();
	}
	
	/**
	 * Stop logging the moves for the current game
	 */
	public void stopLogging()
	{
		tLog.Execute();
	}
	
	/**
	 * Propagate the most recent guess to the board and the logfile
	 * 
	 * @param guess   the most recent guess from the codebreaker
	 */
	public void makeGuess( ArrayList< PegColor > guess)
	{
		// If the guess is null, we are using a computer codebreaker
		if( guess == null )
		{
			guess = breaker.makeMove();
			controller.placeComputerGuess(guess);
		}
		
		// Notify the board
		board.newGuess(guess);
		
		// Log the guess
		if( logging )
		{
			gLog.setGuess(guess);
			gLog.Execute();	
		}
		
		toggleState();
		
	}
	
	/**
	 * Propagate the most recent feedback to the board and the logfile
	 * 
	 * @param feedback  the most recent feedback from the codemaker
	 */
	public int giveFeedback( ArrayList< PegColor > feedback )
	{
		// If the guess is null, we are using a computer codemaker
		if( feedback == null )
		{
			feedback = maker.makeMove();
		}
		
		// Notify the board
		board.newFeedback(feedback);
		
		// Log the feedback
		if( logging )
		{
			fLog.setFeedback(feedback);
			fLog.Execute();
		}
		
		toggleState();
		
		return board.checkWinLoss();
	}
	
	/**
	 * Undo the most recent feedback and/or guess, propagate to board and log file
	 */
	public void undo()
	{
		int numUndo = currState.undoTurn();
		
		// Check the game state and set numUndo
		
		// Notify the board
		board.undoMove(numUndo);
		
		// Log the undo
		if( logging )
		{
			uLog.setNumUndo(numUndo);
			uLog.Execute();
		}
		
		// When a move is undone, regardless of current state, the game
		// goes back to a guess state
		currState = new GuessState();
		nextState = new FeedbackState();
	}
	
	/**
	 * Facilitates the user's ability to select whether the codebreaker should
	 *  be another human player or a computer player
	 *  
	 * @param _playerNum   indicates which type of codebreaker is playing
	 * 
	 * @return   an appropriate CodeBreaker instance
	 */
	public void setCodeBreaker( int _playerNum )
	{
		//CodeBreaker breaker;
		
		switch( _playerNum )
		{
		
		case 0: breaker = new HumanCodeBreaker();
		break;
		
		case 1: breaker = new RandomCodeBreaker();
		break;
		
		case 2: breaker = new SmartCodeBreaker(this);
		break;
			
		default:
			breaker = new HumanCodeBreaker();
		}
		
		//return breaker;
		
	}
	
	/**
	 * Stores the solution entered by the human codemaker
	 * 
	 * @param solutionSet
	 */
	public void setSolution(ArrayList<PegColor> solutionSet ){
		board.newSolution(solutionSet);
	}
	
	/**
	 * Toggles the current state between game and feedback
	 */
	public void toggleState()
	{
		GameState temp;
		
		temp = currState;
		currState = nextState;
		nextState = temp;
	}
	
	/**
	 * Retroactively logs all of the guesses and feedback made in the game up to this point
	 */
	public void retroLog()
	{
		ArrayList<ArrayList<PegColor>> guessHist = board.getGuessHistory();
		ArrayList<ArrayList<PegColor>> fbHist = board.getFeedbackHistory();
		
		for(int i = 0; i < guessHist.size(); i++)
		{
			// Log the past guesses in order
			if( guessHist.get(i).size() > 0)
			{
				gLog.setGuess(guessHist.get(i));
				gLog.Execute();
			}
			
			// Log the past feedback in order
			// Note: There could be one less feedback than there are guesses
			if( fbHist.get(i).size() > 0)
			{
				fLog.setFeedback(fbHist.get(i));
				fLog.Execute();
			}
		}
		
	}
		
}
