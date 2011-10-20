import java.util.ArrayList;

import transferObjects.gameplay.MMGuess;
import transferObjects.gameplay.MMPegForGuessColor;

/**
 * GuessAdapter
 * 
 * @author Gabbie Burns
 *
 * Adapts the internally stored representation of a guess to that of
 * the appropriate TransferObject
 */

public class GuessAdapter extends MMGuess {
	
	/**
	 * The guess stored by this object
	 */
	private ArrayList<PegColor> guess;
	
	/**
	 * Constructor-default, does nothing
	 */
	public GuessAdapter()
	{
	}
	
	/**
	 * Constructor-takes a guess and then adapts it to internally used format
	 * 
	 * @param _guess   guess from the codebreaker
	 */
	public GuessAdapter( ArrayList<PegColor> _guess )
	{
		guess = _guess;
	}
	
	/**
	 * Accessor for the colors/pegs in a guess.
	 * Convert the internal representation to the guess form.
	 * 
	 * @return colors   Colors/pegs in a guess.
	 */
	public ArrayList<MMPegForGuessColor> getColors()
	{
		ArrayList<MMPegForGuessColor> colors = new ArrayList<MMPegForGuessColor>();
		
		for(int i = 0; i < guess.size(); i++)
		{
			colors.add(convertFromPegColor(guess.get(i)));
		}
		
		return colors;
	}
	
	/**
	 * Accessor for the colors/pegs in a guess.
	 * 
	 * @return colors   Colors/pegs in a guess in internal format
	 */
	public ArrayList<PegColor> getInternalColors()
	{
		return guess;
	}
	
	/**
	 * Sets the colors to the ArrayList specified.
	 * Convert the guess to the internal representation.
	 * 
	 * @param   colors - ArrayList of type MMPegForGuessColor 
	 *                   containing the guess to send.
	 */
	public void setColors( ArrayList<MMPegForGuessColor> colors )
	{
		guess = new ArrayList<PegColor>();
		
		for(int i = 0; i < colors.size(); i++)
		{
			guess.add(convertToPegColor(colors.get(i)));
		}
	}
		
	/**
	 * Convert from MMPegForGuessColor to PegColor
	 */
	private PegColor convertToPegColor( MMPegForGuessColor color )
	{
		if( color == MMPegForGuessColor.GUESS_BLACK )
		{
			return PegColor.GBLACK;
		}
		else if( color == MMPegForGuessColor.GUESS_WHITE )
		{
			return PegColor.GWHITE;
		}
		else if( color == MMPegForGuessColor.GUESS_RED )
		{
			return PegColor.RED;
		}
		else if( color == MMPegForGuessColor.GUESS_YELLOW )
		{
			return PegColor.YELLOW;
		}
		else if( color == MMPegForGuessColor.GUESS_GREEN )
		{
			return PegColor.GREEN;
		}
		else if( color == MMPegForGuessColor.GUESS_BLUE )
		{
			return PegColor.BLUE;
		}
		else
		{
			// Return blank as default, error condition
			return PegColor.BLANK;
		}
	}
	
	/**
	 * Convert PegColor to MMPegForGuessColor
	 */
	private MMPegForGuessColor convertFromPegColor( PegColor color )
	{
		if( color == PegColor.GBLACK )
		{
			return MMPegForGuessColor.GUESS_BLACK;
		}
		else if( color == PegColor.GWHITE )
		{
			return MMPegForGuessColor.GUESS_WHITE;
		}
		else if( color == PegColor.RED )
		{
			return MMPegForGuessColor.GUESS_RED;
		}
		else if( color == PegColor.YELLOW )
		{
			return MMPegForGuessColor.GUESS_YELLOW;
		}
		else if( color == PegColor.GREEN )
		{
			return MMPegForGuessColor.GUESS_GREEN;
		}
		else if( color == PegColor.BLUE )
		{
			return MMPegForGuessColor.GUESS_BLUE;
		}
		else
		{
			// Return black as default, error condition
			return MMPegForGuessColor.GUESS_BLACK;
		}
	}

}
