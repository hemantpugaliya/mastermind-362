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
	 * A reference to a FeedbackCommand
	 */
	private MastermindCommand guess;
	
	/**
	 * Constructor, pass up parameters and create guess command
	 */
	public GuessState( BoardController bc, boolean logOn )
	{
		super(bc, logOn);
		guess = new GuessCommand();
	}
	
	/**
	 * Apply guess to the board
	 */
	public void makeMove( ArrayList<PegColor> move)
	{
		// Execute the move
		guess.Execute(move);
		
		// Log the move
		logging.writeMessage(guess);
		
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
		// TODO
		//undo.Execute();
		
		// Log the move
		logging.writeMessage(undo);
		
		// Change the state
		// set BoardController state to Guess
	}

}
