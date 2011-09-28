
/**
 * FeedbackState
 * 
 * @author Gabbie Burns
 *
 */
public class FeedbackState extends GameState {

	/**
	 * When the game is in a feedback state, only the previous move (the 
	 *  most recent guess) is undone.
	 */
	public int undoTurn() {
		return 0;
	}

}
