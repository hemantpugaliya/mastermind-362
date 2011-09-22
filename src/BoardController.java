import java.awt.event.*;
import javax.swing.*;

public class BoardController implements ActionListener{
	
	public JButton[][] guessRows;
	public JButton[][] feedbackRows;
	public JToggleButton[] guessPegs;
	public JButton[] solutionSet;
	public JButton eye;
	
	public int selectedPeg = -1;
	
	public BoardController(JButton[][] guess, JButton[][] feed, JToggleButton[] pegs,
			JButton[] solution, JButton _eye){
		guessRows = guess;
		feedbackRows = feed;
		guessPegs = pegs;
		solutionSet = solution;
		eye = _eye;
		
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 4; j++){
				guessRows[i][j].addActionListener(this);
				String command = Integer.toString(i) + Integer.toString(j);
				guessRows[i][j].setActionCommand("g"+command);
				feedbackRows[i][j].addActionListener(this);
				feedbackRows[i][j].setActionCommand("f"+command);
			}
		}
		
		for(int i = 0; i < 6; i++){
			guessPegs[i].addActionListener(this);
			String command = Integer.toString(i);
			guessPegs[i].setActionCommand("p"+command);
		}
		
	}
	
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		char type = action.charAt(0);

		if(type == 'p'){
			pegSelection(action);
		}
		
		if(type == 'g' && selectedPeg != -1){
			placeGuess(action);
		}
	}
	
	public void pegSelection(String p){
		char peg = p.charAt(1);
		int pegNum = Character.getNumericValue(peg);
		
		for(int i = 0; i < 6; i++){
			if(guessPegs[i].getModel().isSelected() && 
					i != pegNum){
				guessPegs[i].getModel().setSelected(false);
			}
		}
		
		if(selectedPeg != pegNum)
			selectedPeg = pegNum;
		else
			selectedPeg = -1;
	}
	
	public void placeGuess(String p){
		char r = p.charAt(1);
		int row = Character.getNumericValue(r);
		
		char g = p.charAt(2);
		int guess = Character.getNumericValue(g);
		guessRows[row][guess].setIcon(new javax.swing.ImageIcon("icons/"+selectedPeg+".png"));
	}
	

}
