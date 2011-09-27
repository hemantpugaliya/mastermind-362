import java.awt.*;
import javax.swing.*;

public class RowsView {
	
	private JPanel board;
	private JPanel rows;
	private JPanel solution;
	
	private JButton[][] guessRows;
	private JButton[][] feedbackRows;
	private JButton[] solutionSlot;
	private JButton eye;

	public RowsView(){
		board = new JPanel(new BorderLayout());
		rows = new JPanel(new GridLayout(10,5));
		solution = new JPanel(new GridLayout(1, 5));
		guessRows = new JButton[10][];
		feedbackRows = new JButton[10][];
		solutionSlot = new JButton[4];
		eye = new JButton();
	}
	
	public void create(){
		createSolution();
		createRows();
		board.add(solution, BorderLayout.NORTH);
		board.add(rows, BorderLayout.CENTER);	
	}
	
	public JPanel getBoard(){
		return board;
	}
	
	public void createSolution(){
		
		for(int i = 0; i < 5; i++){
			if(i == 0){
				eye.setIcon(new javax.swing.ImageIcon("icons/eye.png"));  
				eye.setBorder(null);  
				eye.setBorderPainted(false);  
				eye.setContentAreaFilled(false);
				eye.setDebugGraphicsOptions(javax.swing.DebugGraphics.BUFFERED_OPTION);  
				eye.setDoubleBuffered(true);  
				eye.setFocusPainted(false); 
				solution.add(eye);
			}
			else{
				int p = i-1;
				solutionSlot[p] = new JButton();
				solutionSlot[p].setIcon(new javax.swing.ImageIcon("icons/gray3.png"));  
				solutionSlot[p].setBorder(null);  
				solutionSlot[p].setBorderPainted(false);  
		        solutionSlot[p].setContentAreaFilled(false);  
		        solutionSlot[p].setDebugGraphicsOptions(javax.swing.DebugGraphics.BUFFERED_OPTION);  
		        solutionSlot[p].setDoubleBuffered(true);  
		        solutionSlot[p].setFocusPainted(false); 
		        solution.add(solutionSlot[p]);
			}
		}	
		solution.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
	}
	
	public void createRows(){	
		for(int i = 0; i < 10; i++){
			JButton[] guessSlot = new JButton[4];
			for(int j = 0; j < 5; j++){
				if(j == 0){
					createFeedback(i);
				}
				else{
					int p = j-1;
					guessSlot[p] = new JButton();
					guessSlot[p].setIcon(new javax.swing.ImageIcon("icons/gray.png"));  
			        guessSlot[p].setBorder(null);  
			        guessSlot[p].setBorderPainted(false);  
			        guessSlot[p].setContentAreaFilled(false);  
			        guessSlot[p].setDebugGraphicsOptions(javax.swing.DebugGraphics.BUFFERED_OPTION);  
			        guessSlot[p].setDoubleBuffered(true);  
			        guessSlot[p].setFocusPainted(false); 			        
			        rows.add(guessSlot[p]);
				}
				guessRows[i] = guessSlot;
			}
		}
	}
	
	
	public void createFeedback(int row){
		JPanel feedbackPanel = new JPanel(new GridLayout(2, 2));
		JButton[] feedbackSlot = new JButton[4];
		
		for(int i = 0; i < 4; i++){
			feedbackSlot[i] = new JButton();
			feedbackSlot[i].setIcon(new javax.swing.ImageIcon("icons/gray2.png"));  
	        feedbackSlot[i].setBorder(null);  
	        feedbackSlot[i].setBorderPainted(false);  
	        feedbackSlot[i].setContentAreaFilled(false);  
	        feedbackSlot[i].setDebugGraphicsOptions(javax.swing.DebugGraphics.BUFFERED_OPTION);  
	        feedbackSlot[i].setDoubleBuffered(true);  
	        feedbackSlot[i].setFocusPainted(false); 
	        feedbackPanel.add(feedbackSlot[i]);
		}
		
		feedbackRows[row] = feedbackSlot;
		
		feedbackPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		rows.add(feedbackPanel);
	}

	public JButton[][] getGuessRows(){
		return guessRows;
	}
	
	public JButton[][] getFeedbackRows(){
		return feedbackRows;
	}
	
	public JButton[] getSolution(){
		return solutionSlot;
	}
	
	public JButton getEye(){
		return eye;
	}
}



