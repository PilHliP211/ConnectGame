package gameElements;

import java.util.Arrays;

/**
 * @author Phillip Byram
 * The board Connect is played on. 
 * This Board has equal width and height
 *
 */
public class QuadraticBoard implements Board{

	private Piece[][] spaces;
	private int dimension = 7;
	private int winCondition = 4;
	/**
	 * Creates a default Connect 4 board
	 */
	public QuadraticBoard() {
		spaces = new Piece[dimension][dimension];
		initBoard();
	}

	/**
	 * Creates a Connect 4 board with a width and a height
	 * @param dimension the width and height of the board
	 */
	public QuadraticBoard(int dimension) {
		spaces = new Piece[dimension][dimension];
		this.dimension = dimension;
		initBoard();
	}
	
	/**
	 * Creates a Connect N board with a width and a height.
	 * @param width the width and height of the board
	 * @param winCondition number needed to connect to win
	 */
	public QuadraticBoard(int dimension, int winCondition) {
		spaces = new Piece[dimension][dimension];
		this.dimension = dimension;
		this.winCondition = winCondition;
		initBoard();
	}
	
	/**
	 * Initializes the board. Can be used to empty the board as well.
	 */
	public void initBoard() {
		for(int h=0;h<dimension;h++) {
			for(int w=0;w<dimension;w++)
				spaces[w][h] = Piece.NONE;
		}
	}
	
