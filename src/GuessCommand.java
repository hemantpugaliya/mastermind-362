import java.util.ArrayList;


/**
 * GuessCommand
 * Processes a guess move during the game
 * 
 * @author Gabbie Burns
 *
 */
public class GuessCommand extends MastermindCommand {
	
	/**
	 * An instance of a codebreaker player, who makes guesses
	 */
	CodeBreaker breaker = null;
	/**
	 * Message to be written out to the log.
	 */
	private String logMsg = null;
	/**
	 * Most recent guess from the codebreaker.
	 */
	private ArrayList< PegColor > guess = null;
	
	private final int NUMPEGS = 4;
	
	/**
	 * Constructor
	 * 
	 * @param move   a guess from the codebreaker
	 */
	public GuessCommand( MastermindBoard b, ArrayList<PegColor> move )
	{
		guess = move;
		board = b;
	}
	
	/**
	 * Execute the given move or get one from the current codebreaker
	 * 
	 * @param move    null if using a computer codebreaker
	 */
	public void Execute()
	{
		// If the guess is null, we are using a computer codebreaker
		if( guess == null )
		{
			guess = breaker.makeMove();		
		}
				
		// Notify the board
		board.newGuess(guess);
	}
	
	/**
	 * Return a representation of the most recent move made
	 * 
	 * @return   the message form of the last move
	 */
	public String getMessage()
	{
		// Start the log string
		logMsg = "Guess:   ";
				
		// Append the guesses for each peg
		for( int i = 0; i < NUMPEGS; i++ )
		{
			logMsg = logMsg + " " + guess.get(i).toString();
		}
		
		return logMsg;
	}
	
}
