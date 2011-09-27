import java.util.ArrayList;


/**
 * GuessCommand
 * 
 * @author Gabbie Burns
 *
 */
public class GuessCommand extends LogfileCommand {
	
	/**
	 * Logfile acts as the receiver for this command.
	 */
	private Logfile myLog = null;
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
	 * Constructor-gets logfile instance at creation.
	 */
	public GuessCommand()
	{
		myLog = Logfile.getInstance();
	}
	
	/**
	 * Update the most recent guess when a move is made
	 * 
	 * @param newGuess: the newest guess from the codebreaker
	 * 
	 */
	public void setGuess( ArrayList< PegColor > newGuess )
	{
		guess = newGuess;
	}
	
	/**
	 * Write the guess out to the logfile.
	 */
	public void Execute()
	{
		if( guess.size() != NUMPEGS )
		{
			// Error
			// Should I bother checking this?
		}
		else
		{
			// Start the log string
			logMsg = "Guess:";
			
			// Append the guesses for each peg
			for( int i = 0; i < NUMPEGS; i++ )
			{
				logMsg = logMsg + " " + guess.get(i).toString();
			}
		}
		
		myLog.writeLog(logMsg);
	}
	
}
