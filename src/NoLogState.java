import java.util.ArrayList;

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
	 * Constructor-default
	 */
	public NoLogState()
	{
		// Do nothing
	}
	
	/**
	 * Constructor
	 * 
	 * @param newLog   a previously existing Logfile to be used
	 */
	public NoLogState( Logfile newLog )
	{
		myLog = newLog;
	}
	
	/**
	 * Create the logfile and open a new log
	 * 
	 * @param filename   the name of the file to save logs in
	 * @return newState   a LogState
	 */
	public LoggingState toggleLogging( String filename, ArrayList<MastermindCommand> history )
	{
		// Start the log
		myLog = new Logfile();
		myLog.openLog(filename);
		myLog.writeLog(startMsg);
		
		// Log the game history up to this point
		for(int i = 0; i < history.size(); i++)
		{
			myLog.writeLog(history.get(i).getMessage());
		}
		
		LoggingState newState = new LogState(myLog);
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
