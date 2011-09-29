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
	public boolean compareGuess(ArrayList<Peg> solution) {
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
	
	/**
	 * Create a memento to store the current state in case of an undo
	 */
	public PegRowMemento createMemento()
	{
		PegRowMemento myMem = new PegRowMemento();
		myMem.setState( puzzlePegs, feedbackPegs );
		
		return myMem;
	}
	
	/**
	 * Reset the internal state to what is saved in the given memento
	 * 
	 * @param mem   a PegRowMemento that was saved by the MastermindBoard
	 */
	public void setMemento(PegRowMemento mem)
	{
		puzzlePegs = mem.getGuessState();
		feedbackPegs = mem.getFeedbackState();
	}

}
