import java.util.ArrayList;


/**
 * GameState
 * 
 * @author Gabbie Burns
 *
 */
public abstract class GameState {
	
	/**
	 * A reference to the controller driving the game that holds these states
	 */
	protected BoardController myBC;
	/**
	 * A reference to an UndoCommand
	 */
	protected MastermindCommand undo;
	/**
	 * Determines whether or not logging is enabled and handles it appropriately
	 */
	protected LoggingState logging;
	
	/**
	 * Constructor, store the reference to the BoardController and create undo command
	 * 
	 * @param bc   the BoardController
	 * @param logOn   true if logging is currently enabled
	 */
	public GameState( BoardController bc, boolean logOn )
	{
		myBC = bc;
		undo = new UndoCommand();
		
		// Set the log state
		if( logOn )
		{
			logging = new LogState();
		}
		else
		{
			logging = new NoLogState();
		}
	}
	
	/**
	 * Take the turn of one of the players, depending on the current state
	 */
	public abstract void makeMove( ArrayList<PegColor> move );
	
	/**
	 * Perform the undo, going back a different amount depending on the current state
	 */
	public abstract void undoTurn();
	
	/**
	 * Enable or disable logging, depending on the state
	 * 
	 * @param filename   a valid filename or null if disabling logging
	 */
	public void toggleLogging(String filename)
	{
		logging = logging.toggleLogging(filename);
	}
	
}
