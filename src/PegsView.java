import java.awt.*;

import javax.swing.*;

public class PegsView {
	
	private JPanel guessPanel;
	private JPanel pegs1;
	private JPanel pegs2;
	private JPanel pegs3;
	private JPanel pegs4;
	private JPanel feedbackPanel;

	private JToggleButton[] pegs;
	
	public PegsView(){
		guessPanel = new JPanel(new BorderLayout());
		feedbackPanel = new JPanel(new BorderLayout());
		pegs1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pegs2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pegs3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pegs4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pegs = new JToggleButton[8];
	}
	
	public void createGuessPanel(){
		for(int i = 0; i < 6; i++){
			ImageIcon icon = new ImageIcon("icons/"+i+".png");	
			JToggleButton peg = new JToggleButton(icon);
			if(i < 3)
				pegs1.add(peg);
			else
				pegs2.add(peg);
			
			pegs[i] = peg;
		}
		
		guessPanel.add(pegs1, "North");
		guessPanel.add(pegs2, "Center");	
	}
	
	public JPanel getGuessPanel(){
		return guessPanel;
	}
	
	public void createFeedbackPanel(){
		for(int i = 4; i < 6; i++){
			ImageIcon icon = new ImageIcon("icons/"+i+".png");	
			JToggleButton peg = new JToggleButton(icon);
			if(i == 4)
				pegs3.add(peg);
			else
				pegs4.add(peg);
			
			pegs[i+2] = peg;
		}
		
		feedbackPanel.add(pegs3, "North");
		feedbackPanel.add(pegs4, "Center");	
	}
	
	public JPanel getFeedbackPanel(){
		return feedbackPanel;
	}
	
	public JToggleButton[] getPegs(){
		return pegs;
	}
}
