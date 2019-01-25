package gameElements;

import java.util.Scanner;

import gameElements.Board.ColumnFullException;
import gameElements.Board.Piece;

public class Player {

	private Board.Piece piece = Piece.NONE;
	private Board board = null;
	private Scanner inputDevice;
	private boolean turn = false;
	
	public Player(Piece p, Board b)
	{
		piece = p;
		board = b;
		inputDevice = new Scanner(System.in);
	}
	
	/**
	 * place a piece in a column on the board
	 * @param col
	 * @return true if the player has played a piece, false otherwise,
	 * @throws ColumnFullException if the index of the column the player is trying to place in is full
	 */
	public boolean play(int col) throws ColumnFullException
	{
		if(board.placePiece(piece, col))
			turn = false;
		else
			return false;
		return true;
	}
	
	public int nextMove() throws NumberFormatException{
		System.out.print(piece.prettyName() + "'s turn: ");
		return Integer.valueOf(inputDevice.nextLine());
	}
	public boolean isTurn() {
		return turn;
	}
	public void toggleTurn() {
		turn = !turn;
	}
}
