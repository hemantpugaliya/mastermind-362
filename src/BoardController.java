import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class BoardController implements ActionListener{
	
	private JButton[][] guessRows;
	private JButton[][] feedbackRows;
	private JToggleButton[] guessPegs;
	private JButton[] solutionSet;
	private JButton eye;
	
	private boolean looking;
	
	private JPanel guessPanel;
	private JPanel feedbackPanel;
	private JPanel pegsButtonsPanel;
	
	private JLabel instruction;
	
	private int[] currentGuess = new int[4];
	private int[] currentFeedback = new int [4];
	private int[] solution = new int[4];
	private int currentGuessRow = -1;
	private int currentFeedbackRow = -1;
	
	private JButton undo;
	private JButton done;
	private JButton clear;
	
	private boolean settingSolution;
	private boolean guessState;
	private boolean computer;
	
	private MastermindGame game;
	
	private int selectedPeg = 8;
	
	public BoardController(MastermindGame _game, JButton[][] guess, JButton[][] feed, JToggleButton[] pegs,
			JButton[] solution, JButton _eye, JButton _undo, JButton _done, JButton _clear,
			JPanel _guessPanel, JPanel _feedbackPanel, JPanel _pegButtons, JLabel _instruction){
		
		guessRows = guess;
		feedbackRows = feed;
		guessPegs = pegs;
		solutionSet = solution;
		eye = _eye;
		game = _game;
		instruction = _instruction;
		
		eye.addActionListener(this);
		eye.setActionCommand("e");
		
		undo = _undo;
		undo.addActionListener(this);
		undo.setActionCommand("u");
		
		done = _done;	
		done.addActionListener(this);
		done.setActionCommand("d");
		
		clear = _clear;
		clear.addActionListener(this);
		clear.setActionCommand("c");
		
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
		
		if(type == 'c'){
			clear();
		}
		
		if(type == 'e'){
			eyeball();
		}
		
		if(type == 'u'){
			undo();
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
		
		if(settingSolution){
			solutionSet[guess].setIcon(new javax.swing.ImageIcon("icons/"+selectedPeg+".png"));		
			solution[guess] = selectedPeg;
		}
	}
	
	public void done(){
		if(settingSolution){
			ArrayList<PegColor> solutionArray = new ArrayList<PegColor>();
			boolean full = false;	
			
			for(int i = 0; i < 4; i++){
				if(solution[i] != 8){
					solutionArray.add(PegColor.values()[solution[i]]);
					if(i == 3)
						full = true;
				}
				else{
					break;
				}
			}
			
			if(full){
				for(int i = 0; i < 4; i++){
					solutionSet[i].setIcon(new javax.swing.ImageIcon("icons/gray3.png"));
				}
				settingSolution = false;
				guessState = true;
				currentGuessRow = 9;
				currentFeedbackRow = 9;
				instruction.setText("Codbreaker's Turn");
				
				if(computer){
					guessState = false;
					for(int i = 0; i < 6; i++){
						guessPegs[i].removeActionListener(this);
					}
					askForComputerGuess();
				}
			}
		}
		else if(guessState){	
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
				instruction.setText("Codemaker's Turn");
			}
		}
		else{
			ArrayList<PegColor> feedback = new ArrayList<PegColor>();
			
			for(int i = 0; i < 4; i++){
				feedback.add(PegColor.values()[currentFeedback[i]]);
			}
			
			int gameState = game.giveFeedback(feedback);
			currentFeedbackRow -= 1;
			resetCurrentFeedback();
			feedbackPanel.setVisible(false);
			guessPanel.setVisible(true);
			guessState = true;
			instruction.setText("Codbreaker's Turn");
			
			if(gameState == 1 || gameState == 2){
				System.out.println("WINNER");
			}
			
			if(looking){
				closeEye();
			}
			
			if(computer){
				askForComputerGuess();
			}
		}
		
		for(int i = 0; i < 8; i++){
			if(guessPegs[i].getModel().isSelected()){
				guessPegs[i].getModel().setSelected(false);
			}
		}
		
		selectedPeg = 8;
	}
	
	public void clear(){
		if(settingSolution){
			for(int i = 0; i < 4; i++){
				solutionSet[i].setIcon(new javax.swing.ImageIcon("icons/gray3.png"));
				resetSolution();
			}
		}
		else if(guessState){
			for(int i = 0; i < 4; i++){
				guessRows[currentGuessRow][i].setIcon(new javax.swing.ImageIcon("icons/gray.png"));
				resetCurrentGuess();
			}
		}
		else{
			for(int i = 0; i < 4; i++){
				feedbackRows[currentFeedbackRow][i].setIcon(new javax.swing.ImageIcon("icons/gray2.png"));
				resetCurrentFeedback();
			}
		}
	}
	
	public void undo(){
		if(guessState){
			clear();
			currentGuessRow += 1;			
			guessState = false;
			currentFeedbackRow += 1;
			clear();
			guessState = true;
			clear();
		}
		else{
			clear();
			currentGuessRow += 1;
			guessState = true;
			clear();
			feedbackPanel.setVisible(false);
			guessPanel.setVisible(true);
		}
		
		game.undo();
	}
	
	public void eyeball(){
		if(!looking && !guessState)
			openEye();
		else
			closeEye();
	}
	
	public void openEye(){
		for(int i = 0; i < 4; i++){
			solutionSet[i].setIcon(new javax.swing.ImageIcon("icons/"+solution[i]+".png"));
		}
		looking = true;	
	}
	
	public void closeEye(){
		for(int i = 0; i < 4; i++){
			solutionSet[i].setIcon(new javax.swing.ImageIcon("icons/gray3.png"));
		}
		looking = false;
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
	
	public void resetSolution(){
		for(int i = 0; i < 4; i++){
			solution[i] = 8;
		}
	}
	
	public void resetGame(){
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 4; j++){
				guessRows[i][j].setIcon(new javax.swing.ImageIcon("icons/gray.png"));
				feedbackRows[i][j].setIcon(new javax.swing.ImageIcon("icons/gray2.png"));
			}
		}
		resetCurrentGuess();
		resetCurrentFeedback();
		resetSolution();
		instruction.setText("Set The Code:");
		settingSolution = true;
		guessState = false;
		looking = false;
		
		feedbackPanel.setVisible(false);
		guessPanel.setVisible(true);
		
	}

	public void placeComputerGuess(ArrayList<PegColor> guess){
		for(int i = 0; i < 4; i++){
			currentGuess[i] = guess.get(i).ordinal();
			guessRows[currentGuessRow][i].setIcon(new javax.swing.ImageIcon("icons/"+currentGuess[i]+".png"));	
		}
		
		currentGuessRow -= 1;
		
		guessState = false;
		feedbackPanel.setVisible(true);
		guessPanel.setVisible(false);
		done.addActionListener(this);
		clear.addActionListener(this);
	}
	
	public void askForComputerGuess(){
		game.makeGuess(null);
		done.removeActionListener(this);
		clear.removeActionListener(this);
	}

	public void setCodebreakerComputer(){
		computer = true;
		if(guessState){
			askForComputerGuess();
			for(int i = 0; i < 6; i++){
				guessPegs[i].removeActionListener(this);
			}
		}
		guessState = false;
		undo.removeActionListener(this);
	}
}
