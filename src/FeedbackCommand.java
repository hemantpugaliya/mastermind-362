
public class FeedbackCommand extends LogfileCommand {
	
	private Logfile myLog = null;
	private String logMsg = null;
	private PegColor[] feedback = null;
	
	private final int NUMPEGS = 4;
	
	public FeedbackCommand()
	{
		myLog = Logfile.getInstance();
	}
	
	public void setFeedback( PegColor[] newFeedback )
	{
		feedback = newFeedback;
	}
	
	public void Execute()
	{
		if( feedback.length != NUMPEGS )
		{
			// Error
		}
		else
		{
			// Start the log string
			logMsg = "Feedback:";
			
			// Append the guesses for each peg
			for( int i = 0; i < NUMPEGS; i++ )
			{
				logMsg = logMsg + " " + calcColor(feedback[i]);
			}
		}
		
		myLog.writeLog(logMsg);
	}
	
	private String calcColor( PegColor peg )
	{
		String colorCode = "";
		
		switch (peg)
		{
		
		case BLANK:
			colorCode = PegColor.BLANK.toString();
			break;
			
		case BLACK:
			colorCode = PegColor.BLACK.toString();
			break;
			
		case WHITE:
			colorCode = PegColor.WHITE.toString();
			break;
			
		default:
				// Error
		}
		
		return colorCode;
	}

}
