/**
 * LogState
 * 
 * @author GabbieBurns
 * 
 * The state for a Mastermind that has logging enabled
 *
 */

public class LogState extends LoggingState {
	
	/**
	 * Initial message to be written out to the log.
	 */
	private final String startMsg = "Key: Red=R, Yellow=Y, Green=G, Blue=B, White=W, Black=K, Blank=0";
	
	/**
	 * Create the logfile and open a new log
	 * @param filename
	 */
	public void startLoggging(String filename)
	{
		myLog = new Logfile();
		myLog.openLog(filename);
		myLog.writeLog(startMsg);
	}

	/**
	 * Writes out a message to the log file
	 * 
	 * @param command   the command that was just executed by the game, used to generate message
	 */
	public void writeMessage( MastermindCommand command )
	{
		myLog.writeLog(command.getMessage());
	}
	
}
