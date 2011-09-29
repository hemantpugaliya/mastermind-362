import java.awt.event.*;
import java.io.File;

import javax.swing.*;


public class MenuListener implements ActionListener{
	
	private JMenuItem newGame;
	private JRadioButtonMenuItem[] player;
	private JCheckBoxMenuItem log;
	private ButtonGroup playerGroup = new ButtonGroup();
	private int selectedCodebreaker = 0;
	private JFileChooser fc = new JFileChooser();
	private JFrame menu = new JFrame();
	private MastermindGame game;
	private BoardController controller;
	private JMenuItem exit;
	private JMenuItem setTimer;
	
	private boolean newGameStarted = false;
	
	private File file = new File("");
	private boolean logging = false;
	
	public MenuListener(MastermindGame _game, JMenuItem newG, JMenuItem _exit, JRadioButtonMenuItem[] _player,
			JCheckBoxMenuItem _log, BoardController control, JMenuItem timer){
		
		controller = control;
		
		newGame = newG;
		newGame.addActionListener(this);
		newGame.setActionCommand("n");
		
		exit = _exit;
		exit.addActionListener(this);
		exit.setActionCommand("x");
		
		game = _game;
		
		log = _log;
		log.addActionListener(this);
		log.setActionCommand("l");
		
		setTimer = timer;
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
	
	public void exit(){
		System.exit(0);
	}
	
	public boolean getLogging(){
		return logging;
	}
	
	public void setTimer(){
		String s = (String)JOptionPane.showInputDialog(
		                    null,
		                    "Seconds: ",
		                    "Set Timer",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    null,
		                    null);

		if ((s != null) && (s.length() > 0)) {
			try{
				int time = Integer.parseInt(s);
				if(time < 30)
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
