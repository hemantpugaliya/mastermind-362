import java.util.ArrayList;

/**
 * MastermindCommand
 * 
 * The interface defining all game-related commands
 * 
 * @author Gabbie Burns
 *
 */

public abstract class MastermindCommand {
	
	/**
	 * An instance of the board, static so that all commands use the same instance
	 */
	protected static MastermindBoard board;
	
	/**
	 * TODO
	 */
	public abstract void Execute( ArrayList< PegColor > move );
	
	/**
	 * Return a representation of the most recent move made
	 * 
	 * @return   the message form of the last move
	 */
	public abstract String getMessage();

}
