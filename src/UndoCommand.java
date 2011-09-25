
public class UndoCommand extends LogfileCommand {
	
	private Logfile myLog = null;
	private String logMsg = null;
	
	public UndoCommand()
	{
		myLog = Logfile.getInstance();
	}
	
	public void Execute( int[] undoInfo )
	{
		// Check that array is not empty
		logMsg = "Undo: Previous " + undoInfo[0] + " rows'\n'";
		myLog.writeLog(logMsg);
	}

}
