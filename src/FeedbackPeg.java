/*
 * FeedbackPeg.java
 * 
 * Version:
 *     $Id$
 *
 * Revisions:
 *      $Log$
 */

/**
 * FeedbackPeg
 * A Peg restricted to be used only for feedback
 *  
 * @author ajg9132
 *
 */
public class FeedbackPeg extends Peg {

	/**
	 * Create a FeedbackPeg.
	 * If an invalid color is chosen, the peg will be blank.
	 * @param color
	 */
	public FeedbackPeg(PegColor color) {
		super(color);
		if (color == PegColor.BLANK || 
				color == PegColor.FBLACK  ||
				color == PegColor.FWHITE) {
			this.color = color;
		} else {
			this.color = PegColor.BLANK;
		}
	}

	/**
	 * Return the color of this peg
	 * 
	 * @return   color
	 */
	@Override
	public PegColor getColor() {
		return this.color;
	}

	/**
	 * Change the color of this peg.
	 * Choosing an invalid color will do nothing.
	 */
	@Override
	public void setColor(PegColor color) {
		if (color == PegColor.BLANK || 
				color == PegColor.FBLACK  ||
				color == PegColor.FWHITE) {
			this.color = color;
		}
	}

}
