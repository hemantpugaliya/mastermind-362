import java.awt.event.*;
import java.io.File;

import javax.swing.*;

/**
 * MenuListener
 * 
 * @author Jim Kuglics
 *
 */

public class MenuListener implements ActionListener{
	
	private JMenuItem newGame;
	private JMenuItem exit;
	private JMenuItem setTimer;
	private JCheckBoxMenuItem log;
	
	private JRadioButtonMenuItem[] player;	
	private ButtonGroup playerGroup = new ButtonGroup();
	private int selectedCodebreaker = 0;
	
	private JFileChooser fc = new JFileChooser();
	private JFrame menu = new JFrame();
	
	private MastermindGame game;
	private BoardController controller;
		
	private boolean newGameStarted = false;
	
	private File file = new File("");
	private boolean logging = false;
	
	/**
	 * Creates a new MenuListener and adds ActionListener's to items accordingly
	 * @param _game
	 * @param _newGame
	 * @param _exit
	 * @param _player
	 * @param _log
	 * @param _control
	 * @param _timer
	 */
	public MenuListener(MastermindGame _game, JMenuItem _newGame, JMenuItem _exit, JRadioButtonMenuItem[] _player,
			JCheckBoxMenuItem _log, BoardController _control, JMenuItem _timer){
		
		controller = _control;
		
		newGame = _newGame;
		newGame.addActionListener(this);
		newGame.setActionCommand("n");
		
		exit = _exit;
		exit.addActionListener(this);
		exit.setActionCommand("x");
		
		game = _game;
		
		log = _log;
		log.addActionListener(this);
		log.setActionCommand("l");
		
		setTimer = _timer;
		setTimer.addActionListener(this);
		setTimer.setActionCommand("t");
		
		player = _player;	
		for(int i = 0; i < 3; i++){
			player[i].addActionListener(this);
			String command = Integer.toString(i);
			player[i].setActionCommand("p"+command);
			
			playerGroup.add(player[i]);
		}
		
	}
	
	/**
	 * Responds to any slection of menu items accordingly.
	 */
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		char type = action.charAt(0);
		
		if(type == 'n'){
			newGame();
		}
		
		if(type == 'p'){
			setCodebreaker(action);
		}
		
		if(type == 'l'){
			promptForFile();
		}
		
		if(type == 't'){
			setTimer();
		}
		
		if(type == 'x'){
			exit();
		}
	}
	
	/**
	 * Notifies the controller and the game when the codebreaker is changed.
	 * @param p
	 */
	public void setCodebreaker(String p){	
		char _player = p.charAt(1);
		int codebreakerNum = Character.getNumericValue(_player);
		selectedCodebreaker = codebreakerNum;
		game.setCodeBreaker(selectedCodebreaker);
		
		if(selectedCodebreaker != 0)
			controller.setCodebreakerComputer();
		else
			controller.setCodebreakerHuman();
	}
	
	/**
	 * Notifies the controller and the game when the user has called for a new game.
	 */
	public void newGame(){
		controller.resetGame();
		if(!logging)
			game.newGame(log.getModel().isSelected(), null, selectedCodebreaker);
		else{
			if(!newGameStarted)
				game.newGame(log.getModel().isSelected(), file.toString(), selectedCodebreaker);
			else{
				promptForFile();
				if(fc.getSelectedFile() != null){
					game.newGame(log.getModel().isSelected(), file.toString(), selectedCodebreaker);
				}
			}
		}
		
		newGameStarted = true;
	}
	
	/**
	 * exits the game
	 */
	public void exit(){
		System.exit(0);
	}
	
	/**
	 * gets logging boolean
	 * @return logging
	 */
	public boolean getLogging(){
		return logging;
	}
	
	/**
	 * Shows a dialog box to allow the user to set the Timer.
	 */
	public void setTimer(){
		String s = (String)JOptionPane.showInputDialog(
		                    null,
		                    "Seconds: ",
		                    "Set Timer",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    null,
		                    null);
		
		//only accepts correct input or nothing at all.
		if ((s != null) && (s.length() > 0)) {
			try{
				int time = Integer.parseInt(s);
				if(time <= 30 && time >= 0)
					controller.setTime(time);
				else
					setTimer();
			}
			catch(Exception e){
				setTimer();
			}
		    return;
		}
	}
	
	/**
	 * Used to start logging.
	 * Shows a file chooser which allows the user to select a file to log to.
	 */
	public void promptForFile(){
		fc.setSelectedFile(null);
		file = null;
		
		if(log.getModel().isSelected()){
			fc.showOpenDialog(menu);
			try{
				file = fc.getSelectedFile();
				game.startLogging(file.toString());
				logging = true;
			}catch(Exception e1){
				log.getModel().setSelected(false);
				fc.setSelectedFile(null);
			}
		}
		else{
			game.stopLogging();
			fc.setSelectedFile(null);
			logging = false;
		}
		controller.setLogging(logging);			
	}
}
