import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class BoardController implements ActionListener{
	
	private JButton[][] guessRows;
	private JButton[][] feedbackRows;
	private JToggleButton[] guessPegs;
	private JButton[] solutionSet;
	private JButton eye;
	
	private JPanel guessPanel;
	private JPanel feedbackPanel;
	private JPanel pegsButtonsPanel;
	
	private int[] currentGuess = new int[4];
	private int[] currentFeedback = new int [4];
	private int currentGuessRow;
	private int currentFeedbackRow;
	
	private JButton undo;
	private JButton done;
	private JButton clear;
	
	private boolean guessState = true;
	
	private MastermindGame game;
	
	private int selectedPeg = 8;
	
	public BoardController(MastermindGame _game, JButton[][] guess, JButton[][] feed, JToggleButton[] pegs,
			JButton[] solution, JButton _eye, JButton _undo, JButton _done, JButton _clear,
			JPanel _guessPanel, JPanel _feedbackPanel, JPanel _pegButtons){
		
		guessRows = guess;
		feedbackRows = feed;
		guessPegs = pegs;
		solutionSet = solution;
		eye = _eye;
		game = _game;
		
		undo = _undo;
		done = _done;
		done.addActionListener(this);
		done.setActionCommand("d");
		clear = _clear;
		
		guessPanel = _guessPanel;
		feedbackPanel = _feedbackPanel;
		pegsButtonsPanel = _pegButtons;
		
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 4; j++){
				guessRows[i][j].addActionListener(this);
				String command = Integer.toString(i) + Integer.toString(j);
				guessRows[i][j].setActionCommand("g"+command);
				feedbackRows[i][j].addActionListener(this);
				feedbackRows[i][j].setActionCommand("f"+command);
			}
		}
		
		for(int i = 0; i < 8; i++){
			guessPegs[i].addActionListener(this);
			String command = Integer.toString(i);
			guessPegs[i].setActionCommand("p"+command);
		}
		
		for(int i = 0; i < 4; i++){
			solutionSet[i].addActionListener(this);
			String command = Integer.toString(i);
			solutionSet[i].setActionCommand("s"+command);
		}
		
		resetCurrentGuess();
		resetCurrentFeedback();
		currentGuessRow = 9;
		currentFeedbackRow = 9;
	}
	
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		char type = action.charAt(0);

		if(type == 'p'){
			selectPeg(action);
		}
		
		if(type == 'g' && selectedPeg != 8){
			placeGuessPeg(action);
		}
		
		if(type == 'f' && selectedPeg != 8){
			placeFeedbackPeg(action);
		}
		
		if(type == 's' && selectedPeg != 8){
			placeSolutionPeg(action);
		}
		
		if(type == 'd'){
			done();
		}
	}
	
	public void selectPeg(String p){
		char peg = p.charAt(1);
		int pegNum = Character.getNumericValue(peg);
		
		for(int i = 0; i < 8; i++){
			if(guessPegs[i].getModel().isSelected() && 
					i != pegNum){
				guessPegs[i].getModel().setSelected(false);
			}
		}
		
		if(selectedPeg != pegNum)
			selectedPeg = pegNum;
		else
			selectedPeg = 8;
	}
	
	public void placeGuessPeg(String p){
		char r = p.charAt(1);
		int row = Character.getNumericValue(r);
		
		char g = p.charAt(2);
		int guess = Character.getNumericValue(g);
		
		if(row == currentGuessRow){
			guessRows[row][guess].setIcon(new javax.swing.ImageIcon("icons/"+selectedPeg+".png"));
			currentGuess[guess] = selectedPeg;
		}
	}
	
	public void placeFeedbackPeg(String p){
		char r = p.charAt(1);
		int row = Character.getNumericValue(r);
		
		char g = p.charAt(2);
		int guess = Character.getNumericValue(g);
		
		if(row == currentFeedbackRow){
			int smallPeg = selectedPeg;
			if(smallPeg > 5){
				feedbackRows[row][guess].setIcon(new javax.swing.ImageIcon("icons/"+smallPeg+".png"));
				currentFeedback[guess] = smallPeg;
			}	
		}
	}
	
	public void placeSolutionPeg(String p){
		char s = p.charAt(1);
		int guess = Character.getNumericValue(s);
		
		solutionSet[guess].setIcon(new javax.swing.ImageIcon("icons/"+selectedPeg+".png"));		
	}
	
	public void done(){
		if(guessState){	
			ArrayList<PegColor> guess = new ArrayList<PegColor>();
			boolean full = false;	
			
			for(int i = 0; i < 4; i++){
				if(currentGuess[i] != 8){
					guess.add(PegColor.values()[currentGuess[i]]);
					if(i == 3)
						full = true;
				}
				else{
					break;
				}
			}
			
			if(full){
				game.makeGuess(guess);
				currentGuessRow -= 1;
				resetCurrentGuess();
				guessPanel.setVisible(false);
				feedbackPanel.setVisible(true);
				guessState = false;
			}
		}
		else{
			ArrayList<PegColor> feedback = new ArrayList<PegColor>();
			
			for(int i = 0; i < 4; i++){
				feedback.add(PegColor.values()[currentFeedback[i]]);
			}
			
			feedbackPanel.setVisible(false);
			guessPanel.setVisible(true);
			resetCurrentFeedback();
			int gameState = game.giveFeedback(feedback);
			
			if(gameState == 1 || gameState == 2){
				System.out.println(gameState);
				System.out.println("WORKING");
			}
			guessState = true;
		}
	}
	
	public void resetCurrentGuess(){
		for(int i = 0; i < 4; i++){
			currentGuess[i] = 8;
		}
	}
	
	public void resetCurrentFeedback(){
		for(int i = 0; i < 4; i++){
			currentFeedback[i] = 8;
		}
	}
}
