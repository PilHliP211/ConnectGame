package GameElements;

import java.util.Arrays;

/**
 * @author Phillip Byram
 * The board Connect is played on
 *
 */
public class Board {

	private Piece[][] spaces;
	private int width = 7;
	private int height = 6;
	private int winCondition = 4;
	/**
	 * Creates a default Connect 4 board
	 */
	public Board() {
		spaces = new Piece[width][height];
		initBoard();
	}

	/**
	 * Creates a Connect 4 board with a width and a height
	 * @param width the width of the board
	 * @param height the height of the board
	 */
	public Board(int width, int height) {
		spaces = new Piece[width][height];
		this.width = width;
		this.height = height;
		initBoard();
	}
	
	/**
	 * Creates a Connect N board with a width and a height.
	 * @param width the width of the board
	 * @param height the height of the board
	 * @param winCondition
	 */
	public Board(int width, int height, int winCondition) {
		spaces = new Piece[width][height];
		this.width = width;
		this.height = height;
		this.winCondition = winCondition;
		initBoard();
	}
	
	/**
	 * Creates a Connect N board.
	 * @param winCondition
	 */
	public Board(int winCondition) {
		spaces = new Piece[width][height];
		this.winCondition = winCondition;
		initBoard();
	}
	
	/**
	 * Initializes the board. Can be used to empty the board as well.
	 */
	public void initBoard() {
		for(int h=0;h<height;h++) {
			for(int w=0;w<width;w++)
				spaces[w][h] = Piece.NONE;
		}
	}
	
	/**
	 * Prints the board to System.Out
	 */
	public void showBoard()
	{
		StringBuilder output = new StringBuilder(getHorizontalBorder());
		for(int h=0;h<height;h++) {
			for(int w=0;w<width;w++)
				output.append("|"+spaces[w][h].getCharacter());
			output.append("|\n");
		}
		output.append(getHorizontalBorder());
		System.out.println(output);
				
	}
	
	/**
	 * Drops a piece down a column of the Connect board.
	 * @param p the type of Piece to place (cannot be Piece.NONE)
	 * @param column the 0 based index of the column to drop the piece (cannot be >= board width)
	 */
	public void placePiece(Piece p, int column)
	{
		if(p == Piece.NONE)return;
		if(column<0 || column>=width)return;
		if(spaces[column][height-1]==Piece.NONE)
			spaces[column][height-1] = p;
		else
		for(int h=height-1;h>=1;h--) {
			if(spaces[column][h-1] == Piece.NONE && spaces[column][h] != Piece.NONE){
				spaces[column][h-1] = p;
				return;
			}
		}
	}
	
	private Piece didWin()
	{
		for(int w=0;w<width;w++)
			for(int h=0;h<height;h++){
				if(checkVertical(Piece.BLACK,spaces[w],h,winCondition))
					return Piece.BLACK;
				if(checkVertical(Piece.RED,spaces[w],h,winCondition))
					return Piece.RED;
			}
		return Piece.NONE;
	}
	
	private boolean checkVertical(Piece p, Piece[] col, int start, int needToWin){
		
		if(col.length>0 && col[start] == p)
			if(needToWin-1 == 0 || checkVertical(p,Arrays.copyOfRange(col,start+1,col.length),0,needToWin-1))
				return true;
		return false;
	}
	/**
	 * Used to create the top and bottom bounds of the board when showing the board
	 * @return the top or bottom border of the board
	 */
	private String getHorizontalBorder() {
		String out = "";
		int i = 0;
		while(width>i) {
			out+="--";
			i++;
		}
		out+= "-\n";
		return out;
	}
	
	/**
	 * Gets the 2D piece array of spaces in the board
	 * @return the spaces in the board
	 */
	public Piece[][] getSpaces()
	{
		return spaces;
	}

	/**
	 * Gets the width of the board
	 * @return the width of the board
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * Gets the height of the board
	 * @return the height of the board
	 */
	public int getHeight()
	{
		return height;
	}
	
	/**
	 * Gets the number needed to connect to win
	 * @return the number needed to connect to win
	 */
	public int getWinCondition()
	{
		return winCondition;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Board B = new Board();
		

		B.placePiece(Piece.RED, 3);
		B.placePiece(Piece.RED, 3);
		B.placePiece(Piece.BLACK, 3);
		B.placePiece(Piece.BLACK, 3);
		B.placePiece(Piece.BLACK, 3);
		B.placePiece(Piece.RED, 3);
		B.placePiece(Piece.BLACK, 3);

		B.placePiece(Piece.BLACK, 4);
		B.placePiece(Piece.BLACK, 4);
		B.placePiece(Piece.BLACK, 4);
		
		B.placePiece(Piece.RED, 2);
		B.placePiece(Piece.RED, 2);
		B.placePiece(Piece.RED, 2);
		B.placePiece(Piece.RED, 2);
		B.placePiece(Piece.RED, 2);
		
		Piece winner = B.didWin();
		if(Piece.NONE != winner)System.out.println( winner.prettyName() + " Wins!");
		B.showBoard();

	}
	private enum Piece {
		NONE(' '),
		RED('X'),
		BLACK('O');
		
		char character = ' ';
		
		Piece(char character)
		{
			this.character = character;
		}
		
		char getCharacter(){
			return character;
		}
		String prettyName()
		{
			return this.toString().substring(0, 1) + this.toString().toLowerCase().substring(1);
		}
	}


}
