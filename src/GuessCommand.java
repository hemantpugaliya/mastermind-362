import java.util.ArrayList;


/**
 * GuessCommand
 * Processes a guess move during the game
 * 
 * @author Gabbie Burns
 *
 */
public class GuessCommand implements MastermindCommand {
	
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
	 * TODO
	 */
	public void Execute( ArrayList< PegColor > move )
	{
		guess = move;
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
