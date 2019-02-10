package gameElements;

import java.util.Scanner;

import gameElements.Board.ColumnFullException;

/**
 * @author Phillip Byram
 * A Player playing Connect
 *
 */
public class Player{

	private Piece piece = Piece.NONE;
	private Board board = null;
	private Scanner inputDevice;
	private boolean turn = false;
	
	public Player(Piece p, Board b)
	{
		piece = p;
		board = b;
		inputDevice = new Scanner(System.in);
	}
	
	public Player(Piece p, Board b, Scanner in)
	{
		piece = p;
		board = b;
		inputDevice = in;
	}
	
	/**
	 * place a piece in a column on the board
	 * @param col the column to play the pi9ece down
	 * @return true if the player has played a piece, false otherwise,
	 * @throws ColumnFullException if the index of the column the player is trying to place in is full
	 */
	private boolean play(int col) throws ColumnFullException
	{
		if(board.placePiece(piece, col))
			turn = false;
		else
			return false;
		return true;
	}
	/**
	 * gets the player's desired next move and returns the success of playing the player's piece.
	 * @param noPrompt true if input should be prompted via output, false for no output indicating needed input
	 * @return the success of the attempted move
	 * @throws NumberFormatException if the input is not a valid integer
	 * @throws ColumnFullException if the index of the column the player is trying to place in is full
	 */
	public boolean nextMove(boolean noPrompt) throws NumberFormatException, ColumnFullException{
		if(!noPrompt)System.out.print(piece.prettyName() + "'s turn: ");
		return play(Integer.valueOf(inputDevice.nextLine()));
	}

	public boolean isTurn() {
		return turn;
	}
	public void toggleTurn() {
		turn = !turn;
	}
}