	/**
	 * Prints the board to System.Out
	 */
	public void showBoard()
	{
		StringBuilder output = new StringBuilder(getHorizontalBorder(" "));
		for(int h=0;h<dimension;h++) {
			output.append(String.valueOf(h));
			for(int w=0;w<dimension;w++)
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
	 * @return true if the piece was placed on the board, false otherwise
	 * @throws ColumnFullException if the index of the piece to place is a full column
	 */
	public boolean placePiece(Piece p, int column) throws ColumnFullException
	{
		if(p == Piece.NONE)return false;
		if(column<0 || column>=dimension)return false;
		if(spaces[column][dimension-1]==Piece.NONE){
			spaces[column][dimension-1] = p;
			return true;
		}
		else
		for(int h=dimension-1;h>=1;h--) {
			if(spaces[column][h-1] == Piece.NONE && spaces[column][h] != Piece.NONE){
				spaces[column][h-1] = p;
				return true;
			}
		}
		throw new ColumnFullException();
	}
	
	/**
	 * Checks if a Player has won the game. 
	 * @return The piece that won the game or the empty piece if there are no winners.
	 */
	public Piece didWin()
	{
		for(int w=0;w<dimension;w++)
			for(int h=0;h<dimension;h++){
				if(checkVertical(Piece.BLACK,spaces[w],h,winCondition))
					return Piece.BLACK;
				if(checkVertical(Piece.RED,spaces[w],h,winCondition))
					return Piece.RED;
			}
		for(int h=0;h<dimension;h++)
			for(int w=0;w<dimension;w++){
				if(checkHorizontal(Piece.BLACK,getRowOf2dArray(spaces,h),w,winCondition))
					return Piece.BLACK;
				if(checkHorizontal(Piece.RED,getRowOf2dArray(spaces,h),w,winCondition))
					return Piece.RED;
		}
		for(int d=0;d<dimension+dimension-1;d++)
			for(int l=0;l<getDiagLength(dimension,d);l++){
				if(checkLeftDiagonal(Piece.BLACK,getDiagOf2dArray(spaces,d,true),l,winCondition))
					return Piece.BLACK;
				if(checkLeftDiagonal(Piece.RED,getDiagOf2dArray(spaces,d,true),l,winCondition))
					return Piece.RED;
		}
		for(int d=0;d<dimension+dimension-1;d++)
			for(int l=0;l<getDiagLength(dimension,d);l++){
				if(checkRightDiagonal(Piece.BLACK,getDiagOf2dArray(spaces,d,false),l,winCondition))
					return Piece.BLACK;
				if(checkRightDiagonal(Piece.RED,getDiagOf2dArray(spaces,d,false),l,winCondition))
					return Piece.RED;
		}
		
		return Piece.NONE;
	}
	
	/**
	 * Checks if the board has any available spaces left
	 * @return true if the board is full; false otherwise
	 */
	public boolean isFull()
	{
		for(int w=0;w<dimension;w++)
			for(int h=0;h<dimension;h++)
				if(spaces[w][h] == Piece.NONE)
					return false;
		return true;
	}

	/**
	 * Copies current board to a new board object that is returned.  Overrides Board class' 
	 * copy function.  
	 * 
	 * @return Board object which is copy of current board. 
	 */
	@Override
	public Board copy() {
		QuadraticBoard newBoard;
		newBoard = new QuadraticBoard(this.dimension,this.winCondition);
		Piece[][] spaces = new Piece[this.getSpaces().length][this.getSpaces()[0].length];
		for(int i=0;i<this.getSpaces().length;i++)
		{
			Piece[] p =Arrays.copyOf(this.getSpaces()[i], this.getSpaces()[i].length);
			spaces[i] = p;
		}
		newBoard.setSpaces(spaces);
		return (Board) newBoard;
	}
	
	/**
	 * Checks column for winning conditions.  
	 * 
	 * @return boolean value true if winning condition met.
	 * @param Piece enumerator type to check for
	 * @param array of pieces to check for winning conditions
	 * @param integer value of where to start checking in array
	 * @param integer value of how many pieces are required for a win
	 */
	private boolean checkVertical(Piece p, Piece[] col, int start, int needToWin){
		
		if(col.length>0 && col[start] == p)
			if(needToWin-1 == 0 || checkVertical(p,Arrays.copyOfRange(col,start+1,col.length),0,needToWin-1))
				return true;
		return false;
	}

	/**
	 * Checks row for winning conditions.  
	 * 
	 * @return boolean value true if winning condition met.
	 * @param Piece enumerator type to check for
	 * @param array of pieces to check for winning conditions
	 * @param integer value of where to start checking in array
	 * @param integer value of how many pieces are required for a win
	 */
	private boolean checkHorizontal(Piece p, Piece[] row, int start, int needToWin){
		
		if(row.length>0 && row[start] == p)
			if(needToWin-1 == 0 || checkHorizontal(p,Arrays.copyOfRange(row,start+1,row.length),0,needToWin-1))
				return true;
		return false;
	}

	/**
	 * Checks left diagonal for winning conditions.  
	 * 
	 * @return boolean value true if winning condition met.
	 * @param Piece enumerator type to check for
	 * @param array of pieces to check for winning conditions
	 * @param integer value of where to start checking in array
	 * @param integer value of how many pieces are required for a win
	 */
	private boolean checkLeftDiagonal(Piece p, Piece[] diag, int start, int needToWin){

		if(diag.length>0 && diag[start] == p)
			if(needToWin-1 == 0 || checkLeftDiagonal(p,Arrays.copyOfRange(diag,start+1,diag.length),0,needToWin-1))
				return true;
		return false;
	}

	/**
	 * Checks right diagonal for winning conditions.  
	 * 
	 * @return boolean value true if winning condition met.
	 * @param Piece enumerator type to check for
	 * @param array of pieces to check for winning conditions
	 * @param integer value of where to start checking in array
	 * @param integer value of how many pieces are required for a win
	 */
	private boolean checkRightDiagonal(Piece p, Piece[] diag, int start, int needToWin){

		if(diag.length>0 && diag[start] == p)
			if(needToWin-1 == 0 || checkRightDiagonal(p,Arrays.copyOfRange(diag,start+1,diag.length),0,needToWin-1))
				return true;
		return false;
	}
	
	/**
	 * returns a row as an array from two dimentional array, so that it
	 * may be checked for winning conditions.  
	 * 
	 * @return Piece array
	 * @param Piece 2D array
	 * @param integer value of row number to return
	 */
	private Piece[] getRowOf2dArray(Piece[][] array,int row){
		
		Piece[] rowArray = new Piece[array.length];
		for(int col = 0; col < array.length; col++)
		{
		    rowArray[col] = array[col][row];
		}
		return rowArray;
	}
	
	/**
	 * given a board of 6 height and 7 width there would be 12 (6+7-1) diagonal indecies
	 * 
	 * for top right to bottom left diagonals (called left diagonals in this class - isLeft = true):
	 * the 0th index would be top left and the 11th index would be bottom right.
	 * 
	 * for top left to bottom right diagonals (called right diagonals in this class - isLeft = false):
	 * the 0th index would be top right and the 11th index would be bottom left.
	 * 
	 * the most extreme indecies would only have a one cell diagonal. 
	 * the second most extreme, 2, and so on.
	 * 
	 * @return Piece array representing diagonal of 2D array
	 * @param Piece 2D array to derive diagonal from
	 * @param integer indicating position of diagonal in 2D array
	 * @param boolean value indicating whether to return left or right diagonal
	 */
	private Piece[] getDiagOf2dArray(Piece[][] array, int diagonal, boolean isLeft)
	{
		Piece[] diagArray = new Piece[getDiagLength(array.length,diagonal)];
		
		for(int i = 0; i < diagArray.length; i++)
		{
			if(isLeft)
				diagArray[i] = getArrayItemFromDiagIdxLeft(array,diagonal,i);
			else
				diagArray[i] = getArrayItemFromDiagIdxRight(array,diagonal,i);
		}
		return diagArray;
	}
	
	/**
	 * get the length of a diagonal in a board with a width and a height
	 * 
	 * @return integer value indicating length of diagonal
	 * @param integer value indicating dimensions of board
	 * @param integer value indicating diagonal position in board
	 */
	private static int getDiagLength(int dimension, int diagonal){
		if(diagonal<dimension)
			return diagonal+1;
		else
			return dimension*2 - (diagonal+1);
	}
	
	
	/**
	 * based on a given array's diagonal index return the item at i of that left diagonal
	 * 
	 * @return Piece enumerator type 
	 * @param Piece 2D array representing the current board configuration
	 * @param integer value indicating position of diagonal in 2D array
	 * @param integer value indicating location of return piece in diagonal array
	 */
	private static Piece getArrayItemFromDiagIdxLeft(Piece[][] array, int diagonal, int i){
			if(diagonal<array[0].length)
				return array[i][diagonal-i];
			else
				return array[(diagonal-array.length)+i+1][array[0].length-1-i];
	}
	
	/**
	 * based on a given array's diagonal index return the item at i of that right diagonal
	 * 
 	 * @return Piece enumerator type 
	 * @param Piece 2D array representing the current board configuration
	 * @param integer value indicating position of diagonal in 2D array
	 * @param integer value indicating location of return piece in diagonal array
	 */
	private static Piece getArrayItemFromDiagIdxRight(Piece[][] array, int diagonal, int i){
		if(diagonal<array[0].length)
			return array[array.length-1 - i][diagonal - i];
		else
			//this has a bug when width = height on diagonal = height
			return array[(2*array.length-1)-diagonal-i-1][array[0].length-i-1];
		
	}
	
	/**
	 * Used to create the top and bottom bounds of the board when showing the board
	 * @param padding precedes the border
	 * @return the top or bottom border of the board
	 */
	private String getHorizontalBorder(String padding) {
		String out = padding;
		int i = 0;
		while(dimension>i) {
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
		while(dimension>i) {
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
	 * Sets the 2D piece array of spaces in the board
	 * @param spaces the spaces in the board
	 */
	public void setSpaces(Piece[][] spaces)
	{
		this.spaces = spaces;
	}

	/**
	 * Sets value at index h, w in 2D array of spaces
	 * 
	 * @param Piece enumerator type to place on board
	 * @param integer value representing vertical position on board
	 * @param integer value representing horizontal position on board
	 */
	public void setPieceAtSpace(Piece val, int h, int w)
	{
		spaces[h][w] = val;
	}


	/**
	 * Gets the dimension of the board
	 * @return the dimension of the board
	 */
	public int getDimension()
	{
		return dimension;
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
		
		Board B = new QuadraticBoard(8,4);
		B.showBoard();
		//B.didWin();
		/*
		B.placePiece(Piece.BLACK, 0);
		B.placePiece(Piece.BLACK, 0);
		
		B.placePiece(Piece.RED, 1);
		B.placePiece(Piece.RED, 1);
		
		B.placePiece(Piece.RED, 2);
		B.placePiece(Piece.BLACK, 2);
		B.placePiece(Piece.BLACK, 2);
		
		B.placePiece(Piece.BLACK, 3);
		B.placePiece(Piece.BLACK, 3);
		B.placePiece(Piece.RED, 3);
		B.placePiece(Piece.BLACK, 3);

		B.placePiece(Piece.BLACK, 4);
		B.placePiece(Piece.RED, 4);
		B.placePiece(Piece.RED, 4);
		B.placePiece(Piece.RED, 4);
		*//*
		B.placePiece(Piece.RED, 0);
		B.placePiece(Piece.BLACK, 0);
		
		B.placePiece(Piece.BLACK, 1);
		B.placePiece(Piece.RED, 1);
		
		B.placePiece(Piece.RED, 2);
		B.placePiece(Piece.RED, 2);
		B.placePiece(Piece.BLACK, 2);
		
		B.placePiece(Piece.BLACK, 3);
		B.placePiece(Piece.RED, 3);
		B.placePiece(Piece.RED, 3);
		B.placePiece(Piece.BLACK, 3);
		B.placePiece(Piece.RED, 4);
		B.placePiece(Piece.BLACK, 4);
		B.placePiece(Piece.BLACK, 4);
		B.placePiece(Piece.RED, 4);

		B.placePiece(Piece.RED, 5);
		B.placePiece(Piece.RED, 6);
		B.placePiece(Piece.RED, 5);
		B.placePiece(Piece.BLACK, 5);
		B.placePiece(Piece.RED, 6);
		B.placePiece(Piece.BLACK, 6);
		B.placePiece(Piece.BLACK, 4);
		
		B.placePiece(Piece.RED, 1);
		B.placePiece(Piece.RED, 2);
		B.placePiece(Piece.RED, 3);
		B.placePiece(Piece.RED, 4);
		*//*
		Board B = new Board();
		B.placePiece(Piece.BLACK, 1);
		B.placePiece(Piece.BLACK, 2);
		B.placePiece(Piece.BLACK, 3);
		B.placePiece(Piece.BLACK, 4);
		*/
		
		/*
		B.showBoard();
		Piece winner = B.didWin();
		if(Piece.NONE != winner)System.out.println( winner.prettyName() + " Wins!");
		*/
		System.out.println((QuadraticBoard.getDiagLength(7,0)==1)?"Pass":"Fail");
		System.out.println((QuadraticBoard.getDiagLength(7,4)==5)?"Pass":"Fail");
		System.out.println((QuadraticBoard.getDiagLength(7,5)==6)?"Pass":"Fail");
		System.out.println((QuadraticBoard.getDiagLength(7,6)==7)?"Pass":"Fail");
		System.out.println((QuadraticBoard.getDiagLength(7,11)==2)?"Pass":"Fail");
		System.out.println((QuadraticBoard.getDiagLength(8,7)==8)?"Pass":"Fail");
		System.out.println((QuadraticBoard.getDiagLength(8,8)==7)?"Pass":"Fail");
		System.out.println((QuadraticBoard.getDiagLength(8,14)==1)?"Pass":"Fail");
		System.out.println((QuadraticBoard.getDiagLength(8,0)==1)?"Pass":"Fail");
		System.out.println((QuadraticBoard.getDiagLength(8,11)==4)?"Pass":"Fail");
		System.out.println((QuadraticBoard.getDiagLength(6,3)==4)?"Pass":"Fail");
		System.out.println((QuadraticBoard.getDiagLength(6,9)==2)?"Pass":"Fail");
		System.out.println((QuadraticBoard.getDiagLength(6,0)==1)?"Pass":"Fail");
		System.out.println((QuadraticBoard.getDiagLength(6,7)==4)?"Pass":"Fail");
		System.out.println((QuadraticBoard.getDiagLength(5,0)==1)?"Pass":"Fail");
		System.out.println((QuadraticBoard.getDiagLength(5,5)==4)?"Pass":"Fail");
		System.out.println((QuadraticBoard.getDiagLength(5,4)==5)?"Pass":"Fail");
		System.out.println((QuadraticBoard.getDiagLength(5,7)==2)?"Pass":"Fail");
		System.out.println((QuadraticBoard.getDiagLength(10,10)==9)?"Pass":"Fail");
		System.out.println((QuadraticBoard.getDiagLength(10,11)==8)?"Pass":"Fail");
	}
}
