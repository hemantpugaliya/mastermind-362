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
	 * An instance of a codemaker player, who makes guesses
	 */
	private CodeMaker maker = null;
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
	 * Constructor
	 * 
	 * @param move   a set of feedback from the codemaker
	 */
	public FeedbackCommand( MastermindBoard b, CodeMaker player, ArrayList<PegColor> move )
	{
		feedback = move;
		board = b;
		maker = player;
	}
	
	/**
	 * Execute the given move or get one from the current codemaker
	 * 
	 * @param move    null if using a computer codemaker
	 */
	public void Execute()
	{
		// If the guess is null, we are using a computer codemaker
		if( feedback == null )
		{
			feedback = maker.makeMove();
		}
				
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
