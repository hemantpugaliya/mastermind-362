import java.util.ArrayList;


/**
 * FeedbackCommand
 * Processes a feedback move during the game
 * 
 * @author Gabbie Burns
 *
 */
public class FeedbackCommand implements MastermindCommand {
	
	/**
	 * Message to be written out to the log.
	 */
	private String logMsg = null;
	/**
	 * Most recent feedback from the codemaker
	 */
	private ArrayList< PegColor > feedback = null;
	
	private final int NUMPEGS = 4;
		
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
	 * 
	 */
	public void Execute()
	{
	}
	
	/**
	 * Return a representation of the most recent move made
	 * 
	 * @return   the message form of the last move
	 */
	public String getMessage()
	{
		logMsg = "Feedback:";
		
		// Append the guesses for each peg
		for( int i = 0; i < NUMPEGS; i++ )
		{
			logMsg = logMsg + " " + feedback.get(i).toString();
		}
		
		return logMsg;
	}
	
}
