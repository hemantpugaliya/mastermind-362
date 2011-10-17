import java.util.ArrayList;


/**
 * GuessState
 * The state of the game when it is the codebreaker's turn
 * 
 * @author Gabbie Burns
 *
 */
public class GuessState extends GameState 
{	
	/**
	 * Constructor, pass up parameters and create guess command
	 */
	public GuessState( BoardController bc, boolean logOn )
	{
		super(bc, logOn);
	}
	
	/**
	 * Apply guess to the board
	 */
	public void makeMove( ArrayList<PegColor> move)
	{
		// Execute the move
		MastermindCommand guess = new GuessCommand(move);
		guess.Execute();
		
		// Log the move
		logging.writeMessage(guess);
		
		// Store the move in the history
		gameHistory.add(guess);
		
		// Change the state
		// set BoardController state to Feedback
	}
	/**
	 * When the game is in a guess state, only the two moves (the 
	 *  most recent guess and feedback) are undone.
	 */
	public void undoTurn() 
	{
		// Execute the undo
		MastermindCommand undo = new UndoCommand(1);
		undo.Execute();
		
		// Log the move
		logging.writeMessage(undo);
		
		// Remove the most recent guess and feedback from the game history
		gameHistory.remove(gameHistory.size() - 1);
		gameHistory.remove(gameHistory.size() - 1);
		
		// Change the state
		// set BoardController state to Guess
	}

}
