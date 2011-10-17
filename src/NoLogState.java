/**
 * NoLogState
 * 
 * @author GabbieBurns
 * 
 * The state for a Mastermind that does not have logging enabled
 *
 */

public class NoLogState extends LoggingState {
	
	
	/**
	 * Close the logfile and set the instance to null
	 */
	public void stopLogging()
	{
		myLog.closeLog();
		myLog = null;
	}

	/**
	 * Do NOT write a message out to the log file
	 * 
	 * @param command   the command that was just executed by the game, not used
	 */
	public void writeMessage( MastermindCommand command )
	{
		// Do nothing
	}
	
}
