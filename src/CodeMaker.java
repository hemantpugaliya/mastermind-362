import java.util.ArrayList;


/**
 * CodeMaker
 * The player choosing the solution and providing feedback after each guess
 * 
 * @author Gabbie Burns
 *
 */

public abstract  class CodeMaker extends MastermindPlayer {
	
	/**
	 * feedback information comes from the UI
	 */
	public ArrayList<PegColor> makeMove(){
		return null;
	}
	
	public PegColor[] getCode(){
		return null;
	}
}
