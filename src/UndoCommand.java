
/**
 * UndoCommand
 * 
 * @author Gabbie Burns
 *
 */
public class UndoCommand extends LogfileCommand {
	
	/**
	 * Logfile acts as the receiver for this command.
	 */
	private Logfile myLog = null;
	/**
	 * Message to be written out to the log.
	 */
	private String logMsg = null;
	/**
	 * The number of moves affected by the previous undo move
	 */
	private int numUndo = 0;
	
	/**
	 * Constructor-gets logfile instance at creation.
	 */
	public UndoCommand()
	{
		myLog = Logfile.getInstance();
	}
	
	/**
	 * Update the info for the effects of this undo
	 * 
	 * @param undo   the number of moves affected by the most recent undo
	 * 
	 */
	public void setNumUndo( int undo )
	{
		numUndo = undo;
	}
	
	/**
	 * Write the undo out to the logfile.
	 */
	public void Execute()
	{
		logMsg = "Undo: Previous " + (numUndo + 1) + " rows'\n'";
		myLog.writeLog(logMsg);
	}

}
