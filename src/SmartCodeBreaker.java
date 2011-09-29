import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public  class SmartCodeBreaker extends CodeBreaker {
		
		/**
		 * A set containing every possible peg combination,
		 * used for Donald Knuth's mastermind algorithm.
		 */
		private HashSet<PegColor[]> possibleMoves;
		
		/**
		 * A reference to the game where this
		 * SmartCodeBreaker is playing.
		 */
		private MastermindGame myGame;
		
		/**
		 * Create a new SmartCodeBreaker and initialize its
		 * possible move set.
		 */
		public SmartCodeBreaker(MastermindGame mg) {
			this.myGame = mg;
			this.possibleMoves = new HashSet<PegColor[]>(1296);
			PegColor[] colors = new PegColor[6];
			colors[0] = PegColor.GBLACK;
			colors[1] = PegColor.GWHITE;
			colors[2] = PegColor.RED;
			colors[3] = PegColor.YELLOW;
			colors[4] = PegColor.GREEN;
			colors[5] = PegColor.BLUE;
			for (PegColor a: colors) {
				for (PegColor b: colors) {
					for (PegColor c: colors) {
						for (PegColor d: colors) {
							PegColor[] e = new PegColor[4];
							e[0] = a;
							e[1] = b;
							e[2] = c;
							e[3] = d;
							possibleMoves.add(e);
						}
					}
				}
			}
		}
		
		/**
		 * Using Knuth's algorithm for solving Mastermind,
		 * choose a move based on the number of possible moves left.
		 */
		public ArrayList<PegColor> makeMove() {
			if (possibleMoves.size() == 1296) {
				ArrayList<PegColor> toReturn = new ArrayList<PegColor>();
				toReturn.add(PegColor.GBLACK);
				toReturn.add(PegColor.GBLACK);
				toReturn.add(PegColor.GWHITE);
				toReturn.add(PegColor.GWHITE);
				for (PegColor[] movePegs: possibleMoves) {
					if (Arrays.equals(movePegs, toReturn.toArray())) {
						possibleMoves.remove(movePegs);
					}
				}
				return toReturn;
			} else {
				PegRow lastGuess = myGame.board.getLastFullRow();
				ArrayList<Peg> feedback = lastGuess.getFeedbackPegs();
				ArrayList<Peg> guess = lastGuess.getPuzzlePegs();
				int fBlack = 0;
				int fWhite = 0;
				for (Peg fp: feedback) {
					if (fp.getColor() == PegColor.FBLACK) {++fBlack;}
					else if (fp.getColor() == PegColor.FWHITE) {++fWhite;}
				}
				int[] lastScore = new int[2];
				lastScore[0] = fBlack;
				lastScore[1] = fWhite;
				if (possibleMoves.size()>1) {
					for (PegColor[] movePegs: possibleMoves) {
						if (!Arrays.equals(lastScore, getScore(movePegs, guess))) {
							possibleMoves.remove(movePegs);
						}
					}
				}
				// You literally cannot take a random element from a HashSet
				// in any normal way, so let's just do something ridiculous.
				for (PegColor[] movePegs: possibleMoves) {
					possibleMoves.remove(movePegs);
					return (ArrayList<PegColor>) Arrays.asList(movePegs);
				}
				// Just in case?
				return null;
			}
			
		}
		
		/**
		 * Compare two rows of pegs to get the resulting feedback score.
		 * @param guess An array of peg colors (doesn't have to be your guess)
		 * @param guess2 An ArrayList of PuzzlePegs (does have to the the sol'n)
		 * @return (black, white)
		 */
		private int[] getScore(PegColor[] guess, 
				ArrayList<Peg> guess2) {
			int b = 0;
			for (int i=0; i<4; ++i) {
				if (guess[i] == guess2.get(i).getColor()){++b;}
			}
			int[] gcolors = new int[6];
			int[] scolors = new int[6];
			int t = 0;
			for (int i=0; i<4; ++i) {
				switch (guess[i]) {
					case GBLACK: ++gcolors[0];
					case GWHITE: ++gcolors[1];
					case RED: ++gcolors[2];
					case YELLOW: ++gcolors[3];
					case GREEN: ++gcolors[4];
					case BLUE: ++gcolors[5];
				}
				switch (guess2.get(i).getColor()) {
					case GBLACK: ++scolors[0];
					case GWHITE: ++scolors[1];
					case RED: ++scolors[2];
					case YELLOW: ++scolors[3];
					case GREEN: ++scolors[4];
					case BLUE: ++scolors[5];
				}
			}
			for (int i=0; i<6; ++i) {
				t += Math.min(gcolors[i], scolors[i]);
			}
			int w = t - b;
			int[] toReturn = new int[2];
			toReturn[0] = b;
			toReturn[1] = w;
			return toReturn;
			
		}
	
}
