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
 * @author ajg9132
 *
 */
public class PuzzlePeg extends Peg {

	/**
	 * Create a PuzzlePeg.
	 * @param color
	 */
	public PuzzlePeg(PegColor color) {
		super(color);
	}

	/* (non-Javadoc)
	 * @see Peg#getColor()
	 */
	@Override
	public PegColor getColor() {
		// TODO Auto-generated method stub
		return this.color;
	}

	/* (non-Javadoc)
	 * @see Peg#setColor(PegColor)
	 */
	@Override
	public void setColor(PegColor color) {
		this.color = color;
	}

}
