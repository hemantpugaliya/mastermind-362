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
		CodeMaker maker = null;
		
		//CodeMaker maker = new CodeMaker();
		
		// Decide based on the input which to return
		switch( playerNum )
		{
		
		//case 0: maker = new HumanCodeMaker();
		//break;
		
		//case 1: maker = new ComputerCodeMaker();
		//break;
		
		//case 2: maker = new NetworkCodeMAker();
		//break;
			
		default:
			// Return a HumanCodeMaker by default
			//breaker = new HumanCodeMaker();
		}
		
		return maker;
	}
}

