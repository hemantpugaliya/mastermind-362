import java.util.ArrayList;

import client.MMGameClient;

import transferObjects.gameplay.MMFeedback;
import transferObjects.gameplay.MMGuess;
import transferObjects.networking.exceptions.MMNetworkingException;


/**
 * GuessCommand
 * Processes a guess move during the game
 * 
 * @author Gabbie Burns
 *
 */
public class GuessCommand extends MastermindCommand {
	
	/**
	 * An instance of a codebreaker player, who makes guesses
	 */
	private CodeBreaker breaker = null;
	/**
	 * Message to be written out to the log.
	 */
	private String logMsg = null;
	/**
	 * Most recent guess from the codebreaker.
	 */
	private ArrayList< PegColor > guess = null;
	
	private final int NUMPEGS = 4;
	
	/**
	 * Constructor
	 * 
	 * @param move   a guess from the codebreaker
	 */
	public GuessCommand( MastermindBoard b, CodeBreaker player, MMGameClient _client, ArrayList<PegColor> move )
	{
		guess = move;
		board = b;
		breaker = player;
		client = _client;
	}
	
	/**
	 * Execute the given move or get one from the current codebreaker
	 * 
	 * @param networked   if true, push move to MMClient in addition to the board
	 */
	public void Execute(boolean networked)
	{
		// If the guess is null, we are using a computer codebreaker
		if( guess == null )
		{
			guess = breaker.makeMove();		
		}
				
		// Notify the board
		board.newGuess(guess);
		
		// Push the move through the network
		if(networked)
		{
			MMGuess transfer = new GuessAdapter(guess);
			try
			{
				client.pushGuess(transfer);
			}
			catch (MMNetworkingException e)
			{
				// Assume network magic always works
			}
		}
	}
	
	/**
	 * Return a representation of the most recent move made
	 * 
	 * @return   the message form of the last move
	 */
	public String getMessage()
	{
		// Start the log string
		logMsg = "Guess:   ";
				
		// Append the guesses for each peg
		for( int i = 0; i < NUMPEGS; i++ )
		{
			logMsg = logMsg + " " + guess.get(i).toString();
		}
		
		return logMsg;
	}
		
}
