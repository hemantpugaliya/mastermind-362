import java.util.ArrayList;

/*
 * Solution.java
 * 
 * Version:
 *     $Id$
 *
 * Revisions:
 *      $Log$
 */

/**
 * Solutions
 * Stores the solution set by the codemaker
 * 
 * @author ajg9132
 *
 */
public class Solution {

	/**
	 * The solution for the current game
	 */
	private ArrayList<PuzzlePeg> solution;
	
	/**
	 * Save the solution upon creation 
	 * @param colors   the solution
	 */
	public Solution(ArrayList<PuzzlePeg> colors) {
		this.solution = colors;
	}

	/**
	 * @return the solution
	 */
	public ArrayList<PuzzlePeg> getSolution() {
		return solution;
	}

	/**
	 * @param solution the solution to set
	 */
	public void setSolution(ArrayList<PuzzlePeg> solution) {
		this.solution = solution;
	}
	
	
}
