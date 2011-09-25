
public class GuessCommand extends LogfileCommand {
	
	private Logfile myLog = null;
	private String logMsg = null;
	
	public GuessCommand()
	{
		myLog = Logfile.getInstance();
	}
	
	public void Execute( int[] guessInfo )
	{
		logMsg = "TODO: Convert array to proper format'\n'";
		myLog.writeLog(logMsg);
	}

}
