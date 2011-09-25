
public class GuessCommand extends LogfileCommand {
	
	private Logfile myLog = null;
	private String logMsg = null;
	private PegColor[] guess = null;
	
	private final int NUMPEGS = 4;
	
	public GuessCommand()
	{
		myLog = Logfile.getInstance();
	}
	
	public void setGuess( PegColor[] newGuess )
	{
		guess = newGuess;
	}
	
	public void Execute()
	{
		if( guess.length != NUMPEGS )
		{
			// Error
		}
		else
		{
			// Start the log string
			logMsg = "Guess:";
			
			// Append the guesses for each peg
			for( int i = 0; i < NUMPEGS; i++ )
			{
				logMsg = logMsg + " " + calcColor(guess[i]);
			}
		}
		
		myLog.writeLog(logMsg);
	}
	
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
				// Error
		}
		
		return colorCode;
	}

}
