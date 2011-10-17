import java.util.ArrayList;


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
	 * TODO
	 */
	public void Execute( ArrayList< PegColor > move )
	{
		// TODO: this one is complicated...
		// Extract the int that we need
		
		// Notify the board
		board.undoMove(numUndo);
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
