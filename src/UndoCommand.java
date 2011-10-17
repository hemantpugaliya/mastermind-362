
/**
 * UndoCommand
 * Procceses an undo move during the game
 * 
 * @author Gabbie Burns
 *
 */
public class UndoCommand implements MastermindCommand {
	
	/**
	 * Message to be written out to the log.
	 */
	private String logMsg = null;
	/**
	 * The number of moves affected by the previous undo move
	 */
	private int numUndo = 0;
	
	/**
	 * Update the info for the effects of this undo
	 * 
	 * @param undo   the number of moves affected by the most recent undo
	 * 
	 */
	public void setNumUndo( int undo )
	{
		numUndo = undo;
	}
	
	/**
	 *
	 */
	public void Execute()
	{
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
