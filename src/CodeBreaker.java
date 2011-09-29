import java.util.ArrayList;

/**
 * CodeBreaker
 * The player trying to correctly guess the solution
 * 
 * @author mmo2912
 *
 */
public abstract class CodeBreaker extends MastermindPlayer {
	
	/**
	 * Calculate and return a move during this player's turn
	 * 
	 * @return  the next move
	 */
	public abstract ArrayList<PegColor> makeMove();

}
