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
	 * Constructor, pass up parameters and create feedback command
	 */
	public FeedbackState( BoardController bc, MastermindBoard board )
	{
		super(bc, board);
	}
	
	/**
	 * Apply feedback to the board
	 */
	public void makeMove( ArrayList<PegColor> move)
	{
		// Execute the move
		MastermindCommand feedback = new FeedbackCommand(board, move);
		feedback.Execute();
		
		// Log the move
		//logging.writeMessage( feedback );
		
		// Store the move in the history
		//gameHistory.add(feedback);
		
		// Check to see if the game is over
		
				
	}
	/**
	 * When the game is in a feedback state, only the previous move (the 
	 *  most recent guess) is undone.
	 */
	public void undoTurn() 
	{
		// Execute the undo
		MastermindCommand undo = new UndoCommand(board, 0);
		undo.Execute();
		
		// Log the move
		//logging.writeMessage(undo);
		
		// Remove the most recent guess from the game history
		//gameHistory.remove(gameHistory.size() - 1);
		
	}

}
