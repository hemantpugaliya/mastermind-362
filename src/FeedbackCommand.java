import java.util.ArrayList;


/**
 * FeedbackCommand
 * Processes a feedback move during the game
 * 
 * @author Gabbie Burns
 *
 */
public class FeedbackCommand extends MastermindCommand {
	
	/**
	 * An instance of a codebreaker player, who makes guesses
	 */
	CodeMaker maker = null;
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
	 * Execute the given move or get one from the current codemaker
	 * 
	 * @param move    null if using a computer codemaker
	 */
	public void Execute( ArrayList< PegColor > move )
	{
		// If the guess is null, we are using a computer codemaker
		if( move == null )
		{
			move = maker.makeMove();
		}
		
		// Save the move
		feedback = move;
				
		// Notify the board
		board.newFeedback(feedback);
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
