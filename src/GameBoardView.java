import java.awt.*;

import javax.swing.*;

public class GameBoardView extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JLabel gameName;
	private JPanel rows;
	private JPanel pegs;
	private JPanel buttons;
	private JPanel pegsButtons;
	
	private JButton undo = new JButton("Undo");
	private JButton done = new JButton("Done");
	private JButton clear = new JButton("Clear");
	
	private JMenuItem newGame = new JMenuItem("New Game");
	private JMenuItem exit = new JMenuItem("Exit");
	private JRadioButtonMenuItem[] player = new JRadioButtonMenuItem[3];
	private JCheckBoxMenuItem log = new JCheckBoxMenuItem("Logging");
	
	public BoardController controller;
	public MenuListener menuListener;

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
		menuListener = new MenuListener(newGame, player, log);
		
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void createMenus(){
		JMenuBar bar = new JMenuBar();
		
		JMenu file = new JMenu("File");
		bar.add(file);
		file.add(newGame);
		file.add(exit);
		
		JMenu codebreakerMenu = new JMenu("Codebreaker");		
		JMenu options = new JMenu("Options");
		
		player[0] = new JRadioButtonMenuItem("Human", true);
		player[1] = new JRadioButtonMenuItem("Random");
		player[2] = new JRadioButtonMenuItem("Smart");
		
		codebreakerMenu.add(player[0]);
		codebreakerMenu.add(player[1]);
		codebreakerMenu.add(player[2]);
		options.add(codebreakerMenu);
		options.add(log);
		
		bar.add(options);
		
		setJMenuBar(bar);
	}
	
	public BoardController getController(){
		return controller;
	}
	
}
