import java.util.ArrayList;
import java.util.Random;

/**
 * RandomCodeBreaker
 * 
 * @author Gabbie Burns
 *
 */

public class RandomCodeBreaker extends CodeBreaker {
	
	/**
	 * The number of pegs in one row of guess
	 */
	public final int NUMPEGS = 4;
	/**
	 * The max number with which to seed the random generator
	 */
	public final int MAXPEG = 6;
	
	/**
	 * Generate a random legal guess for the next move in the game
	 * 
	 * @return guess   color values for each peg in the row
	 */
	public ArrayList<PegColor> makeMove()
	{
		ArrayList< PegColor > guess = new ArrayList < PegColor >();
		Random rand = new Random();
		int nextRand = 0;
		
		// Generate a random guess for each peg
		for( int i = 0; i < NUMPEGS; i++ )
		{
			nextRand = rand.nextInt(MAXPEG);
			// Add one to the generated number to avoid blank
			guess.add( PegColor.values()[nextRand]);	
		}
		
		return guess;
	}
	

}
