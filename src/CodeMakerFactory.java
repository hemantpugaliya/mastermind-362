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
	 * Selects the type of codemaker to create based on input and returns the appropriate
	 * object
	 * 
	 * @param  playerNum   indicates which type of codemaker was selected by the user
	 */
	public CodeMaker setCodeMaker( int playerNum )
	{
		CodeMaker maker = new CodeMaker();
		
		// Decide based on the input which to return
		switch( playerNum )
		{
		
		//case 0: breaker = new HumanCodeMaker();
		//break;
		
		//case 1: breaker = new ComputerCodeBreaker();
		//break;
		
		//case 2: breaker = new NetworkCodeBreaker();
		//break;
			
		default:
			// Return a HumanCodeMaker by default
			//breaker = new HumanCodeMaker();
		}
		
		return maker;
	}
}

