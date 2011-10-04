import java.util.ArrayList;
import java.util.Random;

/*
 * ComputerCodeMaker.java
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
public class ComputerCodeMaker extends CodeMaker {
	
	/**
	 * The code the ComputerCodeMaker chooses.
	 */
	private PegColor[] code;
	/**
	 * A reference to the game this ComputerCodeMaker is playing in.
	 */
	private MastermindGame myGame;
	
	/**
	 * Create a ComputerCodeMaker that generates a random code.
	 * @param mg the current instance of MastermindGame.
	 */
	public ComputerCodeMaker(MastermindGame mg) {
		this.myGame = mg;
		this.code = new PegColor[4];
		Random rand = new Random();
		for (int i=0; i<4; ++i) {
			int r = rand.nextInt(6);
			switch (r) {
				case 0: code[i] = PegColor.GBLACK; break;
				case 1: code[i] = PegColor.GWHITE; break;
				case 2: code[i] = PegColor.RED; break;
				case 3: code[i] = PegColor.YELLOW; break;
				case 4: code[i] = PegColor.GREEN; break;
				case 5: code[i] = PegColor.BLUE; break;
			}
		}
	}
	
	/**
	 * Calculate the score of the last guess made by the CodeBreaker,
	 * and return the PegColors needed to represent that score.
	 * @return four FeedbackPeg colors
	 */
	public ArrayList<PegColor> getMove() {
		PegRow row = myGame.board.getFirstRowWithoutFeedback();
		ArrayList<PuzzlePeg> lastGuess = row.getPuzzlePegs();
		PegColor[] guess = new PegColor[4];
		for (int i=0; i<4; ++i) {
			guess[i] = lastGuess.get(i).getColor();
		}
		ArrayList<PegColor> toReturn = new ArrayList<PegColor>();
		int[] score = new int[2];
		score = Mastermind.getScore(guess, code);
		for (int i=0; i<score[0]; ++i) {
			toReturn.add(PegColor.FBLACK);
		}
		for (int i=0; i<score[1]; ++i) {
			toReturn.add(PegColor.FWHITE);
		}
		while (toReturn.size()<5) {
			toReturn.add(PegColor.BLANK);
		}
		return toReturn;
	}

}
