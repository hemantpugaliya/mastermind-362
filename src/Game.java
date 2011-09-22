
public class Game {
	public static void main(String[] args){
		GameBoardView game = new GameBoardView();
		game.create();
		BoardController controller;
		controller = game.getController();
	}

}
