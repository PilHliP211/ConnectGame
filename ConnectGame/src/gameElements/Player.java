package gameElements;

import java.util.Scanner;

import gameElements.Board.Piece;

public class Player implements Comparable<Player>{

	Board.Piece piece = Piece.NONE;
	Board board = null;
	Scanner inputDevice;
	boolean isMyTurn = false;
	
	public Player(Piece p, Board b)
	{
		piece = p;
		board = b;
		inputDevice = new Scanner(System.in);
	}
	/**
	 * place a piece in a column on the board
	 * @param col
	 */
	public void play(int col)
	{
		board.placePiece(piece, col);
		isMyTurn = false;
	}
	
	public int nextMove(){
		System.out.print(piece.prettyName() + "'s turn: ");
		return Integer.valueOf(inputDevice.nextLine());
	}
	
	@Override
	public int compareTo(Player p) {
		return piece.compareTo(p.piece);
	}
}
