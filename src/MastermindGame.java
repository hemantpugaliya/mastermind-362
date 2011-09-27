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
	
	private GameBoardView gameView = null;
	private BoardController controller = null;
	
	
	public MastermindGame()
	{
		// Create the UI
		GameBoardView gameView = new GameBoardView(this);
		gameView.create();

		newGame();
	}
	
	public void newGame()
	{
		board = new MastermindBoard();
		
		// Query UI for player types selected...return an int?
		// Switch statement to create codebreaker based on input
		maker = new CodeMaker( );
		
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
	public void giveFeedback( ArrayList< PegColor > feedback )
	{
		// Notify the board
		board.newFeedback(feedback);
		
		// Log the feedback
		if( logging )
		{
			fLog.setFeedback(feedback);
			fLog.Execute();
		}
		
		// Check for end of game
		Boolean end = board.checkWinLoss();
		
		if( end == null )
		{
			// Continue the game
		}
		else if( end == true )
		{
			// Codebreaker wins!
		}
		else
		{
			// Codemaker wins!
		}
		
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
	
	/**
	 * Drives the game play
	 */
	public void play()
	{
		GameState temp;
		boolean gameOver = false;
		
		while( !gameOver )
		{
			// Take the player's turn
			currState.takeTurn();
			
			// Advance to the next state
			temp = currState;
			currState = nextState;
			nextState = temp;
			
			// TODO: check for win condition
		}
	}
		
}
