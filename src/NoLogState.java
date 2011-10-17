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
	 * Initial message to be written out to the log.
	 */
	private final String startMsg = "Key: Red=R, Yellow=Y, Green=G, Blue=B, White=W, Black=K, Blank=0";
	
	/**
	 * Create the logfile and open a new log
	 * 
	 * @param filename   the name of the file to save logs in
	 * @return newState   a LogState
	 */
	public LoggingState toggleLogging( String filename )
	{
		myLog = new Logfile();
		myLog.openLog(filename);
		myLog.writeLog(startMsg);
		
		LoggingState newState = new LogState();
		return newState;
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
