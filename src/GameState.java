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
	 * A representation of the board to be used by commands that update it
	 */
	protected MastermindBoard board;
	
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
	public GameState( BoardController bc, MastermindBoard b )
	{
		myBC = bc;
		board = b;
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
     * Start loggging
     * 
     * @param filename   a valid filename or null if disabling logging
     */
    public void startLogging(String filename)
    {
            //logging = logging.toggleLogging(filename, gameHistory);
    }
    
    /**
     * Stop logging
     */
    public void stopLogging()
    {
    	logging = logging.toggleLogging(null, null);
    }
		
}
