import java.util.ArrayList;
import java.util.Random;
import java.util.Set;


public  class SmartCodeBreaker extends CodeBreaker {
		
		/**
		 * A set containing every possible peg combination,
		 * used for Donald Knuth's mastermind algorithm.
		 */
		private Set<PegColor[]> possibleMoves;
		
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
			for (PegColor a: PegColor.values()) {
				for (PegColor b: PegColor.values()) {
					for (PegColor c: PegColor.values()) {
						for (PegColor d: PegColor.values()) {
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
			return null;
			
		}
	
}
