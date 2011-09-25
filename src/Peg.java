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
 * @author ajg9132
 *
 */
public class Peg {
	
	private PegColor color;
	
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
	public void setColor(PegColor color) {
		this.color = color;
	}

	/**
	 * @return the color
	 */
	public PegColor getColor() {
		return color;
	}
	
	

}
