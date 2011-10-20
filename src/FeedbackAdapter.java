import java.util.ArrayList;

import transferObjects.gameplay.MMFeedback;
import transferObjects.gameplay.MMPegForFeedbackColor;


/**
 * FeedbackAdapter
 * 
 * @author Gabbie Burns
 *
 * Adapts the internally stored representation of a feedback to that of
 * the appropriate TransferObject
 */

public class FeedbackAdapter extends MMFeedback {
	
	/**
	 * The guess stored by this object
	 */
	private ArrayList<PegColor> feedback;
	
	/**
	 * The number of pegs in one row of guess
	 */
	private final int NUMPEGS = 4;
	
	
	/**
	 * Constructor-default, does nothing
	 */
	public FeedbackAdapter()
	{
	}
	
	/**
	 * Constructor-takes feedback and stores it internally
	 * 
	 * @param _feedback   the feedback from the user
	 */
	public FeedbackAdapter( ArrayList<PegColor> _feedback )
	{
		feedback = _feedback;
	}
	
	/**
	 * Accessor for the colors/pegs in a feedback.
	 * Convert the internal representation to the feedback form.
	 * 
	 * @return colors   Colors/pegs in a feedback.
	 */
	public ArrayList<MMPegForFeedbackColor> getColors()
	{
		ArrayList<MMPegForFeedbackColor> colors = new ArrayList<MMPegForFeedbackColor>();
		
		for(int i = 0; i < feedback.size(); i++)
		{
			if( feedback.get(i) != PegColor.BLANK )
			{
				colors.add(convertFromPegColor(feedback.get(i)));
			}
		}
		
		return colors;
	}
	
	/**
	 * Accessor for the colors/pegs in a feedback.
	 * 
	 * @return colors   Colors/pegs in a feedback in internal format
	 */
	public ArrayList<PegColor> getInternalColors()
	{
		return feedback;
	}
	
	/**
	 * Sets the colors to the ArrayList specified.
	 * Convert the feedback to the internal representation.
	 * 
	 * @param   colors - ArrayList of type MMPegForFeedbackColor 
	 *                   containing the guess to send.
	 */
	public void setColors( ArrayList<MMPegForFeedbackColor> colors )
	{
		feedback = new ArrayList<PegColor>();
		
		for(int i = 0; i < colors.size(); i++)
		{
			feedback.add(convertToPegColor(colors.get(i)));
		}
		
		// Store the additional blank pegs that the transfer object omits
		while( feedback.size() < NUMPEGS )
		{
			feedback.add(PegColor.BLANK);
		}
	}
	
	/**
	 * Convert from MMPegForFeedbackColor to PegColor
	 */
	private PegColor convertToPegColor( MMPegForFeedbackColor color )
	{
		if( color == MMPegForFeedbackColor.FEEDBACK_BLACK )
		{
			return PegColor.FBLACK;
		}
		else if( color == MMPegForFeedbackColor.FEEDBACK_WHITE )
		{
			return PegColor.FWHITE;
		}
		else
		{
			// Return blank as default, error condition
			return PegColor.BLANK;
		}
	}
	
	/**
	 * Convert PegColor to MMPegForFeedbackColor
	 */
	private MMPegForFeedbackColor convertFromPegColor( PegColor color )
	{
		if( color == PegColor.FBLACK )
		{
			return MMPegForFeedbackColor.FEEDBACK_BLACK;
		}
		else if( color == PegColor.FWHITE )
		{
			return MMPegForFeedbackColor.FEEDBACK_WHITE;
		}
		else
		{
			// Return black as default, error condition
			return MMPegForFeedbackColor.FEEDBACK_BLACK;
		}
	}

}
