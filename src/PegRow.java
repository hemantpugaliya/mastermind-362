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

}
