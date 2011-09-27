import java.util.ArrayList;


/**
 * FeedbackCommand
 * 
 * @author Gabbie Burns
 *
 */
public class FeedbackCommand extends LogfileCommand {
	
	/**
	 * Logfile acts as the receiver for this command.
	 */
	private Logfile myLog = null;
	/**
	 * Message to be written out to the log.
	 */
	private String logMsg = null;
	/**
	 * Most recent feedback from the codebreaker.
	 */
	private ArrayList< PegColor > feedback = null;
	
	private final int NUMPEGS = 4;
	
	/**
	 * Constructor-gets logfile instance at creation.
	 */
	public FeedbackCommand()
	{
		myLog = Logfile.getInstance();
	}
	
	/**
	 * Update the most recent feedback when a move is made
	 * 
	 * @param newGuess: the newest feedback from the codemaker
	 * 
	 */
	public void setFeedback( ArrayList< PegColor > newFeedback )
	{
		feedback = newFeedback;
	}
	
	/**
	 * Write the feedback out to the logfile.
	 */
	public void Execute()
	{
		if( feedback.size() != NUMPEGS )
		{
			// Error
			// Should I bother checking this?
		}
		else
		{
			// Start the log string
			logMsg = "Feedback:";
			
			// Append the guesses for each peg
			for( int i = 0; i < NUMPEGS; i++ )
			{
				logMsg = logMsg + " " + calcColor(feedback.get(i));
			}
		}
		
		myLog.writeLog(logMsg);
	}
	
	/**
	 * Convert the given color information into a format for the logfile.
	 * Colors for feedback pegs can only be blank, black, or white.
	 * 
	 * @param peg  the color information for a given peg
	 * @return colorCode   the appropriate log format to represent the peg
	 * 
	 */
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
			// Use a special character for an error situation
			colorCode = "X";
			break;
		}
		
		return colorCode;
	}

}
