import java.awt.*;

import javax.swing.*;

public class GameBoardView extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public JLabel gameName;
	public JPanel rows;
	public JPanel pegs;
	public JPanel buttons;
	public JPanel pegsButtons;
	
	public JButton undo = new JButton("Undo");
	public JButton done = new JButton("Done");
	public JButton clear = new JButton("Clear");
	
	public BoardController controller;

	public GameBoardView(){
		gameName = new JLabel("Mastermind");
	}
	
	public void create(){
		setSize(250,575);	
		setTitle("MASTERMIND");
		setLayout(new BorderLayout());
		
		add(gameName, BorderLayout.NORTH);
		gameName.setHorizontalAlignment(SwingConstants.CENTER);
		
		RowsView rowsview = new RowsView();
		rowsview.create();
		rows = rowsview.getBoard();
		rows.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
		
		PegsView pegsview = new PegsView();
		pegsview.createGuessPanel();
		pegsview.createFeedbackPanel();
		
		pegsButtons = new JPanel(new BorderLayout());
		
		pegs = pegsview.getGuessPanel();
		buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttons.add(undo);
		buttons.add(done);
		buttons.add(clear);
		
		pegsButtons.add(pegs, BorderLayout.NORTH);
		pegsButtons.add(buttons, BorderLayout.SOUTH);
		
		add(rows, BorderLayout.CENTER);
		add(pegsButtons, BorderLayout.SOUTH);
		
		createMenus();
		
		controller = new BoardController(rowsview.getGuessRows(), rowsview.getFeedbackRows(),
				pegsview.getPegs(), rowsview.getSolution(), rowsview.getEye());
		
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void createMenus(){
		JMenuBar bar = new JMenuBar();
		
		JMenu file = new JMenu("File");
		bar.add(file);
		JMenuItem newGame = new JMenuItem("New Game");
		file.add(newGame);
		JMenuItem exit = new JMenuItem("Exit");
		file.add(exit);
		
		JMenu options = new JMenu("Options");
		JMenuItem other = new JMenuItem("Other Stuff");
		options.add(other);
		bar.add(options);
		
		setJMenuBar(bar);
	}
	
	public BoardController getController(){
		return controller;
	}
	
}
