import java.util.ArrayList;

/**
 * MastermindCommand
 * 
 * The interface defining all game-related commands
 * 
 * @author Gabbie Burns
 *
 */

public interface MastermindCommand {
	
	/**
	 * TODO
	 */
	public void Execute( ArrayList< PegColor > move );
	
	/**
	 * Return a representation of the most recent move made
	 * 
	 * @return   the message form of the last move
	 */
	public String getMessage();

}
