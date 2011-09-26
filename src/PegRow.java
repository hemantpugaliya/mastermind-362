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
	
	private ArrayList<PuzzlePeg> puzzlePegs;
	private ArrayList<FeedbackPeg> feedbackPegs;
	
	public PegRow() {
		this.puzzlePegs = new ArrayList<PuzzlePeg>();
		this.feedbackPegs = new ArrayList<FeedbackPeg>();
	}
	
	/**
	 * Compare the guess in this row to the solution.
	 * @param solution
	 * @return true if solution equals this row's puzzle pegs.
	 */
	public boolean compareGuess(ArrayList<PuzzlePeg> solution) {
		return (solution.get(0).color == puzzlePegs.get(0).color
				&& solution.get(1).color == puzzlePegs.get(1).color
				&& solution.get(2).color == puzzlePegs.get(2).color
				&& solution.get(3).color == puzzlePegs.get(3).color);
	}

	/**
	 * @return the puzzlePegs
	 */
	public ArrayList<PuzzlePeg> getPuzzlePegs() {
		return puzzlePegs;
	}

	/**
	 * @return the feedbackPegs
	 */
	public ArrayList<FeedbackPeg> getFeedbackPegs() {
		return feedbackPegs;
	}

	/**
	 * @param puzzlePegs the puzzlePegs to set
	 */
	public void setPuzzlePegs(ArrayList<PuzzlePeg> puzzlePegs) {
		this.puzzlePegs = puzzlePegs;
	}

	/**
	 * @param feedbackPegs the feedbackPegs to set
	 */
	public void setFeedbackPegs(ArrayList<FeedbackPeg> feedbackPegs) {
		this.feedbackPegs = feedbackPegs;
	}

}
