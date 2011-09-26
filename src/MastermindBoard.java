import java.util.ArrayList;
import java.util.Stack;

/*
 * MastermindBoard.java
 * 
 * Version:
 *     $Id$
 *
 * Revisions:
 *      $Log$
 */

/**
 * @author ajg9132
 *
 */
public class MastermindBoard {
	
	private ArrayList<PegRow> rows;
	private Solution solution;
	private Stack<PegRowMemento> mementos;
	
	public MastermindBoard() {
		// todo: setup board
		
		this.mementos = new Stack<PegRowMemento>();
	}

}
