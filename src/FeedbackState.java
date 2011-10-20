import java.util.ArrayList;

import client.MMGameClient;


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
	 * The player responsible for moves during FeedbackState
	 */
	private CodeMaker maker;
	
	/**
	 * Constructor, pass up parameters and create feedback command
	 */
	public FeedbackState( BoardController bc, MastermindBoard board, LoggingState logging, 
			ArrayList<MastermindCommand> history, CodeMaker player, MMGameClient client, boolean networked)
	{
		super(bc, board, logging, history, client, networked);
		maker = player;
	}
	
	/**
	 * Apply feedback to the board
	 */
	public void makeMove( ArrayList<PegColor> move)
	{
		// Execute the move
		MastermindCommand feedback = new FeedbackCommand(board, maker, client, move);
		feedback.Execute(networked);
		
		// Log the move
		//logging.writeMessage( feedback );
		
		// Store the move in the history
		gameHistory.add(feedback);
		
		// Check to see if the game is over
		int gameOver = board.checkWinLoss();
		
		if( gameOver != 0 )
		{
			myBC.endGame(gameOver);
		}
		
				
	}
	/**
	 * When the game is in a feedback state, only the previous move (the 
	 *  most recent guess) is undone.
	 */
	public void undoTurn() 
	{
		// Execute the undo
		MastermindCommand undo = new UndoCommand(board, client, 0);
		undo.Execute(networked);
		
		// Log the move
		logging.writeMessage(undo);
		
		// Remove the most recent guess from the game history
		gameHistory.remove(gameHistory.size() - 1);
		
	}

}
