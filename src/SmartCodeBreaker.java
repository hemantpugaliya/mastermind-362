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
		
		
		public ArrayList<PegColor> makeMove() {
			if (possibleMoves.size() == 1296) {
				ArrayList<PegColor> toReturn = new ArrayList<PegColor>();
				toReturn.add(PegColor.GBLACK);
				toReturn.add(PegColor.GBLACK);
				toReturn.add(PegColor.GWHITE);
				toReturn.add(PegColor.GWHITE);
				for (PegColor[] m: possibleMoves) {
					if (m.equals(toReturn.toArray())) {
						possibleMoves.remove(m);
					}
				}
				return toReturn;
			} else {
				PegRow lastGuess = myGame.board.getLastFullRow();
				ArrayList<FeedbackPeg> feedback = lastGuess.getFeedbackPegs();
				ArrayList<PuzzlePeg> guess = lastGuess.getPuzzlePegs();
				int b = 0;
				int w = 0;
				for (FeedbackPeg fp: feedback) {
					if (fp.getColor() == PegColor.FBLACK) {++b;}
					else if (fp.getColor() == PegColor.FWHITE) {++w;}
				}
				int[] lastScore = new int[2];
				lastScore[0] = b;
				lastScore[1] = w;
				if (possibleMoves.size()>1) {
					for (PegColor[] m: possibleMoves) {
						if (!lastScore.equals(getScore(m, guess))) {
							possibleMoves.remove(m);
						}
					}
				}
				// You literally cannot take a random element from a HashSet
				// in any normal way, so let's just do something ridiculous.
				for (PegColor[] m: possibleMoves) {
					possibleMoves.remove(m);
					return (ArrayList<PegColor>) Arrays.asList(m);
				}
				// Just in case?
				return null;
			}
			
		}
		
		private int[] getScore(PegColor[] guess, 
				ArrayList<PuzzlePeg> solution) {
			int b = 0;
			for (int i=0; i<4; ++i) {
				if (guess[i] == solution.get(i).getColor()){++b;}
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
				switch (solution.get(i).getColor()) {
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
