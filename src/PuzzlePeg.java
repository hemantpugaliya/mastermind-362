/*
 * PuzzlePeg.java
 * 
 * Version:
 *     $Id$
 *
 * Revisions:
 *      $Log$
 */

/**
 * PuzzlePeg
 * A Peg restricted to be used only for guesses
 * 
 * @author ajg9132
 *
 */
public class PuzzlePeg extends Peg {

	/**
	 * Create a PuzzlePeg.
	 * If an invalid color is chosen, the peg will be blank.
	 * @param color
	 */
	public PuzzlePeg(PegColor color) {
		super(color);
		
		if (color == PegColor.BLANK || 
				color == PegColor.FBLACK  ||
				color == PegColor.FWHITE) 
		{
			this.color = PegColor.BLANK;
		} 
		else 
		{
			this.color = color;
		}
	}

	/**
	 * Return the color of this peg
	 * 
	 * @return   color
	 */
	@Override
	public PegColor getColor() {
		// TODO Auto-generated method stub
		return this.color;
	}

	/**
	 * Change the color of this peg.
	 * Choosing an invalid color will do nothing.
	 */
	@Override
	public void setColor(PegColor color) 
	{
		if (color == PegColor.BLANK || 
				color == PegColor.FBLACK  ||
				color == PegColor.FWHITE) 
		{
			// Do nothing
		}
		else
		{
		this.color = color;
		}
	}

}
