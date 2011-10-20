import java.util.ArrayList;

import transferObjects.networking.exceptions.MMNetworkingException;

import client.MMGameClient;


/**
 * UndoCommand
 * Procceses an undo move during the game
 * 
 * @author Gabbie Burns
 *
 */
public class UndoCommand extends MastermindCommand {
	
	/**
	 * Message to be written out to the log.
	 */
	private String logMsg = null;
	/**
	 * The number of moves affected by the previous undo move
	 */
	private int numUndo = 0;
	
	/**
	 * Constructor
	 * 
	 * @param move   the number of moves to undo
	 */
	public UndoCommand( MastermindBoard b, MMGameClient _client, int move )
	{
		numUndo = move;
		board = b;
		client = _client;
	}
	/**
	 * Apply the undo to the board
	 * 
	 * @param networked   if true, push move to MMClient in addition to the board
	 */
	public void Execute(boolean networked)
	{
		// Notify the board
		board.undoMove(numUndo);
		
		// Push the move through the network
		if(networked)
		{
			try
			{
				client.pushUndo();
			}
			catch( MMNetworkingException e)
			{
				// Assume networking magic always works
			}
		}
	}
	
	/**
	 * Return a representation of the most recent move made
	 * 
	 * @return   the message form of the last move
	 */
	public String getMessage()
	{
		logMsg = "Undo: Previous " + (numUndo + 1) + " rows";
		
		return logMsg;
	}

}
