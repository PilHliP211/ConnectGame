package GameElements;


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
		this.winCondition = winCondition;
		initBoard();
	}
	
	public void initBoard() {
		for(int h=0;h<height;h++) {
			for(int w=0;w<width;w++)
				spaces[w][h] = Piece.NONE;
		}
	}
	
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
	
	public void placePiece(Piece p, int column)
	{
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
		// TODO Auto-generated method stub
		Board B = new Board();
		B.placePiece(Piece.BLACK, 3);
		B.placePiece(Piece.RED, 3);
		B.placePiece(Piece.BLACK, 3);
		B.placePiece(Piece.RED, 3);
		B.placePiece(Piece.BLACK, 3);
		B.placePiece(Piece.RED, 3);
		B.placePiece(Piece.BLACK, 0);
		B.placePiece(Piece.RED, 0);
		B.placePiece(Piece.BLACK, 0);
		B.placePiece(Piece.RED, 0);
		B.placePiece(Piece.BLACK, 0);
		B.placePiece(Piece.RED, 0);

		B.placePiece(Piece.BLACK, 1);
		B.placePiece(Piece.RED, 1);
		B.placePiece(Piece.BLACK, 1);
		B.placePiece(Piece.RED, 1);
		B.placePiece(Piece.BLACK, 1);
		B.placePiece(Piece.RED, 1);
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
	}


}
