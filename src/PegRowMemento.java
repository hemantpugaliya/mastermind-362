import java.util.ArrayList;

/*
 * PegRowMemento.java
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
public class PegRowMemento {
	
	/**
	 * The guess state that it being stored in this memento
	 */
	private ArrayList<PuzzlePeg> guessState;
	/**
	 * The feedback state that is being stored in this memento
	 */
	private ArrayList<FeedbackPeg> feedbackState;
	
	/**
	 * Store the state of the PegRow creating this memento
	 * 
	 * @param puzzlePegs      the guess represented in this row
	 * @param feedbackPegs   the feedback represented in this row
	 */
	public void setState(ArrayList<PuzzlePeg> puzzlePegs, ArrayList<FeedbackPeg> feedbackPegs)
	{
		guessState = puzzlePegs;
		feedbackState = feedbackPegs;
	}
	
	/**
	 * Return the saved guess state
	 * 
	 * @return guessState
	 */
	public ArrayList<PuzzlePeg> getGuessState()
	{
		return guessState;
	}
	
	/**
	 * Return the saved feedback state
	 * 
	 * @return feedbackState
	 */
	public ArrayList<FeedbackPeg> getFeedbackState()
	{
		return feedbackState;
	}

}
