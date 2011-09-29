
/**
 * GameState
 * 
 * @author Gabbie Burns
 *
 */
public interface GameState {
	
	/**
	 * Return information about the effect of an undo in the current state
	 * 
	 * @return   the number of full rows that gameplay moved back
	 */
	public abstract int undoTurn();
	
}
