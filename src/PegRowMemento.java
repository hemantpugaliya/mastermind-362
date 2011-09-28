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
	private ArrayList<Peg> guessState;
	/**
	 * The feedback state that is being stored in this memento
	 */
	private ArrayList<Peg> feedbackState;
	
	/**
	 * Store the state of the PegRow creating this memento
	 * 
	 * @param guess      the guess represented in this row
	 * @param feedback   the feedback represented in this row
	 */
	public void setState(ArrayList<Peg> guess, ArrayList<Peg> feedback)
	{
		guessState = guess;
		feedbackState = feedback;
	}
	
	/**
	 * Return the saved guess state
	 * 
	 * @return guessState
	 */
	public ArrayList<Peg> getGuessState()
	{
		return guessState;
	}
	
	/**
	 * Return the saved feedback state
	 * 
	 * @return feedbackState
	 */
	public ArrayList<Peg> getFeedbackState()
	{
		return feedbackState;
	}

}
