/**
 * CodeMakerFactory
 * 
 * @author Gabbie Burns
 * 
 * Abstracts the creation of different types of codemakers away from the game logic
 *
 */
public class CodeMakerFactory {

	/**
	 * A representation of the current board
	 */
	private MastermindBoard myBoard;
	
	/**
	 * Constructor. Takes an instance of the board for computer players that need the access
	 * 
	 * @param board
	 */
	public CodeMakerFactory( MastermindBoard board )
	{
		myBoard = board;
	}
	
	/**
	 * Selects the type of codemaker to create based on input and returns the appropriate
	 * object
	 * 
	 * @param  playerNum   indicates which type of codemaker was selected by the user
	 */
	public CodeMaker setCodeMaker( int playerNum )
	{
		CodeMaker maker;
		
		// Decide based on the input which to return
		switch( playerNum )
		{
		
		case 0: maker = new HumanCodeMaker();
		break;
		
		case 1: maker = new ComputerCodeMaker(myBoard);
		break;
		
		case 2: maker = new NetworkCodeMaker();
		break;
			
		default:
			// Return a HumanCodeMaker by default
			maker = new HumanCodeMaker();
		}
		
		return maker;
	}
}

