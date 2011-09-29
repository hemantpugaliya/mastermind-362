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
	
	public MenuListener(MastermindGame _game, JMenuItem newG, JRadioButtonMenuItem[] _player,
			JCheckBoxMenuItem _log, BoardController control){
		
		controller = control;
		
		newGame = newG;
		newGame.addActionListener(this);
		newGame.setActionCommand("n");
		
		game = _game;
		
		log = _log;
		log.addActionListener(this);
		log.setActionCommand("l");
		
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
			if(log.getModel().isSelected()){
				fc.showOpenDialog(menu);
				try{
					File file = fc.getSelectedFile();
					game.startLogging(file.toString());
				}catch(Exception e1){
					log.getModel().setSelected(false);
					fc.setSelectedFile(null);
				}
			}
			else{
				game.stopLogging();
				fc.setSelectedFile(null);
			}
				
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
		game.newGame(log.getModel().isSelected(), selectedCodebreaker);		
	}

}
