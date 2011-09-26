
public class MastermindGame extends Game {
	
	private GuessCommand gLog = null;
	private FeedbackCommand fLog = null;
	private UndoCommand uLog = null;
	private MastermindPlayer maker = null;
	private MastermindPlayer breaker = null;
	protected MastermindBoard board = null;
	protected boolean logging = false;
	private GameBoardView gameView = null;
	private BoardController controller = null;
	
	public MastermindGame()
	{
		// Create the UI
		GameBoardView gameView = new GameBoardView();
		gameView.create();
		controller = gameView.getController();
		
		newGame();
	}
	
	public void newGame()
	{
		board = new MastermindBoard();
		
	}
	public void startLogging( String filename )
	{
		logging = true;
		StartCommand startLog = new StartCommand( filename );
		startLog.Execute();
		
		gLog = new GuessCommand();
		fLog = new FeedbackCommand();
		uLog = new UndoCommand();
	}
	
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
	
}
