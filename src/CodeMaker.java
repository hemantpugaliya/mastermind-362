import java.util.ArrayList;


/**
 * CodeMaker
 * The player choosing the solution and providing feedback after each guess
 * 
 * @author Gabbie Burns
 *
 */
public abstract class CodeMaker extends MastermindGame {
	
	/**
	 * feedback information comes from the UI
	 */
	public abstract ArrayList<PegColor> makeMove();		
}
