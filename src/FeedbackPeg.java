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

	/* (non-Javadoc)
	 * @see Peg#getColor()
	 */
	@Override
	public PegColor getColor() {
		return this.color;
	}

	/* (non-Javadoc)
	 * @see Peg#setColor(PegColor)
	 */
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
