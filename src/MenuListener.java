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
	
	private JRadioButtonMenuItem[] codebreaker;	
	private ButtonGroup codebreakerGroup = new ButtonGroup();
	private int selectedCodebreaker = 0;
	private JRadioButtonMenuItem[] codemaker;	
	private ButtonGroup codemakerGroup = new ButtonGroup();
	private int selectedCodemaker = 0;
	
	private JFileChooser fc = new JFileChooser();
	private JFrame menu = new JFrame();
	
	private BoardController controller;
		
	private boolean newGameStarted = false;
	private boolean playing = false;
	
	private File file = new File("");
	private boolean logging = false;
	
	
	/**
	 * Creates a new MenuListener and adds ActionListener's to items accordingly
	 * @param _game
	 * @param _newGame
	 * @param _exit
	 * @param _codebreaker
	 * @param _log
	 * @param _control
	 * @param _timer
	 */
	public MenuListener(JMenuItem _newGame, JMenuItem _exit, JRadioButtonMenuItem[] _codebreaker,
			JCheckBoxMenuItem _log, BoardController _control, JMenuItem _timer, JRadioButtonMenuItem[] _codemaker){
		
		controller = _control;
		
		newGame = _newGame;
		newGame.addActionListener(this);
		newGame.setActionCommand("n");
		
		exit = _exit;
		exit.addActionListener(this);
		exit.setActionCommand("x");
		
		log = _log;
		log.addActionListener(this);
		log.setActionCommand("l");
		
		setTimer = _timer;
		setTimer.addActionListener(this);
		setTimer.setActionCommand("t");
		
		codebreaker = _codebreaker;	
		for(int i = 0; i < 4; i++){
			codebreaker[i].addActionListener(this);
			String command = Integer.toString(i);
			codebreaker[i].setActionCommand("b"+command);
			
			codebreakerGroup.add(codebreaker[i]);
		}
		
		codemaker = _codemaker;
		for(int i = 0; i < 3; i++){
			codemaker[i].addActionListener(this);
			String command = Integer.toString(i);
			codemaker[i].setActionCommand("m"+command);
			
			codemakerGroup.add(codemaker[i]);
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
		
		if(type == 'b'){
			setCodebreaker(action);
		}
		
		if(type == 'm'){
			setCodemaker(action);
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
		controller.setCodeBreaker(selectedCodebreaker);
		
		if(selectedCodebreaker == 0)
			controller.setCodebreakerHuman();
	}
	
	public void setCodemaker(String p){	
		char _player = p.charAt(1);
		int codemakerNum = Character.getNumericValue(_player);
		selectedCodemaker = codemakerNum;
		controller.setCodeMaker(selectedCodemaker);		
	}
	
	/**
	 * Notifies the controller and the game when the user has called for a new game.
	 */
	public void newGame(){
		controller.resetGame();
		if(!logging)
		{
			//game.newGame(log.getModel().isSelected(), null, selectedCodebreaker);
		}
		else{
			if(!newGameStarted)
			{
				//game.newGame(log.getModel().isSelected(), file.toString(), selectedCodebreaker);
			}
			else{
				promptForFile();
				if(fc.getSelectedFile() != null){
					//game.newGame(log.getModel().isSelected(), file.toString(), selectedCodebreaker);
				}
			}
		}
		
		newGameStarted = true;
		playing = true;
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
				//game.startLogging(file.toString());
				logging = true;
			}catch(Exception e1){
				log.getModel().setSelected(false);
				fc.setSelectedFile(null);
			}
		}
		else{
			//game.stopLogging();
			fc.setSelectedFile(null);
			logging = false;
		}
		controller.setLogging(logging);			
	}
}
