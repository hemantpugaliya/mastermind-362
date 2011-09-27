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
	private MastermindPlayer breaker = null;
	
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
	
	private BoardView gameView = null;
	private BoardController controller = null;
	
	
	public MastermindGame()
	{
		// Create the UI
		BoardView gameView = new BoardView(this);
		gameView.create();
		controller = gameView.getController();
		newGame(false, 0);
	}
	
	public void newGame(boolean _logging, int _playerNum)
	{
		board = new MastermindBoard();
		
		// Query UI for player types selected...return an int?
		// Switch statement to create codebreaker based on input
		maker = new CodeMaker( );
		
		breaker = setCodeBreaker(_playerNum);
		
		// Create the game states to use during play
		currState = new GuessState( breaker );
		nextState = new FeedbackState( maker );
		
		// Query UI for logging status
		// if( logging )
		// {
		//		prompt for file name?
		//		startLogging( filename );
		// }
		
		// Get solution
		
		logging = _logging;
			
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
		}
		
		// Notify the board
		board.newGuess(guess);
		
		// Log the guess
		if( logging )
		{
			gLog.setGuess(guess);
			gLog.Execute();	
		}
		
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
		
		return board.checkWinLoss();
	}
	
	/**
	 * Undo the most recent feedback and/or guess, propagate to board and log file
	 */
	public void undo()
	{
		int numUndo = 0;
		
		// Check the game state and set numUndo
		
		// Notify the board
		board.undoMove();
		
		// Log the undo
		if( logging )
		{
			uLog.setNumUndo(numUndo);
			uLog.Execute();
		}
	}
	
	public CodeBreaker setCodeBreaker( int _playerNum )
	{
		CodeBreaker breaker;
		
		switch( _playerNum )
		{
		case 0: breaker = new HumanCodeBreaker();
		break;
		case 1: breaker = new RandomCodeBreaker();
		break;
		case 2: breaker = new SmartCodeBreaker(this);
		default:
			breaker = new HumanCodeBreaker();
		}
		
		return breaker;
		
	}
		
}
