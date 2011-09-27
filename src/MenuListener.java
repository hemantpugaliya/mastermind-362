import java.awt.event.*;
import javax.swing.*;


public class MenuListener implements ActionListener{
	
	private JMenuItem newGame;
	private JRadioButtonMenuItem[] player;
	private JCheckBoxMenuItem log;
	private ButtonGroup playerGroup = new ButtonGroup();
	private int selectedPlayer = 0;
	
	public MenuListener(JMenuItem newG, JRadioButtonMenuItem[] _player, JCheckBoxMenuItem _log){
		
		newGame = newG;
		
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
		
		if(type == 'p'){
			setPlayer(action);
		}
		
		if(type == 'l'){
			
		}
	}
	
	public void setPlayer(String p){	
		char _player = p.charAt(1);
		int playerNum = Character.getNumericValue(_player);
		selectedPlayer = playerNum;		
	}

}
