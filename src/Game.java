import server.MMGameServer;
import client.MMGameClient;


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
		
		// Create the view
		BoardView view = new BoardView();
		view.create();
		
		BoardController controller = view.getController();
		
		// Set up for networked games
		MMGameClient client = new MMGameClient();
		MMGameServer server = new MMGameServer(client);
		
		server.registerListener(controller);
		controller.setNetworkClient(client);
		
		
		
	}
	
}
