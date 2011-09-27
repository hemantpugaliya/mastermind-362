import java.util.ArrayList;
import java.util.Stack;

/*
 * MastermindBoard.java
 * 
 * Version:
 *     $Id$
 *
 * Revisions:
 *      $Log$
 */

/**
 * @author ajg9132
 * @contributor Gabbie Burns
 *
 */
public class MastermindBoard {
	
	private ArrayList<PegRow> rows;
	private Solution solution;
	private Stack<PegRowMemento> mementos;
	private int currRow = 0;
	
	private final int NUMPEGS = 4;
	private final int NUMROWS = 10;
	
	public MastermindBoard() {
		// todo: setup board
		
		// Populate the set of rows with 10 empty ones
		rows = new ArrayList<PegRow>();
		for( int i = 0; i < NUMROWS; i++ )
		{
			rows.add(new PegRow());
		}
		
		this.mementos = new Stack<PegRowMemento>();
	}
	
	/**
	 * Convert the new guess and add to the board's representation
	 * @param guess   the most recent guess
	 */
	public void newGuess( ArrayList< PegColor > guess)
	{
		ArrayList< PuzzlePeg > guessPegs = new ArrayList< PuzzlePeg >();
		
		// Convert the PegColors into PuzzlePegs to store within the current row
		for( int i = 0; i < NUMPEGS; i++ )
		{
			guessPegs.add(new PuzzlePeg(guess.get(i)));
		}
		
		// Set as the guess for the current row
		rows.get(currRow).setPuzzlePegs(guessPegs);
		
	}

	/**
	 * Convert the new feedback and add to the board's representation
	 * @param feedback   the most recent feedback
	 */
	public void newFeedback( ArrayList< PegColor > feedback)
	{
		ArrayList< FeedbackPeg > feedbackPegs = new ArrayList< FeedbackPeg >();
		
		// Convert the PegColors into FeedbackPegs to store within the current row
		for( int i = 0; i < NUMPEGS; i++ )
		{
			feedbackPegs.add(new FeedbackPeg(feedback.get(i)));
		}
		
		// Set as the feedback for the current row
		rows.get(currRow).setFeedbackPegs(feedbackPegs);
		
		// Update the current row
		currRow++;
	}
	
	/**
	 * Undo the most recent row
	 */
	public void undoMove()
	{
		
	}
}
