import java.util.ArrayList;

/*
 * Mastermind.java
 * 
 * Version:
 *     $Id$
 *
 * Revisions:
 *      $Log$
 */

/**
 * A class containing public static final methods,
 * useful for games of Mastermind.
 * @author ajg9132
 *
 */
public final class Mastermind {
	
	private Mastermind() {
		
	}

	/**
	 * Given two PegColor[], determine the score given by one being the
	 * solution, and the other being the guess made by the CodeBreaker.
	 * Can be used by a CodeMaker or a CodeBreaker AI.
	 * @param guess
	 * @param solution
	 * @return [black, white]
	 */
	public final static int[] getScore(PegColor[] guess, PegColor[] solution) {
		int b = 0; // blacks
		int w = 0; // whites
		int t = 0; // total
		for (int i=0; i<4; ++i) {if (guess[i] == solution[i]){++b;}}
		int[] gcolors = new int[6];
		int[] scolors = new int[6];
		for (int i=0; i<4; ++i) {
			switch (guess[i]) {
				case GBLACK: ++gcolors[0]; break;
				case GWHITE: ++gcolors[1]; break;
				case RED: ++gcolors[2]; break;
				case YELLOW: ++gcolors[3]; break;
				case GREEN: ++gcolors[4]; break;
				case BLUE: ++gcolors[5]; break;}
			switch (solution[i]) {
				case GBLACK: ++scolors[0]; break;
				case GWHITE: ++scolors[1]; break;
				case RED: ++scolors[2]; break;
				case YELLOW: ++scolors[3]; break;
				case GREEN: ++scolors[4]; break;
				case BLUE: ++scolors[5]; break;}
		}
		for (int i=0; i<6; ++i) {t += Math.min(gcolors[i], scolors[i]);}
		w = t - b;
		int[] toReturn = new int[2];
		toReturn[0] = b;
		toReturn[1] = w;
		return toReturn;
		
	}
	
}
