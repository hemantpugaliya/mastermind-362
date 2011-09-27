/**
 * GuessState
 * 
 * @author Gabbie Burns
 *
 */

public class GuessState extends GameState {
	
	/**
	 * Constructor-pass the player to the superclass
	 * 
	 * @param p   the player moving during this state
	 */
	public GuessState( MastermindPlayer p )
	{
		super(p);
	}

	@Override
	public int undoTurn() {
		// TODO Auto-generated method stub
		return 0;
	}

}
