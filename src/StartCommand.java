
public class StartCommand extends LogfileCommand {
	
	private String filename = null;
	private Logfile myLog = null;
	private final String startMsg = "Mastermind Game Log'\n\n'";
	
	public StartCommand( String fname )
	{
		filename = fname;
		myLog = Logfile.getInstance();
	}
	
	public void Execute( int[] moveInfo )
	{
		myLog.openLog(filename);
		myLog.writeLog(startMsg);
	}

}
