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
				logMsg = logMsg + " " + calcColor(guess.get(i));
			}
		}
		
		myLog.writeLog(logMsg);
	}
	
	/**
	 * Convert the given color information into a format for the logfile.
	 * Colors for guess pegs can be black, white, red, yellow, green, blue.
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
		case BLACK:
			colorCode = PegColor.BLACK.toString();
			break;
			
		case WHITE:
			colorCode = PegColor.WHITE.toString();
			break;
			
		case RED:
			colorCode = PegColor.RED.toString();
			break;
			
		case YELLOW:
			colorCode = PegColor.YELLOW.toString();
			break;
			
		case GREEN:
			colorCode = PegColor.GREEN.toString();
			
		case BLUE:
			colorCode = PegColor.BLUE.toString();
			
		default:
			// Use a special character for an error situation
			colorCode = "X";
		}
		
		return colorCode;
	}

}
