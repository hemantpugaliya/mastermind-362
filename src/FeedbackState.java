import java.util.ArrayList;


/**
 * FeedbackState
 * The state of the game when it is the codemaker's turn
 * 
 * @author Gabbie Burns
 *
 */
public class FeedbackState extends GameState 
{
	/**
	 * A reference to a FeedbackCommand
	 */
	private MastermindCommand feedback;
	
	/**
	 * Constructor, pass up parameters and create feedback command
	 */
	public FeedbackState( BoardController bc, boolean logOn )
	{
		super(bc, logOn);
		feedback = new FeedbackCommand();
	}
	
	/**
	 * Apply feedback to the board
	 */
	public void makeMove( ArrayList<PegColor> move)
	{
		// Execute the move
		feedback.Execute( move );
		
		// Log the move
		logging.writeMessage( feedback );
		
		// Change the state
		// set BoardController state to guess
		
	}
	/**
	 * When the game is in a feedback state, only the previous move (the 
	 *  most recent guess) is undone.
	 */
	public void undoTurn() 
	{
		// Execute the undo
		// TODO
		//undo.Execute();
		
		// Log the move
		logging.writeMessage(undo);
		
		// Change the state
		// setBoardController state to guess
	}

}
