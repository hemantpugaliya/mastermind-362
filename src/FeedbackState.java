
/**
 * FeedbackState
 * The state of the game when it is the codemaker's turn
 * 
 * @author Gabbie Burns
 *
 */
public class FeedbackState implements GameState 
{

	/**
	 * When the game is in a feedback state, only the previous move (the 
	 *  most recent guess) is undone.
	 */
	public int undoTurn() {
		return 0;
	}

}
