/*
 * Peg.java
 * 
 * Version:
 *     $Id$
 *
 * Revisions:
 *      $Log$
 */

/**
 * Peg
 * The most basic unit of the state representation, contains a color
 * 
 * @author ajg9132
 *
 */
public abstract class Peg {
	
	@SuppressWarnings("unused")
	protected PegColor color;
	
	/**
	 * Create a Peg.
	 * @param color
	 */
	public Peg( PegColor color ) {
		this.setColor(color);
	}

	/**
	 * @param color the color to set
	 */
	public abstract void setColor(PegColor color);

	/**
	 * @return the color
	 */
	public abstract PegColor getColor();
	
	

}
