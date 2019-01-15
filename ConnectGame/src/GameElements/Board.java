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
	 * @param winCondition number needed to connect to win
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
	 * @param winCondition number needed to connect to win
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
		StringBuilder output = new StringBuilder(getHorizontalBorder(" "));
		for(int h=0;h<height;h++) {
			output.append(String.valueOf(h));
			for(int w=0;w<width;w++)
				output.append("|"+spaces[w][h].getCharacter());
			output.append("|\n");
		}
		output.append(getHorizontalIndecies());
		output.append(getHorizontalBorder(" "));
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
	
	/**
	 * Checks if a Player has won the game. 
	 * @return The piece that won the game or the empty piece if there are no winners.
	 */
	public Piece didWin()
	{
		for(int w=0;w<width;w++)
			for(int h=0;h<height;h++){
				if(checkVertical(Piece.BLACK,spaces[w],h,winCondition))
					return Piece.BLACK;
				if(checkVertical(Piece.RED,spaces[w],h,winCondition))
					return Piece.RED;
			}
		for(int h=0;h<height;h++)
			for(int w=0;w<width;w++){
				if(checkHorizontal(Piece.BLACK,getRowOf2dArray(spaces,h),w,winCondition))
					return Piece.BLACK;
				if(checkHorizontal(Piece.RED,getRowOf2dArray(spaces,h),w,winCondition))
					return Piece.RED;
		}
		for(int d=0;d<width+height-1;d++)
			for(int l=0;l<getDiagLength(width,height,d);l++){
				if(checkLeftDiagonal(Piece.BLACK,getLeftDiagOf2dArray(spaces,d),l,winCondition))
					return Piece.BLACK;
				if(checkLeftDiagonal(Piece.RED,getLeftDiagOf2dArray(spaces,d),l,winCondition))
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
	private boolean checkHorizontal(Piece p, Piece[] row, int start, int needToWin){
		
		if(row.length>0 && row[start] == p)
			if(needToWin-1 == 0 || checkHorizontal(p,Arrays.copyOfRange(row,start+1,row.length),0,needToWin-1))
				return true;
		return false;
	}
	private boolean checkLeftDiagonal(Piece p, Piece[] diag, int start, int needToWin){

		if(diag.length>0 && diag[start] == p)
			if(needToWin-1 == 0 || checkLeftDiagonal(p,Arrays.copyOfRange(diag,start+1,diag.length),0,needToWin-1))
				return true;
		return false;
	}
	
	private Piece[] getRowOf2dArray(Piece[][] array,int row){
		
		Piece[] rowArray = new Piece[array.length];
		for(int col = 0; col < array.length; col++)
		{
		    rowArray[col] = array[col][row];
		}
		return rowArray;
	}
	
	/*
	 * given a board of 6 height and 7 width there would be 12 (6+7-1) diagonal indecies
	 * the 0th index would be top left and the 11th index would be bottom right.
	 * the most extreme indecies would only have a one cell diagonal. 
	 * the second most extreme, 2, and so on.
	 */
	private Piece[] getLeftDiagOf2dArray(Piece[][] array, int diagonal)
	{
		Piece[] leftDiagArray = new Piece[getDiagLength(array.length,array[0].length,diagonal)];
		for(int i = 0; i < leftDiagArray.length; i++)
		{
		    leftDiagArray[i] = getArrayItemFromDiagIdx(array,diagonal,i);
		}
		return leftDiagArray;
	}
	
	//TODO actual math 
	private int getDiagLength(int width, int height,int diagonal){
		
		if(width== 7 && height == 6)
			switch (diagonal){
			case 0:
			case 11:
				return 1;
			case 1:
			case 10:
				return 2;
			case 2:
			case 9:
				return 3;
			case 3:
			case 8:
				return 4;
			case 4:
			case 7:
				return 5;
			case 5:
			case 6:
				return 6;
			}
			
		return -1;
	}
	
	/*
	 * based on a given array's diagonal index return the item at i of that diagonal
	 */
	private Piece getArrayItemFromDiagIdx(Piece[][] array, int diagonal, int i){
		int len = getDiagLength(array.length,array[0].length,diagonal);
		if(diagonal<array[0].length)
			return array[i][diagonal - i];
		else
			return array[diagonal - array.length+i+2][diagonal-array[0].length+len -i-1];
		
	}
	
	/**
	 * Used to create the top and bottom bounds of the board when showing the board
	 * @param padding precedes the border
	 * @return the top or bottom border of the board
	 */
	private String getHorizontalBorder(String padding) {
		String out = padding;
		int i = 0;
		while(width>i) {
			out+="--";
			i++;
		}
		out+= "-\n";
		return out;
	}
	/**
	 * Used to index the horizontal row
	 * @return the index string for the row
	 */
	private String getHorizontalIndecies() {
		String out = " ";
		int i = 0;
		while(width>i) {
			out+=" "+ String.valueOf(i);
			i++;
		}
		out+= " \n";
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
		B.placePiece(Piece.RED, 0);
		B.placePiece(Piece.BLACK, 0);
		
		B.placePiece(Piece.RED, 1);
		B.placePiece(Piece.BLACK, 1);
		
		B.placePiece(Piece.RED, 2);
		B.placePiece(Piece.BLACK, 2);
		B.placePiece(Piece.BLACK, 2);
		
		B.placePiece(Piece.BLACK, 3);
		B.placePiece(Piece.RED, 3);
		B.placePiece(Piece.RED, 3);
		B.placePiece(Piece.BLACK, 3);
		B.placePiece(Piece.RED, 4);
		B.placePiece(Piece.BLACK, 4);
		B.placePiece(Piece.BLACK, 4);
		B.placePiece(Piece.RED, 4);
		B.placePiece(Piece.RED, 4);
		
		B.placePiece(Piece.RED, 1);
		B.placePiece(Piece.RED, 2);
		B.placePiece(Piece.RED, 3);
		B.placePiece(Piece.BLACK, 4);
		
		B.placePiece(Piece.RED, 5);
		B.placePiece(Piece.RED, 5);
		B.placePiece(Piece.RED, 5);
		B.placePiece(Piece.RED, 6);
		B.placePiece(Piece.RED, 6);
		B.placePiece(Piece.RED, 6);
		B.placePiece(Piece.BLACK, 6);
		B.placePiece(Piece.BLACK, 5);
		B.placePiece(Piece.RED, 5);
		B.placePiece(Piece.BLACK, 6);
		B.placePiece(Piece.BLACK, 6);
		B.placePiece(Piece.BLACK, 2);
		B.placePiece(Piece.RED, 1);
		B.placePiece(Piece.RED, 1);
		B.placePiece(Piece.BLACK, 1);
		B.placePiece(Piece.RED, 0);
		B.placePiece(Piece.RED, 0);
		B.placePiece(Piece.RED, 0);
		B.placePiece(Piece.RED, 0);
		
		
		/*
		Board B = new Board();
		B.placePiece(Piece.BLACK, 1);
		B.placePiece(Piece.BLACK, 2);
		B.placePiece(Piece.BLACK, 3);
		B.placePiece(Piece.BLACK, 4);
		*/
		B.showBoard();
		Piece winner = B.didWin();
		if(Piece.NONE != winner)System.out.println( winner.prettyName() + " Wins!");

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
