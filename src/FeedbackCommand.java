import java.util.ArrayList;


/**
 * FeedbackCommand
 * Prepares a message for the logfile containing the most recent feedback
 * 
 * @author Gabbie Burns
 *
 */
public class FeedbackCommand implements LogfileCommand {
	
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
				logMsg = logMsg + " " + feedback.get(i).toString();
			}
		}
		
		myLog.writeLog(logMsg);
	}
	
}
