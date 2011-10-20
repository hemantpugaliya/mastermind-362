import java.util.ArrayList;

/**
 * CodeBreaker
 * The player trying to correctly guess the solution
 * 
 * @author mmo2912
 *
 */
public abstract class CodeBreaker {
	
	/**
	 * Calculate  a move during this player's turn
	 */
	public abstract ArrayList<PegColor> makeMove();

}
