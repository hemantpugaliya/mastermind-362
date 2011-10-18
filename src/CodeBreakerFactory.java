/**
 * CodeBreakerFactory
 * 
 * @author Gabbie Burns
 * 
 * Abstracts the creation of different types of codebreakers away from the game logic
 *
 */
public class CodeBreakerFactory {
	
	/**
	 * A representation of the current board
	 */
	private MastermindBoard myBoard;
	
	/**
	 * Constructor. Takes an instance of the board for computer players that need the access
	 * 
	 * @param board
	 */
	public CodeBreakerFactory( MastermindBoard board )
	{
		myBoard = board;
	}
	
	/**
	 * Selects the type of codebreaker to create based on input and returns the appropriate
	 * object
	 * 
	 * @param  playerNum   indicates which type of codebreaker was selected by the user
	 */
	public CodeBreaker setCodeBreaker( int playerNum )
	{
		CodeBreaker breaker;
		
		// Decide based on the input which to return
		switch( playerNum )
		{
		
		case 0: breaker = new HumanCodeBreaker();
		break;
		
		case 1: breaker = new RandomCodeBreaker();
		break;
		
		case 2: breaker = new SmartCodeBreaker(myBoard);
		break;
		
		//case 3: breaker = new NetworkCodeBreaker();
		//break;
			
		default:
			// Return a HumanCodeBreaker by default
			breaker = new HumanCodeBreaker();
		}
		
		return breaker;
	}
}
