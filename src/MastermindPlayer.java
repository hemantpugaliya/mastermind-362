import java.util.ArrayList;

/**
 * MastermindPlayer
 * The standard implemented by both types of players in Mastermind
 * 
 * @author Gabbie Burns
 *
 */

public abstract class MastermindPlayer {
	
	/**
	 * Calculate and return a move during this player's turn
	 * 
	 * @return  the next move
	 */
	public abstract ArrayList<PegColor> makeMove();
	
}
