
public class FeedbackCommand extends LogfileCommand {
	
	private Logfile myLog = null;
	private String logMsg = null;
	
	public FeedbackCommand()
	{
		myLog = Logfile.getInstance();
	}
	
	public void Execute( int[] feedbackInfo )
	{
		logMsg = "TODO: Convert array to proper format'\n'";
		myLog.writeLog(logMsg);
	}

}
