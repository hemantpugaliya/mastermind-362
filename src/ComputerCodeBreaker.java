import java.util.ArrayList;
import java.util.Random;


public class ComputerCodeBreaker extends CodeBreaker {
	
	
	private boolean smart = false;
	MastermindGame mg = new MastermindGame();
	private ArrayList guess = new ArrayList<PegColor>();
		
	public ArrayList<PegColor> makeMove() {
		if(smart)
			return this.smartGuess();
		else
			return this.randomGuess();
	}
	
	private ArrayList<PegColor> randomGuess(){
		Random r = new Random();
		int theGuess = r.nextInt(6);		
		guess.add(PegColor.values()[theGuess]);
		guess.add(PegColor.values()[theGuess]);
		guess.add(PegColor.values()[theGuess]);
		guess.add(PegColor.values()[theGuess]);		
		mg.makeGuess(guess);
		return guess;
	}
	
	private ArrayList<PegColor> smartGuess(){
		Random r = new Random();
		int theGuess = r.nextInt(6);		
		guess.add(PegColor.values()[theGuess]);
		guess.add(PegColor.values()[theGuess]);
		guess.add(PegColor.values()[theGuess]);
		guess.add(PegColor.values()[theGuess]);		
		mg.makeGuess(guess);
		return guess;
	}
	
}
