import java.util.ArrayList;

/**
 * LoggingState
 * 
 * @author Gabbie Burns
 * 
 * An interface defining the behavior of the state used for managing the logging of games
 *
 */

public abstract class LoggingState {
	
	/**
	 * A reference to the logfile being used by the game
	 */
	protected Logfile myLog;
	
	/**
	 * Initial message to be written out to the log.
	 */
	protected final String startMsg = "Key: Red=R, Yellow=Y, Green=G, Blue=B, White=W, Black=K, Blank=0";
	
	/**
	 * Starts or stops logging, depending on the current state and return new state
	 */
	public abstract LoggingState toggleLogging( String filename, ArrayList<MastermindCommand> history );
	
	/**
	 * Writes out a message to the log file, if appropriate
	 * 
	 * @param command   the command that was just executed by the game, used to generate message
	 */
	public abstract void writeMessage( MastermindCommand command );
	
}
