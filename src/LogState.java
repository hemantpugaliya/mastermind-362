import java.util.ArrayList;

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
	 * Constructor-default
	 */
	public LogState()
	{
		
	}
	/**
	 * Stop logging and return the new logging state
	 * 
	 * @param filename    not used
	 * @param history     not used
	 * @return newState   a NoLogState
	 */
	public LoggingState toggleLogging( String filename, ArrayList<MastermindCommand> history )
	{	
		myLog.closeLog();
		myLog = null;
		
		LoggingState newState = new NoLogState();
		return newState;
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
