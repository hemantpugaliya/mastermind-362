import java.util.ArrayList;

import client.MMGameClient;


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
	 * The player responsible for moves during the guess state
	 */
	private CodeBreaker breaker;
	
	/**
	 * Constructor, pass up parameters and create guess command
	 */
	public GuessState( BoardController bc, MastermindBoard board, LoggingState logging, 
			ArrayList<MastermindCommand> history, CodeBreaker player, MMGameClient client, boolean networked)
	{
		super(bc, board, logging, history, client, networked);
		breaker = player;
	}
	
	/**
	 * Apply guess to the board
	 */
	public void makeMove( ArrayList<PegColor> move)
	{
		// Execute the move
		MastermindCommand guess = new GuessCommand(board, breaker, client, move);
		guess.Execute(networked);
		
		// Log the move
		logging.writeMessage(guess);
		
		// Store the move in the history
		gameHistory.add(guess);
		
	}
	/**
	 * When the game is in a guess state, only the two moves (the 
	 *  most recent guess and feedback) are undone.
	 */
	public void undoTurn() 
	{
		// Execute the undo
		MastermindCommand undo = new UndoCommand(board, client, 1);
		undo.Execute(networked);
		
		// Log the move
		logging.writeMessage(undo);
		
		// Remove the most recent guess and feedback from the game history
		gameHistory.remove(gameHistory.size() - 1);
		gameHistory.remove(gameHistory.size() - 1);
		
	}

}
