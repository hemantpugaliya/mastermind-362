/**
 * StartCommand
 * 
 * @author Gabbie Burns
 *
 */
public class ToggleLogCommand extends LogfileCommand {
	
	/**
	 * The name of the file to write logs to
	 */
	private String filename = null;
	/**
	 * Logfile acts as the receiver for this command.
	 */
	private Logfile myLog = null;
	/**
	 * Message to be written out to the log.
	 */
	private final String startMsg = "Mastermind Game Log \n\n ";
	/**
	 * Indicates whether or not the game is being actively logged
	 */
	private boolean logging = false;
	
	/**
	 * Constructor-gets logfile instance at creation and stores the file name.
	 * 
	 * @param fname   the file to use for logging the current game
	 */
	public ToggleLogCommand( String fname )
	{
		filename = fname;
		myLog = Logfile.getInstance();
	}
	
	/**
	 * Open the file for writing logs and write the initial message.
	 */
	public void Execute()
	{
		// Start logging if it is currently disabled
		if( !logging )
		{
			myLog.openLog(filename);
			myLog.writeLog(startMsg);
		}
		// Stop logging if it is currently enabled
		else
		{
			myLog.closeLog();
		}
		
		// Change the logging state
		logging = !logging;
	}
	
}
