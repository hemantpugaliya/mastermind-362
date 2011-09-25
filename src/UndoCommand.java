
public class UndoCommand extends LogfileCommand {
	
	private Logfile myLog = null;
	private String logMsg = null;
	private int numUndo = 0;
	
	public UndoCommand()
	{
		myLog = Logfile.getInstance();
	}
	
	public void setNumUndo( int undo )
	{
		numUndo = undo;
	}
	
	public void Execute()
	{
		// Check that array is not empty
		logMsg = "Undo: Previous " + numUndo + " rows'\n'";
		myLog.writeLog(logMsg);
	}

}
