import java.awt.Color;
import java.util.ArrayList;

/*
 * PegRow.java
 * 
 * Version:
 *     $Id$
 *
 * Revisions:
 *      $Log$
 */

/**
 * @author ajg9132
 *
 */
public class PegRow {
	
	private ArrayList<Peg> puzzlePegs;
	private ArrayList<Peg> feedbackPegs;
	
	public PegRow() {
		this.puzzlePegs = new ArrayList<Peg>();
		this.feedbackPegs = new ArrayList<Peg>();
	}
	
	/**
	 * Compare the guess in this row to the solution.
	 * @param solution
	 * @return true if solution equals this row's puzzle pegs.
	 */
	public boolean compareGuess(ArrayList<Peg> solution) {
		return (solution.get(0).color == puzzlePegs.get(0).color
				&& solution.get(1).color == puzzlePegs.get(1).color
				&& solution.get(2).color == puzzlePegs.get(2).color
				&& solution.get(3).color == puzzlePegs.get(3).color);
	}

	/**
	 * @return the puzzlePegs
	 */
	public ArrayList<Peg> getPuzzlePegs() {
		return puzzlePegs;
	}

	/**
	 * @return the feedbackPegs
	 */
	public ArrayList<Peg> getFeedbackPegs() {
		return feedbackPegs;
	}

	/**
	 * @param puzzlePegs the puzzlePegs to set
	 */
	public void setPuzzlePegs(ArrayList<Peg> puzzlePegs) {
		this.puzzlePegs = puzzlePegs;
	}

	/**
	 * @param feedbackPegs the feedbackPegs to set
	 */
	public void setFeedbackPegs(ArrayList<Peg> feedbackPegs) {
		this.feedbackPegs = feedbackPegs;
	}
	
	/**
	 * Check if the most recent feedback indicates a win
	 * 
	 * @return true if all of the feedback pegs are black
	 */
	public boolean checkWin()
	{
		boolean win = true;
		
		for( Peg peg : feedbackPegs)
		{
			if( peg.getColor() != PegColor.FBLACK)
			{
				win = false;
			}
		}
		
		return win;
		
	}

}
