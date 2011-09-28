/**
 * GuessState
 * 
 * @author Gabbie Burns
 *
 */

public class GuessState extends GameState {
	
	/**
	 * When the game is in a guess state, only the previous two moves (the 
	 *  most recent guess and feedback) is undone.
	 */
	public int undoTurn() {
		
		return 2;
		
	}

}
