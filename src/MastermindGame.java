
public class MastermindGame extends Game {
	
	private GuessCommand gLog = null;
	private FeedbackCommand fLog = null;
	private UndoCommand uLog = null;
	
	public void makeGuess( PegColor[] guess)
	{
		// Do stuff with the MastermindBoard
		
		// Log the guess
		if( logging )
		{
			gLog.setGuess(guess);
			gLog.Execute();	
		}
		
	}
	
	public void giveFeedback( PegColor[] feedback )
	{
		// Do stuff with the MastermindBoard
		
		// Log the feedback
		if( logging )
		{
			fLog.setFeedback(feedback);
			fLog.Execute();
		}
		
	}
	
	public void Undo()
	{
		int numUndo = 0;
		
		// Check the game state and set numUndo
		
		// Do stuff with MastermindBoard
		
		// Log the undo
		if( logging )
		{
			uLog.setNumUndo(numUndo);
			uLog.Execute();
		}
	}
	
	public void StartLogging( String filename )
	{
		super.startLogging(filename);
		
		gLog = new GuessCommand();
		fLog = new FeedbackCommand();
		uLog = new UndoCommand();
			
	}

}
