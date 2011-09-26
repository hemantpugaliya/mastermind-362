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
 * @author ajg9132
 *
 */
public class Solution {

	private ArrayList<PuzzlePeg> solution;
	
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
