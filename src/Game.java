
/**
 * Game
 * The main method, initiates the game and creates the UI
 * 
 * @author Gabbie Burns
 *
 */
public class Game {
	
	/**
	 * Create the game and UI classes and run the game
	 * 
	 * @param args   ignored
	 */
	public static void main(String[] args){
		
		// Create the game model
		MastermindGame game = new MastermindGame();
		
		// Create the view
		BoardView view = new BoardView(game);
		view.create();
		
		// Set the controller within the game
		game.setController(view.getController());
	}
	
}
