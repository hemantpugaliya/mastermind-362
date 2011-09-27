
/**
 * GameState
 * 
 * @author Gabbie Burns
 *
 */
public abstract class GameState {
	
	private MastermindPlayer player = null;
	
	/**
	 * Constructor
	 * 
	 * @param p   the player moving during this state
	 */
	public GameState( MastermindPlayer p )
	{
		player = p;
	}
	
	/**
	 * Instruct the player to make a move during this turn
	 */
	public void takeTurn()
	{
		player.makeMove();
	}
	
	public abstract int undoTurn();
	
}
