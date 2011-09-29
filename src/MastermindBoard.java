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
		ArrayList<PuzzlePeg> guessPegs = new ArrayList<PuzzlePeg>();
		
		// Convert the PegColors into PuzzlePegs to store within the current row
		for( int i = 0; i < NUMPEGS; i++ )
		{
			guessPegs.add(new PuzzlePeg(guess.get(i)));
		}
		
		// Save a memento for the current row
		mementos.push(rows.get(currRow).createMemento());
		
		// Set as the guess for the current row
		rows.get(currRow).setPuzzlePegs(guessPegs);
		
	}

	/**
	 * Convert the new feedback and add to the board's representation
	 * @param feedback   the most recent feedback
	 */
	public void newFeedback( ArrayList< PegColor > feedback)
	{
		ArrayList<FeedbackPeg> feedbackPegs = new ArrayList<FeedbackPeg>();
		
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
	
	public void newSolution(ArrayList< PegColor > solutionSet){
		ArrayList< PuzzlePeg > solutionPegs = new ArrayList< PuzzlePeg >();
		
		for( int i = 0; i < 4; i++ )
		{
			solutionPegs.add(new PuzzlePeg(solutionSet.get(i)));
		}
		solution = new Solution(solutionPegs);
	}
	
	/**
	 * Undo the most recent row
	 * 
	 * @param   a number representing how many rows to go back with the undo
	 */
	public void undoMove(int numUndo)
	{
		// Set the currRow back the number of rows
		currRow = currRow - numUndo;
		
		// Reset the row using the most recent memento
		rows.get(currRow).setMemento(mementos.pop());
	}
	
	/**
	 * Check if the game has been won or lost based on the most recent feedback
	 */
	public int checkWinLoss()
	{
		int end = 0;
		
		if( rows.get(currRow-1).checkWin() )
		{
			end = 2;
		}
		else if( currRow == NUMROWS )
		{
			end = 1;
		}
		
		return end;
			
	}
	
	/**
	 * Return the most recent PegRow that does not have a guess.
	 * @return
	 */
	public PegRow getFirstRowWithoutGuess() {
		for (PegRow pr: this.rows) {
			ArrayList<PuzzlePeg> pegs = pr.getPuzzlePegs();
			if ( pegs.get(0).getColor() == PegColor.BLANK
					&& pegs.get(1).getColor() == PegColor.BLANK
					&& pegs.get(2).getColor() == PegColor.BLANK
					&& pegs.get(3).getColor() == PegColor.BLANK ) {
				return pr;
			}
		}
		// If all rows are full, return null.
		return null;	
	}
	
	/**
	 * Return the most recent PegRow that has not received feedback.
	 * @return
	 */
	public PegRow getFirstRowWithoutFeedback() {
		for (PegRow pr: this.rows) {
			ArrayList<FeedbackPeg> pegs = pr.getFeedbackPegs();
			if ( pegs.get(0).getColor() == PegColor.BLANK
					&& pegs.get(1).getColor() == PegColor.BLANK
					&& pegs.get(2).getColor() == PegColor.BLANK
					&& pegs.get(3).getColor() == PegColor.BLANK ) {
				return pr;
			}
		}
		// If all rows are full, return null.
		return null;
	}
	
	/**
	 * Return the most recent PegRow that has a guess.
	 * Checking for feedback is more difficult because of (0,0) feedback.
	 * @return
	 */
	public PegRow getLastFullRow() {
		
		for(int i = NUMROWS - 1; i >= 0; i--)
		{
			if( rows.get(i).isFull())
			{
				return rows.get(i);
			}
		}
		// If no row is full, return null.
		return null;
	}
}
