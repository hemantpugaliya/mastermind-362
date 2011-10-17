import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * SmartCodeBreaker
 * Tries to win the game by using algorithms based on feedback
 * 
 * @author ajg9132
 *
 */
public  class SmartCodeBreaker extends CodeBreaker {
		
		/**
		 * A set containing every possible peg combination,
		 * used for Donald Knuth's mastermind algorithm.
		 */
		private HashSet<PegColor[]> possibleMoves;
		
		/**
		 * A reference to the board where this
		 * SmartCodeBreaker is playing.
		 */
		private MastermindBoard myBoard;
		
		/**
		 * Create a new SmartCodeBreaker and initialize its
		 * possible move set.
		 */
		public SmartCodeBreaker(MastermindBoard mb) {
			this.myBoard = mb;
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
				// FIRST MOVE
				ArrayList<PegColor> toReturn = new ArrayList<PegColor>();
				toReturn.add(PegColor.GBLACK);
				toReturn.add(PegColor.GBLACK);
				toReturn.add(PegColor.GWHITE);
				toReturn.add(PegColor.GWHITE);
				ArrayList<PegColor[]> temp = new ArrayList<PegColor[]>();
				for (PegColor[] movePegs: possibleMoves) {
					if (Arrays.equals(movePegs, toReturn.toArray())) {
						temp.add(movePegs);
					}
				}
				possibleMoves.removeAll(temp);
				return toReturn;
			} else if (possibleMoves.size() > 1) {
				// MOVE SEARCH
				PegRow lastGuess = myBoard.getLastFullRow();
				ArrayList<FeedbackPeg> feedback = lastGuess.getFeedbackPegs();
				ArrayList<PuzzlePeg> guess = lastGuess.getPuzzlePegs();
				PegColor[] pcguess = new PegColor[4];
				for (int i=0; i<4; ++i) {
					pcguess[i] = guess.get(i).getColor();
				}
				int fBlack = 0;
				int fWhite = 0;
				for (FeedbackPeg fp: feedback) {
					if (fp.getColor() == PegColor.FBLACK) {++fBlack;}
					else if (fp.getColor() == PegColor.FWHITE) {++fWhite;}
				}
				int[] lastScore = new int[2];
				lastScore[0] = fBlack;
				lastScore[1] = fWhite;
				//if (possibleMoves.size()>1) {
					
				HashSet<PegColor[]> temp = new HashSet<PegColor[]>();
				for (PegColor[] movePegs: possibleMoves) {
					//System.out.println(lastScore[0] + " " + lastScore[1]);
					//System.out.println(getScore(movePegs, guess)[0] + " " + getScore(movePegs, guess)[1]);
					if (!(Arrays.equals(lastScore, Mastermind.getScore(movePegs, pcguess)))) {
						temp.add(movePegs);
					}
				}
				//System.out.println(possibleMoves.size());
				//System.out.println(temp.size());
				possibleMoves.removeAll(temp);
				//System.out.println(possibleMoves.size());
				//}
				
				// If the CodeMaker is a liar, you will have to deal.
				if (possibleMoves.size()==0) {
					ArrayList<PegColor> toReturn = new ArrayList<PegColor>();
					toReturn.add(PegColor.GBLACK);
					toReturn.add(PegColor.GBLACK);
					toReturn.add(PegColor.GWHITE);
					toReturn.add(PegColor.GWHITE);
					return toReturn;
				}
				
				// You literally cannot take a random element from a HashSet
				// in any normal way, so let's just do something ridiculous.
				PegColor[] temp2 = null;
				//ArrayList<PegColor[]> chosen = new ArrayList<PegColor[]>();
				for (PegColor[] movePegs: possibleMoves) {
					//for (PegColor pc: movePegs) {
					//	System.out.println(pc);
					//}
					temp2 = movePegs;
					//chosen.add(movePegs);
					break;
				}
				//possibleMoves.removeAll(movePegs);
				possibleMoves.remove(temp2);
				//if (temp == null) {System.out.println("oh no");}
				ArrayList<PegColor> toReturn = new ArrayList<PegColor>();
				for (PegColor pc: temp2) {
					toReturn.add(pc);
				}
				return toReturn;
			} else if (possibleMoves.size() == 1) {
				// LAST POSSIBLE MOVE
				ArrayList<PegColor> toReturn = new ArrayList<PegColor>();
				for (PegColor[] movePegs: possibleMoves) {
					for (PegColor pc: movePegs) {
						toReturn.add(pc);
					}
					break;
				}
				return toReturn;
			} else {
				// STUMPED. RAGEQUIT.
				ArrayList<PegColor> toReturn = new ArrayList<PegColor>();
				toReturn.add(PegColor.GBLACK);
				toReturn.add(PegColor.GBLACK);
				toReturn.add(PegColor.GWHITE);
				toReturn.add(PegColor.GWHITE);
				return toReturn;
			}
		}
		
//		/**
//		 * Compare two rows of pegs to get the resulting feedback score.
//		 * @param guess An array of peg colors (doesn't have to be your guess)
//		 * @param guess2 An ArrayList of PuzzlePegs (does have to the the sol'n)
//		 * @return (black, white)
//		 */
//		private int[] getScore(PegColor[] guess, 
//				ArrayList<PuzzlePeg> guess2) {
//			int b = 0;
//			for (int i=0; i<4; ++i) {
//				if (guess[i] == guess2.get(i).getColor()){++b;}
//			}
//			int[] gcolors = new int[6];
//			int[] g2colors = new int[6];
//			int t = 0;
//			for (int i=0; i<4; ++i) {
//				switch (guess[i]) {
//					case GBLACK: ++gcolors[0]; break;
//					case GWHITE: ++gcolors[1]; break;
//					case RED: ++gcolors[2]; break;
//					case YELLOW: ++gcolors[3]; break;
//					case GREEN: ++gcolors[4]; break;
//					case BLUE: ++gcolors[5]; break;
//				}
//				switch (guess2.get(i).getColor()) {
//					case GBLACK: ++g2colors[0]; break;
//					case GWHITE: ++g2colors[1]; break;
//					case RED: ++g2colors[2]; break;
//					case YELLOW: ++g2colors[3]; break;
//					case GREEN: ++g2colors[4]; break;
//					case BLUE: ++g2colors[5]; break;
//				}
//			}
//			for (int i=0; i<6; ++i) {
//				//System.out.println(Math.min(gcolors[i], g2colors[i]));
//				t += Math.min(gcolors[i], g2colors[i]);
//			}
//			//System.out.println(t);
//			int w = t - b;
//			int[] toReturn = new int[2];
//			toReturn[0] = b;
//			toReturn[1] = w;
//			return toReturn;
//			
//		}
	
}
