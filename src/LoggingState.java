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
	protected static Logfile myLog;
	
	/**
	 * Writes out a message to the log file, if appropriate
	 * 
	 * @param command   the command that was just executed by the game, used to generate message
	 */
	public abstract void writeMessage( MastermindCommand command );
	

}
