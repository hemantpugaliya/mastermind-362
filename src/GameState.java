import java.util.ArrayList;

import client.MMGameClient;


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
     * A history of the moves made in the game thus far
     */
    protected ArrayList<MastermindCommand> gameHistory;
    
    /**
     * Client used to push moves through the network
     */
    protected MMGameClient client;
    
    /**
     * Indicates whether or not moves should be pushed through the network
     */
    protected boolean networked;
	
	/**
	 * Constructor, store the reference to the BoardController and create undo command
	 * 
	 * @param bc   the BoardController
	 * @param logOn   true if logging is currently enabled
	 */
	public GameState( BoardController bc, MastermindBoard b, LoggingState l, 
			ArrayList<MastermindCommand> gH, MMGameClient c, boolean n )
	{
		myBC = bc;
		board = b;
		logging = l;
		gameHistory = gH;
		client = c;
		networked = n;
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
    	logging = logging.toggleLogging(filename, gameHistory);
    }
    
    /**
     * Stop logging
     */
    public void stopLogging()
    {
    	logging = logging.toggleLogging(null, null);
    }
		
}
