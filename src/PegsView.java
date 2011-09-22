import java.awt.*;

import javax.swing.*;

public class PegsView {
	
	public JPanel guessPanel;
	public JPanel pegs1;
	public JPanel pegs2;
	public JPanel feedbackPanel;
	public JPanel buttons;
	
	public JToggleButton[] pegs;
	public JButton undo;
	
	public PegsView(){
		guessPanel = new JPanel(new BorderLayout());
		feedbackPanel = new JPanel(new BorderLayout());
		pegs1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pegs2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pegs = new JToggleButton[6];
		undo = new JButton("Undo");
		buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
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
		buttons.add(undo);
		guessPanel.add(buttons, "South");		
	}
	
	public JPanel getGuessPanel(){
		return guessPanel;
	}
	
	public void createFeedback(){
		
	}
	
	public JToggleButton[] getPegs(){
		return pegs;
	}
}
