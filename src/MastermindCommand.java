import java.util.ArrayList;

import client.MMGameClient;

/**
 * MastermindCommand
 * 
 * The interface defining all game-related commands
 * 
 * @author Gabbie Burns
 *
 */

public abstract class MastermindCommand {
	
	/**
	 * An instance of the board
	 */
	protected MastermindBoard board;
	
	/**
	 * An instance of the client for pushing networked moves
	 */
	protected MMGameClient client;
	
	/**
	 * Execute the stored move and apply it to the state of the game
	 * 
	 * @param networked   if true, push move to MMClient in addition to the board
	 */
	public abstract void Execute(boolean networked);
	
	/**
	 * Return a representation of the most recent move made
	 * 
	 * @return   the message form of the last move
	 */
	public abstract String getMessage();

}
