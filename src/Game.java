
public class Game {
	
	//private Board myBoard = null;
	protected boolean logging = false;
	
	public static void main(String[] args){
		GameBoardView game = new GameBoardView();
		game.create();
		BoardController controller;
		controller = game.getController();
	}
	
	public void startLogging( String filename )
	{
		logging = true;
		StartCommand startLog = new StartCommand( filename );
		startLog.Execute();
	}

}
